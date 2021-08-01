import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserFinancials extends Parser{
    private final Map<String, List<String>> data_; // карта названий столбцов и списков данных по столбцам

    public ParserFinancials() {
        data_ = new HashMap<>();
    }

    @Override
    public void read(String company) {
        String regex1 = "(?<=\\w\\)?|^),(?=\\w)|,\"|\",";
        String regex2 = "\\d{4}-\\d{2}-\\d{2}|(?<=,\").[^\"]+(?=\",)|(?<=,)\\$?-?\\d+\\.?\\d*(?=,|$)|(?<=,)-(?=,|$)";
        String[] paths = new String[4];

        paths[0] = "balance-sheet/";
        paths[1] = "cash-flow-statement/";
        paths[2] = "financial-ratios/";
        paths[3] = "income-statement/";

        for (String folder: paths) { // чтение таблиц
            String filePath = "././data/" + folder + company + ".csv";

            try {
                File fl = new File(filePath);
                byte[] fileContent = Files.readAllBytes(fl.toPath());

                String string = new String(fileContent, StandardCharsets.US_ASCII); // компоновка файла в одну строку
                Scanner scanner = new Scanner(string);
                String[] keys = scanner.nextLine().split(regex1); // сканнер первой строки (названия столбцов)

                for (int i = 0; i < keys.length; i++) { // формирование ключей
                    String key = keys[i].trim();

                    if (key.length() == 0)
                        key = "Date";

                    keys[i] = key;

                    if (!data_.containsKey(key)) { // присваивание ключей карте
                        List<String> lst = new ArrayList<>();
                        data_.put(key, lst);
                    }
                } // чтение первой строки и формирование ключей карты

                List<String> words; // контейнер для данных из всех остальных строк

                while (scanner.hasNext()) { // сканнер остальных строк
                    words = new ArrayList<>();
                    Pattern pattern = Pattern.compile(regex2);
                    Matcher matcher = pattern.matcher(scanner.nextLine());

                    while (matcher.find()) { // чтение строки
                        words.add(matcher.group());
                    }

                    if (words.isEmpty())
                        break;
                    for (int i = 0; i < words.size(); i++){
                        if(i == 0 && !folder.equals(paths[0])) // пропуск столбца с датами в таблицах кроме первой
                            continue;

                        data_.get(keys[i]).add(words.get(i));
                    } // компоновка ключей и списков в карту
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Map<String, List<String>> getParsed() {
        return data_;
    }
}