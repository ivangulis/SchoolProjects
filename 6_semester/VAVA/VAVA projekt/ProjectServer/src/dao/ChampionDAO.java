package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import model.Champion;

/**
 * Class is used for communication with database around champions:
 * Save all champions, get all champions or get 6 most banned champions.
 * @author Ivan Gulis
 */
public class ChampionDAO {

	/** Save all entities of champion into database */
	public void saveAllChampions(EntityManager entityManager, List<Champion> champions) {
		for (Champion champion: champions) {
			entityManager.merge(champion);
		}
	}
	
	/** Load all entities of champion from database */
	public List<Champion> loadFromDbAllChampions(EntityManager entityManager) {
		TypedQuery<Champion> query = entityManager.createNamedQuery("Champion.getChampion", Champion.class);
		return query.getResultList();
	}
	
	/** Load 6 most banned entities of champion from database */
	public List<Champion> getTopBans(EntityManager entityManager) {
		TypedQuery<Champion> query = entityManager.createNamedQuery("Champion.getTopBans", Champion.class);
		query.setMaxResults(6);
		return query.getResultList();
	}

}
