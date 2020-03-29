package getpuzzles;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import com.totsp.crossword.net.AbstractDownloader;
import com.totsp.crossword.net.UclickDownloader;


public class Getpuzzles {
    public static void main(String[] args) {
        UclickDownloader newsday_dl = new UclickDownloader("crnet", "Newsday", null, null);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        try {
            date = df.parse("2020-03-28");
        } catch (ParseException pe) {
            System.out.println("--- Problem parsing date, using today's date instead");
        }

        System.out.println("downloading Newsday puzzle for date: " + date.toString());

        newsday_dl.download(date);
    }
}

