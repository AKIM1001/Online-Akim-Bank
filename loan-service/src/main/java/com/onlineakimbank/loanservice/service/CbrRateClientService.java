package com.onlineakimbank.loanservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CbrRateClientService {

    private final RestTemplate restTemplate;

    private static final String CBR_URL =
            "https://www.cbr.ru/scripts/XML_key.asp";

    public BigDecimal getKeyRate() {

        String response = restTemplate.getForObject(CBR_URL, String.class);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

        try {

            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(response)));

            NodeList nodes = doc.getElementsByTagName("Record");

            Element last = (Element) nodes.item(nodes.getLength() - 1);

            String rate = last.getElementsByTagName("Value")
                    .item(0)
                    .getTextContent()
                    .replace(",", ".");

            return new BigDecimal(rate);

        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch CBR key rate", e);
        }
    }
}
