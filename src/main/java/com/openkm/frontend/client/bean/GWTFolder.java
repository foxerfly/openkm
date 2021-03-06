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

package com.openkm.frontend.client.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 * @author jllort
 *
 */
public class GWTFolder implements IsSerializable {
	public static final String TYPE = "okm:folder";

	private String parentPath;
	private String path;
	private String name;
	private boolean hasChildren;
	private Date created;
	private String author;
	private int permissions;
	private boolean subscribed;
	private String uuid;
	private boolean hasNotes = false;	
	private List<GWTNote> notes;
	private Set<GWTUser> subscriptors;
	private Set<GWTFolder> categories;
	private Set<String> keywords;
	private GWTUser user;
	
	public void initMetadata(String fldPath, boolean hasChildren) {
		setAuthor("");
		setCategories(new HashSet<GWTFolder>());
		setCreated(new Date());
		setHasChildren(hasChildren);
		setHasNotes(false);
		setKeywords(new HashSet<String>());
		setName(fldPath.substring(fldPath.lastIndexOf("/")+1));
		setNotes(new ArrayList<GWTNote>());
		setPath(fldPath);
		setParentPath(fldPath.substring(0,fldPath.lastIndexOf("/")));
		setPermissions(GWTPermission.READ);
		setSubscribed(false);
		setSubscriptors(new HashSet<GWTUser>());
		setUuid("");
		setUser(new GWTUser());
	}
	
	public void initMetadata(String fldPath, String name, boolean hasChilds) {
		initMetadata(fldPath, hasChilds);
		setName(name);
	}
	
	public Set<GWTUser> getSubscriptors() {
		return subscriptors;
	}
	
	public void setSubscriptors(Set<GWTUser> subscriptors) {
		this.subscriptors = subscriptors;
	}

	public boolean isHasChildren() {
		return hasChildren;
	}

	public void setHasChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	public String getParentPath() {
		return parentPath;
	}
	
	public void setParentPath(String parentPath) {
		this.parentPath = parentPath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public Date getCreated() {
		return created;
	}
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public int getPermissions() {
		return permissions;
	}

	public void setPermissions(int permissions) {
		this.permissions = permissions;
	}
	
	public boolean isSubscribed() {
		return subscribed;
	}
	
	public void setSubscribed(boolean subscribed) {
		this.subscribed = subscribed;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public boolean isHasNotes() {
		return hasNotes;
	}

	public void setHasNotes(boolean hasNotes) {
		this.hasNotes = hasNotes;
	}
	
	public List<GWTNote> getNotes() {
		return notes;
	}

	public void setNotes(List<GWTNote> notes) {
		this.notes = notes;
	}
	
	public Set<GWTFolder> getCategories() {
		return categories;
	}

	public void setCategories(Set<GWTFolder> categories) {
		this.categories = categories;
	}
	
	public Set<String> getKeywords() {
		return keywords;
	}

	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}
	
	public GWTUser getUser() {
		return user;
	}

	public void setUser(GWTUser user) {
		this.user = user;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("path=").append(path);
		sb.append(", permissions=").append(permissions);
		sb.append(", created=").append(created);
		sb.append(", hasChildren=").append(hasChildren);
		sb.append(", subscribed=").append(subscribed);
		sb.append(", subscriptors=").append(subscriptors);
		sb.append(", uuid=").append(uuid);
		sb.append(", keywords=").append(keywords);
		sb.append(", categories=").append(categories);
		sb.append(", notes=").append(notes);
		sb.append(", user=").append(user.getId());
		sb.append(", username=").append(user.getUsername());
		sb.append("}");
		return sb.toString();
	}
}