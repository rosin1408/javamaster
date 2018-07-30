<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
	<meta charset="UTF-8">
	<title>Incluir/Alterar Projeto</title>
	<link type="text/css" rel="stylesheet" href="/static/bootstrap-3.3.5-dist/css/bootstrap.min.css">
</head>
<body>
	<div class="container">
		<form class="form-horizontal" action="/projeto/" method="POST">
			<fieldset>
				<legend>Projeto</legend>
				${mensagem}
				${erros}
				<input type="hidden" name="codigo" id="codigo" value="${projeto.id}">
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="nome">Nome</label>
					<div class="col-md-6">
						<input id="nome" name="nome" type="text" class="form-control input-md" required value="${projeto.nome}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="dataInicio">Data de Início</label>
					<div class="col-md-6">
						<input id="dataInicio" name="dataInicio" type="text" class="form-control input-md" value="${projeto.dataInicio}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="dataPrevisaoFim">Data Previsão Fim</label>
					<div class="col-md-6">
						<input id="dataPrevisaoFim" name="dataPrevisaoFim" type="text" class="form-control input-md" value="${pessoa.dataPrevisaoFim}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="dataFim">Data Fim</label>
					<div class="col-md-6">
						<input id="dataFim" name="dataFim" type="text" class="form-control input-md" value="${pessoa.dataFim}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="descricao">Descrição</label>
					<div class="col-md-6">
						<input id="descricao" name="descricao" type="text" class="form-control input-md" value="${pessoa.descricao}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="status">Status</label>
					<div class="col-md-6">
						<select id="status" name="status" class="form-control input-md">
							<option value="EM_ANALISE">Em Análise</option>
							<option value="ANALISE_REALIZADA">Análise Realizada</option>
							<option value="ANALISE_APROVADA">Análise Aprovada</option>
							<option value="INICIADO">Iniciado</option>
							<option value="PLANEJADO">Planejado</option>
							<option value="EM_ANDAMENTO">Em Andamento</option>
							<option value="ENCERRADO">Encerrado</option>
							<option value="CANCELADO">Cancelado</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="risco">Risco</label>
					<div class="col-md-6">
						<select id="risco" name="risco" class="form-control input-md">
							<option value="BAIXO">Baixo</option>
							<option value="MEDIO">Médio</option>
							<option value="ALTO">Alto</option>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="orcamento">Orçamento</label>
					<div class="col-md-6">
						<input id="orcamento" name="orcamento" type="text" class="form-control input-md" value="${pessoa.orcamento}">
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-md-4 control-label" for="gerente">Gerente</label>
					<div class="col-md-6">
						<select id="gerente" name="gerente.id" class="form-control input-md">
							<c:forEach items="${pessoas}" var="pessoa">
								<option value="${ pessoa.id }">${ pessoa.nome }</option>
							</c:forEach>
						</select>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-xs-3 control-label" for="save"></label>
					<div class="col-xs-8">
						<button id="save" name="save" class="btn btn-primary">
							<span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>&nbsp;Salvar
						</button>
					</div>
				</div>
				
				<!-- <span class="glyphicon glyphicon-search" aria-hidden="true"></span>&nbsp; -->
			</fieldset>
		</form>
	</div>
</body>
</html>