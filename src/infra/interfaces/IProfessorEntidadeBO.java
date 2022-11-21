package infra.interfaces;

import java.util.List;

import core.entities.ProfessorEntidade;

public interface IProfessorEntidadeBO {
	public void createProfessorEntidade(List<ProfessorEntidade> relacionamentos)
	throws Exception;
}
