import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QtrToYear {
    public String count(int idx, int rep, Pattern pattern, List<String> data){
        double num = 0;

        for (int j = 0; j < rep; j += 1) {
            try {
                Matcher matcher = pattern.matcher(data.get(idx + j));

                if (matcher.find()) {
                    NumberFormat nf = NumberFormat.getInstance(Locale.US);
                    num += ((Number) nf.parse(matcher.group())).doubleValue();
                } else {
                    return "nan";
                }
            }
            catch (ParseException | IllegalStateException | IndexOutOfBoundsException e) {
                return "nan";
            }
        }

        return Double.toString(num);
    }
}
