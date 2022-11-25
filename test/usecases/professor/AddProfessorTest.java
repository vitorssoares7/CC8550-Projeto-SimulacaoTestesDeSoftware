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

public class AddProfessorTest {
	
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

    private Professor TransposeAlunoData(Aluno aluno) {
        Professor professor = new Professor();

        professor.setFiliado(aluno.getFiliado());

        return professor;
    }
	
	// Cenário 01
	@Test
	public void ReturnsOk_FromAlunoData() throws Exception {
		// Arange
		clearDatabase();
        boolean isAlunoSaved = alunoDao.save(aluno);
        assertEquals(true, isAlunoSaved);
		assertEquals(1, alunoDao.list().size());
		

		Filiado f = new Filiado();
		f.setNome("Vitorio Lotto");
		Aluno a = new Aluno();
		a.setFiliado(f);
		
		// Act
		List<Aluno> result = alunoDao.search(a);
        Professor professor = TransposeAlunoData(result.get(0));
        boolean isProfessorSaved = professorDao.save(professor);

		// Assert
        assertEquals(1, result.size());
        assertEquals(true, isProfessorSaved);

		assertEquals("393.052.858-48", CPFValidator.imprimeCPF(professorDao.get(professor).getFiliado().getCpf()));
		assertEquals("Vitorio Lotto", professorDao.get(professor).getFiliado().getNome());
		assertEquals("vitorio.lotto@gmail.com", professorDao.get(professor).getFiliado().getEmail());
		assertEquals("18.889.037-3", professorDao.get(professor).getFiliado().getRg().getNumero());
		assertEquals("SSP", professorDao.get(professor).getFiliado().getRg().getOrgaoExpedidor());
		assertEquals("11992366841", professorDao.get(professor).getFiliado().getTelefone1());
	}
	
    // Cenário 02
	@Test
	public void ReturnsOk_FromNewData() throws Exception {
		// Arange
		clearDatabase();

		// Act
		boolean isProfessorSaved = professorDao.save(professor);

		// Assert
		assertEquals(true, isProfessorSaved);
		assertEquals(1, professorDao.list().size());

		assertEquals("036.464.453-27", CPFValidator.imprimeCPF(professorDao.get(professor).getFiliado().getCpf()));
		assertEquals("Kleginaldo Rossi", professorDao.get(professor).getFiliado().getNome());
		assertEquals("kleginaldo.rossi@gmail.com", professorDao.get(professor).getFiliado().getEmail());
		assertEquals("18.889.037-3", professorDao.get(professor).getFiliado().getRg().getNumero());
		assertEquals("SSP", professorDao.get(professor).getFiliado().getRg().getOrgaoExpedidor());
		assertEquals("11992366841", professorDao.get(professor).getFiliado().getTelefone1());
	}

	// Cenário 03
	@Test
	public void ReturnsInvalidData() throws Exception {
		// Arange
		clearDatabase();
        professor.getFiliado().setCpf("45345234523");
		
		// Act
		boolean isProfessorSaved = professorDao.save(professor);

		// Assert
		assertEquals(false, isProfessorSaved);
		assertEquals(0, professorDao.list().size());

		assertEquals(false, CPFValidator.isCPF(professor.getFiliado().getCpf()));
	}

	// Cenário 04
	@Test
	public void ReturnsAbsentData() throws Exception {
		// Arange
		clearDatabase();

        professor.getFiliado().setCpf("");
        professor.getFiliado().setTelefone1(null);
		
		// Act
		boolean isProfessorSaved = professorDao.save(professor);

		// Assert
		assertEquals(false, isProfessorSaved);
		assertEquals(0, professorDao.list().size());
	}
	
}