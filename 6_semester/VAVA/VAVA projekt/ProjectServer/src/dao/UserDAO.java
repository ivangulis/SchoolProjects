package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.User;

/**
 * Class is used for communication with database around users:
 * Save user, get specified user or create new user.
 * @author Ivan Gulis
 */
public class UserDAO {
	
	/** Get user from database by username */
	public User getUser(EntityManager entityManager, String username) {
		TypedQuery<User> query = entityManager.createNamedQuery("User.getUser", User.class);
		query.setParameter("paramName", username);
		return query.getSingleResult();
	}

	/** Save new user into database */
	public void createAccount(EntityManager entityManager, User user) {
		entityManager.persist(user);
	}
	
	/** Actualize user from database */
	public void saveAccount(EntityManager entityManager, User user) {
		entityManager.merge(user);
	}
}
