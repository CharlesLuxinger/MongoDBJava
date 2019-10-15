package br.com.mongodb.escola.codecs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import br.com.mongodb.escola.model.Habilidade;
import br.com.mongodb.escola.model.Nota;

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
		List<Habilidade> habilidades = aluno.getHabilidades();
		List<Nota> notas = aluno.getNotas();

		Document document = new Document();

		document.put("_id", id);
		document.put("nome", nome);
		document.put("dataNascimento", dataNascimento);
		document.put("curso", new Document("nome", curso.getNome()));

		if (habilidades != null) {
			List<Document> habilidadesDocument = new ArrayList<Document>();

			habilidades.forEach(habilidade ->
			habilidadesDocument.add(new Document("nome", habilidade.getNome())
										.append("nivel", habilidade.getNivel())));

			document.put("habilidades", habilidadesDocument);
		}

		if (notas != null) {
			List<Double> notasList = new ArrayList<Double>();

			notas.forEach(nota -> notasList
					.add(nota.getValor()));

			document.put("notas", notasList);
		}

		codec.encode(writer, document, encoderContext);
	}

	@Override
	public Class<Aluno> getEncoderClass() {
		return Aluno.class;
	}

	@Override
	public Aluno decode(BsonReader reader, DecoderContext decoderContext) {
		Document document = codec.decode(reader, decoderContext);

		Aluno aluno = new Aluno();
		aluno.setId(document.getObjectId("_id"));
		aluno.setNome(document.getString("nome"));
		aluno.setDataNascimento(document.getDate("data_nascimento"));

		Document curso = (Document) document.get("curso");

		if (curso != null) {
			String nome = curso.getString("nome");
			aluno.setCurso(new Curso(nome));
		}

		List<Double> notas = (List<Double>) document.get("notas");

		if (notas != null) {
			List<Nota> notaList = new ArrayList<Nota>();

			notas.forEach(nota -> notaList.add(new Nota(nota)));

			aluno.setNotas(notaList);
		}

		List<Document> habilidadesDoc = (List<Document>) document.get("habilidades");

		if (habilidadesDoc != null) {
			List<Habilidade> habilidadeList = new ArrayList<Habilidade>();

			habilidadesDoc.forEach(
					habilidadeDoc -> habilidadeList
					.add(new Habilidade(habilidadeDoc.getString("nome"),
							habilidadeDoc.getString("nivel"))));

			aluno.setHabilidades(habilidadeList);
		}

		return aluno;
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
