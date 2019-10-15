package br.com.mongodb.escola.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.mongodb.escola.codecs.AlunoCodec;
import br.com.mongodb.escola.model.Aluno;

@Repository
public class AlunoRepository {

	private MongoClient client;
	private MongoCollection<Aluno> alunosCollection;

	public void salvar(Aluno aluno) {
		createConnection();

		if (aluno.getId() == null) {
			alunosCollection.insertOne(aluno);
		} else {
			alunosCollection.updateOne(Filters.eq("_id", aluno.getId()), new Document("$set", aluno));
		}

		closeConnection();
	}

	public Aluno findById(String id) {
		createConnection();

		Aluno aluno = alunosCollection.find(Filters.eq("_id", new ObjectId(id))).first();

		closeConnection();

		return aluno;
	}

	public List<Aluno> findAll() {
		createConnection();

		MongoCursor<Aluno> results = alunosCollection.find().iterator();

		List<Aluno> alunos = addAlunos(results);

		closeConnection();

		return alunos;

	}

	public List<Aluno> findByName(String nome) {
		createConnection();

		MongoCursor<Aluno> results = alunosCollection.find(Filters.eq("nome", nome), Aluno.class).iterator();

		List<Aluno> alunos = addAlunos(results);

		closeConnection();

		return alunos;
	}

	private void closeConnection() {
		client.close();
	}

	private void createConnection() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		CodecRegistry registries = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(alunoCodec));

		MongoClientOptions clientOptions = MongoClientOptions.builder().codecRegistry(registries).build();

		client = new MongoClient("localhost:27017", clientOptions);
		MongoDatabase database = client.getDatabase("test");
		alunosCollection = database.getCollection("alunos", Aluno.class);
	}

	private List<Aluno> addAlunos(MongoCursor<Aluno> results) {

		List<Aluno> alunos = new ArrayList<Aluno>();

		while (results.hasNext()) {
			alunos.add(results.next());

		}

		return alunos;
	}

}
