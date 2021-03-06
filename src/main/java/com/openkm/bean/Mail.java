/**
 *  OpenKM, Open Document Management System (http://www.openkm.com)
 *  Copyright (c) 2006-2013  Paco Avila & Josep Llort
 *
 *  No bytes were intentionally harmed during the development of this application.
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package com.openkm.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author pavila
 *
 */
public class Mail implements Serializable {
	private static final long serialVersionUID = -2127459738076624036L;
	
	public static final String MIME_TEXT = "text/plain";
	public static final String MIME_HTML = "text/html";
	
	public static final String TYPE = "okm:mail";
	public static final String SIZE = "okm:size";
	public static final String FROM = "okm:from";
	public static final String REPLY = "okm:reply";
	public static final String TO = "okm:to";
	public static final String CC = "okm:cc";
	public static final String BCC = "okm:bcc";
	public static final String SENT_DATE = "okm:sentDate";
	public static final String RECEIVED_DATE = "okm:receivedDate";
	public static final String SUBJECT = "okm:subject";
	public static final String CONTENT = "okm:content";
	public static final String MIME_TYPE = "okm:mimeType";
	public static final String AUTHOR = "okm:author";
		
	private String path;
	private String uuid;
	private int permissions;
	private String from;
	private String[] reply = new String[]{};
	private String[] to;
	private String[] cc;
	private String[] bcc;
	private Calendar sentDate;
	private Calendar receivedDate;
	private String subject;
	private String content;
	private String mimeType;
	private long size;
	private String author;
	private Calendar created;
	private List<Document> attachments;
	private Set<String> keywords = new HashSet<String>();
	private Set<Folder> categories = new HashSet<Folder>();
	private List<Note> notes = new ArrayList<Note>();
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getReply() {
		return reply;
	}

	public void setReply(String[] reply) {
		this.reply = reply;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public String[] getCc() {
		return cc;
	}

	public void setCc(String[] cc) {
		this.cc = cc;
	}

	public String[] getBcc() {
		return bcc;
	}

	public void setBcc(String[] bcc) {
		this.bcc = bcc;
	}

	public Calendar getSentDate() {
		return sentDate;
	}

	public void setSentDate(Calendar sentDate) {
		this.sentDate = sentDate;
	}

	public Calendar getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Calendar getCreated() {
		return created;
	}

	public void setCreated(Calendar created) {
		this.created = created;
	}
	
	public List<Document> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Document> attachments) {
		this.attachments = attachments;
	}

	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}

	public Set<Folder> getCategories() {
		return categories;
	}

	public void setCategories(Set<Folder> categories) {
		this.categories = categories;
	}
	
	public List<Note> getNotes() {
		return notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("path=").append(path);
		sb.append(", uuid=").append(uuid);
		sb.append(", permissions=").append(permissions);
		sb.append(", size=").append(size);
		sb.append(", from=").append(from);
		sb.append(", reply=").append(Arrays.toString(reply));
		sb.append(", to=").append(Arrays.toString(to));
		sb.append(", sentDate=").append(sentDate==null?null:sentDate.getTime());
		sb.append(", receivedDate=").append(receivedDate==null?null:receivedDate.getTime());
		sb.append(", subject=").append(subject);
		//sb.append(", content=").append(content);
		sb.append(", author=").append(author);
		sb.append(", created=").append(created==null?null:created.getTime());
		sb.append(", mimeType=").append(mimeType);
		sb.append(", keywords=").append(keywords);
		sb.append(", categories=").append(categories);
		sb.append(", notes=").append(notes);
		sb.append(", attachments=").append(attachments);
		sb.append("}");
		return sb.toString();
	}
}
