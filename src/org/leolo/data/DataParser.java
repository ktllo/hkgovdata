package org.leolo.data;

import java.util.List;
import java.util.Map;

public interface DataParser {
	
	List<Message> parse(Map<String,String> data);
	List<Message> parse();
	
}
