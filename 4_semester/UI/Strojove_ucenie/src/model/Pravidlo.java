package model;

import java.util.ArrayList;
import java.util.List;

public class Pravidlo {
	private String meno;
	private List<Podmienka> podmienky = new ArrayList<Podmienka>();
	private List<Akcia> akcie = new ArrayList<Akcia>();
	private String[] premenne = new String[26];
	

	public Pravidlo(String m, List<Podmienka> pod, List<Akcia> akc){
		podmienky = new ArrayList<Podmienka>();
		akcie = new ArrayList<Akcia>();
		premenne = new String[26];
		setMeno(m);
		setPodmienky(pod);
		setAkcie(akc);
		for (int i = 0;i<26;i++)
			premenne[i] = "0";
	}
	
	public Pravidlo(){
		for (int i = 0;i<26;i++)
			premenne[i] = "0";
	}
	
	public String[] getPremenne() {
		return premenne;
	}

	public void setPremenne(String[] premenne) {
		this.premenne = premenne;
	}

	public String getMeno() {
		return meno;
	}

	public void setMeno(String meno) {
		this.meno = meno;
	}

	public List<Podmienka> getPodmienky() {
		return podmienky;
	}

	public void setPodmienky(List<Podmienka> podmienky) {
		this.podmienky = podmienky;
	}

	public List<Akcia> getAkcie() {
		return akcie;
	}

	public void setAkcie(List<Akcia> akcie) {
		this.akcie = akcie;
	}

}
