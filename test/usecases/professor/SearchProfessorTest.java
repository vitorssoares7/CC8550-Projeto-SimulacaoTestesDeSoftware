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

import app.utils.DatabaseManager;

public class SearchProfessorTest {
	
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
	
	// Cenário 01
	@Test
	public void ReturnsOk() throws Exception {
		// Arange
		clearDatabase();
		professorDao.save(professor);
		
		Filiado f = new Filiado();
		f.setNome("Kleginaldo Rossi");
		Professor a = new Professor();
		a.setFiliado(f);
		
		// Act
		List<Professor> result = professorDao.search(a);

		// Assert
		assertEquals(1, professorDao.list().size());
		assertEquals(1, result.size());
		assertEquals("03646445327", result.get(0).getFiliado().getCpf());

	}

	// Cenário 02
	@Test
	public void ReturnsNotFound() throws Exception {
        // Arange
        clearDatabase();
        professorDao.save(professor);
        
        Filiado f = new Filiado();
        f.setNome("Klebinho Rossi");
        Professor a = new Professor();
        a.setFiliado(f);
        
        // Act
        List<Professor> result = professorDao.search(a);

		// Assert
		assertEquals(1, professorDao.list().size());
		assertEquals(0, result.size());

	}
	
}