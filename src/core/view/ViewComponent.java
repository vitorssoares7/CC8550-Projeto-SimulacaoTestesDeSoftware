package core.view;

import javax.swing.JPanel;

import core.facade.AppFacade;

public interface ViewComponent {
	public JPanel getGui();
	public void registerFacade(AppFacade fac);
}
