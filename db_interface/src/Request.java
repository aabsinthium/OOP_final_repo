import java.text.MessageFormat;
import java.util.*;
import java.io.*;


public class Request {
    //private final String path_data_;
    //private final String path_save_;
    private Map<String, Boolean> status_;
    private Map<String, List<String>> data_in_;
    private Map<String, List<String>> data_out_;

    public Request(){
        //this.path_data_ = path_data; // при создании указывается путь к базе данных, возможно из настроек
        //this.path_save_ = path_save; // путь к директории для сохранения финального файла
        this.status_ = new HashMap<>();
        this.data_in_ = new HashMap<>();
        this.data_out_ = new HashMap<>();
        System.out.println("Request created.");
    }

    // определение типа запроса и вызов
    public void formRequest(Map<String, Boolean> status){
        this.status_ = status;

        if(status_.get("Multiple")){
            multipleRequest();
        }
        else if(status_.get("Single")){
            singleRequest();
        }
        else{
            System.out.println("Error while reading status!");
        }
    }

    // запрос для всех компаний
    private void multipleRequest(){

    }

    // запрос для одной компании
    private void singleRequest(){
        ParserFinancials financials_parser = new ParserFinancials();
        ParserYield yield_parser = new ParserYield();

        financials_parser.read("AAPL");
        yield_parser.read("AAPL");

        DateMerger merger = new DateMerger(financials_parser.getParsed(), yield_parser.getParsed());
        data_in_ = merger.merge();
        data_out_.put("Date", data_in_.get("Date"));

        if(status_.get("PE")){
            ColumnPE pe = new ColumnPE(data_in_);
            data_out_.put("PE", pe.calculateValue());
        }

        if(status_.get("PB")){
            ColumnPB pb = new ColumnPB(data_in_);
            data_out_.put("PB", pb.calculateValue());
        }

        System.out.println("Request formed.");

    }

    public void getFile(){
        try{
            FileWriter fr = new FileWriter("../data.csv");

            List<String> columns = new ArrayList<>();
            Map<String, Integer> indexes = new HashMap<>();

            int idx = 1;
            String format_str = "{0}";
            columns.add("Date");

            for(String key : status_.keySet()) {
                if (status_.get(key)) {
                    indexes.put(key, idx);
                    columns.add(key);
                    format_str += ";{" + indexes.get(key) + "}";
                    idx += 1;
                }
            }
            System.out.println(MessageFormat.format(format_str, columns.toArray()));

            fr.write(MessageFormat.format(format_str, columns.toArray()) + "\n");

            for(int i = 0; i < data_out_.get("Date").size(); i += 1) {
                List<String> line = new ArrayList<>();

                for(String col : columns)
                    line.add(data_out_.get(col).get(i));

                fr.write(MessageFormat.format(format_str, line.toArray()) + "\n");
                System.out.println(MessageFormat.format(format_str, line.toArray()));
            }

            fr.close();
            System.out.println("File created.");
        }
        catch (IOException e){
            System.out.println("Writing file error!");
            e.printStackTrace();
        }
    }
}
