package org.leolo.data;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BinaryData implements java.lang.Comparable<BinaryData>{
	
	private String name;
	private String mimeType;
	private byte[] data;
	@Override
	public int compareTo(BinaryData o) {
		return name.compareTo(o.name);
	}
	public String getMimeType() {
		return mimeType;
	}
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public OutputStream setDataStream(){
		return new DataOutputStream();
	}
	
	public InputStream getDataStream(){
		return new ByteArrayInputStream(data);
	}
	
	public class DataOutputStream extends ByteArrayOutputStream{
		@Override
		public void flush() throws IOException{
			super.flush();
			byte [] tmpData = data;
			data = new byte[tmpData.length+buf.length];
			for(int i=0;i<tmpData.length;i++){
				data[i]=tmpData[i];
			}
			for(int i=0;i<buf.length;i++){
				data[i+tmpData.length] = buf[i];
			}
			clear();
		}
	}
	
	public void clear(){
		data = new byte[0];
	}
}
