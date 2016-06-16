package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SuborPravidla {
	
	private List<Pravidlo> pravidla;
	
	public SuborPravidla(){
		setPravidla(new ArrayList<Pravidlo>());
		List<Podmienka> pod;
		List<Akcia> akc;
		String m;
		String pom;
		int pocetPod, pocetAkc;
		try {
			Scanner scanner = new Scanner(new File("pravidla.txt"));
			while (scanner.hasNextLine() == true){
				pod = new ArrayList<Podmienka>();
				akc = new ArrayList<Akcia>();
				m = scanner.nextLine();
				pocetPod = Integer.parseInt(scanner.nextLine());
				for (int i=0;i<pocetPod;i++){
					Podmienka novaPod = new Podmienka();
					pom = scanner.nextLine();
					novaPod.setPodmienka(pom.split(" "));
					pod.add(novaPod);
				}
				pocetAkc = Integer.parseInt(scanner.nextLine());
				for (int i=0;i<pocetAkc;i++){
					Akcia novaAkcia = new Akcia();
					pom = scanner.nextLine();
					novaAkcia.setAkcia(pom.split(" "));
					akc.add(novaAkcia);
				}
				scanner.nextLine();
				Pravidlo nove = new Pravidlo(m, pod, akc);
				pravidla.add(nove);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

	public List<Pravidlo> getPravidla() {
		return pravidla;
	}

	public void setPravidla(List<Pravidlo> pravidla) {
		this.pravidla = pravidla;
	}

}
