package com.hochschild.visualizarArchivo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hochschild.visualizarArchivo.alfresco.CmisDownloadDocument;

@Controller
public class VisualizarArchivoController {

	@RequestMapping(value = "/descargarSAP.htm", method = RequestMethod.GET)
	public String descargarSAP(HttpServletRequest req,Model model,HttpSession sesion) throws IOException {
		sesion.setAttribute("codigoArchivo", req.getParameter("codigoArchivo"));
		CmisDownloadDocument ccde = new CmisDownloadDocument();
		String archivo =  ccde.visualizarArchivo(req.getParameter("codigoArchivo"));		
		model.addAttribute("nombreArchivo", archivo.split("[|]")[0]);
		model.addAttribute("codigobase64", archivo.split("[|]")[1]);
		return "visualizarArchivo";
	}
	
	@RequestMapping(value="/descargarArchivo.htm", method = {RequestMethod.GET})
	public void descargarArchivo(HttpServletResponse response,HttpSession sesion) throws IOException {		
		CmisDownloadDocument ccde = new CmisDownloadDocument();
		String codigoArchivo = (String) sesion.getAttribute("codigoArchivo");
		ccde.descargarArchivo(codigoArchivo,response);
	}

}
