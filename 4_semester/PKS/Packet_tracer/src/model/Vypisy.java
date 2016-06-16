package model;

import java.util.ArrayList;
import java.util.List;

public class Vypisy {
	
	public String vypisIP(List<IPv4> ipadresy, int pocetIP){ //vypis IP k 1. ulohe
		int j;
		int k;
		int maxOdvysielane = 0;
		int maxIndex = 0;
		StringBuilder celyVypis = new StringBuilder();
		celyVypis.append("IP adresy vysielaj˙cich uzlov:\n");
		for (j=0;j<pocetIP;j++){
			for (k=0;k<8;k+=2){
				celyVypis.append(Integer.valueOf(ipadresy.get(j).getIpcka().substring(k,k+2),16)); //konötrukcia IPËky
				if (k < 6) celyVypis.append(".");
			}
			celyVypis.append("\n");
			/**N·jdenie maxima odvysielan˝ch bytov a jeho IP*/
			if (maxOdvysielane < ipadresy.get(j).getOdvysielane()){
				maxOdvysielane = ipadresy.get(j).getOdvysielane();
				maxIndex = j;
			}
		}
		celyVypis.append("\nAdresa uzla s najv‰ËöÌm poËtom odvysielan˝ch bajtov:\n");
		
		for (k=0;k<8;k+=2){
			celyVypis.append(Integer.valueOf(ipadresy.get(maxIndex).getIpcka().substring(k,k+2),16));
			if (k < 6) celyVypis.append(".");
		}
		celyVypis.append("   " + maxOdvysielane + " bajtov\n");
		return celyVypis.toString();
	}

	public String vypis(List<Ramec> list){ //vypÌöe r·mce istu
		int j;
		StringBuilder celyVypis = new StringBuilder();
		for (Ramec k : list) { 
			celyVypis.append("r·mec " + k.getCislo() + "\n" + 
			        "dÂûka r·mca poskytnut· paketov˝m drajverom - " + k.getDlzkaDriver() + " B\n" + 
			        "dÂûka r·mca pren·öanÈho po mÈdiu - " + k.getDlzkaMedium() + " B\n" + 
			        k.getTyp() + "\n");
			celyVypis.append("Zdrojov· MAC adresa: ");
			for (j = 0; j < 12; j+=2){
				celyVypis.append(k.getCMAC().substring(j, j+2));
				celyVypis.append(" ");
			}
			celyVypis.append("\n");
			celyVypis.append("Cieæov· MAC adresa: ");
			for (j = 0; j < 12; j+=2){
				celyVypis.append(k.getZMAC().substring(j, j+2));
				celyVypis.append(" ");
			}
			celyVypis.append("\n");
        for (j = 2; j <= 1+2*k.getDlzkaDriver(); j+=2) {
        	celyVypis.append(k.getRamec().substring(j-2,j));
        	celyVypis.append(" ");
                if ((j % 16 == 0) && (j != 0)) celyVypis.append(" ");
                if ((j % 32 == 0) && (j != 0)) celyVypis.append("\n");
        }
        celyVypis.append("\n");
        celyVypis.append("\n");
		}
		return celyVypis.toString();
    }
	
