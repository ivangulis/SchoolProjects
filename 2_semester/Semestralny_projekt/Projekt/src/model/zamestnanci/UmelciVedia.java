package model.zamestnanci;

import model.prvky.Kniha;

/** Tieto met�dy vedia v�etci umelci */
public interface UmelciVedia {

	public void nakresli(Kniha komix, int z, int x, int y, String s);

	public String svojStav();
}
