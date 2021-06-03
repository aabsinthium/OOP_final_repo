import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.read("A.csv");

        Map<String, List<String>> data = parser.getParsed();

        for (String key : data.keySet()){
            System.out.println(key);
        }

        for (String element : data.get("Date")){
            System.out.println(element);
        }
    }
}
