package com.oliver.seenews.base.dmm;

import java.util.Locale;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.oliver.seenews.base.dmm.document.Magazine;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.LogUtils;

public class DMMReader {

    private static final String TAG = "DMMReader";

    private String mDmmPath;

    public DMMReader() {
    }

    public Magazine readWithUnzip(@NonNull String filePath, String dmmPath) {
        if (!FileUtils.exists(filePath)) {
            return null;
        }
        FileUtils.mkdirs(dmmPath);
        LogUtils.debug(TAG, String.format(Locale.US, "unzip, from = %s, to = %s", filePath, dmmPath));
        FileUtils.unzip(filePath, dmmPath);
        return read(dmmPath);
    }

    public Magazine read(@NonNull String dmmPath) {
        mDmmPath = dmmPath;
        return parse();
    }

    public String getDmmPath() {
        return mDmmPath;
    }

    private Magazine parse() {
        return new DMMParser(mDmmPath).parse();
    }
}
