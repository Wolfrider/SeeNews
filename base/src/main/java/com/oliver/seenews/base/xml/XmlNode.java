package com.oliver.seenews.base.xml;

import android.support.annotation.NonNull;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlNode {

    private Node mNode;
    private NamedNodeMap mAttrNodeMap;

    XmlNode(Node node) {
        mNode = node;
        mAttrNodeMap = node.getAttributes();
    }

    public String getName() {
        return mNode.getNodeName();
    }

    public String getValue() {
        return mNode.getNodeValue();
    }

    public XmlNode getAttr(@NonNull String attrName) {
        if (null != mAttrNodeMap) {
            Node node = mAttrNodeMap.getNamedItem(attrName);
            if (null != node) {
                return new XmlNode(node);
            }
        }
        return null;
    }
}
