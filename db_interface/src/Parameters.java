import java.util.*;

// контейнер для записи выбранных мультипликаторов
public class Parameters {
    //private final String feature_; // имя мультипликатора
    private Map<String, Boolean> multipliers_; // карта имени и статуса выбора (true/false)
    private Map<String, String> params_;
    //private Map<String, Boolean> multipliers_;

    public Parameters(String[] multipliers, String[] params){
        multipliers_ = new HashMap<>();
        params_ = new HashMap<>();

        for(String m : multipliers)
            multipliers_.put(m, false);

        for(String p : params)
            params_.put(p, "Undefined");
    }

    // смена статуса (метод вызывается при нажатии на чекбокс)
    public void changeStatus(String feature){
        multipliers_.replace(feature, !multipliers_.get(feature));
    }

    // задание параметра
    public void setParam(String name, String value){
        params_.replace(name, value);
    }
    
    // возврат статуса при формировании запроса
    public Map<String, Boolean> getStatus(){
        return multipliers_;
    }

    // возвзат параметров
    public Map<String, String> getParams(){
        return params_;
    }
}
