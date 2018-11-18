package com.oliver.seenews.base.util;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.xml.XmlNode;
import com.oliver.seenews.base.xml.XmlReader;

public class XmlUtils {

    public static XmlReader openFile(@NonNull String filePath) {
        XmlReader xmlReader = new XmlReader();
        if (xmlReader.open(filePath)) {
            return xmlReader;
        }
        return null;
    }

    public static void closeFile(XmlReader xmlReader) {
        if (null != xmlReader) {
            xmlReader.close();
        }
    }

    public static String parseAttr(@NonNull XmlNode xmlNode, @NonNull String attrName) {
        XmlNode attrNode = xmlNode.getAttr(attrName);
        if (null != attrNode) {
            return attrNode.getValue();
        }
        return "";
    }

    public static int parseAttrInt(@NonNull XmlNode xmlNode, @NonNull String attrName) {
        return NumUtils.toInt(parseAttr(xmlNode, attrName));
    }
}
