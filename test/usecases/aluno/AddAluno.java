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
import models.ValidatorResponse;
import net.java.dev.genesis.annotation.ValidateBefore;
import infra.dao.DAO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import app.utils.CPFValidator;
import app.utils.DatabaseManager;

public class AddAluno {
	
	private static AlunoValidator alunoValidator;
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

		alunoValidator = new AlunoValidator();

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

	
	private ValidatorResponse validateAluno(Aluno aluno, String message) {
		ValidatorResponse response = new ValidatorResponse();
		if (alunoValidator.validate(aluno)) {
			response.setIsSuccess(true);
		}
		else {
			response.setIsSuccess(false);
			response.setErrorMessage(message);
		}
		return response;

	}
	
	// Cenário 01
	@Test
	public void ReturnsOk() throws Exception {
		// Arange
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
		ValidatorResponse response = validateAluno(aluno, null);

		// Assert
		assertEquals(true, response.getIsSuccess());
		assertEquals(null, response.getErrorMessage());

		assertEquals("393.052.858-48", CPFValidator.imprimeCPF(aluno.getFiliado().getCpf()));
		assertEquals("Vitorio Lotto", aluno.getFiliado().getNome());
		assertEquals("vitorio.lotto@gmail.com", aluno.getFiliado().getEmail());
		assertEquals("18.889.037-3", aluno.getFiliado().getRg().getNumero());
		assertEquals("SSP", aluno.getFiliado().getRg().getOrgaoExpedidor());
		assertEquals("11992366841", aluno.getFiliado().getTelefone1());
		assertEquals("Kleginaldo Rossi", aluno.getProfessor().getFiliado().getNome());
		assertEquals("Arbos", aluno.getEntidade().getNome());
		assertEquals("09112-280", aluno.getProfessor().getFiliado().getEndereco().getCep());
		assertEquals("Parque Marajoara", aluno.getProfessor().getFiliado().getEndereco().getBairro());
	}
	
	// Cenário 02
	@Test
	public void ReturnsInvalidData() throws Exception {
		// Arange
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
		ValidatorResponse response = validateAluno(aluno, "CPF inválido");

		// Assert
		assertEquals(false, response.getIsSuccess());
		assertEquals("CPF inválido", response.getErrorMessage());

		assertEquals(false, CPFValidator.isCPF(filiado.getCpf()));
	}

	// Cenário 03
	@Test
	public void ReturnsAbsentData() throws Exception {
		// Arange
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
		ValidatorResponse cpfResponse = validateAluno(aluno, "O campo CPF é obrigatorio");

		ValidatorResponse phoneResponse = validateAluno(aluno, "O campo Telefone 1 é obrigatorio");

		// Assert
		assertEquals(false, cpfResponse.getIsSuccess());
		assertEquals("O campo CPF é obrigatorio", cpfResponse.getErrorMessage());

		assertEquals(false, phoneResponse.getIsSuccess());
		assertEquals("O campo Telefone 1 é obrigatorio", phoneResponse.getErrorMessage());
	}
	
	@AfterClass
	public static void closeDatabase(){
		clearDatabase();
		DatabaseManager.close();
	}
	
}
