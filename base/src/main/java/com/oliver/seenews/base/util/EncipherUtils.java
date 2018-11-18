package com.oliver.seenews.base.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.Constant;

public class EncipherUtils {

    private static final String TAG = "EncipherUtils";

    public static String md5FromFile(@NonNull String filePath) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            try (FileChannel fileChannel = new FileInputStream(filePath).getChannel()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(Constant.BUFFER_SIZE);
                int length;
                while ((length = fileChannel.read(byteBuffer)) != -1) {
                    digest.digest(byteBuffer.array(), 0, length);
                    byteBuffer.position(0);
                }
            }
            return toHexString(digest.digest());
        } catch (IOException | NoSuchAlgorithmException | DigestException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String md5(@NonNull String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] data = digest.digest(text.getBytes());
            LogUtils.debug(TAG, String.format(Locale.US, "md5, text = %s, data length = %d", text, data.length));
            return toHexString(data);
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    private static String toHexString(@NonNull byte[] data) {
        StringBuilder builder = new StringBuilder();
        for (byte b : data) {
            String temp = Integer.toHexString(b & 0xFF);
            if (temp.length() == 1) {
                builder.append('0');
            }
            builder.append(temp);
        }
        return builder.toString();
    }
}
