import java.text.MessageFormat;
import java.util.*;
import java.io.*;


public class Request {
    private List<String> tickers_;
    private Map<String, String> params_;
    private Map<String, Boolean> status_;
    private Map<String, List<String>> data_in_;
    private Map<String, List<String>> data_out_;

    public Request(){
        this.status_ = new HashMap<>();
        this.params_ = new HashMap<>();
        this.data_in_ = new HashMap<>();
        this.data_out_ = new HashMap<>();
        System.out.println("Request created.");
    }

    // определение типа запроса и вызов
    public void formRequest(Map<String, Boolean> status, Map<String, String> params){
        this.status_ = status;
        this.params_ = params;

        if(params_.get("Multiple/Single").equals("Multiple")){
            multipleRequest(params_.get("Open year"), params_.get("Close year"));
        }
        else if(params_.get("Multiple/Single").equals("Single")){
            singleRequest(params_.get("Ticker"));
        }
        else{
            System.out.println("Error while reading status!");
        }
    }

    // запрос для всех компаний
    private void multipleRequest(String open_year, String close_year){
        ParserFinancials financials_parser = new ParserFinancials();
        ParserYield yield_parser = new ParserYield();

        for(String ticker : tickers_) {
            financials_parser.read(ticker);
            yield_parser.read(ticker);

            DateMerger merger = new DateMerger(financials_parser.getParsed(), yield_parser.getParsed());
            data_in_ = merger.merge();
            data_out_.put("Date", data_in_.get("Date"));

            if (status_.get("PE")) {
                ColumnPE pe = new ColumnPE(data_in_);
                data_out_.put("PE", pe.calculateValue());
            }

            if (status_.get("PB")) {
                ColumnPB pb = new ColumnPB(data_in_);
                data_out_.put("PB", pb.calculateValue());
            }
        }
        System.out.println("Request formed.");
    }

    // запрос для одной компании
    private void singleRequest(String ticker){
        ParserFinancials financials_parser = new ParserFinancials();
        ParserYield yield_parser = new ParserYield();

        financials_parser.read(ticker);
        yield_parser.read(ticker);

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

    private void setTickers(List tickers){
        this.tickers_ = tickers;
    }

    public void getFile(){
        try{
            FileWriter fr = new FileWriter("././data.csv");

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
                    idx++;
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
