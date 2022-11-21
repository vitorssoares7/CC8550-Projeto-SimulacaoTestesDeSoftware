package infra.business;

import java.util.List;

import core.entities.Professor;
import infra.dao.DAO;
import app.views.AppView;
import infra.interfaces.IProfessorBO;

import app.utils.FiliadoID;

public class ProfessorBO implements IProfessorBO {
	
	private AppView view;
	private static DAO<Professor> dao = new DAO<Professor>(Professor.class);

	public ProfessorBO(AppView view) {
		this.view = view;
	}

	private void fireModelChangeEvent(Professor professor) {
		view.handleModelChange(professor);
	}

	@Override
	public void createProfessor(Professor professor) throws Exception {
		try {
			professor.getFiliado().setId(FiliadoID.getNextID());
			dao.save(professor);
			fireModelChangeEvent(professor);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( "Ocorreu um erro ao cadastrar o professor!"
				+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar o professor.");
		}
	}
	
	@Override
	public void updateProfessor(Professor professor) throws Exception {
		try {
			dao.save(professor);
			fireModelChangeEvent(professor);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( "Ocorreu um erro ao atualizar os dados do professor!"
				+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao atualizar o professor.");
		}
	}

	@Override
	public List<Professor> listAll() throws Exception {
		List<Professor> result;
		try {
			result = dao.list();
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao listar de professores."
				+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao listar professores.");
		}
		return result;
	}

	@Override
	public List<Professor> searchProfessor(Professor professor)
			throws Exception {
		List<Professor> result;
		try {
			result = dao.search(professor);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Ocorreu um erro ao buscar os professores."
				+ " Verifique se todos os dados foram preenchidos corretamente!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao buscar os professores.");
		}
		return result;
	}
}
