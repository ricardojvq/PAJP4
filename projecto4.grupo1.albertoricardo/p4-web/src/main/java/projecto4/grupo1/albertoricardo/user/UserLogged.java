package projecto4.grupo1.albertoricardo.user;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import projecto4.grupo1.albertoricardo.UserEJBLocal;
import projecto4.grupo1.albertoricardo.UserEntity;
import projecto4.grupo1.albertoricardo.security.PasswordEncryptor;

@Named
@SessionScoped
public class UserLogged implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	private UserEJBLocal userejb;
	
	private UserEntity user;
	private String newName;
	private String newPassword;
	private String result = "";

	public UserLogged() {
		super();
	}
	
	public String doLogout() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ext = context.getExternalContext();
		HttpServletRequest req = (HttpServletRequest) ext.getRequest();
		HttpSession session = req.getSession();
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}
	
	public boolean changeSettings() {
		boolean success = false;
		try {
			if (newName != null && newName != user.getName()) user.setName(newName);
			if (newPassword != null && newPassword != user.getPassword()) user.setPassword(newPassword);
			success = userejb.changeUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (success) {
			FacesMessage msg = new FacesMessage("Estado","Alterações efectuadas com sucesso.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		else {
			FacesMessage msg = new FacesMessage("Estado","Erro nas alterações.");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		return success;
	}
	
	public String deleteAcc() {
		String statement = "";
		boolean success = false;
		try {
			success = userejb.deleteUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (success) {
			statement = invalidateSession();
		} else {
			statement = "Erro ao eliminar a conta.";
		}
		
		System.out.println(statement);
		return statement;
	}
	
	public String invalidateSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext ext = context.getExternalContext();
		HttpServletRequest req = (HttpServletRequest) ext.getRequest();
		HttpSession session = req.getSession();
		session.removeAttribute("logged");
		session.invalidate();
		return "/login.xhtml?faces-redirect=true";
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	public String showEmail() {
		return user.getEmail();
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		PasswordEncryptor pe = new PasswordEncryptor();
		String ePassword = pe.encrypt(newPassword);
		this.newPassword = ePassword;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}



}
