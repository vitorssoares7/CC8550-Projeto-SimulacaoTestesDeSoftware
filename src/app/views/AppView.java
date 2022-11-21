package app.views;

import core.interfaces.IAppFacade;

public interface AppView {
	public void handleModelChange(Object obj);
	public void displayException(Exception e);
	public void registerFacade(IAppFacade facade);
}
