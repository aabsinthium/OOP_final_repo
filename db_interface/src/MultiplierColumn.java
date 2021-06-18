import java.util.*;

abstract class MultiplierColumn {
    private final List<Double> column_;

    public MultiplierColumn(){
        this.column_ = new ArrayList<>();
    }

    abstract List calculateValue(Map<String, List<String>> data);
}
