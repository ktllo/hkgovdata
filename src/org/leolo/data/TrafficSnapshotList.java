package org.leolo.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TrafficSnapshotList {
	private static Logger logger = LoggerFactory.getLogger(TrafficSnapshotList.class);

	private static Map<String,TrafficSnapshotListItem> cache = null;
	private static Object lock = new Object();
	
	public static Map<String,TrafficSnapshotListItem> getList(){
		synchronized(lock){
			if(cache==null){
				updateCacheWithLock();
			}
		}
		return cache;
	}
	
	private static void updateCacheWithLock(){
		final String URL = "http://data.one.gov.hk/code/td/imagelist.xml";
		HashMap<String,TrafficSnapshotListItem> map = new HashMap<>();
		try {
			URLConnection connection = new URL(URL).openConnection();
			InputStream xmlIn = connection.getInputStream();
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlIn);
			NodeList nodeList = doc.getElementsByTagName("image");
			for(int i=0;i<nodeList.getLength();i++){
				Node imageNode = nodeList.item(i);
				TrafficSnapshotListItem tsli = new TrafficSnapshotListItem();
				NodeList imageChild = imageNode.getChildNodes();
				for(int j=0;j<imageChild.getLength();j++){
					Node dataNode = imageChild.item(j);
					if(dataNode.getNodeName().equals("key")){
						tsli.setKey(dataNode.getTextContent());
					}else if(dataNode.getNodeName().equals("english-region")){
						tsli.setRegion_en(dataNode.getTextContent());
					}else if(dataNode.getNodeName().equals("chinese-region")){
						tsli.setRegion_zh(dataNode.getTextContent());
					}else if(dataNode.getNodeName().equals("english-description")){
						tsli.setName_en(dataNode.getTextContent());
					}else if(dataNode.getNodeName().equals("chinese-description")){
						tsli.setName_zh(dataNode.getTextContent());
					}else if(dataNode.getNodeName().equals("#text")){
					}else{
						logger.warn("Unexected node {}, with data {}",dataNode.getNodeName(), dataNode.getTextContent());
					}
				}
				logger.debug(tsli.toString());
				map.put(tsli.getKey(), tsli);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cache = map;
	}
	
	public static void invalidateCache(){
		synchronized(lock){
			cache = null;
		}
	}
	
	public static void updateCache(){
		synchronized(lock){
			cache = null;
			updateCacheWithLock();
		}
	}
}

