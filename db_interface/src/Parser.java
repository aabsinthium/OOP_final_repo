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

public class Parser {
    private final Map<String, List<String>> data_;

    public Parser() {
        data_ = new HashMap<>();
    }

    public void read(String fileName) {
        try {
            File fl = new File(fileName);
            byte[] fileContent = Files.readAllBytes(fl.toPath());   

            // здесь кодировку нужно подставить
            String string = new String(fileContent, StandardCharsets.US_ASCII);
            Scanner scanner = new Scanner(string);
            String[] keys = scanner.nextLine().split(",");

            for (int i = 0; i < keys.length; i++) {
                String key = keys [i].trim();

                if (key.length() == 0)
                    key = "Date";

                keys[i] = key;

                if (!data_.containsKey(key)) {
                    List<String> lst = new ArrayList<>();
                    data_.put(key, lst);
                }
            }

            List<String> words;
            String regex = "\\d{4}-\\d{2}-\\d{2}|(?<=,\").[^\"]+(?=\",)|(?<=,)\\$?-?\\d+\\.?\\d*(?=,|$)|(?<=,)-(?=,|$)";

            while (scanner.hasNext()) {
                words = new ArrayList<>();
                Matcher matcher = Pattern.compile(regex).matcher(scanner.nextLine());

                while (matcher.find()) {
                    words.add(matcher.group());
                }

                if (words.isEmpty())
                    break;
                for (int i = 0; i < words.size(); i++)
                    data_.get(keys[i]).add(words.get(i));
            }
        }

        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, List<String>> getParsed() {
        return data_;
    }
}