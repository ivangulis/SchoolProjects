package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SuborFakty {
	
	private List<Fakt> fakty;
	
	public SuborFakty(){
		setFakty(new ArrayList<Fakt>());
		try {
			Scanner scanner = new Scanner(new File("fakty.txt"));
			String s;
			while (scanner.hasNextLine() == true){
				s = scanner.nextLine();
				Fakt novy = new Fakt(s.split(" "));
				fakty.add(novy);
			}
			scanner.close();
		} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

	public List<Fakt> getFakty() {
		return fakty;
	}

	public void setFakty(List<Fakt> fakty) {
		this.fakty = fakty;
	}

}
