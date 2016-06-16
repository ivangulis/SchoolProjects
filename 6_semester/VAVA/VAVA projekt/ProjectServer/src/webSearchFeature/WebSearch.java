package webSearchFeature;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.jsoup.Connection;

import model.Champion;

/**
 * Class that get document from web URL and parse it.
 * Extracted champions are then sent in return.
 * @author Ivan Gulis
 */
public class WebSearch {
	
	Properties properties;

	public List<Champion> searchForChampions() throws IOException { //logging on the other side
		properties = new Properties();
		List<Champion> champions = null;
		properties.load(this.getClass().getClassLoader().getResourceAsStream("Conf.properties"));
		Connection connection;
		Document document = null;
		connection = Jsoup.connect(properties.getProperty("URL")).userAgent(properties.getProperty("UserAgent"));
		document = connection.get(); //get connection
		champions = new ArrayList<Champion>();
		Elements table1 = document.select("div[id=leftcol]").select("table");
		Elements body = table1.select("tbody");
		Elements rows = body.select("tr");
		for(Element element : rows) { //get champion list
			Elements columns = element.select("td");
		
			String name = columns.get(0).select("a").text();
			Integer rpCost = Integer.parseInt(columns.get(1).attr("data-sortval"));
			Integer ipCost = Integer.parseInt(columns.get(2).attr("data-sortval"));
			Double playRatio = Double.parseDouble(columns.get(3).attr("data-sortval"));
			Double winRatio = Double.parseDouble(columns.get(4).attr("data-sortval"));
			Double banRatio = Double.parseDouble(columns.get(5).attr("data-sortval"));
			String role = columns.get(6).text();
			String released = columns.get(7).text();
			
			Champion champion = new Champion();
			champion.setName(name);
			champion.setRpCost(rpCost);
			champion.setIpCost(ipCost);
			champion.setPlayRatio(playRatio);
			champion.setWinRatio(winRatio);
			champion.setBanRatio(banRatio);
			champion.setRole(role);
			champion.setReleased(released);
			
			champions.add(champion);
		}
		return champions;
	}
}

