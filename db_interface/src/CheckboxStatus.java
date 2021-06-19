import java.util.*;

// контейнер для записи выбранных мультипликаторов
public class CheckboxStatus {
    private final String feature_; // имя мультипликатора
    private boolean status_; // карта имени и статуса выбора (true/false)

    public CheckboxStatus(String feature){
        feature_ = feature; // присваивание имени мультипликатора из параметра конструктора
        status_ = false;
    }

    // смена статуса (метод вызывается при нажатии на чекбокс)
    public void changeStatus(){
        status_ = !status_;
    }

    // возврат статуса при формировании запроса
    public boolean getStatus(){
        return status_;
    }
}
