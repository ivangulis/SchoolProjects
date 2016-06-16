package table;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Champion;

/**
 * Object of table row for tables with champions in MainWindow.
 * @author Ivan Gulis
 */
public class TableChampion {
	
	private final Champion champion;
	private final StringProperty name;
	private final StringProperty role;
	private final DoubleProperty winRatio;
	private final DoubleProperty banRatio;

	public TableChampion(Champion champion, String name, String role, Double winRatio, Double banRatio) {
		this.name = new SimpleStringProperty(name);
		this.role = new SimpleStringProperty(role);
		this.winRatio = new SimpleDoubleProperty(winRatio);
		this.banRatio = new SimpleDoubleProperty(banRatio);
		this.champion = champion;
	}

	public String getName() {
		return name.get();
	}

	public String getRole() {
		return role.get();
	}

	public Double getWinRatio() {
		return winRatio.get();
	}

	public Champion getChampion() {
		return champion;
	}

	public Double getBanRatio() {
		return banRatio.get();
	}
	
	

}
