package com.oliver.seenews.base.xml;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import android.support.annotation.NonNull;
import org.w3c.dom.NodeList;

public class XmlNodeList implements Iterable<XmlNode> {

    private List<XmlNode> mNodeList;

    XmlNodeList(@NonNull NodeList nodeList) {
        mNodeList = new LinkedList<>();
        make(nodeList);
    }

    private void make(NodeList nodeList) {
        for (int i = 0; i < nodeList.getLength(); ++i) {
            mNodeList.add(new XmlNode(nodeList.item(i)));
        }
    }

    @NonNull
    @Override
    public Iterator<XmlNode> iterator() {
        return new Iter();
    }

    private class Iter implements Iterator<XmlNode> {

        private int mIndex;

        private Iter() {
            mIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return mIndex < mNodeList.size();
        }

        @Override
        public XmlNode next() {
            return mNodeList.get(mIndex++);
        }
    }
}
