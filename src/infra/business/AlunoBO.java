package infra.business;

import java.util.List;

import core.entities.Aluno;
import infra.dao.DAO;
import app.views.AppView;
import infra.interfaces.IAlunoBO;

import app.utils.FiliadoID;

public class AlunoBO implements IAlunoBO {
	private static DAO<Aluno> dao = new DAO<Aluno>(Aluno.class);
	private AppView view;

	public AlunoBO(AppView view) {
		this.view = view;
	}

	private void fireModelChangeEvent(Aluno aluno) {
		view.handleModelChange(aluno);
	}

	@Override
	public void createAluno(Aluno aluno) throws Exception {
		System.out.println("AlunoBOImpl.createAluno()");
		try {
			aluno.getFiliado().setId(FiliadoID.getNextID());
			dao.save(aluno);
			fireModelChangeEvent(aluno);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( "Ocorreu um erro ao cadastrar o aluno!"
				+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar o aluno.");
		}
	}

	@Override
	public void updateAluno(Aluno aluno) throws Exception{
		try {
			Aluno old = null;
			old = dao.get(aluno);
			if(old!=null){		
				old.copyProperties(aluno);
			}
			fireModelChangeEvent(old);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new IllegalArgumentException( "Ocorreu um erro ao salvar os dados do aluno."
				+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar o aluno.");
		}
	}
	
	

	@Override
	public List<Aluno> searchAluno(Aluno aluno) throws Exception {
		List<Aluno> result;
		try {
			result = dao.search(aluno);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao salvar os dados do aluno."
				+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao buscar os aluno.");
		}
		return result;
	}

	@Override
	public List<Aluno> listAll() throws Exception {
		List<Aluno> result;
		try {
			result = dao.list();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao obter a lista de alunos."
				+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido o obter a lista de alunos.");
		}
		return result;
	}
}