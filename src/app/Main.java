package app;

import java.util.Date;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import core.facade.AppFacade;
import core.interfaces.IAppFacade;
import core.entities.Endereco;
import core.entities.Entidade;
import core.entities.Filiado;
import core.entities.Professor;
import infra.dao.DAO;
import app.views.AppView;
import app.views.MainAppView;

public class Main {
	
	public static void main(String[] args) {
//		new StartupHelper().initialize();
//		PlafOptions.setAsLookAndFeel();
//		dbPopulator();
		
		try {
		    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
		        if ("Nimbus".equals(info.getName())) {
		            UIManager.setLookAndFeel(info.getClassName());
		            break;
		        }
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		AppView view = new MainAppView();
		IAppFacade facade = new AppFacade(view);
		view.registerFacade(facade);
	}
	
	public static void dbPopulator(){
		Endereco endereco = new Endereco();
		endereco.setBairro("Dirceu");
		endereco.setCep("64078-213");
		endereco.setCidade("Teresina");
		endereco.setEstado("PI");
		endereco.setRua("Rua Des. Berilo Mota");
		
		Filiado filiadoProf = new Filiado();
		filiadoProf.setNome("Neto");
		filiadoProf.setCpf("036.464.453-27");
		filiadoProf.setDataNascimento(new Date());
		filiadoProf.setDataCadastro(new Date());
		filiadoProf.setId(3332L);
		filiadoProf.setEndereco(endereco);
		
		Professor professor = new Professor();
		professor.setFiliado(filiadoProf);
		
		Entidade entidade = new Entidade();
		entidade.setEndereco(endereco);
		entidade.setNome("Ricardo Paraguasu");
		entidade.setTelefone1("(086)1234-5432");
		
		DAO<Professor> dao = new DAO<Professor>(Professor.class);
		dao.save(professor);
		
		DAO<Entidade> daoEnt = new DAO<Entidade>(Entidade.class);
		daoEnt.save(entidade);
	}
}