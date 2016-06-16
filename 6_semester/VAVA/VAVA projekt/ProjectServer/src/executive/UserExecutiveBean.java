package executive;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import dao.UserDAO;
import model.Champion;
import model.User;

/**
 * Session Bean implementation class UserExecutiveBean
 * This bean is used to register new user, login (get registered user) and create new user.
 * @author Ivan Gulis
 */
@Stateless
public class UserExecutiveBean {

	@PersistenceContext
    private EntityManager entityManager;
	private UserDAO userDAO = new UserDAO();
	
	/**
	 * Get object of user
	 * @param username - name of user
	 * @return
	 * @throws PersistenceException
	 */
	public User getUser(String username) throws PersistenceException { //catching and logging on the other side
		User user = null;
		user = userDAO.getUser(entityManager, username);
		for (Champion c : user.getPool())
			entityManager.detach(c);
		entityManager.detach(user);
		user.setPool(new ArrayList<Champion>(user.getPool()));
		return user;
	}

	/**
	 * Create and insert object of new user into database
	 * @param username - name of user
	 * @param password - password of user
	 * @param salt - adds to password for hash
	 * @param server - game server of user
	 * @param nick - game nick of user
	 * @throws EntityExistsException
	 * @throws IllegalArgumentException
	 */
	public void createAccount(String username, String password, String salt, 
			String server, String nick) throws EntityExistsException, IllegalArgumentException { //catching and logging on the other side
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setSalt(salt);
		user.setServer(server);
		user.setNick(nick);
		userDAO.createAccount(entityManager, user);
	}

	/**
	 * Actualize user into database
	 * @param user - object of user
	 * @throws IllegalArgumentException
	 */
	public void saveUser(User user) throws IllegalArgumentException { //catching and logging on the other side
		userDAO.saveAccount(entityManager, user);
	}

}
