package model;

import java.util.ArrayList;

public class Tabula {
	
	/** Obsahuje v�etky pr�spevky v syst�me */
	private ArrayList<Prispevok> databaza = new ArrayList<Prispevok>();

	public ArrayList<Prispevok> getDatabaza() {
		return databaza;
	}

	public void setDatabaza(ArrayList<Prispevok> databaza) {
		this.databaza = databaza;
	}

}
