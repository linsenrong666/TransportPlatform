package com.linsr.wanandroid.utils;

import android.content.Context;
import android.os.Environment;

import com.linsr.linlibrary.utils.JLog;

import java.io.File;


/**
 * Created by Linsr on 2015/9/10.
 *
 * @author Linsr
 */
public class FileUtils {

    /*
     * 判断sdcard是否挂载
	 */
    public static boolean isSDCardMounted() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * get storage space path
     */
    public static String getWorkingDirectory(Context context) {
        String res;
        if (isSDCardMounted()) {
            String directory = "com.jcloud.transport-platform";
            res = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + directory;
        } else {
            res = context.getFilesDir().getAbsolutePath() + "/";
        }
        if (!res.endsWith("/")) {
            res += "/";
        }
        File f = new File(res);
        if (!f.exists()) {
            boolean success = f.mkdirs();
            if (!success) {
                JLog.e("FileUtils create file failed");
            }
        }
        return res;
    }

    public static String getImageCachePath(Context context) {
        return createImagePath(context, "cache");
    }

    public static String getImageSavePath(Context context) {
        return createImagePath(context, "images");
    }

    private static String createImagePath(Context context, String name) {
        File f = new File(getWorkingDirectory(context) + "/" + name + "/");
        if (!f.exists()) {
            boolean success = f.mkdirs();
            if (!success) {
                JLog.e("create image cache file failed");
            }
        }
        String res = f.getPath();
        if (!res.endsWith("/")) {
            res += "/";
        }
        return res;
    }

    /**
     * clear files
     *
     * @param directory directory
     */
    public static void clearDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                boolean success = item.delete();
                if (!success) {
                    JLog.e("file delete failed");
                }
            }
        }
    }

}
