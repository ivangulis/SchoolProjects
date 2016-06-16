package model.prvky;

public class Bublina {

	/** Bublina má v sebe text */
	private String text;

	/** Môe by aj hranatá */
	private boolean hranata = false;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isHranata() {
		return hranata;
	}

	public void setHranata(boolean hranata) {
		this.hranata = hranata;
	}

}
