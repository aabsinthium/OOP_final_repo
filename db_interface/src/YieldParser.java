import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YieldParser extends Parser{
    private final Map<String, List<String>> data_;

    public YieldParser() {
        data_ = new HashMap<>();
    }

    @Override
    public void read(String company) {
        String filePath = "c:/it/java/final/data/yield/prices/" + company + ".csv";
        String regex = "(?<=\").[^,].[^\"]+.[^,](?=\")|(?<=,)\\d+\\.?\\d+(?=,)";

        try {
            File fl = new File(filePath);
            byte[] fileContent = Files.readAllBytes(fl.toPath());

            String string = new String(fileContent, StandardCharsets.US_ASCII); // компоновка файла в одну строку
            Scanner scanner = new Scanner(string);
            String[] keys = scanner.nextLine().split(","); // сканнер первой строки (названия столбцов)

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
                Matcher matcher = Pattern.compile(regex).matcher(scanner.nextLine());

                while (matcher.find()) { // чтение строки
                    words.add(matcher.group());
                }

                if (words.isEmpty())
                    break;
                for (int i = 0; i < words.size(); i++) // компоновка ключей и списков в карту
                    data_.get(keys[i]).add(words.get(i));
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, List<String>> getParsed() {
        return data_;
    }
}