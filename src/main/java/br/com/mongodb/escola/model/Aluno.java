package br.com.mongodb.escola.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class Aluno {

	private ObjectId id;

	private String nome;

	private Date dataNascimento;

	private Curso curso;

	private List<Nota> notas;

	private List<Habilidade> habilidades;

	private Contato contato;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setDataNascimento(String dataNascimento) {
		Integer year = Integer.valueOf(dataNascimento.substring(0, 4));
		Integer month = Integer.valueOf(dataNascimento.substring(6, 7));
		Integer day = Integer.valueOf(dataNascimento.substring(9, 10));

		this.dataNascimento = new Date(year, month, day);
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Nota> getNotas() {
		if (this.notas == null) {
			this.notas = new ArrayList<Nota>();
		}
		return notas;
	}

	public void setNotas(List<Nota> notas) {
		this.notas = notas;
	}

	public List<Habilidade> getHabilidades() {
		if(this.habilidades == null) {
			this.habilidades = new ArrayList<Habilidade>();
		}
		return habilidades;
	}

	public void setHabilidades(List<Habilidade> habilidades) {
		this.habilidades = habilidades;
	}

	public Aluno criarId() {
		setId(new ObjectId());
		return this;
	}

	public void addHabilidade(Habilidade habilidade) throws IllegalAccessException {
		List<Habilidade> list = this.getHabilidades();

		if (habilidade == null) {
			throw new IllegalAccessException("O parâmetro habilidades é requerido");
		}

		list.add(habilidade);
		this.setHabilidades(list);

	}

	public void addNota(Nota nota) throws IllegalAccessException {
		List<Nota> list = this.getNotas();

		if (nota == null) {
			throw new IllegalAccessException("O parâmetro notas é requerido");
		}

		list.add(nota);
		this.setNotas(list);

	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

}
