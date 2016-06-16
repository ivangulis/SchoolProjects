package controller;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Externy;
import model.IPv4;
import model.Ramec;
import model.Vypisy;

import org.jnetpcap.Pcap;
import org.jnetpcap.packet.PcapPacket;

import view.Gui;

public class Analyzator {
	
	/** Singleton */
	private static Analyzator instance = null;
	private Analyzator() {}
	public static Analyzator getInstance() {
		if (instance == null)
			instance = new Analyzator();
		return instance;
	}
	
	/**Listy r·mcov, IPËiek a pomocnÈ listy*/
	private static List<Ramec> ramce;
	private static List<IPv4> ipadresy;
	private static List<Ramec> listHttp;
	private static List<Ramec> listHttps;
	private static List<Ramec> listTelnet;
	private static List<Ramec> listSsh;
	private static List<Ramec> listFtpControl;
	private static List<Ramec> listFtpData;
	private static List<Ramec> listIcmp;
	private static List<Ramec> listArp;
	private static List<Ramec> httplistSYN;
	private static List<Ramec> httplistFIN_RES;
	private static List<Ramec> httpslistSYN;
	private static List<Ramec> httpslistFIN_RES;
	private static List<Ramec> telnetlistSYN;
	private static List<Ramec> telnetlistFIN_RES;
	private static List<Ramec> sshlistSYN;
	private static List<Ramec> sshlistFIN_RES;
	private static List<Ramec> ftpControllistSYN;
	private static List<Ramec> ftpControllistFIN_RES;
	private static List<Ramec> ftpDatalistSYN;
	private static List<Ramec> ftpDatalistFIN_RES;
	private static List<Ramec> tftpStarty;
	private static List<Ramec> listtftp;
	
	Externy externy = new Externy();
	Vypisy vypisy;
	
	/**PoËet r·mcov a IP*/
	public static int pocet = 0;
	public static int pocetIP = 0;
	
	public static String menoSuboru;
	
	public static Pcap celySubor; //pcap s˙bor
    
    PcapPacket jedenPacket; //pr·zdny packet
    
    public String vypisHttp(){
    	vypisy = new Vypisy();
		return vypisy.vypisTCP(listHttp, httplistSYN, httplistFIN_RES, externy);
    }
    
    public String vypisHttps(){
    	vypisy = new Vypisy();
		return vypisy.vypisTCP(listHttps, httpslistSYN, httpslistFIN_RES, externy);
    }
    
    public String vypisTelnet(){
    	vypisy = new Vypisy();
		return vypisy.vypisTCP(listTelnet, telnetlistSYN, telnetlistFIN_RES, externy);
    }
    
    public String vypisSsh(){
    	vypisy = new Vypisy();
		return vypisy.vypisTCP(listSsh, sshlistSYN, sshlistFIN_RES, externy);
    }
    
    public String vypisFtpControl(){
    	vypisy = new Vypisy();
		return vypisy.vypisTCP(listFtpControl, ftpControllistSYN, ftpControllistFIN_RES, externy);
    }
    
    public String vypisFtpData(){
    	vypisy = new Vypisy();
		return vypisy.vypisTCP(listFtpData, ftpDatalistSYN, ftpDatalistFIN_RES, externy);
    }
    
    public String vypisTftp(){
    	vypisy = new Vypisy();
		return vypisy.vypisTftp(tftpStarty, listtftp);
    }
    
    public String vypisIcmp(){
    	vypisy = new Vypisy();
		return vypisy.vypisICMP(listIcmp);
    }
    
    public String vypisArp(){
    	vypisy = new Vypisy();
		return vypisy.vypisArp(listArp);
    }
    
    
	/**Funkcia na v˝pis inform·cii o r·mcoch k 1. zadaniu*/
	public String vypisAnalyzy() {
		vypisy = new Vypisy();
		return vypisy.vypis(ramce);
	}
	
	/**V˝pis inform·cii o IPËkach*/
	public String vypisIP(){
		vypisy = new Vypisy();
		return vypisy.vypisIP(ipadresy, pocetIP);
	}
	
	/**Otvorenie s˙boru s n·zvom z labelu*/
	public void nazovSuboru(String meno){
		Analyzator.menoSuboru = meno;
		StringBuilder errory = new StringBuilder();
		celySubor = Pcap.openOffline(menoSuboru, errory);
	}
	
