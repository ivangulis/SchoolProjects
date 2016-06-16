package executive;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import dao.ChampionDAO;
import model.Champion;
import webSearchFeature.WebSearch;

/**
 * Session Bean implementation class LoginExecutiveBean
 * This bean is used to register new user, login (get registered user) and create new user.
 * @author Ivan Gulis
 */
@Stateless
public class ChampionsExecutiveBean {

	@PersistenceContext
    private EntityManager entityManager;
	private ChampionDAO championDAO = new ChampionDAO();
	
	/**
	 * Save all entities of champion from web into database
	 * @throws IOException
	 * @throws IllegalArgumentException
	 */
	public void saveAllChampions() throws IOException, IllegalArgumentException { //catching and logging on the other side
		List<Champion> allChampions = new ArrayList<Champion>();
		WebSearch webSearch = new WebSearch();
		allChampions = webSearch.searchForChampions();
		championDAO.saveAllChampions(entityManager, allChampions);
	}
	
	/**
	 * Load all entities of champion from database
	 * @return - list of all champions from database
	 * @throws PersistenceException
	 */
	public List<Champion> loadFromDbAllChampions() throws PersistenceException { //catching and logging on the other side
    	List<Champion> allChampions = championDAO.loadFromDbAllChampions(entityManager);
		for (Champion champion : allChampions) {
			entityManager.detach(champion);
		}
    	return allChampions; 
    }
	
	/**
	 * Load 6 most banned entities of champion from database
	 * @return - list of 6 best banned champions from database
	 * @throws PersistenceException
	 */
	public List<Champion> getTopBans() throws PersistenceException { //catching and logging on the other side
    	List<Champion> banList = championDAO.getTopBans(entityManager);
		for (Champion champion : banList) {
			entityManager.detach(champion);
		}
    	return banList; // not success, there are no champions in this list
    }

}
