package app.views.forms;

import java.util.List;

import javax.swing.JComboBox;

import core.entities.Aluno;
import core.entities.Entidade;
import core.entities.Professor;
import infra.dao.DAO;
import app.views.gui.AlunoPanel;

public class AlunoForm {
	private Aluno aluno;
	private AlunoPanel alunoPanel;
	private FiliadoForm filiadoForm;
	
	public AlunoForm(AlunoPanel alunoPanel) {
		init(alunoPanel, new Aluno());
	}
	
	public AlunoForm(AlunoPanel alunoPanel, Aluno aluno){
		init(alunoPanel, aluno);
		setAluno(aluno);
	}
	
	private void init(AlunoPanel alunoPanel, Aluno aluno){
		this.aluno = aluno;
		this.alunoPanel = alunoPanel;
		this.filiadoForm = new FiliadoForm(alunoPanel.getFiliadoPanel());
		populaProfessorCombo();
		populaEntidadeCombo();
	}
	
	public void setAluno(Aluno aluno) {
		this.aluno = aluno; 
		filiadoForm.setFiliado(aluno.getFiliado());
		setProfessor(aluno.getProfessor());
		setEntidade(aluno.getEntidade());
	}
	
	public Aluno getAluno() {
		aluno.setFiliado(filiadoForm.getFiliado());
		aluno.setProfessor(getProfessor());
		aluno.setEntidade(getEntidade());
		return aluno;
	}

	public void populaProfessorCombo(){
		JComboBox professorCombo = alunoPanel.getProfessor();
		List<Professor> resultProfessores = null;
		try{
			resultProfessores  = new DAO<Professor>(Professor.class).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		for (Professor p : resultProfessores) {
			professorCombo.addItem(p);
		}
		professorCombo.setSelectedItem(null);
	}
	
	public void populaEntidadeCombo(){
		JComboBox entidadeCombo = alunoPanel.getEntidade();
		List<Entidade> resultEntidades = null;	
		try{
			resultEntidades = new DAO<Entidade>(Entidade.class).list();
		}catch (Exception e) {
			e.printStackTrace();
		}
		for (Entidade e : resultEntidades) {
			entidadeCombo.addItem(e);
		}
		entidadeCombo.setSelectedItem(null);
	}
	
	/* Métodos de acesso ao dados da GUI */
	public Entidade getEntidade() {
		return (Entidade) alunoPanel.getEntidade().getSelectedItem();
	}
	public Professor getProfessor() {
		return (Professor) alunoPanel.getProfessor().getSelectedItem();
	}
	
	/* Métodos modificadores dos dados dad GUI */
	public void setEntidade(Entidade entidade) {
		alunoPanel.getEntidade().setSelectedItem(entidade);
	}
	public void setProfessor(Professor professor) {
		alunoPanel.getProfessor().setSelectedItem(professor);
	}
}