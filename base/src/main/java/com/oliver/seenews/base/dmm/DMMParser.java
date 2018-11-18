package com.oliver.seenews.base.dmm;

import android.support.annotation.NonNull;
import com.oliver.seenews.base.dmm.Constant.XPath;
import com.oliver.seenews.base.dmm.document.Focus;
import com.oliver.seenews.base.dmm.document.Img;
import com.oliver.seenews.base.dmm.document.Magazine;
import com.oliver.seenews.base.dmm.document.Magazine.Builder;
import com.oliver.seenews.base.dmm.document.Page;
import com.oliver.seenews.base.dmm.document.Text;
import com.oliver.seenews.base.util.FileUtils;
import com.oliver.seenews.base.util.XmlUtils;
import com.oliver.seenews.base.xml.XmlNode;
import com.oliver.seenews.base.xml.XmlNodeList;
import com.oliver.seenews.base.xml.XmlReader;

public class DMMParser {

    private String mDirPath;

    public DMMParser(@NonNull String dirPath) {
        mDirPath = dirPath;
    }

    public Magazine parse() {
        XmlReader contentXmlReader = XmlUtils.openFile(FileUtils.joinPath(mDirPath, Constant.CONTENT_OPF));
        if (null != contentXmlReader) {
            Magazine.Builder builder = new Builder();
            parseLayout(contentXmlReader, builder);
            parsePages(contentXmlReader, builder);
            XmlUtils.closeFile(contentXmlReader);
            return builder.build();
        } else {
            return null;
        }
    }

    private void parseLayout(XmlReader xmlReader, Magazine.Builder builder) {
        XmlNode layoutNode = xmlReader.getNode(XPath.CONTENT_LAYOUT);
        if (null != layoutNode) {
            builder.setWidth(XmlUtils.parseAttrInt(layoutNode, XPath.ATTR_WIDTH))
                .setHeight(XmlUtils.parseAttrInt(layoutNode, XPath.ATTR_HEIGHT));
        }
    }

    private void parsePages(XmlReader xmlReader, Magazine.Builder builder) {
        XmlNodeList itemNodeList = xmlReader.getNodeList(XPath.CONTENT_ITEM);
        if (null != itemNodeList) {
            for (XmlNode xmlNode: itemNodeList) {
                Page page = readPage(XmlUtils.parseAttr(xmlNode, XPath.ATTR_HREF),
                    XmlUtils.parseAttr(xmlNode, XPath.ATTR_THUMBNAIL));
                builder.addPage(page);
            }
        }
    }

    private Page readPage(String hRef, String thumbnail) {
        Page.Builder builder = new Page.Builder();
        builder.setName(hRef).setThumbnail(thumbnail);
        XmlReader xmlReader = XmlUtils.openFile(FileUtils.joinPath(mDirPath, hRef));
        if (null != xmlReader) {
            parsePageImg(xmlReader, builder);
            parsePageText(xmlReader, builder);
            parsePageFocus(xmlReader, builder);
            XmlUtils.closeFile(xmlReader);
        }
        return builder.build();
    }

    private void parsePageImg(XmlReader xmlReader, Page.Builder builder) {
        XmlNode imgNode = xmlReader.getNode(XPath.PAGE_IMG);
        if (null != imgNode) {
            Img.Builder imgBuilder = new Img.Builder();
            imgBuilder.setSrc(XmlUtils.parseAttr(imgNode, XPath.ATTR_SRC))
                .setX(XmlUtils.parseAttrInt(imgNode, XPath.ATTR_X))
                .setY(XmlUtils.parseAttrInt(imgNode, XPath.ATTR_Y))
                .setWidth(XmlUtils.parseAttrInt(imgNode, XPath.ATTR_WIDTH))
                .setHeight(XmlUtils.parseAttrInt(imgNode, XPath.ATTR_HEIGHT));
            builder.addBody(imgBuilder.build());
        }
    }

    private void parsePageText(XmlReader xmlReader, Page.Builder builder) {
        XmlNodeList textNodeList = xmlReader.getNodeList(XPath.PAGE_TEXT);
        if (null != textNodeList) {
            for (XmlNode textNode: textNodeList) {
                Text.Builder textBuilder = new Text.Builder();
                textBuilder.setFontColor(XmlUtils.parseAttr(textNode, XPath.ATTR_FONT_COLOR))
                    .setFontSize(XmlUtils.parseAttrInt(textNode, XPath.ATTR_FONT_SIZE))
                    .setContent(XmlUtils.parseAttr(textNode, XPath.ATTR_CONTENT))
                    .setX(XmlUtils.parseAttrInt(textNode, XPath.ATTR_X))
                    .setY(XmlUtils.parseAttrInt(textNode, XPath.ATTR_Y))
                    .setWidth(XmlUtils.parseAttrInt(textNode, XPath.ATTR_WIDTH))
                    .setHeight(XmlUtils.parseAttrInt(textNode, XPath.ATTR_HEIGHT));
                builder.addBody(textBuilder.build());
            }
        }
    }

    private void parsePageFocus(XmlReader xmlReader, Page.Builder builder) {
        XmlNodeList focusNodeList = xmlReader.getNodeList(XPath.PAGE_FOCUS);
        if (null != focusNodeList) {
            for (XmlNode focusNode: focusNodeList) {
                Focus.Builder focusBuilder = new Focus.Builder();
                focusBuilder.setType(XmlUtils.parseAttr(focusNode, XPath.ATTR_TYPE))
                    .setDest(XmlUtils.parseAttr(focusNode, XPath.ATTR_DEST))
                    .setX(XmlUtils.parseAttrInt(focusNode, XPath.ATTR_X))
                    .setY(XmlUtils.parseAttrInt(focusNode, XPath.ATTR_Y))
                    .setWidth(XmlUtils.parseAttrInt(focusNode, XPath.ATTR_WIDTH))
                    .setHeight(XmlUtils.parseAttrInt(focusNode, XPath.ATTR_HEIGHT));
                builder.addBody(focusBuilder.build());
            }
        }
    }

}
