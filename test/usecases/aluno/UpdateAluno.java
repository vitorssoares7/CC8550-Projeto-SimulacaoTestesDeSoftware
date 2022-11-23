package usecases.aluno;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import core.entities.Aluno;
import core.entities.Endereco;
import core.entities.Entidade;
import core.entities.Filiado;
import core.entities.Professor;
import infra.interfaces.IDAO;
import infra.dao.DAO;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import app.utils.DatabaseManager;

public class UpdateAluno {
	
	private static IDAO<Aluno> alunoDao;
	private static Aluno aluno;
	private static Entidade entidade;
	private static Endereco endereco;
	private static Filiado filiado;
	private static Filiado filiadoProf;
	private static Professor professor;
	
	@BeforeClass
	public static void setUp(){
		DatabaseManager.setEnviroment(DatabaseManager.TEST);
		filiado = new Filiado();
		filiado.setNome("Vitorio Lotto");
		filiado.setCpf("841.572.150-10");
		filiado.setDataNascimento(new Date());
		filiado.setDataCadastro(new Date());
		filiado.setEmail("vitorio.lotto@gmail.com");
		filiado.setTelefone1("11992366841");
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
		
		alunoDao = new DAO<Aluno>(Aluno.class);
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
	public void  ReturnsOk() throws Exception {
		// Arange
		clearDatabase();
		assertEquals(0, alunoDao.list().size());
		
		alunoDao.save(aluno);
		assertEquals(1, alunoDao.list().size());
		assertEquals("Vitorio Lotto", aluno.getFiliado().getNome());
		
		

		// Act
		Aluno a1 = alunoDao.get(aluno);
		a1.getFiliado().setNome("Jefferson Tomiatti");
		alunoDao.save(a1);
		
		Aluno a2 = alunoDao.get(a1);


		// Assert
		assertEquals("Jefferson Tomiatti", a2.getFiliado().getNome());
		assertEquals(1, alunoDao.list().size());
	}
	
	@Test
	public void updateAluno() throws Exception{
		clearDatabase();
		assertEquals(0, alunoDao.list().size());
		
		alunoDao.save(aluno);
		assertEquals(1, alunoDao.list().size());
		assertEquals("Vitorio Lotto", aluno.getFiliado().getNome());
		
		Aluno a1 = alunoDao.get(aluno);
		a1.getFiliado().setNome("TesteUpdate");
		alunoDao.save(a1);
		
		Aluno a2 = alunoDao.get(a1);
		assertEquals("TesteUpdate", a2.getFiliado().getNome());
		assertEquals(1, alunoDao.list().size());
	}
	
	// @Test
	// public void testListarEAdicionarAlunos(){
	// 	int qtd = alunoDao.list().size();
		
	// 	alunoDao.save(new Aluno());
	// 	assertEquals(qtd+1, alunoDao.list().size());
		
	// 	alunoDao.save(new Aluno());
	// 	assertEquals(qtd+2, alunoDao.list().size());
		
	// 	alunoDao.save(new Aluno());
	// 	assertEquals(qtd+3, alunoDao.list().size());
		
	// 	alunoDao.save(new Aluno());
	// 	assertEquals(qtd+4, alunoDao.list().size());
		
	// 	clearDatabase();
	// 	assertEquals(0, alunoDao.list().size());
		
	// 	alunoDao.save(new Aluno());
	// 	assertEquals(1, alunoDao.list().size());
	// }
	
	@Test
	public void testSearchAluno() throws Exception{
		clearDatabase();
		alunoDao.save(aluno);
		
		Filiado f = new Filiado();
		f.setNome("Vitorio Lotto");
		Aluno a = new Aluno();
		a.setFiliado(f);
		
		List<Aluno> result = alunoDao.search(a);
		assertEquals(1, result.size());
		assertEquals("841.572.150-10", result.get(0).getFiliado().getCpf());
		
		clearDatabase();
		assertEquals(0, alunoDao.search(a).size());
	}
	
	@AfterClass
	public static void closeDatabase(){
		clearDatabase();
		DatabaseManager.close();
	}
	
}
