import java.util.*;

public class DateMerger {
    private final Map<String, List<String>> data_;
    private final Map<String, List<String>> financials_;
    private final Map<String, List<String>> yield_;
    private final Map<String, String> months_;

    public DateMerger(Map<String, List<String >> financials, Map<String, List<String >> yield){
        this.financials_ = financials;
        this.yield_ = yield;
        this.data_ = financials_;
        this.months_ = new HashMap<>();

        this.months_.put("Jan", "-12-31");
        this.months_.put("Feb", "-01-31");
        this.months_.put("Mar", "-02-28");
        this.months_.put("Mar+", "-02-29"); // високосный
        this.months_.put("Apr", "-03-31");
        this.months_.put("May", "-04-30");
        this.months_.put("Jun", "-05-31");
        this.months_.put("Jul", "-06-30");
        this.months_.put("Aug", "-07-31");
        this.months_.put("Sep", "-08-31");
        this.months_.put("Oct", "-09-30");
        this.months_.put("Nov", "-10-31");
        this.months_.put("Dec", "-11-30");
    }

    public Map<String, List<String>> merge(){
        List<String> yield_dates = yield_.get("Date");

        for(int i = 0; i < yield_dates.size(); i+= 1){
            String[] date_arr = yield_dates.get(i).split(", ");
            String year = date_arr[1];
            String month = date_arr[0].substring(0, 3);

            if(Integer.parseInt(year) % 4 == 0 && month.equals("Mar")) // високосный год
                month += "+";

            if(month.equals("Jan")) // перенос с января на декабрь
                year = Integer.toString(Integer.parseInt(year) - 1);

            String date_str = year;
            date_str += months_.get(month);

            yield_dates.set(i, date_str);
        }

        yield_.put("Date", yield_dates);

        List<String> prices = new ArrayList<>();

        for(String date : financials_.get("Date")){
            boolean b = false;

            for(int i = 0; i < yield_dates.size(); i += 1){
                if(date.equals(yield_dates.get(i))) {
                    prices.add(yield_.get("Close*").get(i));
                    b = true;
                    break;
                }
            }

            if(!b)
                prices.add("-");
        }

        data_.put("Price", prices);

        return data_;
    }
}
