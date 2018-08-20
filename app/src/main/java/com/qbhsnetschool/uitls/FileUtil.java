package com.qbhsnetschool.uitls;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

    public String SDCARD_PAHT;

    public FileUtil (Context context) {
        SDCARD_PAHT = context.getFilesDir() + "/";
        try {
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                SDCARD_PAHT = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/";
            }
        } catch (Exception ignored) {}
    }

    public static boolean makeMultiDirs(String dirs) {
        boolean mkOK = false;
        File file = new File(dirs);
        if (!file.exists()) {
            mkOK = file.mkdirs();
        }
        return mkOK;
    }

    /**
     * 写入图片到sd卡中
     *
     * @param bitmap
     * @param saveName 保存图片的名称
     * @param quality
     */
    public void writeImage(Bitmap bitmap, String saveName, int quality) {
        try {
            //sd卡存在路径
            String path = SDCARD_PAHT + saveName;
            deleteFile(path);
            if (createFile(path)) {
                FileOutputStream out = new FileOutputStream(path);
                if (bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out)) {
                    out.flush();
                    out.close();
                    out = null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个文件
     *
     * @param filePath 要删除的文件路径名
     * @return true if this file was deleted, false otherwise
     */
    public boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                return file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 创建一个文件，创建成功返回true
     *
     * @param filePath
     * @return
     */
    public boolean createFile(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                return file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
