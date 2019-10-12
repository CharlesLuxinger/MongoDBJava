package br.com.mongodb.escola.repository;

import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.mongodb.escola.model.Aluno;

@Repository
public class AlunoRepository {

	public void salvar(Aluno aluno) {
		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("test");
		MongoCollection<Aluno> alunosCollection = database.getCollection("alunos", Aluno.class);

		alunosCollection.insertOne(aluno);

		client.close();
	}
}
