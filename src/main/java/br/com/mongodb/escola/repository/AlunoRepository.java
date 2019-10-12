package br.com.mongodb.escola.repository;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import br.com.mongodb.escola.codecs.AlunoCodec;
import br.com.mongodb.escola.model.Aluno;

@Repository
public class AlunoRepository {

	public void salvar(Aluno aluno) {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		AlunoCodec alunoCodec = new AlunoCodec(codec);
		CodecRegistry registries = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(alunoCodec));

		MongoClientOptions clientOptions = MongoClientOptions.builder().codecRegistry(registries).build();

		MongoClient client = new MongoClient("localhost:27017", clientOptions);
		MongoDatabase database = client.getDatabase("test");
		MongoCollection<Aluno> alunosCollection = database.getCollection("alunos", Aluno.class);

		alunosCollection.insertOne(aluno);

		client.close();
	}
}
