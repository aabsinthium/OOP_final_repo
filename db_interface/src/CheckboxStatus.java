import java.util.HashMap;

// контейнер для записи выбранных мультипликаторов
public class CheckboxStatus {
    private final String feature_; // имя мультипликатора
    private final HashMap<String, Boolean> status_; // карта имени и статуса выбора (true/false)

    public CheckboxStatus(String feature){
        feature_ = feature; // присваивание имени мультипликатора из параметра конструктора
        status_ = new HashMap<>();
        status_.put(feature, false); // дефолтное присваивание статуса false (не выбран)
    }

    // смена статуса (метод вызывается при нажатии на чекбокс)
    public void changeStatus(){
        status_.replace(feature_, !status_.get(feature_));
    }

    // возврат статуса при формировании запроса
    public HashMap<String, Boolean> sendStatus(){
        return status_;
    }
}
