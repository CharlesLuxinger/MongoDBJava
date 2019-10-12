package br.com.mongodb;

import java.util.Arrays;
import java.util.Date;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class Main {
	public static void main(String[] args) {
		// Connection
		MongoClient client = new MongoClient();

		MongoDatabase database = client.getDatabase("test");

		MongoCollection<Document> alunos = database.getCollection("alunos");

		// Query

		Document aluno = alunos.find().first();

		System.out.println(aluno);

		// Insert a new document

		Document newDocument = new Document();

		newDocument.append("nome", "João da Silva")
					.append("data_nascimento", new Date(1966,10,24))
					.append("curso", new Document("nome", "Moda"))
					.append("notas", Arrays.asList(10,9,8))
					.append("habilidades", Arrays.asList(new Document().append("nome", "Inglês")
							.append("nível", "Avançado"),
							new Document().append("nome", "Corte e Costura")
							.append("nível", "Ninja")));

		// alunos.insertOne(newDocument);

		// Update
		alunos.updateOne(Filters.eq("nome", "João da Silva"),
				new Document("$set", new Document("nome", "João Pereira")));
		
		// Delete
		alunos.deleteOne(Filters.eq("nome", "João Pereira"));

		client.close();
	}
}
