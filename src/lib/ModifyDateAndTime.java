package lib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Peter on 19/03/2015.
 */
public class ModifyDateAndTime {

    public String modifyDateLayout(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }

    public String modifyTimeLayout(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("HH:mm").format(date);
    }

    public String getYear(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("yyyy").format(date);
    }

    public String getMonth(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("MM").format(date);
    }

    public String getDate(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("dd").format(date);
    }

    public String getHour(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("HH").format(date);
    }

    public String getMinute(String inputDate) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(inputDate);
        return new SimpleDateFormat("mm").format(date);
    }

}
