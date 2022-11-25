package usecases.professor;

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
import core.validators.ProfessorValidator;
import infra.interfaces.IDAO;
import infra.dao.DAO;
import org.junit.BeforeClass;
import org.junit.Test;

import app.utils.CPFValidator;
import app.utils.DatabaseManager;

public class UpdateProfessor_ReturnsInvalidDataTest {
	
	private static IDAO<Aluno> alunoDao;
	private static IDAO<Professor> professorDao;
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
		filiadoProf.setCpf("03646445327");
		filiadoProf.setDataNascimento(new Date());
		filiadoProf.setDataCadastro(new Date());
        filiadoProf.setEmail("kleginaldo.rossi@gmail.com");
        filiadoProf.setRg(rg);
		filiadoProf.setTelefone1("11992366841");
		filiadoProf.setId(3332L);
		filiadoProf.setEndereco(endereco);
		
		professor = new Professor();
		professor.setFiliado(filiadoProf);
		
		entidade = new Entidade();
		entidade.setEndereco(endereco);
		entidade.setNome("Arbos");
		entidade.setTelefone1("(086)1234-5432");

        filiado = new Filiado();
		filiado.setNome("Vitorio Lotto");
		filiado.setCpf("39305285848");
		filiado.setDataNascimento(new Date());
		filiado.setDataCadastro(new Date());
		filiado.setEmail("vitorio.lotto@gmail.com");
		filiado.setRg(rg);
		filiado.setTelefone1("11992366841");
		filiado.setId(1332L);
        filiado.setEndereco(endereco);

		aluno = new Aluno();
		aluno.setFiliado(filiado);
		aluno.setProfessor(professor);
		aluno.setEntidade(entidade);
		
		alunoDao = new DAO<Aluno>(Aluno.class, new AlunoValidator(), true);
		professorDao = new DAO<Professor>(Professor.class, new ProfessorValidator(), true);
	}

	public static void clearDatabase(){
		List<Aluno> all = alunoDao.list();
		for (Aluno each : all) {
			alunoDao.delete(each);
		}
        List<Professor> allProfessores = professorDao.list();
		for (Professor each : allProfessores) {
			professorDao.delete(each);
		}
		assertEquals(0, alunoDao.list().size());
		assertEquals(0, professorDao.list().size());
	}
	
	// Cenário 03
	@Test
	public void ReturnsInvalidData() throws Exception {
		// Arange
		clearDatabase();
		assertEquals(0, professorDao.list().size());
		
		professorDao.save(professor);
		assertEquals(1, professorDao.list().size());
		assertEquals("Kleginaldo Rossi", professor.getFiliado().getNome());

		// Act
		Professor a1 = professorDao.get(professor);
		a1.getFiliado().setCpf("45345234523");
		boolean isProfessorUpdated = professorDao.save(a1);
	
		Filiado f = new Filiado();
		f.setNome("Kleginaldo Rossi");
		Professor a = new Professor();
		a.setFiliado(f);

		List<Professor> result = professorDao.search(a);

		// Assert
		assertEquals(false, CPFValidator.isCPF(a1.getFiliado().getCpf()));
		assertEquals(false, isProfessorUpdated);
		assertEquals(1, professorDao.list().size());
		assertEquals(1, result.size());
	}
}