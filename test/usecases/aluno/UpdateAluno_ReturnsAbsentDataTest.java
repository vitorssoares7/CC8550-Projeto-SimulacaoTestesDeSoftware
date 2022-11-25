package usecases.aluno;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import core.entities.Aluno;
import core.entities.Endereco;
import core.entities.Entidade;
import core.entities.Filiado;
import core.entities.Professor;
import core.entities.Rg;
import core.validators.AlunoValidator;
import infra.interfaces.IDAO;
import infra.dao.DAO;
import org.junit.BeforeClass;
import org.junit.Test;

import app.utils.DatabaseManager;

public class UpdateAluno_ReturnsAbsentDataTest {
	
	private static IDAO<Aluno> alunoDao;
	private static Aluno aluno;
	private static Entidade entidade;
	private static Endereco endereco;
	private static Filiado filiado;
	private static Filiado filiadoProf;
	private static Professor professor;
	private static Rg rg;
	
	@BeforeClass
	public static void setUp(){
		DatabaseManager.setEnviroment(DatabaseManager.TEST);

		rg = new Rg();
		rg.setNumero("18.889.037-3");
		rg.setOrgaoExpedidor("SSP");

		filiado = new Filiado();
		filiado.setNome("Vitorio Lotto");
		filiado.setCpf("84157215010");
		filiado.setDataNascimento(new Date());
		filiado.setDataCadastro(new Date());
		filiado.setEmail("vitorio.lotto@gmail.com");
		filiado.setRg(rg);
		filiado.setTelefone1("11 992366841");
		filiado.setRegistroCbj("123");
		filiado.setId(1332L);
		
		endereco = new Endereco();
		endereco.setBairro("Parque Marajoara");
		endereco.setCep("09112-280");
		endereco.setCidade("Santo André");
		endereco.setEstado("SP");
		endereco.setNumero("491");
		endereco.setRua("Rua Professora Maria Losangeles Navarro");
		
		filiadoProf = new Filiado();
		filiadoProf.setNome("Kleginaldo Rossi");
		filiadoProf.setCpf("036.464.453-27");
		filiadoProf.setDataNascimento(new Date());
		filiadoProf.setDataCadastro(new Date());
		filiadoProf.setId(3332L);
		filiadoProf.setEndereco(endereco);
		
		professor = new Professor();
		professor.setFiliado(filiadoProf);
		
		entidade = new Entidade();
		entidade.setEndereco(endereco);
		entidade.setNome("Arbos");
		entidade.setTelefone1("(086)1234-5432");
		
		aluno = new Aluno();
		aluno.setFiliado(filiado);
		aluno.setProfessor(professor);
		aluno.setEntidade(entidade);
		
		alunoDao = new DAO<Aluno>(Aluno.class, new AlunoValidator(), true);
	}

	public static void clearDatabase(){
		List<Aluno> all = alunoDao.list();
		for (Aluno each : all) {
			alunoDao.delete(each);
		}
		assertEquals(0, alunoDao.list().size());
	}
	
	// Cenário 04
	@Test
	public void ReturnsAbsentData() throws Exception {
		// Arange
		clearDatabase();
		assertEquals(0, alunoDao.list().size());
		
		alunoDao.save(aluno);
		assertEquals(1, alunoDao.list().size());
		assertEquals("Vitorio Lotto", aluno.getFiliado().getNome());

		// Act
		Aluno a1 = alunoDao.get(aluno);
		aluno.getFiliado().setNome(null);
		boolean isAlunoUpdated = alunoDao.save(a1);
	
		Filiado f = new Filiado();
		f.setNome("Vitorio Lotto");
		Aluno a = new Aluno();
		a.setFiliado(f);

		List<Aluno> result = alunoDao.search(a);

		// Assert
		assertEquals(false, isAlunoUpdated);
		assertEquals(1, alunoDao.list().size());
		assertEquals(1, result.size());
	}
}