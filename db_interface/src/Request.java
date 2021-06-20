import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
    private final String path_data_;
    private final String path_save_;
    private Map<String, List<String>> data_in_;
    private Map<String, List<String>> data_out_;

    public Request(String path_data, String path_save){
        this.path_data_ = path_data; // при создании указывается путь к базе данных, возможно из настроек
        this.path_save_ = path_save; // путь к директории для сохранения финального файла
        this.data_in_ = new HashMap<>();
        this.data_out_ = new HashMap<>();
        // прописать дефолтный
    }

    public void formRequest(Map<String, Boolean> status){
        // вызов при отправке запроса
        // создание запроса:
        // подгрузка файлов, парсинг большой таблицы
        // парсинг требуемых колонок
        FinancialsParser financials_parser = new FinancialsParser();
        YieldParser yield_parser = new YieldParser();

        // сделать цикл, поставить путь
        financials_parser.read("AAPL");
        yield_parser.read("AAPL");

        DateMerger merger = new DateMerger(financials_parser.getParsed(), yield_parser.getParsed());
        data_in_ = merger.merge();
        data_out_.put("Date", data_in_.get("Date"));

        if(status.get("PE")){
            PEColumn pe = new PEColumn(data_in_, "(?<=\\$).[^\"]+");
            data_out_.put("PE", pe.calculateValue());
        }
    }

    public void getFile(){
        for(int i = 0; i < data_out_.get("Date").size(); i += 1){
            System.out.println(
                data_out_.get("Date").get(i) + "   " +
                data_out_.get("PE").get(i));
        }
        // добавить кнопку создания файла
        // запись данных в файл в указанной директории
    }
}
