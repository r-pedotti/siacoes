package br.edu.utfpr.dv.siacoes.view;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.NativeSelect;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import br.edu.utfpr.dv.siacoes.bo.DocumentBO;
import br.edu.utfpr.dv.siacoes.bo.UserBO;
import br.edu.utfpr.dv.siacoes.model.Document;
import br.edu.utfpr.dv.siacoes.model.User;
import br.edu.utfpr.dv.siacoes.model.Module.SystemModule;
import br.edu.utfpr.dv.siacoes.model.User.UserProfile;
import br.edu.utfpr.dv.siacoes.window.EditDocumentWindow;
import br.edu.utfpr.dv.siacoes.window.EditUserWindow;

public class UserView extends ListView {
	
	public static final String NAME = "users";
	
	private final NativeSelect comboProfile;
	private final TextField textName;
	private final CheckBox checkActive;
	private final CheckBox checkExternal;
	
	public UserView(){
		super(SystemModule.GENERAL);
		
		this.setProfilePerimissions(UserProfile.ADMINISTRATOR);
		
		this.comboProfile = new NativeSelect("Perfil");
		this.comboProfile.setWidth("400px");
		this.comboProfile.setNullSelectionAllowed(false);
		this.comboProfile.addItem(UserProfile.STUDENT);
		this.comboProfile.addItem(UserProfile.PROFESSOR);
		this.comboProfile.addItem(UserProfile.ADMINISTRATOR);
		this.comboProfile.addItem("Todos");
		this.comboProfile.select("Todos");
		
		this.textName = new TextField("Nome:");
		this.textName.setWidth("400px");
		
		this.checkActive = new CheckBox("Somente usu�rios ativos");
		this.checkActive.setValue(true);
		
		this.checkExternal = new CheckBox("Somente usu�rios externos");
		
		VerticalLayout vl = new VerticalLayout(this.checkActive, this.checkExternal);
		vl.setSpacing(true);
		
		this.addFilterField(new HorizontalLayout(this.textName, this.comboProfile, vl));
		
		this.setDeleteVisible(false);
	}
	
	protected void loadGrid(){
		this.getGrid().addColumn("Login", String.class);
		this.getGrid().addColumn("Nome", String.class);
		this.getGrid().addColumn("Email", String.class);
		this.getGrid().addColumn("Perfil", String.class);
		
		this.getGrid().getColumns().get(0).setWidth(300);
		
		try {
			int profile = -1;
			
			if(!this.comboProfile.getValue().equals("Todos")){
				profile = ((UserProfile)this.comboProfile.getValue()).getValue();
			}
			
			UserBO bo = new UserBO();
	    	List<User> list = bo.list(this.textName.getValue(), profile, this.checkActive.getValue(), this.checkExternal.getValue());
	    	
	    	for(User u : list){
				Object itemId = this.getGrid().addRow(u.getLogin(), u.getName(), u.getEmail(), u.getProfile().toString());
				this.addRowId(itemId, u.getIdUser());
			}
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			
			Notification.show("Listar Usu�rios", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
    }

	@Override
	public void addClick() {
		UI.getCurrent().addWindow(new EditUserWindow(null, this));
	}

	@Override
	public void editClick(Object id) {
		try {
			UserBO bo = new UserBO();
			User user = bo.findById((int)id);
			
			UI.getCurrent().addWindow(new EditUserWindow(user, this));
		} catch (Exception e) {
			Logger.getGlobal().log(Level.SEVERE, e.getMessage(), e);
			
			Notification.show("Editar Usu�rio", e.getMessage(), Notification.Type.ERROR_MESSAGE);
		}
	}

	@Override
	public void deleteClick(Object id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void filterClick() {
		// TODO Auto-generated method stub
		
	}

}
