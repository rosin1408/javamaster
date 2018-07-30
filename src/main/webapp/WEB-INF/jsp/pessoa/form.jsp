<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Incluir/Alterar Pessoa</title>
	<link type="text/css" rel="stylesheet" href="/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<form class="form-horizontal" action="/pessoa/" method="POST">
			<fieldset>
				<legend>Pessoa</legend>
				${mensagem}
				<input type="hidden" name="codigo" id="codigo" value="${pessoa.id}">
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="nome">Nome</label>
					<div class="col-md-6">
						<input id="nome" name="nome" type="text" class="form-control input-md" required value="${pessoa.nome}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="cpf">CPF</label>
					<div class="col-md-2">
						<input id="cpf" name="cpf" type="text" class="form-control input-md" required value="${pessoa.cpf}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="dataNascimento">Data Nascimento</label>
					<div class="col-md-6">
						<input id="dataNascimento" name="dataNascimento" type="text" class="form-control input-md" required value="${pessoa.dataNascimento}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="funcionario">Funcionario</label>
					<div class="col-md-6">
						<select id="funcionario" name="funcionario" class="form-control input-md">
							<option value="true">Sim</option>
							<option value="false">Não</option>
						</select>
					</div>
				</div>
				
				<!-- <span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp; -->
			</fieldset>
		</form>
	</div>
</body>
</html>