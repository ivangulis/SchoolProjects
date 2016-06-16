package model;

public class Fakt {
	
	private String[] slova;
	
	public Fakt(String[] s){
		setSlova(s);
	}
	
	public Fakt(){}

	public String[] getSlova() {
		return slova;
	}

	public void setSlova(String[] slova) {
		this.slova = slova;
	}

}
