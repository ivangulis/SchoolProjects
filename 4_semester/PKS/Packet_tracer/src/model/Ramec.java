package model;

public class Ramec {
	private int cislo; //èíslo rámca
	private int dlzkaDriver;
	private int dlzkaMedium;
	private String typ; //ethertyp
	private String ZMAC; //zdrojová
	private String CMAC; //cielová
	private String ramec;
	private String sourceIP;
	private String destIP;
	/**Flagy*/
	private char SYN;
	private char ACK;
	private char FIN;
	private char RES;
	private String transportProt;
	
	public char getSYN() {
		return SYN;
	}
	public void setSYN(char sYN) {
		SYN = sYN;
	}
	public char getACK() {
		return ACK;
	}
	public void setACK(char aCK) {
		ACK = aCK;
	}
	public char getFIN() {
		return FIN;
	}
	public void setFIN(char fIN) {
		FIN = fIN;
	}
	public char getRES() {
		return RES;
	}
	public void setRES(char rES) {
		RES = rES;
	}
	public int getCislo() {
		return cislo;
	}
	public void setCislo(int cislo) {
		this.cislo = cislo;
	}
	public int getDlzkaDriver() {
		return dlzkaDriver;
	}
	public void setDlzkaDriver(int dlzkaDriver) {
		this.dlzkaDriver = dlzkaDriver;
	}
	public int getDlzkaMedium() {
		return dlzkaMedium;
	}
	public void setDlzkaMedium(int dlzkaMedium) {
		this.dlzkaMedium = dlzkaMedium;
	}
	public String getTyp() {
		return typ;
	}
	public void setTyp(String typ) {
		this.typ = typ;
	}
	public String getZMAC() {
		return ZMAC;
	}
	public void setZMAC(String zMAC) {
		ZMAC = zMAC;
	}
	public String getCMAC() {
		return CMAC;
	}
	public void setCMAC(String cMAC) {
		CMAC = cMAC;
	}
	public String getRamec() {
		return ramec;
	}
	public void setRamec(String ramec) {
		this.ramec = ramec;
	}
	public String getSourceIP() {
		return sourceIP;
	}
	public void setSourceIP(String sourceIP) {
		this.sourceIP = sourceIP;
	}
	public String getDestIP() {
		return destIP;
	}
	public void setDestIP(String destIP) {
		this.destIP = destIP;
	}
	public String getTransportProt() {
		return transportProt;
	}
	public void setTransportProt(String transportProt) {
		this.transportProt = transportProt;
	}
}
