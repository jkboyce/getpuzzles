// Getpuzzles.java
//
// Copyright 2020 by Jack Boyce (jboyce@gmail.com)

package getpuzzles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//import com.totsp.crossword.net.AbstractDownloader;
import com.totsp.crossword.net.UclickDownloader;


public class Getpuzzles {
    public static void main(String[] args) {
        ArrayList<String> gpargs = new ArrayList<String>(Arrays.asList(args));
        String outpath = null;

        // no GUI
        System.setProperty("java.awt.headless", "true");

        for (int i = 0; i < gpargs.size(); i++) {
            if (gpargs.get(i).equalsIgnoreCase("-out")) {
                gpargs.remove(i);
                if (i == gpargs.size()) {
                    System.out.println("Warning: no output path specified after -out flag; ignoring");
                    break;
                }

                outpath = gpargs.remove(i);
                break;
            }
        }

        boolean show_help = (gpargs.size() == 0 || gpargs.get(0).equalsIgnoreCase("help"));
        // List<String> modes = Arrays.asList("gen", "anim", "togif", "tojml");
        // boolean show_help = !modes.contains(firstarg);

        if (show_help) {
            // Print a help message and return
            System.out.println(
                "Getpuzzles version 1.0\n" +
                "https://github.com/jkboyce/getpuzzles\n" +
                "This program is released under the GNU General Public License v2.\n\n" +
                "Download crossword puzzles from a variety of sources on the internet and save\n" +
                "them in Across Lite (.puz) format. Nearly all of this code is adapted from\n" +
                "Robert Cooper's `Shortyz` app for Android. https://github.com/kebernet/shortyz\n\n" +
                "Usage: getpuzzles [<date>] (all | <source>+) [-out <directory>]\n" +
                "    <date>       is of the form yyyy-MM-dd, and if omitted defaults to today's date.\n" +
                "    <source>+    is a space-separated list of one or more puzzle sources.\n" +
                "    <directory>  is an optional directory to save into.\n\n" +
                "Recognized sources are:\n" +
                "    crnet        - Newsday\n");
            return;
        }

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

