package org.leolo.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TrafficSnapshot implements DataParser {
	Logger logger = LoggerFactory.getLogger(TrafficSnapshotList.class);
	@Override
	public List<Message> parse(Map<String, String> data) {
		String target = data.get("key");
		if(target==null){
			throw new IllegalArgumentException();
		}
		String url = "http://tdcctv.data.one.gov.hk/image?key="+target;
		logger.debug("url is {}",url);
		List<Message> messages = new Vector<>();
		try {
			URLConnection connection = new URL(url).openConnection();
			InputStream xmlIn = connection.getInputStream();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlIn);
			NodeList nodeList = doc.getElementsByTagName("data");
			for(int i=0;i<nodeList.getLength();i++){
				Node node = nodeList.item(i);
				BinaryData image = new BinaryData();
				image.setMimeType("image/jpeg");
				image.setName(target+".jpg");
				image.setData(Base64.decodeBase64(node.getTextContent()));
				Map<String,TrafficSnapshotListItem> listofSnapshot = TrafficSnapshotList.getList();
				Message msg = new Message();
				msg.setContent(listofSnapshot.get(target).getName_en());
				msg.setTitle(listofSnapshot.get(target).getName_en());
				logger.info("Snapshot {} in {} with key {} is retrived.",listofSnapshot.get(target).getName_en(),listofSnapshot.get(target).getRegion_en(),listofSnapshot.get(target).getKey());
				msg.getAttachments().add(image);
				messages.add(msg);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		return messages;
	}

	@Override
	public List<Message> parse() {
		throw new UnsupportedOperationException();
	}

}
