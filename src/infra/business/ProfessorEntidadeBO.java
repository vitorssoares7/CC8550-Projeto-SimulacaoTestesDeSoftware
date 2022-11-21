package infra.business;

import java.util.List;

import core.entities.ProfessorEntidade;
import infra.dao.DAO;
import app.views.AppView;
import infra.interfaces.IProfessorEntidadeBO;

public class ProfessorEntidadeBO implements IProfessorEntidadeBO {

	DAO<ProfessorEntidade> dao = new DAO<ProfessorEntidade>(ProfessorEntidade.class);
	private AppView view;
	
	
	public ProfessorEntidadeBO(AppView view) {
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
