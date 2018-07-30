<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Cadastro de Projetos</title>
	<link type="text/css" rel="stylesheet" href="/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
</head>
<body>
	
	<div class="container">
		<table class="table table-striped table-bordered display" id="tabela">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Data Inicio</th>
					<th>Data Previsão Fim</th>
					<th>Data Fim</th>
					<th>Orcamento</th>
					<th>Gerente</th>
					<th>Status</th>
					<th>Risco</th>
					<th>Opções</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${projetos}" var="projeto">
					<tr>
						<td>${projeto.nome}</td>
						<td>${projeto.dataInicio}</td>
						<td>${projeto.dataPrevisaoFim}</td>
						<td>${projeto.dataFim}</td>
						<td>${projeto.orcamento}</td>
						<td>${projeto.gerente.nome}</td>
						<td>${projeto.status}</td>
						<td>${projeto.risco}</td>
						<td>
							<a href="/projeto/${projeto.id}"><span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp;Alterar </a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>