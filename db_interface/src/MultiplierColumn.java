import java.util.*;

abstract class MultiplierColumn {
    private final List<Double> data_;

    public MultiplierColumn(){
        this.data_ = new ArrayList<>();
    }

    abstract List calculateValue();
}
