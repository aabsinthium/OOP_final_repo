import java.text.MessageFormat;
import java.util.*;
import java.io.*;


public class Request {
    //private final String path_data_;
    //private final String path_save_;
    private final Map<String, Boolean> status_;
    private Map<String, List<String>> data_in_;
    private Map<String, List<String>> data_out_;

    public Request(Map<String, Boolean> status){
        //this.path_data_ = path_data; // при создании указывается путь к базе данных, возможно из настроек
        //this.path_save_ = path_save; // путь к директории для сохранения финального файла
        this.status_ = status;
        this.data_in_ = new HashMap<>();
        this.data_out_ = new HashMap<>();
    }

    public void formRequest(){
        // вызов при отправке запроса
        // создание запроса:
        // подгрузка файлов, парсинг большой таблицы
        // парсинг требуемых колонок
        ParserFinancials financials_parser = new ParserFinancials();
        ParserYield yield_parser = new ParserYield();

        // сделать цикл, поставить путь
        financials_parser.read("AAPL");
        yield_parser.read("AAPL");

        DateMerger merger = new DateMerger(financials_parser.getParsed(), yield_parser.getParsed());
        data_in_ = merger.merge();
        data_out_.put("Date", data_in_.get("Date"));

        if(status_.get("PE")){
            ColumnPE pe = new ColumnPE(data_in_);
            data_out_.put("PE", pe.calculateValue());
        }
    }

    public void getFile(){
        for(int i = 0; i < data_out_.get("Date").size(); i += 1){
            System.out.println(
                data_out_.get("Date").get(i) + "   " +
                data_out_.get("PE").get(i));
        }

        try{
            FileWriter fr = new FileWriter("../PE.csv");

            String format_str = "{date}";

            for(String key : status_.keySet())
                if(status_.get(key))
                    format_str += ";{" + key + "}";


            System.out.println(MessageFormat.format(format_str, ""));

            fr.write("Date;PE\n");

            for(int i = 0; i < data_out_.get("Date").size(); i += 1)
                fr.write(data_out_.get("Date").get(i) + ";" + data_out_.get("PE").get(i) + "\n");

            fr.close();
            System.out.println("File created.");
        }
        catch (IOException e){
            System.out.println("Writing file error!");
            e.printStackTrace();
        }
        // добавить кнопку создания файла
        // запись данных в файл в указанной директории
    }
}
