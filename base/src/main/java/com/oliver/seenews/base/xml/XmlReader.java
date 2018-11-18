package com.oliver.seenews.base.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import android.support.annotation.NonNull;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlReader {

    private Document mXmlDoc;
    private XPath mXPath;

    public XmlReader() {
    }

    public boolean open(@NonNull String filePath) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            mXmlDoc = documentBuilder.parse(new File(filePath));
            mXPath = XPathFactory.newInstance().newXPath();
            return true;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public void close() {
        mXmlDoc = null;
        mXPath = null;
    }

    public XmlNode getNode(@NonNull String xpath) {
        ensureState();
        try {
            return new XmlNode((Node)mXPath.evaluate(xpath, mXmlDoc, XPathConstants.NODE));
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public XmlNodeList getNodeList(@NonNull String xpath) {
        ensureState();
        try {
            return new XmlNodeList((NodeList)mXPath.evaluate(xpath, mXmlDoc, XPathConstants.NODESET));
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private void ensureState() {
        if (null == mXmlDoc || null == mXPath) {
            throw new IllegalStateException();
        }
    }

}
