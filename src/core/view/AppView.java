package core.view;

import core.facade.AppFacade;

public interface AppView {
	public void handleModelChange(Object obj);
	public void displayException(Exception e);
	public void registerFacade(AppFacade facade);
}
