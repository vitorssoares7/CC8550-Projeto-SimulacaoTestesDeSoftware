package core.business;

import java.util.List;

import core.model.beans.ProfessorEntidade;
import core.model.dao.DAO;
import core.model.dao.DAOImpl;
import core.view.AppView;

public class ProfessorEntidadeBOImpl implements ProfessorEntidadeBO {

	DAO<ProfessorEntidade> dao = new DAOImpl<ProfessorEntidade>(ProfessorEntidade.class);
	private AppView view;
	
	
	public ProfessorEntidadeBOImpl(AppView view) {
		this.view = view;
	}

	private void fireModelChangeEvent(List<ProfessorEntidade> relacionamentos) {
		view.handleModelChange(relacionamentos);
	}

	@Override
	public void createProfessorEntidade(List<ProfessorEntidade> relacionamentos)
	throws Exception {
		System.out.println("ProfessorEntidadeBOImpl.createProfessorEntidade()");
		try {
			for (ProfessorEntidade professorEntidade : relacionamentos) {
				dao.save(professorEntidade);
			}
			fireModelChangeEvent(relacionamentos);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException( "Ocorreu um erro ao associar o professor às suas entidades!"
				+ " Verifique se todos os dados foram preenchidos corretamente.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Desculpe, ocorreu um erro desconhecido ao salvar os relacionamentos.");
		}
	}



}
