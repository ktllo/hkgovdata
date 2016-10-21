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
			BinaryData.this.data = this.toByteArray();
		}
	}
}
