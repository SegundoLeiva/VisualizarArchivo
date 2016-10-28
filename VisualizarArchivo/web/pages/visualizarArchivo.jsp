<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap-theme.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/bootstrap.min.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/fileinput.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/fileinput.min.css" />
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-1.11.3.min.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/jquery/jquery-ui.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/fileinput.js"></script>
<script language="javascript" type="text/javascript" src="<%=request.getContextPath()%>/js/fileinput.min.js"></script>
 

<title>:: Visualizar Archivo::</title>
</head>
<body>

	<input type="hidden" id="codigoArchivo" value="${codigoArchivo}">
	<input type="hidden" id="codigobase64" value="${codigobase64}">
	<input type="hidden" id="nombreArchivo" value="${nombreArchivo}">
	<div class="container">
		<div class="header clearfix">
			<h3 class="text-muted">Visualizador de Archivo</h3>
		</div>

		<div class="jumbotron">
			<p class="lead">Nombre de Archivo: ${nombreArchivo}</p>		
			<div>
				<div class="btn-group">				
					<button type="button" class="btn btn-lg btn-success dropdown-toggle" onclick="visualizarArchivo()">
						Visualizar<span class=""></span>
					</button>
				</div>
				<div class="btn-group">
					<a id="btnDescargar" class="btn btn-lg btn-warning dropdown-toggle" href="<c:url value="descargarArchivo.htm"/>">
						Descargar
					</a>
				</div>
			</div>

		</div>
	</div>
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title" id="myModalLabel">${nombreArchivo}</h4>
      </div>
      <div class="modal-body" align="center">
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">Cerrar</button>
      </div>
    </div>
  </div>
</div>

</body>
  <script type="text/javascript">  
  
  function visualizarArchivo(){	  
		$(".modal-body img").remove();
		$(".modal-body iframe").remove();
		var codigo64 = $("#codigobase64").val();
		var ext = $("#nombreArchivo").val().split(".")[1];
 		if(ext.toLowerCase()=="png" || ext.toLowerCase()=="jpg"){
 			$("#myModal").modal("show");
 			$(".modal-body").append("<img src='data:image/"+ext+";base64,"+codigo64+"' />")
 		}else if(ext.toLowerCase()=="pdf"){
 			$("#myModal").modal("show");
 			$(".modal-body").append("<iframe width='100%' height='500px' src='data:application/pdf;base64,"+codigo64+"'></iframe>")
 		}else{
 			var href = $("#btnDescargar").attr("href");
 			window.location = href;
 		}
	}
  </script>
</html>


