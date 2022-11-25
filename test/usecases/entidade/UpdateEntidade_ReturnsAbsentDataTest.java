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

public class UpdateEntidade_ReturnsAbsentDataTest {
	
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
	
	// Cenário 04
	@Test
	public void ReturnsAbsentData() throws Exception {
		// Arange
		clearDatabase();
		assertEquals(0, entidadeDao.list().size());
		
		entidadeDao.save(entidade);
		assertEquals(1, entidadeDao.list().size());
		assertEquals("Arbos", entidade.getNome());

		// Act
		Entidade e2 = entidadeDao.get(entidade);
		entidade.setNome(null);
		boolean isEntidadeUpdated = entidadeDao.save(e2);
	
		Entidade e = new Entidade();
		e.setNome("Arbos");

		List<Entidade> result = entidadeDao.search(e);

		// Assert
		assertEquals(false, isEntidadeUpdated);
		assertEquals(1, entidadeDao.list().size());
		assertEquals(1, result.size());
	}
}