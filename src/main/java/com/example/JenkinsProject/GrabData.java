package com.example.JenkinsProject;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;



public class GrabData {
    private String path;
    private static HttpURLConnection conn;
    private ArrayList<Item> items = new ArrayList();

    public GrabData(String path) {
        this.path = path;
    }

    private int Connection() {
        int status = 500;

        try {
            URL url = new URL(this.path);
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            status = conn.getResponseCode();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

        return status;
    }

    public ArrayList<Item> Readitems() {
        try {
            BufferedReader reader;
            if (this.Connection() >= 300) {
                reader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                this.Readlines(reader);
            } else {
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                this.Readlines(reader);
            }
        } catch (IOException var3) {
            var3.printStackTrace();
        }

        return this.items;
    }

    private void Readlines(BufferedReader reader) {
        StringBuilder responseContent = new StringBuilder();

        try {
            String line;
            for(line = reader.readLine(); !line.contains("item"); line = reader.readLine()) {
            }

            while(line != null) {
                while(line != null && !line.contains("</item>")) {
                    responseContent.append(line);
                    line = reader.readLine();
                }

                if (line == null) {
                    break;
                }

                responseContent.append(line);
                this.parser(responseContent.toString());
                responseContent.setLength(0);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private void parser(String xml) {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(xml));
            Document doc = builder.parse(src);
            String title = doc.getElementsByTagName("title").item(0).getTextContent();
            String description = doc.getElementsByTagName("description").item(0).getTextContent();
            String link = doc.getElementsByTagName("link").item(0).getTextContent();
            String pubDate = doc.getElementsByTagName("pubDate").item(0).getTextContent();
            String guid = doc.getElementsByTagName("guid").item(0).getTextContent();
            String tags = doc.getElementsByTagName("tags").item(0).getTextContent();
            Item i = new Item(title, description, link, pubDate, guid, tags);
            this.items.add(i);
        } catch (SAXException | IOException | ParserConfigurationException var12) {
            var12.printStackTrace();
        }

    }
}
