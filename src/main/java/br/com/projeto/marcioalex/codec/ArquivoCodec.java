package br.com.projeto.marcioalex.codec;

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

import br.com.projeto.marcioalex.modelo.Arquivo;

public class ArquivoCodec implements CollectibleCodec<Arquivo> {
	
	private Codec<Document> codec;
	
	public ArquivoCodec(Codec<Document> codec) {
		this.codec = codec;
	}

	@Override
	public void encode(BsonWriter writer, Arquivo Arquivo, EncoderContext encoder) {
		ObjectId id = Arquivo.getId();
		String title = Arquivo.getTitle();
		Integer duration = Arquivo.getDuration();
		Date endTime = Arquivo.getEndTime();
		Date startTime = Arquivo.getStartTime();
		String reconcileKey = Arquivo.getReconcileKey();
		String idJobCorte = Arquivo.getIdJobCorte();
		String statusJobCorte = Arquivo.getStatusJobCorte();
		String name = Arquivo.getName();
		
		Document document = new Document();
		
		document.put("_id", id);
		document.put("title", title);
		document.put("name", name);
		document.put("duration", duration);
		document.put("endTime", endTime);
		document.put("startTime", startTime);
		document.put("reconcileKey", reconcileKey);
		document.put("idJobCorte", idJobCorte);
		document.put("statusJobCorte", statusJobCorte);
		
		codec.encode(writer, document, encoder);
		
	}

	@Override
	public Class<Arquivo> getEncoderClass() {
		return Arquivo.class;
	}

	@Override
	public Arquivo decode(BsonReader reader, DecoderContext decoder) {
		Document document = codec.decode(reader, decoder);
		
		Arquivo Arquivo = new Arquivo();
		Arquivo.setId(document.getObjectId("_id"));
		Arquivo.setTitle(document.getString("title"));
		Arquivo.setName(document.getString("name"));
		Arquivo.setDuration(document.getInteger("duration"));
		Arquivo.setStartTime(document.getDate("startTime"));
		Arquivo.setEndTime(document.getDate("endTime"));
		Arquivo.setReconcileKey(document.getString("reconcileKey"));
		Arquivo.setIdJobCorte(document.getString("idJobCorte"));
		Arquivo.setStatusJobCorte(document.getString("statusJobCorte"));
		
		return Arquivo;
	}

	@Override
	public boolean documentHasId(Arquivo Arquivo) {
		return Arquivo.getId() == null;
	}

	@Override
	public Arquivo generateIdIfAbsentFromDocument(Arquivo Arquivo) {
		return documentHasId(Arquivo) ? Arquivo.criarId() : Arquivo;
	}

	@Override
	public BsonValue getDocumentId(Arquivo Arquivo) {
		if (!documentHasId(Arquivo)) {
			throw new IllegalStateException("Esse Document nao tem id");
		}
		
		return new BsonString(Arquivo.getId().toHexString());
	}

}
