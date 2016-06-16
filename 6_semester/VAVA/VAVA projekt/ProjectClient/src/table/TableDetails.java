package table;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Object of table row for table with champion detail table in MainWindow.
 * Have name of atribute, and value
 * @author Ivan Gulis
 */
public class TableDetails {
	
	private final StringProperty name;
	private final StringProperty value;

	public TableDetails(String name, String value) {
		this.name = new SimpleStringProperty(name);
		this.value = new SimpleStringProperty(value);
	}

	public String getName() {
		return name.get();
	}

	public String getValue() {
		return value.get();
	}

}
