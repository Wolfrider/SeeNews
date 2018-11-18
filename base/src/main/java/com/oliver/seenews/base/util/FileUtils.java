package com.oliver.seenews.base.util;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import android.os.Environment;
import android.support.annotation.NonNull;

public class FileUtils {

    private static final String TAG = "FileUtils";

    public static boolean exists(@NonNull String filePath) {
        try {
            return new File(filePath).exists();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static boolean mkdirs(@NonNull String dirPath) {
        try {
            return new File(dirPath).mkdirs();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void delete(@NonNull String filePath) {
        delete(new File(filePath));
    }

    public static void delete(@NonNull File file) {
        try {
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] files = file.listFiles();
                    if (null != files) {
                        for (File item: files) {
                            delete(item);
                        }
                    }
                }
                file.delete();
            }
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
    }

    public static void copy(@NonNull String from, @NonNull String to) {
        try {
            try (FileChannel fromChannel = new FileInputStream(from).getChannel()) {
                try (FileChannel toChannel = new FileOutputStream(to).getChannel()) {
                    fromChannel.transferTo(0, fromChannel.size(), toChannel);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            LogUtils.warn(TAG, String.format(Locale.US,
                "copy failed, from = %s, to = %s, ex = %s", from, to, ex.toString()));
        }
    }

    public static boolean rename(@NonNull String from, @NonNull String to) {
        try {
            return new File(from).renameTo(new File(to));
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static String joinPath(String...paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            if (sb.length() > 0) {
                if (sb.charAt(sb.length() - 1) != File.separatorChar) {
                    sb.append(File.separatorChar);
                }
            }
            sb.append(path);
        }
        return sb.toString();
    }

    public static boolean isDir(@NonNull String dirPath) {
        try {
            File dir = new File(dirPath);
            return dir.exists() && dir.isDirectory();
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void close(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String getFileName(@NonNull String filePath) {
        return new File(filePath).getName();
    }

    public static String getSDCardPath() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return "";
    }

    public static String getCacheDirPath() {
        File cacheDir = AppUtils.getApp().getExternalCacheDir();
        if (null != cacheDir) {
            String cacheDirPath = cacheDir.getAbsolutePath();
            mkdirs(cacheDirPath);
            return cacheDirPath;
        }
        return "";
    }

    public static String getFilesDirPath() {
        File filesDir = AppUtils.getApp().getExternalFilesDir(null);
        if (null != filesDir) {
            String filesDirPath = filesDir.getAbsolutePath();
            mkdirs(filesDirPath);
            return filesDirPath;
        }
        return "";
    }

    public static void unzip(String fromFilePath, String toDirPath) {
        if (exists(fromFilePath) && isDir(toDirPath)) {
            try {
                try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(fromFilePath))) {
                    byte[] buffer = new byte[4096];
                    ZipEntry zipEntry;
                    while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                        String filePath = joinPath(toDirPath, zipEntry.getName());
                        if (zipEntry.isDirectory()) {
                            mkdirs(filePath);
                        } else {
                            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                                int length;
                                while ((length = zipInputStream.read(buffer)) != -1) {
                                    outputStream.write(buffer, 0, length);
                                }
                            }
                        }
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                LogUtils.warn(TAG, "unzip failed, ex =" + ex.toString());
            }
        } else {
            LogUtils.warn(TAG, "unzip failed, path is invalid.");
        }
    }

    public static long getSize(@NonNull String filePath) {
        return getSize(new File(filePath));
    }

    private static long getSize(@NonNull File file) {
        long size = 0;
        if (file.exists()) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File subFile: files) {
                    size += getSize(subFile);
                }
            } else {
                size += file.length();
            }
        }
        return size;
    }

    public static double toMB(long byteSize) {
        return toKB(byteSize) / 1024.0;
    }

    public static double toKB(long byteSize) {
        return byteSize / 1024.0;
    }



}
