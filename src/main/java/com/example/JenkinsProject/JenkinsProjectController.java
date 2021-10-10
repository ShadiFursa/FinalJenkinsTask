//package com.example.JenkinsProject;
//
//
//import java.util.ArrayList;
//import javax.annotation.PostConstruct;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.net.URL;
//import java.nio.charset.StandardCharsets;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//
//
//@Controller
//public class JenkinsProjectController {
//    static PrintStream out;
//    static File output_file;
//    static String[] header_tags = {"title", "description", "link", "pubDate", "guid", "tags"};
//    static StringBuilder htmlBuilder = new StringBuilder();
//
//    public JenkinsProjectController() {
//    }
//
//    ///////
//    public static void main(String[] args) {
//
//        try {
//            URL url = new URL("http://www.ynet.co.il/Integration/StoryRss2.xml");
//            out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
//
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document doc = db.parse(url.openStream());
//
//            doc.getDocumentElement().normalize();
//
//            NodeList nList = doc.getElementsByTagName("item");
//            System.out.println("============================");
//            create_file();
//            for (int temp = 0; temp < nList.getLength(); temp++) {
//                Node node = nList.item(temp);
//                if (node.getNodeType() == Node.ELEMENT_NODE) {
//                    Element eElement = (Element) node;
//                    addItem(eElement);
//                }
//            }
//            end_table();
//            write_to_file(htmlBuilder.toString());// write the row to the end of file
//
//        } catch (Exception e) {
//
//            System.out.println(e);
//        }
//    }
//
//
//    public static void addItem(Element element) {
//        htmlBuilder.append("<tr>");
//        for (String tag : header_tags) {
//            String value_tag = element.getElementsByTagName(tag).item(0).getTextContent();
//            htmlBuilder.append(String.format("<td>%s</td>\n", value_tag));
//        }
//        htmlBuilder.append("</tr>");
//    }
//
//    public static void start_table() {
//        htmlBuilder.append("<!DOCTYPE html>\n" +
//                "<html dir=\"rtl\" lang=\"he\">\n" +
//                "<head>\n" +
//                "<meta charset=\"utf-8\"/>\n" +
//                "<style>\n" +
//                "table, th, td {\n" +
//                "  border: 1px solid black;\n" +
//                "  border-collapse: collapse;\n" +
//                "}\n" +
//                "</style>\n" +
//                "</head>");
//        htmlBuilder.append("<table>");
//        htmlBuilder.append("<tr>"); // for the header line
//        for (String header_tag : header_tags) {
//            htmlBuilder.append(String.format("<th>%s</th>", header_tag));
//        }
//        htmlBuilder.append("</tr>");
//    }
//
//    public static void end_table() {
//        htmlBuilder.append("</table></html>");
//    }
//
//    public static void create_file() {
//        try {
//            output_file = new File("output.html");
//            if (output_file.createNewFile()) {
//                System.out.println("File created: " + output_file.getName());
//                start_table();
//
//            } else {
//                System.out.println("File already exists.");
//            }
//        } catch (IOException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    public static void write_to_file(String str) {
//
//        try (BufferedWriter wr = Files.newBufferedWriter(Paths.get("output.html"), StandardCharsets.UTF_8)) {
//            wr.write(str);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    //////
////    @PostConstruct
////    private void loadData() {
////        GrabData data = new GrabData("http://www.ynet.co.il/Integration/StoryRss2.xml");
////        this.items = data.Readitems();
////    }
////
//    @GetMapping({"/"})
//    public String hello(Model model) {
//        return "output_file";
//    }
//}
package com.example.JenkinsProject;


import java.util.ArrayList;
import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JenkinsProjectController {
    private ArrayList<Item> items = new ArrayList();

    public JenkinsProjectController() {
    }

    @PostConstruct
    private void loadData() {
        GrabData data = new GrabData("http://www.ynet.co.il/Integration/StoryRss2.xml");
        this.items = data.Readitems();
    }

    @GetMapping({"/"})
    public String hello(Model model) {
        model.addAttribute("items", this.items);
        return "table.html";
    }
}
