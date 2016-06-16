package facade;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import executive.UserExecutiveBean;
import model.User;

/**
 * Session Bean implementation class UserFacadeBean
 * This bean is used to provide access for client to methods around user.
 * @author Ivan Gulis
 */
@Stateless
public class UserFacadeBean implements UserFacadeBeanRemote {

	@EJB
    UserExecutiveBean userExecBean;

	@Override
	public void createAccount(String username, String password, String salt, 
			String server, String nick) throws EntityExistsException, IllegalArgumentException {
		userExecBean.createAccount(username, password, salt, server, nick);
	}
	
	@Override
	public User getUser(String username) throws PersistenceException {
		return userExecBean.getUser(username);
	}
	
	@Override
	public void saveUser(User user) throws IllegalArgumentException {
		userExecBean.saveUser(user);
	}
}
