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

import app.utils.CPFValidator;
import app.utils.DatabaseManager;

public class AddAlunoTest {
	
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
		
		alunoDao = new DAO<Aluno>(Aluno.class, new AlunoValidator(), true);
	}

	public static void clearDatabase(){
		List<Aluno> all = alunoDao.list();
		for (Aluno each : all) {
			alunoDao.delete(each);
		}
		assertEquals(0, alunoDao.list().size());
	}
	
	// Cenário 01
	@Test
	public void ReturnsOk() throws Exception {
		// Arange
		clearDatabase();

		filiado = new Filiado();
		filiado.setNome("Vitorio Lotto");
		filiado.setCpf("39305285848");
		filiado.setDataNascimento(new Date());
		filiado.setDataCadastro(new Date());
		filiado.setEmail("vitorio.lotto@gmail.com");
		filiado.setRg(rg);
		filiado.setTelefone1("11992366841");
		filiado.setId(1332L);

		aluno = new Aluno();
		aluno.setFiliado(filiado);
		aluno.setProfessor(professor);
		aluno.setEntidade(entidade);
		
		// Act
		boolean isAlunoSaved = alunoDao.save(aluno);

		// Assert
		assertEquals(true, isAlunoSaved);
		assertEquals(1, alunoDao.list().size());

		assertEquals("393.052.858-48", CPFValidator.imprimeCPF(alunoDao.get(aluno).getFiliado().getCpf()));
		assertEquals("Vitorio Lotto", alunoDao.get(aluno).getFiliado().getNome());
		assertEquals("vitorio.lotto@gmail.com", alunoDao.get(aluno).getFiliado().getEmail());
		assertEquals("18.889.037-3", alunoDao.get(aluno).getFiliado().getRg().getNumero());
		assertEquals("SSP", alunoDao.get(aluno).getFiliado().getRg().getOrgaoExpedidor());
		assertEquals("11992366841", alunoDao.get(aluno).getFiliado().getTelefone1());
		assertEquals("Kleginaldo Rossi", alunoDao.get(aluno).getProfessor().getFiliado().getNome());
		assertEquals("Arbos", alunoDao.get(aluno).getEntidade().getNome());
		assertEquals("09112-280", alunoDao.get(aluno).getProfessor().getFiliado().getEndereco().getCep());
		assertEquals("Parque Marajoara", alunoDao.get(aluno).getProfessor().getFiliado().getEndereco().getBairro());
	}
	
	// Cenário 02
	@Test
	public void ReturnsInvalidData() throws Exception {
		// Arange
		clearDatabase();

		filiado = new Filiado();
		filiado.setNome("Vitorio Lotto");
		filiado.setCpf("45345234523");
		filiado.setDataNascimento(new Date());
		filiado.setDataCadastro(new Date());
		filiado.setEmail("vitorio.lotto@gmail.com");
		filiado.setRg(rg);
		filiado.setTelefone1("11992366841");
		filiado.setId(1332L);

		aluno = new Aluno();
		aluno.setFiliado(filiado);
		aluno.setProfessor(professor);
		aluno.setEntidade(entidade);
		
		// Act
		boolean isAlunoSaved = alunoDao.save(aluno);

		// Assert
		assertEquals(false, isAlunoSaved);
		assertEquals(0, alunoDao.list().size());

		assertEquals(false, CPFValidator.isCPF(filiado.getCpf()));
	}

	// Cenário 03
	@Test
	public void ReturnsAbsentData() throws Exception {
		// Arange
		clearDatabase();

		filiado = new Filiado();
		filiado.setNome("Vitorio Lotto");
		filiado.setCpf("");
		filiado.setDataNascimento(new Date());
		filiado.setDataCadastro(new Date());
		filiado.setEmail("vitorio.lotto@gmail.com");
		filiado.setRg(rg);
		filiado.setTelefone1("");
		filiado.setId(1332L);

		aluno = new Aluno();
		aluno.setFiliado(filiado);
		aluno.setProfessor(professor);
		aluno.setEntidade(entidade);
		
		// Act
		boolean isAlunoSaved = alunoDao.save(aluno);

		// Assert
		assertEquals(false, isAlunoSaved);
		assertEquals(0, alunoDao.list().size());
	}
	
}