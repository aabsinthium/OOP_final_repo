import java.util.*;

public abstract class Parser {
    private final Map<String, List<String>> data_;

    public Parser(){
        this.data_ = new HashMap<>();
    }

    abstract void read(String company);
    abstract Map<String, List<String>> getParsed();
}
