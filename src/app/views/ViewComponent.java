package app.views;

import javax.swing.JPanel;

import core.interfaces.IAppFacade;

public interface ViewComponent {
	public JPanel getGui();
	public void registerFacade(IAppFacade fac);
}
