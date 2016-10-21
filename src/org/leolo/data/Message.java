package org.leolo.data;

import java.util.Date;
import java.util.List;

public class Message {

	private List<BinaryData> attachments;
	private String content;
	private Date date;
	private String style;
	private String title;

	public List<BinaryData> getAttachments() {
		return attachments;
	}

	public String getContent() {
		return content;
	}

	public Date getDate() {
		return date;
	}

	public String getStyle() {
		return style;
	}

	public String getTitle() {
		return title;
	}

	public void setAttachments(List<BinaryData> attachments) {
		this.attachments = attachments;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
