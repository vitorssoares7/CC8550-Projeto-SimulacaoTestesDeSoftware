package infra.interfaces;

import java.util.List;

import core.entities.Aluno;

public interface IAlunoBO {
	public void createAluno(Aluno aluno) throws Exception;
	public void updateAluno(Aluno aluno) throws Exception;
	public List<Aluno> searchAluno(Aluno aluno) throws Exception;
	public List<Aluno> listAll() throws Exception;
}
