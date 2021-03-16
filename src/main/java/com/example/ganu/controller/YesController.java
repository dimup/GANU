package com.example.ganu.controller;


import com.example.ganu.domain.Article;
import com.example.ganu.domain.Output;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

@RestController
@RequestMapping("/Yes")
public class YesController {
    @PostMapping
    public Article get() {
        Output output = new Output();
        BufferedReader br;
        DocumentBuilderFactory factory;
        DocumentBuilder builder;
        Document doc;
        boolean isRain, isSnow, isCloudy, isGloomy;

        factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        isRain = false;
        isSnow = false;
        isCloudy = false;
        isGloomy = false;

        String urlstr = "http://www.kma.go.kr/wid/queryDFSRSS.jsp?zone=1114055000";
        String result = "";
        String line;
        try {
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                result = result + line.trim();// result = URL로 XML을 읽은 값
            }


            InputSource is = new InputSource(new StringReader(result));
            builder = factory.newDocumentBuilder();
            doc = builder.parse(is);
            XPathFactory xpathFactory = XPathFactory.newInstance();
            XPath xpath = xpathFactory.newXPath();
            XPathExpression expr = xpath.compile("//data[day<1]/wfKor");
            NodeList nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            if(nodeList.getLength() == 0) { // 23시 이후부터는 익일의 날씨 출력하도록
                expr = xpath.compile("//data[day<2]/wfKor");
                nodeList = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
            }

            for (int i = 0; i < nodeList.getLength(); i++) {
                NodeList child = nodeList.item(i).getChildNodes();

                for (int j = 0; j < child.getLength(); j++) {
                    Node node = child.item(j);
                    if(node.getTextContent().equals("비") || node.getTextContent().equals("소나기") ) {
                        isRain = true;
                    }
                    if(node.getTextContent().equals("눈")) {
                        isSnow = true;
                    }
                    if(node.getTextContent().equals("구름 많음")) {
                        isCloudy = true;
                    }
                    if(node.getTextContent().equals("흐림")) {
                        isGloomy = true;
                    }
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(isRain) {
            output.setWeather("비");
            if(isSnow) {
                output.setWeather("눈과 비");
            }
        }
        else if (isSnow) {
            output.setWeather("눈");
        }
        else if(isGloomy) {
            output.setWeather("흐림");
        }
        else if(isCloudy) {
            output.setWeather("구름 많음");
        }
        else {
            output.setWeather("맑음");
        }
        return new Article(output);
    }
}
