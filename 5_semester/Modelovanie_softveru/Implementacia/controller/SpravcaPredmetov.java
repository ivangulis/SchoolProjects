package controller;

import model.BeznyPouzivatel;
import model.PremiovyPouzivatel;


public interface SpravcaPredmetov {
	
	public void odcitajSerpenty(BeznyPouzivatel kupujuci, int hodnota);
	public void odcitajDublony(PremiovyPouzivatel kupujuci, int hodnota);
	
}
