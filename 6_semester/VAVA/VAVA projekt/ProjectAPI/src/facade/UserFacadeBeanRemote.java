package facade;

import javax.ejb.Remote;

import model.User;

/**
 * Interface of user facade bean interface for creating new user, 
 * load existing user and save him.
 * @author Ivan Gulis
 */
@Remote
public interface UserFacadeBeanRemote {

	public void createAccount(String username, String password, 
			String salt, String server, String nick) throws RuntimeException;
	public User getUser(String username) throws RuntimeException;
	void saveUser(User user) throws IllegalArgumentException;

}
