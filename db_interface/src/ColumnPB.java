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
        List<String> data_price = data_.get("Price");
        List<String> data_shares = data_.get("Shares Outstanding"); // income-statement
        List<String> data_assets = data_.get("Total Assets"); // balance-sheet
        List<String> data_liabilities = data_.get("Total Liabilities"); // balance-sheet

        NumberFormat nf = NumberFormat.getInstance(Locale.US);

        for(int i = 0; i < data_.get("Date").size(); i += 1) {
            String pb;
            Pattern pattern = Pattern.compile(regex_);
            QtrToYear counter = new QtrToYear();

            String price_str = counter.count(i, 1, pattern, data_price);
            String shares_str = counter.count(i, 1, pattern, data_shares);
            String assets_str = counter.count(i, 1, pattern, data_assets);
            String liabilities_str = counter.count(i, 1, pattern, data_liabilities);

            try {
                double price = Double.parseDouble(price_str);
                double shares = Double.parseDouble(shares_str);
                double assets = Double.parseDouble(assets_str);
                double liabilities = Double.parseDouble(liabilities_str);

                pb = String.format("%.2f", (price * shares) / (assets - liabilities));
            }
            catch (NumberFormatException e) {
                pb = "nan";
            }

            column_.add(pb);
        }
        return column_;
    }
}
