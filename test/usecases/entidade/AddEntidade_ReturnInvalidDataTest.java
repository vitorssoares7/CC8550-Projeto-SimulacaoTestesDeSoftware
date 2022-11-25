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

public class AddEntidade_ReturnInvalidDataTest {
	
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
		entidade.setTelefone1("(086)1234-54324234242342423423");
		entidade.setCnpj("53531234-5432423423423424234234");
		
		entidadeDao = new DAO<Entidade>(Entidade.class, new EntidadeValidator(), true);
	}

	public static void clearDatabase(){
		List<Entidade> all = entidadeDao.list();
		for (Entidade each : all) {
			entidadeDao.delete(each);
		}
		assertEquals(0, entidadeDao.list().size());
	}
	
	// Cenário 02
	@Test
	public void ReturnsInvalidData() throws Exception {
		// Arange
		clearDatabase();
		
		// Act
		boolean isEntidadeSaved = entidadeDao.save(entidade);

		// Assert
		assertEquals(false, isEntidadeSaved);
		assertEquals(0, entidadeDao.list().size());
	}
}
