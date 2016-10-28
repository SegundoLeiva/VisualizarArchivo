package com.hochschild.visualizarArchivo.alfresco;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.Action;
import org.apache.chemistry.opencmis.commons.exceptions.CmisUnauthorizedException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

public class CmisDownloadDocument extends BaseOnPremExample {

	public void descargarArchivo(String codigoArchivo,
			HttpServletResponse response) throws IOException {

		Session session = getCmisSession();
		CmisObject object = session.getObject(codigoArchivo);

		String documentPath = "/Sites/hochschild-mining/documentLibrary/Logistica/Solped/"+ object.getName();

		Document templateDocument = (Document) session.getObjectByPath(documentPath);// Original

		if (templateDocument != null) {
			// Make sure the user is allowed to get the content stream (bytes)
			// for the document
			if (templateDocument.getAllowableActions().getAllowableActions().contains(Action.CAN_GET_CONTENT_STREAM) == false) {
				throw new CmisUnauthorizedException(
						"Current user does not have permission to get the content stream for "+ documentPath);
			}

			try {
				response.setHeader("Content-Type", "application/octet-stream");
				response.setHeader("Content-Disposition","attachment; filename=" + templateDocument.getName());

				ServletOutputStream out = response.getOutputStream();
				ContentStream cs = templateDocument.getContentStream();
				InputStream is = cs.getStream();

				int size = 1024;
				int len = 0;
				byte[] data = new byte[size];

				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				while ((len = is.read(data, 0, size)) != -1) {
					bos.write(data, 0, len);
				}

				data = bos.toByteArray();
				IOUtils.write(data, out);
				out.flush();
				out.close();
			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public String visualizarArchivo(String codigoArchivo) throws IOException {

		Session session = getCmisSession();
		CmisObject object = session.getObject(codigoArchivo);
		byte[] buf = null;
		String documentPath = "/Sites/hochschild-mining/documentLibrary/Logistica/Solped/"
				+ object.getName();

		Document templateDocument = (Document) session
				.getObjectByPath(documentPath);// Original

		if (templateDocument != null) {

			InputStream input = null;

			input = templateDocument.getContentStream().getStream();

			int len;
			int size = 1024;

			if (input instanceof ByteArrayInputStream) {
				size = input.available();
				buf = new byte[size];
				len = input.read(buf, 0, size);
			} else {
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				buf = new byte[size];
				while ((len = input.read(buf, 0, size)) != -1)
					bos.write(buf, 0, len);
				buf = bos.toByteArray();
			}
			// Close streams and handle exceptions
			input.close();

		}
		return templateDocument.getName() + "|"	+ Base64.encodeBase64String(buf);
	}
}