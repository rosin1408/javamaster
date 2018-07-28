<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Cadastro de Pessoas</title>
	<link type="text/css" rel="stylesheet" href="/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<table class="table table-striped table-bordered display" id="tabela">
			<thead>
				<tr>
					<th>Nome</th>
					<th>Data Nascimento</th>
					<th>CPF</th>
					<th>Funcionario</th>
					<th>Opções</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pessoas}" var="pessoa">
					<tr>
						<td>${pessoa.nome}</td>
						<td>${pessoa.dataNascimento}</td>
						<td>${pessoa.cpf}</td>
						<td>${pessoa.funcionario}</td>
						<td><a href="/pessoa/${pessoa.id}">Alterar </a>
						<a href="/pessoa/${pessoa.id}/excluir">Excluir</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>