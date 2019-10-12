package br.com.mongodb.escola.codecs;

import java.util.Date;

import org.bson.BsonReader;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.BsonWriter;
import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.CollectibleCodec;
import org.bson.codecs.DecoderContext;
import org.bson.codecs.EncoderContext;
import org.bson.types.ObjectId;

import br.com.mongodb.escola.model.Aluno;
import br.com.mongodb.escola.model.Curso;

public class AlunoCodec implements CollectibleCodec<Aluno> {

	private Codec<Document> codec;

	public AlunoCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Aluno aluno, EncoderContext encoderContext) {
		ObjectId id = aluno.getId();
		String nome = aluno.getNome();
		Date dataNascimento = aluno.getDataNascimento();
		Curso curso = aluno.getCurso();

		Document document = new Document();

		document.put("_id", id);
		document.put("nome", nome);
		document.put("dataNascimento", dataNascimento);
		document.put("curso", new Document("nome", curso.getNome()));

		codec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<Aluno> getEncoderClass() {
		return Aluno.class;
	}

	@Override
	public Aluno decode(BsonReader reader, DecoderContext decoderContext) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Aluno generateIdIfAbsentFromDocument(Aluno aluno) {
		return documentHasId(aluno) ? aluno.criarId() : aluno;
	}

	@Override
	public boolean documentHasId(Aluno aluno) {
		return aluno.getId() == null;
	}

	@Override
	public BsonValue getDocumentId(Aluno aluno) {
		if (!documentHasId(aluno)) {
			throw new IllegalStateException("Este Documento não possui ID");
		}

		return new BsonString(aluno.getId().toHexString());
	}

}
