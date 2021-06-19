import java.util.*;

public class PEColumn extends MultiplierColumn{
    private final List<Double> column_;
    private final Map<String, List<String>> data_;

    public PEColumn(Map<String, List<String>> data){
        this.column_ = new ArrayList<>();
        this.data_ = data;
    }

    @Override
    public List calculateValue(Map<String, List<String>> data){
        List<String> eps_str = data_.get("EPS - Earnings Per Share");
        List<String> prices_str = data_.get("Price");


        return column_;
    }
}
