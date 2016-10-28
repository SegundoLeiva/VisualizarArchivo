package com.hochschild.visualizarArchivo.alfresco;
import java.io.IOException;

import org.apache.chemistry.opencmis.client.api.Session;

import com.alfresco.api.example.model.ContainerEntry;
import com.alfresco.api.example.model.ContainerList;
import com.alfresco.api.example.model.NetworkEntry;
import com.alfresco.api.example.model.NetworkList;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;

/**
 * This class contains constants and methods that are common across
 * the Alfresco Public API regardless of where the target repository is
 * hosted.
 * 
 * @author jpotts
 *
 */
abstract public class BasePublicAPIExample {
	public static final String SITES_URL = "/public/alfresco/versions/1/sites/";

	private String homeNetwork;


	public String getHomeNetwork() throws IOException {
		if (this.homeNetwork == null) {
			GenericUrl url = new GenericUrl(getAlfrescoAPIUrl());
	        
			HttpRequest request = getRequestFactory().buildGetRequest(url);
	
	        NetworkList networkList = request.execute().parseAs(NetworkList.class);
	        System.out.println("Found " + networkList.list.pagination.totalItems + " networks.");
	        for (NetworkEntry networkEntry : networkList.list.entries) {
	        	if (networkEntry.entry.homeNetwork) {
	        		this.homeNetwork = networkEntry.entry.id;
	        	}
	        }
	
	        if (this.homeNetwork == null) {
	        	this.homeNetwork = "-default-";
	        }
	
	        System.out.println("Your home network appears to be: " + homeNetwork);
		}
	    return this.homeNetwork;
	}

	abstract public String getAlfrescoAPIUrl();	
	abstract public Session getCmisSession();
	abstract public HttpRequestFactory getRequestFactory();
}
