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

package com.openkm.automation;

import java.io.File;
import java.util.HashMap;

import com.openkm.dao.bean.NodeBase;
import com.openkm.dao.bean.NodeDocument;
import com.openkm.dao.bean.NodeFolder;
import com.openkm.dao.bean.NodeMail;

/**
 * AutomationUtils
 * 
 * @author jllort
 *
 */
public class AutomationUtils {
	public static final String UPLOAD_RESPONSE = "response";
	public static final String PARENT_UUID = "parentUuid";
	public static final String PARENT_PATH = "parentPath";
	public static final String PARENT_NODE = "parentNode";
	public static final String TEXT_EXTRACTED = "textExtracted";
	public static final String DOCUMENT_NODE = "documentNode";
	public static final String DOCUMENT_UUID = "documentUuid";
	public static final String DOCUMENT_FILE = "documentFile";
	public static final String DOCUMENT_NAME = "documentName";
	public static final String DOCUMENT_KEYWORDS = "documentKeywords";
	public static final String DOCUMENT_MIME_TYPE = "documentMimeType";
	public static final String FOLDER_NODE = "folderNode";
	public static final String FOLDER_UUID = "folderUuid";
	public static final String FOLDER_NAME = "folderName";
	public static final String MAIL_NODE = "mailNode";
	public static final String MAIL_UUID = "mailUuid";
	public static final String NODE_UUID = "nodeUuid";
	public static final String NODE_PATH = "nodePath";
	public static final String PROPERTY_GROUP_NAME = "propGroupName";
	public static final String PROPERTY_GROUP_PROPERTIES = "propGroupProperties";
	
	/**
	 * getUuid
	 */
	public static String getUuid(HashMap<String, Object> env) {
		NodeDocument docNode = (NodeDocument) env.get(DOCUMENT_NODE);
		NodeFolder fldNode = (NodeFolder) env.get(FOLDER_NODE);
		NodeMail mailNode = (NodeMail) env.get(MAIL_NODE);
		String docUuid = (String) env.get(DOCUMENT_UUID);
		String uuid = null;
		
		if (docNode != null) {
			uuid = docNode.getUuid();
		} else if (fldNode != null) {
			uuid = fldNode.getUuid();
		} else if (mailNode != null) {
			uuid = mailNode.getUuid();
		} else if (docUuid != null) {
			uuid = docUuid;
		}
		
		return uuid;
	}
	
	/**
	 * getNode
	 */
	public static NodeBase getNode(HashMap<String, Object> env) {
		NodeDocument docNode = (NodeDocument) env.get(DOCUMENT_NODE);
		NodeFolder fldNode = (NodeFolder) env.get(FOLDER_NODE);
		NodeMail mailNode = (NodeMail) env.get(MAIL_NODE);
		
		if (docNode != null) {
			return docNode;
		} else if (fldNode != null) {
			return fldNode;
		} else if (mailNode != null) {
			return mailNode;
		}
		
		return null;
	}
	
	/**
	 * getFile
	 */
	public static File getFile(HashMap<String, Object> env) {
		return (File) env.get(DOCUMENT_FILE);
	}
	
	/**
	 * getParentUuid
	 */
	public static String getParentUuid(HashMap<String, Object> env) {
		return (String) env.get(PARENT_UUID);
	}
	
	/**
	 * getParentPath
	 */
	public static String getParentPath(HashMap<String, Object> env) {
		return (String) env.get(PARENT_PATH);
	}
	
	/**
	 * getTextExtracted
	 */
	public static String getTextExtracted(HashMap<String, Object> env) {
		return (String) env.get(TEXT_EXTRACTED);
	}
	
	/**
	 * getString
	 */
	public static String getString(int index, Object... params) {
		return (String) params[index];
	}
	
	/**
	 * getInterger
	 */
	public static Integer getInterger(int index, Object... params) {
		return (Integer) params[index];
	}
	
	/**
	 * getBoolean
	 */
	public static Boolean getBoolean(int index, Object... params) {
		return (Boolean) params[index];
	}
}