	public String vypisTCP(List<Ramec> list, List<Ramec> listSYN, List<Ramec> listFIN_RES, Externy externy){ //vypisuje TCP
		if (listSYN.size() <= 0)
			return "V s˙bore sa nenach·dza ani ukonËen· ani neukonËena komunik·cia danÈho typu \n";
		int i,j;
		int SynAck;
		int Ack;
		int hladamPrvyFin;
		int hladamDruhyFin = 0;
		int hladamPrvyAck = 0;
		int hladamDruhyAck;
		int hladamPrvyFinAck;
		int ukoncenaFinal = -1;
		int[] stats = new int[8];
		for (int y=0;y<8;y++) stats[y] = 0;
		StringBuilder celyVypis = new StringBuilder();
		List<Ramec> komplet = new ArrayList<Ramec>();
		List<Ramec> nekomplet = new ArrayList<Ramec>();
		List<Ramec> finalKomplet = new ArrayList<Ramec>();
		List<Ramec> finalNekomplet = new ArrayList<Ramec>();
		int mamKomplet = -1;
		int mamNekomplet = -1;
		int nasielAckSynNekompletna = -1;
		int otvorenaNekompletna = -1;
		int nasielAckSynKompletna = -1;
		int nasielFinNekompletS = -1;
		int nasielFinNekompletK = -1;
		int otvorenaKompletna = -1;
		int nasielPrvyFin1 = -1, nasielDruhyFin1 = -1, nasielPrvyAck1 = -1; //premenne pre 1. typ konca
		int nasielPrvyFin2 = -1, nasielDruhyFin2 = -1, nasielPrvyAck2 = -1; //premenne pre 2. typ konca
		int nasielPrvyFin3 = -1, nasielPrvyFinAck3 = -1; //pre 3. typ
		int nasielPrvyFin4 = -1, nasielPrvyFinAck4 = -1; //pre 4. typ
		int finExistuje = -1;
		//hladam komplet
		for (i=0;i<listSYN.size();i++){ //kaûda SIN
			if (mamKomplet >= 0) break;
			nasielAckSynKompletna = -1;
			otvorenaKompletna = -1;
			nasielPrvyFin1 = -1; nasielDruhyFin1 = -1; nasielPrvyAck1 = -1; //premenne pre 1. typ konca
			nasielPrvyFin2 = -1; nasielDruhyFin2 = -1; nasielPrvyAck2 = -1; //premenne pre 2. typ konca
			nasielPrvyFin3 = -1; nasielPrvyFinAck3 = -1; //pre 3. typ
			nasielPrvyFin4 = -1; nasielPrvyFinAck4 = -1; //pre 4. typ
			for (SynAck=0;SynAck < list.size();SynAck++){ //hlad· SYN+ACK
				if (list.get(SynAck).getACK() == '1' && list.get(SynAck).getSYN() == '1'
					&& list.get(SynAck).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
					&& list.get(SynAck).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
					&& (list.get(SynAck).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					nasielAckSynKompletna = 1;
					break;
				}	
			}
			if (nasielAckSynKompletna == 1)
			for (Ack=SynAck+1;Ack < list.size();Ack++){ //hlad· ACK
				if (list.get(Ack).getACK() == '1'
					&& list.get(Ack).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
					&& (list.get(Ack).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
					&& (list.get(Ack).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					otvorenaKompletna = 1;
					break;
				}
			}
			if (listFIN_RES.size()==0)	break;
			if (nasielAckSynKompletna == 1 && otvorenaKompletna == 1){ //komunikacia otvoren·, hæad·m koniec
				for (j=0;j<listFIN_RES.size();j++){ //kaûdÈ FIN/RES
					if (listFIN_RES.get(j).getRES() == '1'  //RES proste ukonËuje, bodka
							&& listFIN_RES.get(j).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0 //res od servera
							&& listFIN_RES.get(j).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
							&& (listFIN_RES.get(j).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
							if (mamKomplet == -1) mamKomplet = i;
							ukoncenaFinal = 1;
							break;
					}
					if (listFIN_RES.get(j).getRES() == '1'
							&& listFIN_RES.get(j).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0 //res od klienta
							&& (listFIN_RES.get(j).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
							&& (listFIN_RES.get(j).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
							if (mamKomplet == -1) mamKomplet = i;
							ukoncenaFinal = 1;
							break;
					}
					if (listFIN_RES.get(j).getFIN() == '1'){ //niekde tam moûno existuje koniec (nejaky FIN tam je)
						finExistuje = 1;
					}
				}
		//nasleduj˙ 4 moûnosti ukonËenia
		//1. Klient FIN, Server ACK, Server FIN, Klient ACK
				if (finExistuje == 1){
					for (hladamPrvyFin=0;hladamPrvyFin < list.size();hladamPrvyFin++){ //hlad· FIN od klienta
						if (list.get(hladamPrvyFin).getFIN() == '1'
								&& list.get(hladamPrvyFin).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
								&& (list.get(hladamPrvyFin).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
								&& (list.get(hladamPrvyFin).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
							nasielPrvyFin1 = 1;
							break;
						}
					}
					if (nasielPrvyFin1 == 1)
						for (hladamPrvyAck=0;hladamPrvyAck < list.size();hladamPrvyAck++){ //hlad· ACK od servera
							if (list.get(hladamPrvyAck).getACK() == '1'
								&& list.get(hladamPrvyAck).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
								&& list.get(hladamPrvyAck).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
								&& (list.get(hladamPrvyAck).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
								nasielPrvyAck1 = 1;
								break;
							}	
						}
					if (nasielPrvyAck1 == 1)
						for (hladamDruhyFin=0;hladamDruhyFin < list.size();hladamDruhyFin++){ //hlad· FIN od servera
							if (list.get(hladamDruhyFin).getFIN() == '1'
								&& list.get(hladamDruhyFin).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
								&& list.get(hladamDruhyFin).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
								&& (list.get(hladamDruhyFin).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
								nasielDruhyFin1 = 1;
								break;
							}	
						}
					if (nasielDruhyFin1 == 1)
						for (hladamDruhyAck=0;hladamDruhyAck < list.size();hladamDruhyAck++){ //hlad· ACK od klienta
							if (list.get(hladamDruhyAck).getACK() == '1'
								&& list.get(hladamDruhyAck).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
								&& (list.get(hladamDruhyAck).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
								&& (list.get(hladamDruhyAck).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
								ukoncenaFinal = 1;
								if (mamKomplet == -1) mamKomplet = i;
								break;
							}
						}
//2. Server FIN, Klient ACK, Klient FIN, Server ACK
					if (ukoncenaFinal == -1){
						for (hladamPrvyFin=0;hladamPrvyFin < list.size();hladamPrvyFin++){ //hlad· FIN od servera
							if (list.get(hladamPrvyFin).getFIN() == '1'
								&& list.get(hladamPrvyFin).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
								&& list.get(hladamPrvyFin).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
								&& (list.get(hladamPrvyFin).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
								nasielPrvyFin2 = 1;
								break;
							}	
						}
						if (nasielPrvyFin2 == 1)
							for (hladamPrvyAck=0;hladamPrvyAck < list.size();hladamPrvyAck++){ //hlad· ACK od klienta
								if (list.get(hladamPrvyAck).getACK() == '1'
									&& list.get(hladamPrvyAck).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
									&& (list.get(hladamPrvyAck).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
									&& (list.get(hladamPrvyAck).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
									nasielPrvyAck2 = 1;
									break;
								}
							}
						if (nasielPrvyAck2 == 1)
							for (hladamDruhyFin=0;hladamDruhyFin < list.size();hladamDruhyFin++){ //hlad· FIN od klienta
								if (list.get(hladamDruhyFin).getFIN() == '1'
									&& list.get(hladamDruhyFin).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
									&& (list.get(hladamDruhyFin).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
									&& (list.get(hladamDruhyFin).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
									nasielDruhyFin2 = 1;
									break;
								}
							}
						if (nasielDruhyFin2 == 1)
							for (hladamDruhyAck=0;hladamDruhyAck < list.size();hladamDruhyAck++){ //hlad· ACK od servera
								if (list.get(hladamDruhyAck).getACK() == '1'
									&& list.get(hladamDruhyAck).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
									&& list.get(hladamDruhyAck).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
									&& (list.get(hladamDruhyAck).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
									ukoncenaFinal = 1;
									if (mamKomplet == -1) mamKomplet = i;
									break;
								}	
							}
	//3. Klient FIN, Server FIN ACK, Klient ACK
						if (ukoncenaFinal == -1){
							for (hladamPrvyFin=0;hladamPrvyFin < list.size();hladamPrvyFin++){ //hlad· FIN od klienta
								if (list.get(hladamPrvyFin).getFIN() == '1'
										&& list.get(hladamPrvyFin).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
										&& (list.get(hladamPrvyFin).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
										&& (list.get(hladamPrvyFin).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
									nasielPrvyFin3 = 1;
									break;
								}
							}
							if (nasielPrvyFin3 == 1)
								for (hladamPrvyFinAck=0;hladamPrvyFinAck < list.size();hladamPrvyFinAck++){ //hlad· ACK+FIN od servera
									if (list.get(hladamPrvyFinAck).getACK() == '1' && list.get(hladamPrvyFinAck).getFIN() == '1'
										&& list.get(hladamPrvyFinAck).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
										&& list.get(hladamPrvyFinAck).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
										&& (list.get(hladamPrvyFinAck).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
										nasielPrvyFinAck3 = 1;
										break;
									}	
								}
							if (nasielPrvyFinAck3 == 1)
								for (hladamDruhyAck=0;hladamDruhyAck < list.size();hladamDruhyAck++){ //hlad· ACK od klienta
									if (list.get(hladamDruhyAck).getACK() == '1'
										&& list.get(hladamDruhyAck).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
										&& (list.get(hladamDruhyAck).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
										&& (list.get(hladamDruhyAck).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
										ukoncenaFinal = 1;
										if (mamKomplet == -1) mamKomplet = i;
										break;
									}
								}
		//4. Server FIN, Klient FIN ACK, Server ACK
							if (ukoncenaFinal == -1){
								for (hladamPrvyFin=0;hladamPrvyFin < list.size();hladamPrvyFin++){ //hlad· FIN od servera
									if (list.get(hladamPrvyFin).getFIN() == '1'
										&& list.get(hladamPrvyFin).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
										&& list.get(hladamPrvyFin).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
										&& (list.get(hladamPrvyFin).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
										nasielPrvyFin4 = 1;
										break;
									}	
								}
								if (nasielPrvyFin4 == 1)
									for (hladamPrvyFinAck=0;hladamPrvyFinAck < list.size();hladamPrvyFinAck++){ //hlad· Fin+ACK od klienta
										if (list.get(hladamPrvyFinAck).getACK() == '1' && list.get(hladamPrvyFinAck).getFIN() == '1'
											&& list.get(hladamPrvyFinAck).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
											&& (list.get(hladamPrvyFinAck).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
											&& (list.get(hladamPrvyFinAck).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
											nasielPrvyFinAck4 = 1;
											break;
										}
									}
								if (nasielPrvyFinAck4 == 1)
									for (hladamDruhyAck=0;hladamDruhyAck < list.size();hladamDruhyAck++){ //hlad· ACK od servera
										if (list.get(hladamDruhyAck).getACK() == '1'
											&& list.get(hladamDruhyAck).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
											&& list.get(hladamDruhyAck).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
											&& (list.get(hladamDruhyAck).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
											ukoncenaFinal = 1;
											if (mamKomplet == -1) mamKomplet = i;
											break;
										}	
									}
							}
			//teraz uû musel najst aspon 1/4 koncov
						}
					}
					
				}
			}
		}
		//hladam nekompletnu
		for (i=0;i<listSYN.size();i++){ //kaûda SIN
			if (mamNekomplet >= 0) break;
			nasielAckSynNekompletna = -1;
			otvorenaNekompletna = -1;
			nasielFinNekompletS = -1;
			nasielFinNekompletK = -1;
			for (SynAck=0;SynAck < list.size();SynAck++){ //hlad· SYN+ACK
				if (list.get(SynAck).getACK() == '1' && list.get(SynAck).getSYN() == '1'
					&& list.get(SynAck).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0
					&& list.get(SynAck).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
					&& (list.get(SynAck).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					nasielAckSynNekompletna = 1;
					break;
				}	
			}
			for (Ack=SynAck+1;Ack < list.size();Ack++){ //hlad· ACK
				if (list.get(Ack).getACK() == '1'
					&& list.get(Ack).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0
					&& (list.get(Ack).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
					&& (list.get(Ack).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					otvorenaNekompletna = 1;
					break;
				}
			}
			if (listFIN_RES.size()==0 && nasielAckSynNekompletna == 1 && otvorenaNekompletna == 1){ //naöiel 2/3 handshake a subor nema fin/res
				if (mamNekomplet == -1) mamNekomplet = i;
				break;
			}
			if (listFIN_RES.size()==0 && (nasielAckSynNekompletna != 1 || otvorenaNekompletna != 1)){ //nenaöiel 2/3 handshake a subor nema fin/res
				break;
			}
			for (j=0;j<=listFIN_RES.size();j++){ //kaûda FIN/RES
				if (j==listFIN_RES.size() && nasielAckSynNekompletna == 1 && otvorenaNekompletna == 1
						&& (nasielFinNekompletS == -1 || nasielFinNekompletK == -1)){	//otvorena ale neukoncena
					if (mamNekomplet == -1) mamNekomplet = i;
					break;
				}
				//hladam resy
				if (listFIN_RES.get(j).getRES() == '1'  //RES proste ukonËuje, bodka
					&& listFIN_RES.get(j).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0 //res od servera
					&& listFIN_RES.get(j).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
					&& (listFIN_RES.get(j).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					break;
				}
				if (listFIN_RES.get(j).getRES() == '1'
					&& listFIN_RES.get(j).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0 //res od klienta
					&& (listFIN_RES.get(j).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
					&& (listFIN_RES.get(j).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					break;
				}	
				//este hlad·m finy
				if (listFIN_RES.get(j).getFIN() == '1'
					&& listFIN_RES.get(j).getDestIP().compareTo(listSYN.get(i).getSourceIP()) == 0 //fin od servera
					&& listFIN_RES.get(j).getSourceIP().compareTo(listSYN.get(i).getDestIP()) == 0
					&& (listFIN_RES.get(j).getRamec().substring(72,76).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					nasielFinNekompletS = 1;
				}
				if (listFIN_RES.get(j).getFIN() == '1'
					&& listFIN_RES.get(j).getSourceIP().compareTo(listSYN.get(i).getSourceIP()) == 0 //fin od klienta
					&& (listFIN_RES.get(j).getDestIP().compareTo(listSYN.get(i).getDestIP()) == 0)
					&& (listFIN_RES.get(j).getRamec().substring(68,72).compareTo(listSYN.get(i).getRamec().substring(68,72)) == 0)){
					nasielFinNekompletK = 1;
				}
				if (nasielFinNekompletS == 1 && nasielFinNekompletK == 1) {
					break;
				}
			}
				
		}
		if (mamKomplet >= 0 && ukoncenaFinal == 1){ //vypis kompletnej komunikacie
			for (Ramec k : list) {
				if (((k.getDestIP().compareTo(listSYN.get(mamKomplet).getSourceIP()) == 0)
						&& (k.getSourceIP().compareTo(listSYN.get(mamKomplet).getDestIP()) == 0))
						&& (k.getRamec().substring(72,76).compareTo(listSYN.get(mamKomplet).getRamec().substring(68,72)) == 0)
						
						|| ((k.getSourceIP().compareTo(listSYN.get(mamKomplet).getSourceIP()) == 0)
						&& (k.getDestIP().compareTo(listSYN.get(mamKomplet).getDestIP()) == 0)
						&& (k.getRamec().substring(68,72).compareTo(listSYN.get(mamKomplet).getRamec().substring(68,72)) == 0))){
						if (k.getDlzkaDriver() >= 1280 && k.getDlzkaDriver() < 2540) stats[7]++;
						if (k.getDlzkaDriver() >= 640 && k.getDlzkaDriver() < 1280) stats[6]++;
						if (k.getDlzkaDriver() >= 320 && k.getDlzkaDriver() < 640) stats[5]++;
						if (k.getDlzkaDriver() >= 160 && k.getDlzkaDriver() < 320) stats[4]++;
						if (k.getDlzkaDriver() >= 80 && k.getDlzkaDriver() < 160) stats[3]++;
						if (k.getDlzkaDriver() >= 40 && k.getDlzkaDriver() < 80) stats[2]++;
						if (k.getDlzkaDriver() >= 20 && k.getDlzkaDriver() < 40) stats[1]++;
						if (k.getDlzkaDriver() >= 0 && k.getDlzkaDriver() < 20) stats[0]++;
						komplet.add(k);
					}
			}
				celyVypis.append("Komunik·cia kompletn·" + "\n");
				celyVypis.append("Klient: ");
				for (int o=0;o<8;o+=2){
					celyVypis.append(Integer.valueOf(listSYN.get(mamKomplet).getSourceIP().substring(o,o+2),16));
					if (o < 6) celyVypis.append(".");
				}
				celyVypis.append(" : ");
				celyVypis.append(Integer.valueOf(listSYN.get(mamKomplet).getRamec().substring(68,72),16));
				celyVypis.append("  Server: ");
				for (int o=0;o<8;o+=2){
					celyVypis.append(Integer.valueOf(listSYN.get(mamKomplet).getDestIP().substring(o,o+2),16));
					if (o < 6) celyVypis.append(".");
				}
				celyVypis.append(" : ");
				if (listSYN.get(mamKomplet).getTransportProt().compareTo(externy.getHttp()) == 0) {
					celyVypis.append("http");
					celyVypis.append(" (");
					celyVypis.append(Integer.parseInt(externy.getHttp(), 16) + ")" + "\n");
				}
				if (listSYN.get(mamKomplet).getTransportProt().compareTo(externy.getHttps()) == 0) {
					celyVypis.append("https");
					celyVypis.append(" (");
					celyVypis.append(Integer.parseInt(externy.getHttps(), 16) + ")" + "\n");
				}
				if (listSYN.get(mamKomplet).getTransportProt().compareTo(externy.getTelnet()) == 0) {
					celyVypis.append("telnet");
					celyVypis.append(" (");
					celyVypis.append(Integer.parseInt(externy.getTelnet(), 16) + ")" + "\n");
				}
				if (listSYN.get(mamKomplet).getTransportProt().compareTo(externy.getSsh()) == 0) {
					celyVypis.append("ssh");
					celyVypis.append(" (");
					celyVypis.append(Integer.parseInt(externy.getSsh(), 16) + ")" + "\n");
				}
				if (listSYN.get(mamKomplet).getTransportProt().compareTo(externy.getFtp_control()) == 0) {
					celyVypis.append("ftp control");
					celyVypis.append(" (");
					celyVypis.append(Integer.parseInt(externy.getFtp_control(), 16) + ")" + "\n");
				}
				if (listSYN.get(mamKomplet).getTransportProt().compareTo(externy.getFtp_data()) == 0) {
					celyVypis.append("ftp data");
					celyVypis.append(" (");
					celyVypis.append(Integer.parseInt(externy.getFtp_data(), 16) + ")" + "\n");
				}			    
				celyVypis.append("\n");
				celyVypis.append("\n");
				if (komplet.size() > 20){
					for (int p=0;p<10;p++)
						finalKomplet.add(komplet.get(p));
					for (int q=komplet.size()-10;q<komplet.size();q++)
						finalKomplet.add(komplet.get(q));
					celyVypis.append(vypis(finalKomplet));
				}
				else celyVypis.append(vypis(komplet));
				celyVypis.append("\n");
				celyVypis.append("ätatistika dÂûky r·mcov v bajtoch: " + "\n");
				celyVypis.append("0 ñ 19  " + stats[0] + "\n");
				celyVypis.append("20 ñ 39  " + stats[1] + "\n");
				celyVypis.append("40 ñ 79  " + stats[2] + "\n");
				celyVypis.append("80 ñ 159  " + stats[3] + "\n");
				celyVypis.append("160 ñ 319  " + stats[4] + "\n");
				celyVypis.append("320 ñ 639  " + stats[5] + "\n");
				celyVypis.append("640 ñ 1279 " + stats[6] + "\n");
				celyVypis.append("1280 ñ 2539  " + stats[7] + "\n");
				celyVypis.append("\n");
				celyVypis.append("\n");
		}
		
		
		//nekompletna vypis
		if (mamNekomplet >= 0 && nasielAckSynNekompletna == 1 && otvorenaNekompletna == 1){
			for (Ramec k : list) {
				if (((k.getDestIP().compareTo(listSYN.get(mamNekomplet).getSourceIP()) == 0)
					&& (k.getSourceIP().compareTo(listSYN.get(mamNekomplet).getDestIP()) == 0))
					&& (k.getRamec().substring(72,76).compareTo(listSYN.get(mamNekomplet).getRamec().substring(68,72)) == 0)
					
					|| ((k.getSourceIP().compareTo(listSYN.get(mamNekomplet).getSourceIP()) == 0)
					&& (k.getDestIP().compareTo(listSYN.get(mamNekomplet).getDestIP()) == 0)
					&& (k.getRamec().substring(68,72).compareTo(listSYN.get(mamNekomplet).getRamec().substring(68,72)) == 0))){
					nekomplet.add(k);
				}
			}
			celyVypis.append("\n");
			celyVypis.append("Komunik·cia nekompletn·" + "\n");
			celyVypis.append("Klient: ");
			for (int o=0;o<8;o+=2){
				celyVypis.append(Integer.valueOf(listSYN.get(mamNekomplet).getSourceIP().substring(o,o+2),16));
				if (o < 6) celyVypis.append(".");
			}
			celyVypis.append(" : ");
			celyVypis.append(Integer.valueOf(listSYN.get(mamNekomplet).getRamec().substring(68,72),16));
			celyVypis.append("  Server: ");
			for (int o=0;o<8;o+=2){
				celyVypis.append(Integer.valueOf(listSYN.get(mamNekomplet).getDestIP().substring(o,o+2),16));
				if (o < 6) celyVypis.append(".");
			}
			celyVypis.append(" : ");
			if (listSYN.get(mamNekomplet).getTransportProt().compareTo(externy.getHttp()) == 0) {
				celyVypis.append("http");
				celyVypis.append(" (");
				celyVypis.append(Integer.parseInt(externy.getHttp(), 16) + ")" + "\n");
			}
			if (listSYN.get(mamNekomplet).getTransportProt().compareTo(externy.getHttps()) == 0) {
				celyVypis.append("https");
				celyVypis.append(" (");
				celyVypis.append(Integer.parseInt(externy.getHttps(), 16) + ")" + "\n");
			}
			if (listSYN.get(mamNekomplet).getTransportProt().compareTo(externy.getTelnet()) == 0) {
				celyVypis.append("telnet");
				celyVypis.append(" (");
				celyVypis.append(Integer.parseInt(externy.getTelnet(), 16) + ")" + "\n");
			}
			if (listSYN.get(mamNekomplet).getTransportProt().compareTo(externy.getSsh()) == 0) {
				celyVypis.append("ssh");
				celyVypis.append(" (");
				celyVypis.append(Integer.parseInt(externy.getSsh(), 16) + ")" + "\n");
			}
			if (listSYN.get(mamNekomplet).getTransportProt().compareTo(externy.getFtp_control()) == 0) {
				celyVypis.append("ftp control");
				celyVypis.append(" (");
				celyVypis.append(Integer.parseInt(externy.getFtp_control(), 16) + ")" + "\n");
			}
			if (listSYN.get(mamNekomplet).getTransportProt().compareTo(externy.getFtp_data()) == 0) {
				celyVypis.append("ftp data");
				celyVypis.append(" (");
				celyVypis.append(Integer.parseInt(externy.getFtp_data(), 16) + ")" + "\n");
			}
			celyVypis.append("\n");
			if (nekomplet.size() > 20){
				for (int p=0;p<10;p++)
					finalNekomplet.add(nekomplet.get(p));
				for (int q=nekomplet.size()-10;q<nekomplet.size();q++)
					finalNekomplet.add(nekomplet.get(q));
				celyVypis.append(vypis(finalNekomplet));
			}
			else celyVypis.append(vypis(nekomplet));
		}
			
		return celyVypis.toString();
	}
	
	public String vypisArp(List<Ramec> list){ //vypis zvl·öù pre ARP
		StringBuilder celyVypis = new StringBuilder();
		int pocet = 0;
		int bool;
		List<Ramec> vypis;
		for (int i=0;i<list.size();i++){
			bool = 0;
			if (list.get(i).getRamec().substring(42,44).compareTo("01") == 0){
				vypis = new ArrayList<Ramec>();
				vypis.add(list.get(i));
				for (int j=i+1;j<list.size();j++){
					if (list.get(j).getRamec().compareTo(list.get(i).getRamec()) == 0){
						vypis.add(list.get(j));
					}
					if (list.get(j).getRamec().substring(42,44).compareTo("02") == 0 
					&& list.get(j).getRamec().substring(56,64).compareTo(list.get(i).getRamec().substring(76,84)) == 0
					&& list.get(j).getRamec().substring(64,76).compareTo(list.get(i).getRamec().substring(12,24)) == 0){
						vypis.add(list.get(j));
						bool = 1;
						pocet++;
						break;
					}
				}
				
				if (bool == 1) {
					for (Ramec k : vypis) {
						celyVypis.append("Komunik·cia Ë. " + pocet + "\n");
						if (k.getRamec().substring(42,44).compareTo("01") == 0){
							celyVypis.append("ARP-Request, ");
							celyVypis.append("IP adresa: ");
							for (int m=76;m<84;m+=2){
								celyVypis.append(Integer.valueOf(k.getRamec().substring(m,m+2),16));
								if (m < 82) celyVypis.append(".");
							}
							celyVypis.append(", MAC adresa: ");
							if (k.getRamec().substring(64,76).compareTo("000000000000") == 0)
								celyVypis.append("???");
							else 	for (int m=64;m<76;m+=2){
										celyVypis.append(k.getRamec().substring(m,m+2));
										celyVypis.append(" ");
									}
							celyVypis.append("\n");
							celyVypis.append("Zdrojov· IP: ");
							for (int m=56;m<64;m+=2){
								celyVypis.append(Integer.valueOf(k.getRamec().substring(m,m+2),16));
								if (m < 62) celyVypis.append(".");
							}
							celyVypis.append(", Cieæov· IP: ");
							for (int m=76;m<84;m+=2){
								celyVypis.append(Integer.valueOf(k.getRamec().substring(m,m+2),16));
								if (m < 82) celyVypis.append(".");
							}
							celyVypis.append("\n");
						}
						////////////////////////////////////////
						if (k.getRamec().substring(42,44).compareTo("02") == 0){
							celyVypis.append("ARP-Reply, ");
							celyVypis.append("IP adresa: ");
							for (int m=56;m<64;m+=2){
								celyVypis.append(Integer.valueOf(k.getRamec().substring(m,m+2),16));
								if (m < 62) celyVypis.append(".");
							}
							celyVypis.append(", MAC adresa: ");
							if (k.getRamec().substring(44,56).compareTo("000000000000") == 0)
								celyVypis.append("???");
							else 	for (int m=44;m<56;m+=2){
										celyVypis.append(k.getRamec().substring(m,m+2));
										celyVypis.append(" ");
									}
							celyVypis.append("\n");
							celyVypis.append("Zdrojov· IP: ");
							for (int m=56;m<64;m+=2){
								celyVypis.append(Integer.valueOf(k.getRamec().substring(m,m+2),16));
								if (m < 62) celyVypis.append(".");
							}
							celyVypis.append(", Cieæov· IP: ");
							for (int m=76;m<84;m+=2){
								celyVypis.append(Integer.valueOf(k.getRamec().substring(m,m+2),16));
								if (m < 82) celyVypis.append(".");
							}
							celyVypis.append("\n");
						}
						
						celyVypis.append("r·mec " + k.getCislo() + "\n" + 
						        "dÂûka r·mca poskytnut· paketov˝m drajverom - " + k.getDlzkaDriver() + " B\n" + 
						        "dÂûka r·mca pren·öanÈho po mÈdiu - " + k.getDlzkaMedium() + " B\n" + 
						        k.getTyp() + "\n");
						celyVypis.append("Zdrojov· MAC adresa: ");
						for (int j = 0; j < 12; j+=2){
							celyVypis.append(k.getZMAC().substring(j, j+2));
							celyVypis.append(" ");
						}
						celyVypis.append("\n");
						celyVypis.append("Cieæov· MAC adresa: ");
						for (int j = 0; j < 12; j+=2){
							celyVypis.append(k.getCMAC().substring(j, j+2));
							celyVypis.append(" ");
						}
						celyVypis.append("\n");
			        /**Pridanie po 2 charov z r·mca do fin·lneho stringu, v jednom riadku 32 charov*/
			        for (int j = 2; j <= 1+2*k.getDlzkaDriver(); j+=2) {
			        	celyVypis.append(k.getRamec().substring(j-2,j));
			        	celyVypis.append(" ");
			                if ((j % 16 == 0) && (j != 0)) celyVypis.append(" ");
			                if ((j % 32 == 0) && (j != 0)) celyVypis.append("\n");
			        }
			        celyVypis.append("\n");
			        celyVypis.append("\n");
						
					}
					
				}
				
			}
		}
		return celyVypis.toString();
	}
	
	public String vypisICMP(List<Ramec> list){ //vypis zvl·öù pre ICMP
		if (list.size() <= 0)
			return "V s˙bore sa nenach·dza ani ukonËen· ani neukonËena komunik·cia danÈho typu \n";
		int j;
		StringBuilder celyVypis = new StringBuilder();
		for (Ramec k : list) { 
			celyVypis.append("ICMP - ");
			if (k.getRamec().substring(68,70).compareTo("00") == 0) celyVypis.append("Echo Reply" + "\n");
			if (k.getRamec().substring(68,70).compareTo("03") == 0) celyVypis.append("Destination Unreachable" + "\n");
			if (k.getRamec().substring(68,70).compareTo("04") == 0) celyVypis.append("Source Quench" + "\n");
			if (k.getRamec().substring(68,70).compareTo("05") == 0) celyVypis.append("Redirect" + "\n");
			if (k.getRamec().substring(68,70).compareTo("08") == 0) celyVypis.append("Echo Request" + "\n");
			if (k.getRamec().substring(68,70).compareTo("09") == 0) celyVypis.append("Router Advertisement" + "\n");
			if (k.getRamec().substring(68,70).compareTo("0a") == 0) celyVypis.append("Router Selection" + "\n");
			if (k.getRamec().substring(68,70).compareTo("0b") == 0) celyVypis.append("Time Exceeded" + "\n");
			if (k.getRamec().substring(68,70).compareTo("0c") == 0) celyVypis.append("Parameter Problem" + "\n");
			if (k.getRamec().substring(68,70).compareTo("0d") == 0) celyVypis.append("Timestamp Request" + "\n");
			if (k.getRamec().substring(68,70).compareTo("0e") == 0) celyVypis.append("Timestamp Reply" + "\n");
			if (k.getRamec().substring(68,70).compareTo("0f") == 0) celyVypis.append("Information Request" + "\n");
			if (k.getRamec().substring(68,70).compareTo("10") == 0) celyVypis.append("Information Reply" + "\n");
			if (k.getRamec().substring(68,70).compareTo("11") == 0) celyVypis.append("Address Mask Request" + "\n");
			if (k.getRamec().substring(68,70).compareTo("12") == 0) celyVypis.append("Address Mark Reply" + "\n");
			if (k.getRamec().substring(68,70).compareTo("1e") == 0) celyVypis.append("Traceroute" + "\n");
			celyVypis.append("Zdrojov· IP: ");
			for (int i=0;i<8;i+=2){
				celyVypis.append(Integer.valueOf(k.getSourceIP().substring(i,i+2),16));
				if (i < 6) celyVypis.append(".");
			}
			celyVypis.append("  Cieæov· IP: ");
			for (int i=0;i<8;i+=2){
				celyVypis.append(Integer.valueOf(k.getDestIP().substring(i,i+2),16));
				if (i < 6) celyVypis.append(".");
			}
			celyVypis.append("\n");
			celyVypis.append("r·mec " + k.getCislo() + "\n" + 
			        "dÂûka r·mca poskytnut· paketov˝m drajverom - " + k.getDlzkaDriver() + " B\n" + 
			        "dÂûka r·mca pren·öanÈho po mÈdiu - " + k.getDlzkaMedium() + " B\n" + 
			        k.getTyp() + "\n");
			celyVypis.append("Zdrojov· MAC adresa: ");
			for (j = 0; j < 12; j+=2){
				celyVypis.append(k.getCMAC().substring(j, j+2));
				celyVypis.append(" ");
			}
			celyVypis.append("\n");
			celyVypis.append("Cieæov· MAC adresa: ");
			for (j = 0; j < 12; j+=2){
				celyVypis.append(k.getZMAC().substring(j, j+2));
				celyVypis.append(" ");
			}
			celyVypis.append("\n");
        /**Pridanie po 2 charov z r·mca do fin·lneho stringu, v jednom riadku 32 charov*/
        for (j = 2; j <= 1+2*k.getDlzkaDriver(); j+=2) {
        	celyVypis.append(k.getRamec().substring(j-2,j));
        	celyVypis.append(" ");
                if ((j % 16 == 0) && (j != 0)) celyVypis.append(" ");
                if ((j % 32 == 0) && (j != 0)) celyVypis.append("\n");
        }
        celyVypis.append("\n");
        celyVypis.append("\n");
		}
		return celyVypis.toString();
	}
	
	public String vypisTftp(List<Ramec> tftpStarty, List<Ramec> listTftp){ //vypis zvl·öù pre tftp
		if (tftpStarty.size() <= 0)
			return "V s˙bore sa nenach·dza ani ukonËen· ani neukonËena komunik·cia danÈho typu \n";
		List<Ramec> vysledok;
		StringBuilder celyVypis = new StringBuilder();
		for (int i=0;i<tftpStarty.size();i++){
			vysledok = new ArrayList<Ramec>();
			vysledok.add(tftpStarty.get(i));
			for (int j=0;j<listTftp.size();j++){
				if (listTftp.get(j).getDestIP().compareTo(tftpStarty.get(i).getSourceIP()) == 0 //od servera
						&& listTftp.get(j).getSourceIP().compareTo(tftpStarty.get(i).getDestIP()) == 0
						&& (listTftp.get(j).getRamec().substring(72,76).compareTo(tftpStarty.get(i).getRamec().substring(68,72)) == 0)){
						vysledok.add(listTftp.get(j));
				}
				if (listTftp.get(j).getSourceIP().compareTo(tftpStarty.get(i).getSourceIP()) == 0 //od servera
						&& listTftp.get(j).getDestIP().compareTo(tftpStarty.get(i).getDestIP()) == 0
						&& (listTftp.get(j).getRamec().substring(68,72).compareTo(tftpStarty.get(i).getRamec().substring(68,72)) == 0)){
						vysledok.add(listTftp.get(j));
				}
			}
			celyVypis.append("Klient IP: ");
			for (int p=0;p<8;p+=2){
				celyVypis.append(Integer.valueOf(vysledok.get(i).getSourceIP().substring(p,p+2),16));
				if (p < 6) celyVypis.append(".");
			}
			celyVypis.append("   Klient Port: ");
			celyVypis.append(Integer.parseInt(vysledok.get(i).getRamec().substring(68,72),16));
			celyVypis.append("\n");
			celyVypis.append("Server IP: ");
			for (int p=0;p<8;p+=2){
				celyVypis.append(Integer.valueOf(vysledok.get(i).getDestIP().substring(p,p+2),16));
				if (p < 6) celyVypis.append(".");
			}
			celyVypis.append("   Server Port: ");
			celyVypis.append(Integer.parseInt(vysledok.get(i+1).getRamec().substring(68,72),16));
			celyVypis.append("\n");
			celyVypis.append("\n");
			celyVypis.append(vypis(vysledok));
		}
		
		return celyVypis.toString();
	}

}
