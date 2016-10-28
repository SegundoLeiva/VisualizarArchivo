package com.hochschild.visualizarArchivo.alfresco;
import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;

/**
 * Shows how to use CMIS to create a document using the Alfresco Public API.
 * Also uses the REST API to like a folder and comment on a document.
 * 
 * @author jpotts
 *
 */
public class CreateCommentExample extends BaseOnPremExample {


	public String enviarArchivo(String idArchivo,String idSolicitud,String ruta, String type) {
		try {
			
			ResourceBundle bundle = ResourceBundle.getBundle("config");
            String rootFolderId = getRootFolderId(bundle.getString("site"));
			Folder subFolder = createFolder(rootFolderId, bundle.getString("folder_name"));
	        
			// Create a test document in the subFolder
			Document document = createDocument(idArchivo, idSolicitud, subFolder, new File(ruta),"application/"+type, null);

			// Create a comment on the test document
			// NOTE: When dealing with documents, the REST API wants a versionSeriesID! 
			comment(document.getVersionSeriesId(), "Here is a comment!");
			return document.getId();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return null;
	}
	
	public void eliminarArchivo(String idArchivo,String idSolicitud) throws IOException {
			
			ResourceBundle bundle = ResourceBundle.getBundle("config");
            String rootFolderId = getRootFolderId(bundle.getString("site"));
			Folder subFolder = createFolder(rootFolderId, bundle.getString("folder_name"));	        
			eliminarDocumento(idArchivo, idSolicitud, subFolder);
	}

}

