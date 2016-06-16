package model;

public class Cvicisko {
	
	public double behaj(Jedinec j){
		int kroky = 0;
		int akt = 0;
		String s;
		int instr;
		int adresa;
		int nasiel;
		int index;
		while (kroky < 500){
			if (j.getMapa().getPocetPokladov() == 0) break;
			index = akt%64;
			s = j.getBunky()[index].substring(0, 2);
			instr = Integer.parseInt(s,2); //premení na èíslo
			s = j.getBunky()[index].substring(2, 8);
			adresa = Integer.parseInt(s,2);
			switch (instr) {
			case 0: inkrementuj(j,adresa);
					break;
			case 1: dekrementuj(j,adresa);
					break;
			case 2: akt = adresa-1; //dalej sa inkrementuje
					break;
			case 3: nasiel = vypis(j,adresa);
					if (nasiel == -1) return j.getFitnes();
					else j.setFitnes(j.getFitnes()+nasiel); 
					break;
			default: break;
			}
			akt++;
			kroky++;
			j.setFitnes(j.getFitnes() - 0.001);
			
		}
		return	j.getFitnes();
	}
	
	public void inkrementuj(Jedinec j, int adresa){
		String pom = new String(j.getBunky()[adresa]);
		int celok = Integer.parseInt(pom,2);
		StringBuilder pomString = new StringBuilder();
		if (celok == 255)
			celok = 0;
		else celok++;
		for (int z = Integer.toBinaryString(celok).length();z<8;z++)
			pomString.append(0);
		pomString.append(Integer.toBinaryString(celok));
		j.getBunky()[adresa] = pomString.toString();
	}
	
	public void dekrementuj(Jedinec j, int adresa){
		String pom = new String(j.getBunky()[adresa]);
		int celok = Integer.parseInt(pom,2);
		StringBuilder pomString = new StringBuilder();
		if (celok == 0)
			celok = 255;
		else celok--;
		for (int z = Integer.toBinaryString(celok).length();z<8;z++)
			pomString.append(0);
		pomString.append(Integer.toBinaryString(celok));
		j.getBunky()[adresa] = pomString.toString();
	}
	
	public int vypis(Jedinec j, int adresa){
		int instr;
		int nasiel = 0;
		instr = Integer.parseInt(j.getBunky()[adresa].substring(6, 8),2);
		switch (instr) {
		case 0: j.setRiadok(j.getRiadok()-1); //H
				j.getPostupnost().add('H');
				if (j.getRiadok() < 0) return -1;
				break;
		case 1: j.setRiadok(j.getRiadok()+1); //D
				j.getPostupnost().add('D');
				if (j.getRiadok() > j.getMapa().getRozmerR()) return -1;
				break;
		case 2: j.setStlpec(j.getStlpec()+1); //P
				j.getPostupnost().add('P');
				if (j.getStlpec() > j.getMapa().getRozmerS()) return -1;
				break;
		case 3: j.setStlpec(j.getStlpec()-1); //L
				j.getPostupnost().add('L');
				if (j.getStlpec() < 0) return -1;
				break;
		default: break;
		}
		if (j.getMapa().getMapa()[j.getRiadok()][j.getStlpec()] == 1){
			nasiel = 1;
			j.getMapa().getMapa()[j.getRiadok()][j.getStlpec()] = 0;
			j.getMapa().setPocetPokladov((j.getMapa().getPocetPokladov() - 1));
		}
		return nasiel;
	}

}
