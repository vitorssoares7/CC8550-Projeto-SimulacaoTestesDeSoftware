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

public class AddEntidadeTest {
	
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
		
		// Act
		boolean isEntidadeSaved = entidadeDao.save(entidade);

		// Assert
		assertEquals(true, isEntidadeSaved);
		assertEquals(1, entidadeDao.list().size());

		assertEquals("Arbos", entidadeDao.get(entidade).getNome());
		assertEquals("1191234-5432", entidadeDao.get(entidade).getTelefone1());

		assertEquals("Parque Marajoara", entidadeDao.get(entidade).getEndereco().getBairro());
		assertEquals("09112-280", entidadeDao.get(entidade).getEndereco().getCep());
		assertEquals("Santo André", entidadeDao.get(entidade).getEndereco().getCidade());
		assertEquals("SP", entidadeDao.get(entidade).getEndereco().getEstado());
		assertEquals("491", entidadeDao.get(entidade).getEndereco().getNumero());
		assertEquals("Rua Professora Maria Losangeles Navarro", entidadeDao.get(entidade).getEndereco().getRua());
	}
}