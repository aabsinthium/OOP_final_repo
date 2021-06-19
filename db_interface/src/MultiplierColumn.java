import java.util.*;

abstract class MultiplierColumn {
    private final String regex_;
    private final List<String> column_;
    private final Map<String, List<String>> data_;

    public MultiplierColumn(Map<String, List<String>> data, String regex){
        this.data_ = data;
        this.regex_ = regex;
        this.column_ = new ArrayList<>();
    }

    abstract List calculateValue();
}
