package com.raymondmarr;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.dom.DOMSource;

public class Application {
    private final static String filePath = "C:\\Users\\rmarr\\Desktop\\test.xml";

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        String element = "LIN";

        countElementsXSL(element, filePath);
        countElementsW3cDom(element, filePath);
        countElementsRegex(element, filePath);

    }

    public static void countElementsXSL(String element, String xmlFile) throws IOException, ParserConfigurationException, SAXException, TransformerException {
        System.out.println("countElementsXSL Running:");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Document xmldoc = factory.newDocumentBuilder().parse(xmlFile);

        String xslFile = "C:\\Users\\rmarr\\IdeaProjects\\XmlElementCounter\\counter.xsl";
        String stylesheet = FileUtils.readFileToString(new File(xslFile), "UTF-8");
        InputStream inputStream = new ByteArrayInputStream(stylesheet.getBytes(Charset.forName("UTF-8")));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        StreamSource styleSource = new StreamSource(inputStream);

        Transformer transformer = transformerFactory.newTransformer(styleSource);
        transformer.setParameter("elementname", element);
        DOMSource source = new DOMSource(xmldoc);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
        System.out.println("\n");
    }


   public static void countElementsW3cDom(String element, String xmlFile) throws ParserConfigurationException, IOException, SAXException {
            System.out.println("countElementsW3cDom Running:");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            Document doc = factory.newDocumentBuilder().parse(xmlFile);
            NodeList nodes = doc.getElementsByTagName(element);
            System.out.println("Total # of Elements \""+element+"\": " + nodes.getLength());
            System.out.println();
    }

    public static void countElementsRegex(String element, String xmlFile) throws IOException {
        System.out.println("countElementsRegex Running:");
        Pattern pattern = Pattern.compile("</"+element+">");
        Matcher matcher = pattern.matcher(fromFile(xmlFile));
        int matchCount = 0;
        while (matcher.find()){
            matchCount++;
        }
        System.out.println("Total # of Elements \""+element+"\": " + matchCount);
    }


    // Converts the contents of a file into a CharSequence
    public static CharSequence fromFile(String filename) throws IOException {
        FileInputStream input = new FileInputStream(filename);
        FileChannel channel = input.getChannel();

        // Create a read-only CharBuffer on the file
        ByteBuffer bbuf = channel.map(FileChannel.MapMode.READ_ONLY, 0, (int)channel.size());
        CharBuffer cbuf = Charset.forName("8859_1").newDecoder().decode(bbuf);
        return cbuf;
    }
}
