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

import com.totsp.crossword.net.AbstractDownloader;
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

        ArrayList<String[]> dls = AbstractDownloader.getDownloaders();
        boolean show_help = (gpargs.size() == 0 || gpargs.get(0).equalsIgnoreCase("help"));

        if (show_help) {
            // Print a help message and return
            System.out.println(
                "Getpuzzles version 1.0\n" +
                "https://github.com/jkboyce/getpuzzles\n" +
                "This program is released under the GNU General Public License v2.\n\n" +
                "Downloads crossword puzzles from a variety of sources on the internet and saves\n" +
                "them in Across Lite (.puz) format. Nearly all of this code is adapted from\n" +
                "Robert Cooper's `Shortyz` app for Android. https://github.com/kebernet/shortyz\n\n" +
                "Usage: getpuzzles [<date>] (all | <source>+) [-out <directory>]\n" +
                "    <date>       is of the form yyyy-MM-dd, and if omitted defaults to today's date.\n" +
                "    <source>+    is a space-separated list of one or more puzzle sources.\n" +
                "    <directory>  is an optional directory to save into.\n\n" +
                "Recognized sources are:");
            for (String[] dl_tuple : dls) {
                String dl_name = dl_tuple[0];
                String dl_description = dl_tuple[1];
                System.out.println(String.format("    %1$-13s- %2$s", dl_name, dl_description));
            }
            System.out.println("    all          - all available sources");
            return;
        }

        // try to parse the first argument as a date
        Date date = new Date();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            date = df.parse(gpargs.get(0));
            gpargs.remove(0);       // parsed ok; remove it
        } catch (ParseException pe) {
        }

        String datestring = String.format("%1$04d-%2$02d-%3$02d", date.getYear() + 1900, date.getMonth() + 1, date.getDate());

        for (String arg : gpargs) {
            boolean found = false;

            for (String[] dl_tuple : dls) {
                if (arg.equals(dl_tuple[0]) || arg.equals("all")) {
                    AbstractDownloader dl = AbstractDownloader.getDownloader(dl_tuple[0]);
                    if (dl == null)
                        continue;
                    System.out.println("Downloading " + dl_tuple[1] + " puzzle for date: " + datestring);

                    dl.download(date);
                    found = true;
                }
            }
            if (!found) {
                System.out.println("Downloader `" + arg + "` not recognized; skipping");
            }
        }

    }
}

