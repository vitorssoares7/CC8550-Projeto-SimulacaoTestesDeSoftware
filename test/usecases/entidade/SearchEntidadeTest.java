package usecases.entidade;

import static org.junit.Assert.assertEquals;

import java.util.List;

import core.entities.Endereco;
import core.entities.Entidade;
import core.validators.EntidadeValidator;
import infra.interfaces.IDAO;
import infra.dao.DAO;
import org.junit.BeforeClass;
import org.junit.Test;

import app.utils.DatabaseManager;

public class SearchEntidadeTest {
	
	private static IDAO<Entidade> entidadeDao;
	private static Entidade entidade;
	private static Endereco endereco;
	
	@BeforeClass
	public static void setUp(){
		DatabaseManager.setEnviroment(DatabaseManager.TEST);
		endereco = new Endereco();
		endereco.setBairro("Parque Marajoara");
		endereco.setCep("09112-280");
		endereco.setCidade("Santo André");
		endereco.setEstado("SP");
		endereco.setNumero("491");
		endereco.setRua("Rua Professora Maria Losangeles Navarro");
		
		entidade = new Entidade();
		entidade.setEndereco(endereco);
		entidade.setNome("Arbos");
		entidade.setTelefone1("1191234-5432");
		entidade.setCnpj("53531234-5432");
		
		entidadeDao = new DAO<Entidade>(Entidade.class, new EntidadeValidator(), true);
	}

	public static void clearDatabase(){
		List<Entidade> all = entidadeDao.list();
		for (Entidade each : all) {
			entidadeDao.delete(each);
		}
		assertEquals(0, entidadeDao.list().size());
	}
	
	// Cenário 01
	@Test
	public void ReturnsOk() throws Exception {
		// Arange
		clearDatabase();
		entidadeDao.save(entidade);
		
		Entidade e = new Entidade();
		e.setNome("Arbos");
		
		// Act
		List<Entidade> result = entidadeDao.search(e);

		// Assert
		assertEquals(1, entidadeDao.list().size());
		assertEquals(1, result.size());
		assertEquals("53531234-5432", result.get(0).getCnpj());

	}

	// Cenário 02
	@Test
	public void ReturnsNotFound() throws Exception {
		// Arange
		clearDatabase();
		entidadeDao.save(entidade);
		
		Entidade e = new Entidade();
		e.setNome("Arbos1");
		
		// Act
		List<Entidade> result = entidadeDao.search(e);

		// Assert
		assertEquals(1, entidadeDao.list().size());
		assertEquals(0, result.size());

	}
	
}
