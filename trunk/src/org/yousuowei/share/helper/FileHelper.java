/**
 * @author Brin.Liu
 * @email liubilin@gmail.com
 * @create 2012-5-22
 *          <p>
 */
package org.yousuowei.share.helper;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class FileHelper {
    private final static String DIR_ROOT = ".om.wear";
    private final static String DIR_DOWNLOAD_APK = "/version/";

    private final static String DIR_TASK_RECORD = "/task/record/";

    private static String getRootDir(Context context) {
	String rootDir;
	if (hasSDCard()) {
	    rootDir = new StringBuilder()
		    .append(Environment.getExternalStorageDirectory()
			    .getAbsolutePath()).append(File.separator)
		    .append(DIR_ROOT).append(File.separator).toString();
	    File dir = new File(rootDir);
	    if (!dir.isDirectory()) {
		dir.mkdir();
	    }
	} else {
	    rootDir = context.getFilesDir().getPath();
	}
	return rootDir;
    }

    public static boolean hasSDCard() {
	if (android.os.Environment.getExternalStorageState().equals(
		android.os.Environment.MEDIA_MOUNTED)) {
	    return true;
	} else {
	    return false;
	}
    }

    public static String getApkDownloadFilePath(Context context) {
	String dirPath = getRootDir(context) + DIR_DOWNLOAD_APK;
	checkDir(dirPath);
	String filePath = dirPath + "demp.apk";
	return filePath;
    }

    public static String getTaskRecordDirPath(Context context) {
	String dirPath = getRootDir(context) + DIR_TASK_RECORD;
	checkDir(dirPath);
	return dirPath;
    }

    public static boolean checkDir(String path) {
	File file = new File(path);
	if (!file.exists()) {
	    file.mkdirs();
	}
	return true;
    }
}
