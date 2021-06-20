import java.util.*;

// контейнер для записи выбранных мультипликаторов
public class CheckboxStatus {
    //private final String feature_; // имя мультипликатора
    private Map<String, Boolean> status_; // карта имени и статуса выбора (true/false)

    public CheckboxStatus(String[] features){
        //feature_ = feature; // присваивание имени мультипликатора из параметра конструктора
        status_ = new HashMap<>();

        for(String f : features)
            status_.put(f, false);
    }

    // смена статуса (метод вызывается при нажатии на чекбокс)
    public void changeStatus(String feature){
        status_.replace(feature, !status_.get(feature));
    }

    // возврат статуса при формировании запроса
    public Map<String, Boolean> getStatus(){
        return status_;
    }
}
