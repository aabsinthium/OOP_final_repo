import java.util.*;

abstract class Column {
    private final String regex_;
    private final List<String> column_;
    private final Map<String, List<String>> data_;

    public Column(Map<String, List<String>> data){
        this.data_ = data;
        this.regex_ = "[^\"$]+";
        this.column_ = new ArrayList<>();
    }

    abstract List calculateValue();
}
