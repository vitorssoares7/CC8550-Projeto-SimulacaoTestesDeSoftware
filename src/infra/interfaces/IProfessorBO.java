package infra.interfaces;

import java.util.List;

import core.entities.Professor;

public interface IProfessorBO {
	public void createProfessor(Professor professor) throws Exception;
	public void updateProfessor(Professor professor) throws Exception;
	public List<Professor> searchProfessor(Professor professor) throws Exception;
	public List<Professor> listAll() throws Exception;
}
