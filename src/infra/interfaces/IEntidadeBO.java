package infra.interfaces;

import java.util.List;

import core.entities.Entidade;

public interface IEntidadeBO {
	public void createEntidade(Entidade entidade) throws Exception;
	public void updateEntidade(Entidade entidade) throws Exception;
	public List<Entidade> searchEntidade(Entidade entidade) throws Exception;
	public List<Entidade> listAll() throws Exception;
}
