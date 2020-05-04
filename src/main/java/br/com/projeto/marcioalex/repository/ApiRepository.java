package br.com.projeto.marcioalex.repository;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.Codec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import br.com.projeto.marcioalex.codec.ArquivoCodec;
import br.com.projeto.marcioalex.modelo.Arquivo;

@Repository
public class ApiRepository{

	private MongoClient cliente;
	private MongoDatabase banco;
	@Value("${url.base}")
	private String URL;
	@Value("${colecao.base}")
	private String COLECAO;
	
	private void criarConexao() {
		Codec<Document> codec = MongoClient.getDefaultCodecRegistry().get(Document.class);
		
		ArquivoCodec arquivoCodec = new ArquivoCodec(codec);
		
		CodecRegistry registro = CodecRegistries.fromRegistries(
				MongoClient.getDefaultCodecRegistry(),
				CodecRegistries.fromCodecs(arquivoCodec)
				);
		
		MongoClientOptions opcoes = MongoClientOptions.builder().codecRegistry(registro).build();
		
		this.cliente = new MongoClient(URL, opcoes);
		this.banco = cliente.getDatabase(COLECAO);
		
	}
	
	public Arquivo salvar(Arquivo arquivo){
		Arquivo arquivoSalvo = null;
		criarConexao();
		MongoCollection<Arquivo> arquivos = this.banco.getCollection("arquivos", Arquivo.class);
		if (arquivo.getId() == null) {
			arquivos.insertOne(arquivo);
		}else{
			arquivos.updateOne(Filters.eq("_id", arquivo.getId()), new Document("$set", arquivo));
		}
		arquivoSalvo = arquivo;
		fecharConexao();
		return arquivoSalvo;
	}

	
	
	public List<Arquivo> findAll(){
		criarConexao();
		MongoCollection<Arquivo> arquivos = this.banco.getCollection("arquivos", Arquivo.class);
		MongoCursor<Arquivo> resultados = arquivos.find().iterator();
		List<Arquivo> arquivosEncontrados = popularArquivos(resultados);
		fecharConexao();
		return arquivosEncontrados;
	}
	
	private List<Arquivo> popularArquivos(MongoCursor<Arquivo> resultados){
		List<Arquivo> arquivos = new ArrayList<>();
		while(resultados.hasNext()){
			arquivos.add(resultados.next());
		}
		return arquivos;
	}
	
	public Arquivo findArquivoById(String id){
		criarConexao();
		MongoCollection<Arquivo> arquivos = this.banco.getCollection("arquivos", Arquivo.class);
		Arquivo arquivo = arquivos.find(Filters.eq("_id", new ObjectId(id))).first();
		return arquivo;
	}
	
	public Arquivo findArquivoByTitle(String title){
		criarConexao();
		MongoCollection<Arquivo> arquivos = this.banco.getCollection("arquivos", Arquivo.class);
		Arquivo arquivo = arquivos.find(Filters.eq("title", new String(title))).first();
		return arquivo;
	}
	
	public Arquivo findArquivoByName(String name){
		criarConexao();
		MongoCollection<Arquivo> arquivos = this.banco.getCollection("arquivos", Arquivo.class);
		Arquivo arquivo = arquivos.find(Filters.eq("name", new String(name))).first();
		return arquivo;
	}
	
	public Arquivo findArquivoByReconcileKey(String key){
		criarConexao();
		MongoCollection<Arquivo> arquivos = this.banco.getCollection("arquivos", Arquivo.class);
		Arquivo arquivo = arquivos.find(Filters.eq("reconcileKey", new String(key))).first();
		return arquivo;
	}

	private void fecharConexao() {
		this.cliente.close();
	}
	
}
