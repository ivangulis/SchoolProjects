package facade;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;

import executive.ChampionsExecutiveBean;
import model.Champion;

/**
 * Session Bean implementation class ChampionsFacadeBean
 * This bean is used to provide access for client to methods around champions.
 * @author Ivan Gulis
 */
@Stateless
public class ChampionsFacadeBean implements ChampionsFacadeBeanRemote {

	@EJB
	ChampionsExecutiveBean championsExecBean;
	
	@Override
	public void saveAllChampions() throws IOException, IllegalArgumentException {
		championsExecBean.saveAllChampions();
	}
	
	@Override
	public List<Champion> loadFromDbAllChampions() throws PersistenceException {
		return championsExecBean.loadFromDbAllChampions();
	}
	
	@Override
	public List<Champion> getTopBans() throws PersistenceException {
		return championsExecBean.getTopBans();
	}
}