	/**NaËÌta externy subor*/
	public void nacitajExterny(){
		List<String> slova = new ArrayList<String>();
		File externyPorty = new File("externy.txt");
		BufferedReader reader = null;
		
		try {
		reader = new BufferedReader(new FileReader(externyPorty));
	    String riadok = null;

	    while ((riadok = reader.readLine()) != null) {
	        slova.add(riadok);
	    }
	    /**VyplnÌ vöetky atributy*/
	    externy.setIPv4(slova.get(1));
	    externy.setARP(slova.get(3));
	    externy.setICMP(slova.get(5));
	    externy.setTCP(slova.get(7));
	    externy.setUDP(slova.get(9));
	    externy.setTftp(slova.get(11));
	    externy.setHttp(slova.get(13));
	    externy.setHttps(slova.get(15));
	    externy.setTelnet(slova.get(17));
	    externy.setSsh(slova.get(19));
	    externy.setFtp_control(slova.get(21));
	    externy.setFtp_data(slova.get(23));
	    
		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String error(){
		return "Nepodarilo sa otvoriù s˙bor.";
	}
	
	/**Telo celej anal˝zy*/
	public void analyzuj()
	{
		ramce = new ArrayList<Ramec>();
		ipadresy = new ArrayList<IPv4>();
		listHttp = new ArrayList<Ramec>();
		listHttps = new ArrayList<Ramec>();
		listTelnet = new ArrayList<Ramec>();
		listSsh = new ArrayList<Ramec>();
		listFtpControl = new ArrayList<Ramec>();
		listFtpData = new ArrayList<Ramec>();
		listIcmp = new ArrayList<Ramec>();
		listArp = new ArrayList<Ramec>();
		httplistSYN = new ArrayList<Ramec>();
		httplistFIN_RES = new ArrayList<Ramec>();
		httpslistSYN = new ArrayList<Ramec>();
		httpslistFIN_RES = new ArrayList<Ramec>();
		telnetlistSYN = new ArrayList<Ramec>();
		telnetlistFIN_RES = new ArrayList<Ramec>();
		sshlistSYN = new ArrayList<Ramec>();
		sshlistFIN_RES = new ArrayList<Ramec>();
		ftpControllistSYN = new ArrayList<Ramec>();
		ftpControllistFIN_RES = new ArrayList<Ramec>();
		ftpDatalistSYN = new ArrayList<Ramec>();
		ftpDatalistFIN_RES = new ArrayList<Ramec>();
		tftpStarty = new ArrayList<Ramec>();
		listtftp = new ArrayList<Ramec>();
		
		byte buffer[] = new byte[2000];
		jedenPacket = new PcapPacket(buffer);
		int j;
		/**Vyber·m dalsÌ packet*/
        while (celySubor.nextEx(jedenPacket) > 0){
        	j = 0;
        	int pom;
        	jedenPacket = new PcapPacket(jedenPacket);
        	Ramec dalsiRamec = new Ramec();
        	/**UloûÌm si tam premenen˝ hexastring*/
        	dalsiRamec.setRamec(hexadecimalne(jedenPacket));
        	pocet++;
        	dalsiRamec.setCislo(pocet);
        	ramce.add(dalsiRamec); //uloûÌm si r·mec
        	/**Substringom uloûÌm MAC adresy, ËÌseln˝ typ*/
        	ramce.get(pocet-1).setCMAC(ramce.get(pocet-1).getRamec().substring(0, 12));
        	ramce.get(pocet-1).setZMAC(ramce.get(pocet-1).getRamec().substring(12, 24));
        	ramce.get(pocet-1).setTyp(ramce.get(pocet-1).getRamec().substring(24, 28));
        	ramce.get(pocet-1).setDlzkaDriver(ramce.get(pocet-1).getRamec().length()/2); //dlûka driver
        	if (ramce.get(pocet-1).getDlzkaDriver() > 60) //dlûka po mÈdiu
        		ramce.get(pocet-1).setDlzkaMedium(ramce.get(pocet-1).getRamec().length()/2 + 4);
        	else ramce.get(pocet-1).setDlzkaMedium(64);
        	
        	if (ramce.get(pocet-1).getTyp().compareTo(externy.getIPv4()) == 0){ //ak je tam IPv4 protokol
        		/**Analyzujem transportnu vrstvu*/
        		if (ramce.get(pocet-1).getRamec().substring(46,48).compareTo(externy.getTCP()) == 0){
        			StringBuilder flagy = new StringBuilder();
        			flagy.append(donuluj(Integer.toBinaryString(Integer.valueOf(ramce.get(pocet-1).getRamec().substring(94,96),16))));
        			ramce.get(pocet-1).setSYN(flagy.charAt(6));
        			ramce.get(pocet-1).setFIN(flagy.charAt(7));
        			ramce.get(pocet-1).setACK(flagy.charAt(3));
        			ramce.get(pocet-1).setRES(flagy.charAt(5));
        			/**Flagy s=u uoûenÈ, dalej rozober·m r·mce do pomocn˝ch polÌ podla protokolu v TCP*/
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getHttp()) == 0
        			|| ramce.get(pocet-1).getRamec().substring(68,72).compareTo(externy.getHttp()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getHttp());
        				listHttp.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getSYN() == '1' && ramce.get(pocet-1).getACK() == '0')
        					httplistSYN.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getFIN() == '1' || ramce.get(pocet-1).getRES() == '1')
        					httplistFIN_RES.add(ramce.get(pocet-1));		
        			}
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getHttps()) == 0
                	|| ramce.get(pocet-1).getRamec().substring(68,72).compareTo(externy.getHttps()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getHttps());
        				listHttps.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getSYN() == '1' && ramce.get(pocet-1).getACK() == '0')
        					httpslistSYN.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getFIN() == '1' || ramce.get(pocet-1).getRES() == '1')
        					httpslistFIN_RES.add(ramce.get(pocet-1));		
        			}
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getTelnet()) == 0
                	|| ramce.get(pocet-1).getRamec().substring(68,72).compareTo(externy.getTelnet()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getTelnet());
        				listTelnet.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getSYN() == '1' && ramce.get(pocet-1).getACK() == '0')
        					telnetlistSYN.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getFIN() == '1' || ramce.get(pocet-1).getRES() == '1')
        					telnetlistFIN_RES.add(ramce.get(pocet-1));		
        			}
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getSsh()) == 0
                    || ramce.get(pocet-1).getRamec().substring(68,72).compareTo(externy.getSsh()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getSsh());
                		listSsh.add(ramce.get(pocet-1));
                		if (ramce.get(pocet-1).getSYN() == '1' && ramce.get(pocet-1).getACK() == '0')
        					sshlistSYN.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getFIN() == '1' || ramce.get(pocet-1).getRES() == '1')
        					sshlistFIN_RES.add(ramce.get(pocet-1));		
        			}
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getFtp_control()) == 0
                    || ramce.get(pocet-1).getRamec().substring(68,72).compareTo(externy.getFtp_control()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getFtp_control());
                		listFtpControl.add(ramce.get(pocet-1));
                		if (ramce.get(pocet-1).getSYN() == '1' && ramce.get(pocet-1).getACK() == '0')
        					ftpControllistSYN.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getFIN() == '1' || ramce.get(pocet-1).getRES() == '1')
        					ftpControllistFIN_RES.add(ramce.get(pocet-1));		
        			}
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getFtp_data()) == 0
                    || ramce.get(pocet-1).getRamec().substring(68,72).compareTo(externy.getFtp_data()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getFtp_data());
                    	listFtpData.add(ramce.get(pocet-1));
                    	if (ramce.get(pocet-1).getSYN() == '1' && ramce.get(pocet-1).getACK() == '0')
        					ftpDatalistSYN.add(ramce.get(pocet-1));
        				if (ramce.get(pocet-1).getFIN() == '1' || ramce.get(pocet-1).getRES() == '1')
        					ftpDatalistFIN_RES.add(ramce.get(pocet-1));		
        			}
        		}
        		/**Dalej je uû len UDP a icmp*/
        		if (ramce.get(pocet-1).getRamec().substring(46,48).compareTo(externy.getUDP()) == 0){
        			if (ramce.get(pocet-1).getRamec().substring(72,76).compareTo(externy.getTftp()) == 0){
        				ramce.get(pocet-1).setTransportProt(externy.getTftp());
        				tftpStarty.add(ramce.get(pocet-1));
        			}	
        			else listtftp.add(ramce.get(pocet-1)); //TFTP potrebuje viac r·mcov
        		}
        		if (ramce.get(pocet-1).getRamec().substring(46,48).compareTo(externy.getICMP()) == 0){
        			listIcmp.add(ramce.get(pocet-1));
        		}
        		/**UloûÌm aj IPËky do r·mcov*/
        		ramce.get(pocet-1).setSourceIP(ramce.get(pocet-1).getRamec().substring(52, 60));
        		ramce.get(pocet-1).setDestIP(ramce.get(pocet-1).getRamec().substring(60, 68));
        	}
        	
        	if (ramce.get(pocet-1).getTyp().compareTo(externy.getARP()) == 0){ //ak je to zas ARP header
        		listArp.add(ramce.get(pocet-1));
    			ramce.get(pocet-1).setSourceIP(ramce.get(pocet-1).getRamec().substring(56, 64)); //m· inde IPËky
    			ramce.get(pocet-1).setDestIP(ramce.get(pocet-1).getRamec().substring(64, 72));
        	}
        	if (ramce.get(pocet-1).getTyp().compareTo("0600") > 0){ //je to Ethernet?
        		if (
        				//(ramce.get(pocet-1).getTyp().compareTo(externy.getARP()) == 0) ||
        				(ramce.get(pocet-1).getTyp().compareTo(externy.getIPv4()) == 0)){
        			/**Ak je to 1. IPËka tak ju hned prid·*/
            		if (pocetIP == 0){
            			ipadresy.add(new IPv4());
            			ipadresy.get(0).setIpcka(ramce.get(pocet-1).getSourceIP());
            			ipadresy.get(0).setOdvysielane(ramce.get(pocet-1).getDlzkaDriver());
            			pocetIP++;
            		}
            		/**Ak nieje 1.*/
            		if (pocetIP != 0){
            			for (j=0;j<=pocetIP;j++){
            				/**Ak priöiel na koniec, vytvorÌ nov˙ a breakne*/
            				if (j == pocetIP){
            					ipadresy.add(new IPv4());
                				ipadresy.get(j).setIpcka(ramce.get(pocet-1).getSourceIP());
                				ipadresy.get(j).setOdvysielane(ramce.get(pocet-1).getDlzkaDriver());
                				pocetIP++;
                				break;
            				}
            				/**Ak nie je nakonci a n·jde, prir·ta novÈ byty a breakne*/
            				if (ramce.get(pocet-1).getSourceIP().compareTo(ipadresy.get(j).getIpcka()) == 0){
                    			pom = ipadresy.get(j).getOdvysielane();
            					ipadresy.get(j).setOdvysielane(pom	+ ramce.get(pocet-1).getDlzkaDriver());
                    			break;
            				}
            				
                    	}
            		}
            	}
            	ramce.get(pocet-1).setTyp("Ethernet II");
        	}
        	else if (ramce.get(pocet-1).getRamec().substring(28, 32).compareTo("ffff") == 0)
        		ramce.get(pocet-1).setTyp("IEEE 802.3 - Raw");
        	else if (ramce.get(pocet-1).getRamec().substring(28, 32).compareTo("aaaa") == 0)
        		ramce.get(pocet-1).setTyp("IEEE 802.3 - LLC - SNAP");
        	else ramce.get(pocet-1).setTyp("IEEE 802.3 - LLC");
        	
        }
	}
		/**Prevod po bytoch do hexadecimal. tvaru*/
        public static String hexadecimalne(PcapPacket nejakyPacket) {
            StringBuilder hexastring = new StringBuilder();
            int velkost = nejakyPacket.size();
            for (int i = 0; i < velkost; i++) {
                hexastring.append(String.format("%02x", nejakyPacket.getUByte(i)));
            }
            return hexastring.toString();
        }
        /**Donuluje binarny string do 8 miest*/
        public String donuluj(String string){
    		StringBuilder celyString = new StringBuilder();
    		switch (string.length()){
    		case 1:	
    			celyString.append("0000000" + string);
    			break;
    		case 2:
    			celyString.append("000000" + string);
    			break;
    		case 3:
    			celyString.append("00000" + string);
    			break;
    		case 4:
    			celyString.append("0000" + string);
    			break;
    		case 5:
    			celyString.append("000" + string);
    			break;
    		case 6:
    			celyString.append("00" + string);
    			break;
    		case 7:
    			celyString.append("0" + string);
    			break;
    		}
    		return celyString.toString();
    	}
    
        /**Tu sa len otvorÌ GUI*/
	public static void main (String[] args) {
		new Gui();          
	}

	}


