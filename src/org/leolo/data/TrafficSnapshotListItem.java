package org.leolo.data;

public class TrafficSnapshotListItem {
	private String key;
	private String region_en;
	private String name_en;
	private String region_zh;
	private String name_zh;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getRegion_en() {
		return region_en;
	}
	public void setRegion_en(String region_en) {
		this.region_en = region_en;
	}
	public String getName_en() {
		return name_en;
	}
	public void setName_en(String name_en) {
		this.name_en = name_en;
	}
	public String getRegion_zh() {
		return region_zh;
	}
	public void setRegion_zh(String region_zh) {
		this.region_zh = region_zh;
	}
	public String getName_zh() {
		return name_zh;
	}
	public void setName_zh(String name_zh) {
		this.name_zh = name_zh;
	}

	@Override
	public String toString(){
		return "TrafficSnapshotListItem[key="+key+";name={"+name_en+";"+name_zh+"};region={"+region_en+";"+region_zh+"}]";
	}
}
