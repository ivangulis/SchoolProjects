package model.prvky;

public class Bublina {

	/** Bublina m� v sebe text */
	private String text;

	/** M��e by� aj hranat� */
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
