package com.grlife.newscrawler.repository;

import com.google.gson.Gson;
import org.springframework.boot.json.JsonParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.sql.DataSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JdbcNewsRepository implements NewsRepository {

    private final DataSource dataSource;

    public JdbcNewsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Map<String, Object>> getRssData(Map<String, Object> reqMap) {

        SimpleDateFormat oldFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
        SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd HH:mm:ss");
        SimpleDateFormat timeSTFormat = new SimpleDateFormat("MMddHHmmss");

        String base = (String) reqMap.get("base"); // 신문사
        String url = (String) reqMap.get("url"); // url
        String tag = (String) reqMap.get("tag"); // 파싱대상태그
        String title = (String) reqMap.get("title"); // 타이틀
        String time = (String) reqMap.get("time"); // 시간
        String cont = (String) reqMap.get("cont"); // 내용
        String link = (String) reqMap.get("link"); // 링크
        String uId = (String) reqMap.get("uId"); // 구분id Parameter

        List<Map<String, Object>> rtnList = new ArrayList<Map<String, Object>>();
        Map<String, Object> dataMap = null;

        DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        Document doc = null;

        try {
            dBuilder = dbFactoty.newDocumentBuilder();
            doc = dBuilder.parse(url);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // root tag
        doc.getDocumentElement().normalize();

        // 파싱할 tag
        NodeList nList = doc.getElementsByTagName(tag);

        for(int temp = 0; temp < nList.getLength(); temp++){
            Node nNode = nList.item(temp);
            if(nNode.getNodeType() == Node.ELEMENT_NODE){

                try {

                    Element eElement = (Element) nNode;

                    dataMap = new HashMap<String, Object>();
                    dataMap.put("base", base);
                    dataMap.put("title", getTagValue(title, eElement));


                    String tmpTime1 = getTagValue(time, eElement);
                    Date parsedDate = oldFormat.parse(tmpTime1);
                    String tmpTime2 = newFormat.format(parsedDate);
                    dataMap.put("time", tmpTime2);

                    String _tmpTime1 = getTagValue(time, eElement);
                    Date _parsedDate = oldFormat.parse(_tmpTime1);
                    String _tmpTime2 = timeSTFormat.format(_parsedDate);
                    dataMap.put("timeST", _tmpTime2);


                    dataMap.put("cont", getTagValue(cont, eElement));

                    String tmpLink = getTagValue(link, eElement);

                    dataMap.put("link", tmpLink);
                    dataMap.put("uId", tmpLink.split(uId + "=")[1]);

                    rtnList.add(dataMap);

                } catch (ParseException e) {

                }

            }
        }

        return rtnList;

    }

    // tag값의 정보를 가져오는 메소드
    private static String getTagValue(String tag, Element eElement) {

        NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
        Node nValue = (Node) nlList.item(0);

        if(nValue == null) return null;

        return nValue.getNodeValue();

    }

}
