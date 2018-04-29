package com.rs2systems.timeclocklayouttest;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Robert on 3/15/2018.
 */

public class externalwriter {
    private static File mFile = null;

    public static String getFileName() {
        if (mFile.exists()) {
            return mFile.getAbsolutePath();
        } else {
            return "";
        }
    }
    /**
     * Creates the history file on the storage card.
     */
    private static void createFile() {
// Check if external storage is present.
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
// Create a folder History on storage card.
            final File path = new File(Environment.getExternalStorageDirectory() +
                    "/History");
            if (!path.exists()) {
                path.mkdir();
            }
            if (path.exists()) {
// create a file HISTORYFILE.
                mFile = new File(path, "HISTORYFILE.txt");
                if (mFile.exists()) {
                    mFile.delete();
                }
                try {
                    mFile.createNewFile();
                } catch (IOException e) {
                    mFile = null;
                }
            }
        }
    }
    /**
     * Write data to the history file.
     * @param messages to be written to the history file.
     */
    public static void writeHistory(String log) {
        if ((mFile == null) || (!mFile.exists())) {
            createFile();
        }
        if (mFile != null && mFile.exists()) {
            try {
                final PrintWriter out = new PrintWriter(new BufferedWriter(
                        new FileWriter(mFile, true)));
                out.println(log);
                out.close();
            } catch (IOException e) {
                Log.w("ExternalStorage", "Error writing " + mFile, e);
            }
        }
    }
    /**
     * Deletes the history file.
     * @return true if file deleted, false otherwise.
     */
    public static boolean deleteFile() {
        return mFile.delete();
    }
}
