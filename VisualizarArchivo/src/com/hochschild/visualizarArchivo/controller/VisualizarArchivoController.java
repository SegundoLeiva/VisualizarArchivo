package com.hochschild.visualizarArchivo.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hochschild.visualizarArchivo.alfresco.CmisDownloadDocument;

@Controller
public class VisualizarArchivoController {

	@RequestMapping(value = "/descargarSAP/{codigoArchivo}", method = RequestMethod.GET)
	public String descargarSAP(@PathVariable("codigoArchivo") String codigoArchivo, Model model,HttpSession sesion) throws IOException {
		sesion.setAttribute("codigoArchivo", codigoArchivo);
		CmisDownloadDocument ccde = new CmisDownloadDocument();
		String archivo =  ccde.visualizarArchivo(codigoArchivo);		
		model.addAttribute("nombreArchivo", archivo.split("[|]")[0]);
		model.addAttribute("codigobase64", archivo.split("[|]")[1]);
		return "visualizarArchivo";
	}
	
	@RequestMapping(value="/descargarArchivo", method = {RequestMethod.GET})
	public void descargarArchivo(HttpServletResponse response,HttpSession sesion) throws IOException {		
		CmisDownloadDocument ccde = new CmisDownloadDocument();
		String codigoArchivo = (String) sesion.getAttribute("codigoArchivo");
		ccde.descargarArchivo(codigoArchivo,response);
	}

}
