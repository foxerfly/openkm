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

package com.openkm.util.impexp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import com.openkm.bean.Document;
import com.openkm.bean.Version;
import com.openkm.core.AccessDeniedException;
import com.openkm.core.Config;
import com.openkm.core.DatabaseException;
import com.openkm.core.PathNotFoundException;
import com.openkm.core.RepositoryException;
import com.openkm.dao.NodeBaseDAO;
import com.openkm.dao.NodeDocumentDAO;
import com.openkm.dao.NodeFolderDAO;
import com.openkm.dao.NodeMailDAO;
import com.openkm.dao.bean.NodeDocument;
import com.openkm.dao.bean.NodeFolder;
import com.openkm.dao.bean.NodeMail;
import com.openkm.module.DocumentModule;
import com.openkm.module.ModuleManager;
import com.openkm.spring.PrincipalUtils;
import com.openkm.util.FileLogger;

public class DbRepositoryChecker {
	private static Logger log = LoggerFactory.getLogger(DbRepositoryChecker.class);
	private static final String BASE_NAME = DbRepositoryChecker.class.getSimpleName();
	
	private DbRepositoryChecker() {}

	/**
	 * Performs a recursive repository document check
	 */
	public static ImpExpStats checkDocuments(String token, String fldPath, boolean versions, Writer out, 
			InfoDecorator deco) throws PathNotFoundException, AccessDeniedException, RepositoryException,
			IOException, DatabaseException {
		log.debug("checkDocuments({}, {}, {}, {}, {})", new Object[] { token, fldPath, versions, out, deco });
		@SuppressWarnings("unused")
		Authentication auth = null, oldAuth = null;
		ImpExpStats stats = new ImpExpStats();
		
		try {
			if (token == null) {
				auth = PrincipalUtils.getAuthentication();
			} else {
				oldAuth = PrincipalUtils.getAuthentication();
				auth = PrincipalUtils.getAuthenticationByToken(token);
			}
			
			FileLogger.info(BASE_NAME, "Start repository check for ''{0}'", fldPath);
			String uuid = NodeBaseDAO.getInstance().getUuidFromPath(fldPath);
			stats = checkDocumentsHelper(token, uuid, versions, out, deco);
			FileLogger.info(BASE_NAME, "Repository check finalized");
		} catch (PathNotFoundException e) {
			log.error(e.getMessage(), e);
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "PathNotFoundException ''{0}''", e.getMessage());
			throw e;
		} catch (AccessDeniedException e) {
			log.error(e.getMessage(), e);
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "AccessDeniedException ''{0}''", e.getMessage());
			throw e;
		} catch (FileNotFoundException e) {
			log.error(e.getMessage(), e);
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "FileNotFoundException ''{0}''", e.getMessage());
			throw e;
		} catch (RepositoryException e) {
			log.error(e.getMessage(), e);
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "RepositoryException ''{0}''", e.getMessage());
			throw e;
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "IOException ''{0}''", e.getMessage());
			throw e;
		} catch (DatabaseException e) {
			log.error(e.getMessage(), e);
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "DatabaseException ''{0}''", e.getMessage());
			throw e;
		} finally {
			if (token != null) {
				PrincipalUtils.setAuthentication(oldAuth);
			}
		}
		
		log.debug("checkDocuments: {}", stats);
		return stats;
	}

	/**
	 * Performs a recursive repository document check
	 */
	private static ImpExpStats checkDocumentsHelper(String token, String uuid,
			boolean versions, Writer out, InfoDecorator deco) throws FileNotFoundException,
			PathNotFoundException, AccessDeniedException, RepositoryException, IOException,
			DatabaseException {
		log.debug("checkDocumentsHelper({}, {}, {}, {}, {})", new Object[] { token, uuid, versions, out, deco });
		ImpExpStats stats = new ImpExpStats();
		
		// Check documents
		for (NodeDocument nDoc : NodeDocumentDAO.getInstance().findByParent(uuid)) {
			String path = NodeBaseDAO.getInstance().getPathFromUuid(nDoc.getUuid());
			ImpExpStats tmp = readDocument(token, path, versions, out, deco);
			stats.setDocuments(stats.getDocuments() + tmp.getDocuments());
			stats.setFolders(stats.getFolders() + tmp.getFolders());
			stats.setSize(stats.getSize() + tmp.getSize());
			stats.setOk(stats.isOk() && tmp.isOk());
		}
		
		// Check folders
		for (NodeFolder nFld : NodeFolderDAO.getInstance().findByParent(uuid)) {
			ImpExpStats tmp = readFolder(token, nFld, versions, out, deco);
			stats.setDocuments(stats.getDocuments() + tmp.getDocuments());
			stats.setFolders(stats.getFolders() + tmp.getFolders());
			stats.setSize(stats.getSize() + tmp.getSize());
			stats.setOk(stats.isOk() && tmp.isOk());
		}
		
		// Check mails
		for (NodeMail nMail : NodeMailDAO.getInstance().findByParent(uuid)) {
			ImpExpStats tmp = readMail(token, nMail, versions, out, deco);
			stats.setDocuments(stats.getDocuments() + tmp.getDocuments());
			stats.setFolders(stats.getFolders() + tmp.getFolders());
			stats.setSize(stats.getSize() + tmp.getSize());
			stats.setOk(stats.isOk() && tmp.isOk());
		}
		
		log.debug("checkDocumentsHelper: {}", stats);
		return stats;
	}
	
	/**
	 * Read document contents.
	 */
	private static ImpExpStats readDocument(String token, String docPath, boolean versions,
			Writer out, InfoDecorator deco) throws PathNotFoundException, AccessDeniedException,
			RepositoryException, DatabaseException, IOException {
		log.debug("readDocument({})", docPath);
		DocumentModule dm = ModuleManager.getDocumentModule();
		File fsPath = new File(Config.NULL_DEVICE);
		ImpExpStats stats = new ImpExpStats();
		Document doc = dm.getProperties(token, docPath);
		
		try {
			FileOutputStream fos = new FileOutputStream(fsPath);
			InputStream is = dm.getContent(token, docPath, false);
			IOUtils.copy(is, fos);
			IOUtils.closeQuietly(is);
			
			if (versions) { // Check version history
				for (Version ver : dm.getVersionHistory(token, docPath)) {
					is = dm.getContentByVersion(token, docPath, ver.getName());
					IOUtils.copy(is, fos);
					IOUtils.closeQuietly(is);
				}
			}
			
			IOUtils.closeQuietly(fos);
			out.write(deco.print(docPath, doc.getActualVersion().getSize(), null));
			out.flush();
			
			// Stats
			stats.setSize(stats.getSize() + doc.getActualVersion().getSize());
			stats.setDocuments(stats.getDocuments() + 1);
			
			FileLogger.info(BASE_NAME, "Checked document ''{0}''", docPath);
		} catch (RepositoryException e) {
			log.error(e.getMessage());
			stats.setOk(false);
			FileLogger.error(BASE_NAME, "RepositoryException ''{0}''", e.getMessage());
			out.write(deco.print(docPath, doc.getActualVersion().getSize(), e.getMessage()));
			out.flush();
		}
		
		return stats;
	}
	
	/**
	 * Read folder contents. 
	 */
	private static ImpExpStats readFolder(String token, NodeFolder nFld, boolean versions,
			Writer out, InfoDecorator deco) throws FileNotFoundException, PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		String fldPath = NodeBaseDAO.getInstance().getPathFromUuid(nFld.getUuid());
		log.debug("readFolder({})", fldPath);
		FileLogger.info(BASE_NAME, "Checked folder ''{0}''", fldPath);
		ImpExpStats stats = checkDocumentsHelper(token, nFld.getUuid(), versions, out, deco);
		
		// Stats
		stats.setFolders(stats.getFolders() + 1);
		
		return stats;
	}
	
	/**
	 * Read mail contents. 
	 */
	private static ImpExpStats readMail(String token, NodeMail nMail, boolean versions,
			Writer out, InfoDecorator deco) throws FileNotFoundException, PathNotFoundException,
			AccessDeniedException, RepositoryException, IOException, DatabaseException {
		String mailPath = NodeBaseDAO.getInstance().getPathFromUuid(nMail.getUuid());
		log.debug("readMail({})", mailPath);
		FileLogger.info(BASE_NAME, "Checked mail ''{0}''", mailPath);
		ImpExpStats stats = checkDocumentsHelper(token, nMail.getUuid(), versions, out, deco);
		
		// Stats
		stats.setDocuments(stats.getDocuments() + 1);
		
		return stats;
	}
}