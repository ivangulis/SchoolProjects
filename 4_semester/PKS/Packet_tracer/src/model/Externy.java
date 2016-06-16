package model;

public class Externy {
	/**Všetky atributy naèítavane z externého suboru*/
	private String IPv4;
	private String ARP;
	private String ICMP;
	private String TCP;
	private String UDP;
	private String tftp;
	private String http;
	private String https;
	private String telnet;
	private String ssh;
	private String ftp_control;
	private String ftp_data;
	
	public String getIPv4() {
		return IPv4;
	}
	public void setIPv4(String iPv4) {
		IPv4 = iPv4;
	}
	public String getARP() {
		return ARP;
	}
	public void setARP(String aRP) {
		ARP = aRP;
	}
	public String getICMP() {
		return ICMP;
	}
	public void setICMP(String iCMP) {
		ICMP = iCMP;
	}
	public String getTCP() {
		return TCP;
	}
	public void setTCP(String tCP) {
		TCP = tCP;
	}
	public String getUDP() {
		return UDP;
	}
	public void setUDP(String uDP) {
		UDP = uDP;
	}
	public String getTftp() {
		return tftp;
	}
	public void setTftp(String tftp) {
		this.tftp = tftp;
	}
	public String getHttp() {
		return http;
	}
	public void setHttp(String http) {
		this.http = http;
	}
	public String getHttps() {
		return https;
	}
	public void setHttps(String https) {
		this.https = https;
	}
	public String getTelnet() {
		return telnet;
	}
	public void setTelnet(String telnet) {
		this.telnet = telnet;
	}
	public String getSsh() {
		return ssh;
	}
	public void setSsh(String ssh) {
		this.ssh = ssh;
	}
	public String getFtp_control() {
		return ftp_control;
	}
	public void setFtp_control(String ftp_control) {
		this.ftp_control = ftp_control;
	}
	public String getFtp_data() {
		return ftp_data;
	}
	public void setFtp_data(String ftp_data) {
		this.ftp_data = ftp_data;
	}
	
	
}
