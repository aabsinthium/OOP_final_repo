import java.text.*;
import java.util.*;
import java.util.regex.*;

public class ColumnPB extends Column{
    private final String regex_;
    private final List<String> column_;
    private final Map<String, List<String>> data_;

    public ColumnPB(Map<String, List<String>> data){
        super(data);
        this.data_ = data;
        this.regex_ = "[^\"$]+";
        this.column_ = new ArrayList<>();
    }

    @Override
    public List calculateValue(){
        List<String> price_str = data_.get("Price");
        List<String> shares_str = data_.get("Shares Outstanding"); // income-statement
        List<String> assets_str = data_.get("Total Assets"); // balance-sheet
        List<String> liabilities_str = data_.get("Total Liabilities"); // balance-sheet

        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        for(int i = 0; i < shares_str.size(); i += 1){
            double eps = 0;
            boolean b = true;
            Double price;

            Pattern pattern = Pattern.compile(regex_);

            try {
                for (int j = 0; j < 4; j += 1) {
                    Matcher eps_matcher = pattern.matcher(shares_str.get(i + j));

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
    }

}
