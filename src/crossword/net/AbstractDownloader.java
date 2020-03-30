package com.totsp.crossword.net;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;
import java.util.Date;
//import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.totsp.crossword.puz.PuzzleMeta;


public abstract class AbstractDownloader {
    protected static final Logger LOG = Logger.getLogger("com.totsp.crossword");
    public static File DOWNLOAD_DIR = new File(".");

    // return a list of available downloaders
    public static ArrayList<String[]> getDownloaders() {
        return new ArrayList<String[]>(Arrays.<String[]>asList(
            new String[]{"crnet", "Newsday (daily)"},
            new String[]{"usaon", "USA Today (Monday-Saturday, not holidays)"},
            new String[]{"fcx", "Universal (daily)"}
            // new String[]{"lacal", "LA Times Sunday Calendar (Sunday)"}
        ));
    }

    // return a downloader instance
    public static AbstractDownloader getDownloader(String name) {
        if (name.equals("crnet"))
            return new UclickDownloader("crnet", "Newsday", "Newsday");
        if (name.equals("usaon"))
            return new UclickDownloader("usaon", "USA Today", "USA Today");
        if (name.equals("fcx"))
            return new UclickDownloader("fcx", "Universal", "Universal");
        /*
        if (name.equals("lacal"))
            return new UclickDownloader("lacal", "LA Times Sunday Calendar", "LA Times");
        */
        return null;
    }

    // These lists must be sorted for binary search.
    /*
    int[] DATE_SUNDAY = new int[] { 0 };
    int[] DATE_MONDAY = new int[] { 1 };
    int[] DATE_TUESDAY = new int[] { 2 };
    int[] DATE_WEDNESDAY = new int[] { 3 };
    int[] DATE_THURSDAY = new int[] { 4 };
    int[] DATE_FRIDAY = new int[] { 5 };
    int[] DATE_SATURDAY = new int[] { 6 };
    int[] DATE_DAILY = new int[] { 0, 1, 2, 3, 4, 5, 6 };
    int[] DATE_NO_SUNDAY = new int[] { 1, 2, 3, 4, 5, 6 };
    */

    protected File downloadDirectory;
    protected String baseUrl;
    private String downloaderName;
    protected File tempFolder;

    // constructor
    protected AbstractDownloader(String baseUrl, File downloadDirectory, String downloaderName) {
        this.baseUrl = baseUrl;
        this.downloadDirectory = downloadDirectory;
        this.downloaderName = downloaderName;
        this.tempFolder = new File(downloadDirectory, "temp");
        this.tempFolder.mkdirs();
    }

    public String createFileName(Date date) {
        return String.format("%1$04d-%2$02d-%3$02d",
               date.getYear() + 1900, date.getMonth() + 1, date.getDate()) +
               "-" + this.downloaderName.replaceAll(" ", "") + ".puz";
    }

    public String sourceUrl(Date date) {
        return this.baseUrl + this.createUrlSuffix(date);
    }

    /*
    public String toString() {
        return getName();
    }
    */
    // public abstract String getName();

    protected abstract String createUrlSuffix(Date date);

    public abstract File download(Date date);

/*
    protected File download(Date date, String urlSuffix) {   //, Map<String, String> headers, boolean canDefer
        LOG.info("Mkdirs: " + this.downloadDirectory.mkdirs());
        LOG.info("Exist: " + this.downloadDirectory.exists());

        try {
            URL url = new URL(this.baseUrl + urlSuffix);
            System.out.println(url);

            File f = new File(downloadDirectory, this.createFileName(date));

            PuzzleMeta meta = new PuzzleMeta();
            meta.date = date;
            meta.source = getName();
            meta.sourceUrl = url.toString();
            meta.updatable = false;
*/
            /*
            utils.storeMetas(Uri.fromFile(f), meta);
            if( canDefer ){
	            if (utils.downloadFile(url, f, headers, true, this.getName())) {
	                DownloadReceiver.metas.remove(Uri.fromFile(f));

	                return f;
	            } else {
	                return Downloader.DEFERRED_FILE;
	            }
            } else {
            	AndroidVersionUtils.Factory.getInstance().downloadFile(url, f, headers, true, this.getName());
            	return f;
            }
            */
/*
            downloadFile(url, f);
            return f;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
*/
    /*
    protected File download(Calendar cal, String urlSuffix) {
        return download(cal, urlSuffix, EMPTY_MAP);
    }
    */

    // Utility function to download file from a URL.
    // returns true on success, false on failure
    protected static void downloadFile(URL url, File destination) throws IOException {
        // System.out.println("downloading url "+url.toString()+" to file "+destination.getAbsolutePath());
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(destination);
            BufferedInputStream in = new BufferedInputStream(url.openStream());
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fos.write(dataBuffer, 0, bytesRead);
            }
            fos.close();
        } catch (IOException e) {
            throw e;
        } finally {
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
    protected File downloadToTempFile(String fullName, Date date) {
        File downloaded = new File(downloadDirectory, this.createFileName(date));

        try {
            URL url = new URL(this.baseUrl + this.createUrlSuffix(date));
            LOG.log(Level.INFO, fullName +" "+url.toExternalForm());
            // AndroidVersionUtils.Factory.getInstance().downloadFile(url, downloaded, EMPTY_MAP, false, null);
        } catch (Exception e) {
            e.printStackTrace();
            downloaded.delete();
            downloaded = null;
        }

        if (downloaded == null) {
            LOG.log(Level.SEVERE, "Unable to download plain text KFS file.");

            return null;
        }

        System.out.println("Text file: " + downloaded);

        try {
            File tmpFile =  new File(this.tempFolder, "txt-tmp"+System.currentTimeMillis()+".txt"); //File.createTempFile("kfs-temp", "txt");
            downloaded.renameTo(tmpFile);
            LOG.log(Level.INFO, "Downloaded to text file "+tmpFile);
            return tmpFile;
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Unable to move KFS file to temporary location.");

            return null;
        }
    }
    */
}
