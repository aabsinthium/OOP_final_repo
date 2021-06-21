import java.util.*;
import java.text.*;
import java.util.regex.*;

public class ColumnPE extends Column {
    private final String regex_;
    private final List<String> column_;
    private final Map<String, List<String>> data_;

    public ColumnPE(Map<String, List<String>> data){
        super(data);
        this.data_ = data;
        this.regex_ = "[^\"$]+";
        this.column_ = new ArrayList<>();
    }

    @Override
    public List calculateValue(){
        List<String> data_price = data_.get("Price");
        List<String> data_eps = data_.get("EPS - Earnings Per Share");

        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        for(int i = 0; i < data_.get("Date").size(); i += 1) {
            String pe;
            Pattern pattern = Pattern.compile(regex_);
            QtrToYear counter = new QtrToYear();

            String price_str = counter.count(i, 1, pattern, data_price);
            String eps_str = counter.count(i, 4, pattern, data_eps);

            try {
                double price = Double.parseDouble(price_str);
                double eps = Double.parseDouble(eps_str);

                pe = String.format("%.2f", (price / eps));
            }
            catch (NumberFormatException e) {
                pe = "nan";
            }

            column_.add(pe);
        }
        return column_;

        /*
        List<String> eps_str = data_.get("EPS - Earnings Per Share");
        List<String> price_str = data_.get("Price");

        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        for(int i = 0; i < data_.get("Date").size(); i += 1){
            double eps = 0;
            boolean b = true;
            Double price;

            Pattern pattern = Pattern.compile(regex_);

            try {
                for (int j = 0; j < 4; j += 1) {
                    Matcher eps_matcher = pattern.matcher(eps_str.get(i + j));

                    if (eps_matcher.find()) {
                        try {
                            eps += (double) nf.parse(eps_matcher.group());
                        }  // перевод в численный формат EPS
                        catch (ParseException e) {
                            //System.out.println("Cannot parse EPS: " + eps_matcher.group());
                            column_.add("-");
                            b = false;
                            break;
                        }
                    }
                    else {
                        //System.out.println("Cannot match EPS: " + eps_str.get(i));
                        column_.add("-");
                        b = false;
                        break;
                    }
                }
            }
            catch (IndexOutOfBoundsException e){
                //System.out.println("Cannot match EPS: " + eps_str.get(i));
                column_.add("-");
                continue;
            }

            if(b) {
                try {
                    price = (double) nf.parse(price_str.get(i));
                }  // перевод в численный формат Price
                catch (ParseException e) {
                    //System.out.println("Cannot parse Price: " + price_str.get(i));
                    column_.add("-");
                    continue;
                }

                try {
                    column_.add(String.format("%.2f", price / eps));
                } // подсчет PE
                catch (NullPointerException e) {
                    column_.add("-");
                    //System.out.println("Cannot calculate PE: " + eps_str.get(i) + " " + price_str.get(i));
                }
            }
        }

        return column_;
         */
    }
}
