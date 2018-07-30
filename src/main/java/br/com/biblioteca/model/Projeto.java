package br.com.biblioteca.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Projeto implements Serializable {
	
	private static final long serialVersionUID = -6888524883805929207L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "projeto.nome.obrigatorio")
	private String nome;
	
	@Column(name = "data_inicio")
	private Date dataInicio;
	
	@Column(name = "data_previsao_fim")
	private Date dataPrevisaoFim;
	
	@Column(name = "data_fim")
	private Date dataFim;
	private String descricao;
	
	@Enumerated(EnumType.STRING)
	private Status status;
	private Double orcamento;
	
	@Enumerated(EnumType.STRING)
	private Risco risco;
	
	@ManyToOne
	@JoinColumn(name="idgerente")
	@NotNull(message = "projeto.gerente.obrigatorio")
	private Pessoa gerente;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "membros", 
			joinColumns = { @JoinColumn(name = "idprojeto") },
			inverseJoinColumns = { @JoinColumn(name = "idpessoa") }
	)
	private List<Pessoa> membros;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataPrevisaoFim() {
		return dataPrevisaoFim;
	}

	public void setDataPrevisaoFim(Date dataPrevisaoFim) {
		this.dataPrevisaoFim = dataPrevisaoFim;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Double getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Double orcamento) {
		this.orcamento = orcamento;
	}

	public Risco getRisco() {
		return risco;
	}

	public void setRisco(Risco risco) {
		this.risco = risco;
	}

	public Pessoa getGerente() {
		return gerente;
	}

	public void setGerente(Pessoa gerente) {
		this.gerente = gerente;
	}

	public List<Pessoa> getMembros() {
		return membros;
	}

	public void setMembros(List<Pessoa> membros) {
		this.membros = membros;
	}
}
