package Model;

public class NetworkDevice {
	
	private String IPAddress;
	private String MACAddress;
	private int Id;
	private String type;
	
	public NetworkDevice(String iPAddress, String mACAddress, int id, String type) {
		IPAddress = iPAddress;
		MACAddress = mACAddress;
		Id = id;
		this.type = type;
	}
	public String getIPAddress() {
		return IPAddress;
	}
	public void setIPAddress(String iPAddress) {
		IPAddress = iPAddress;
	}
	public String getMACAddress() {
		return MACAddress;
	}
	public void setMACAddress(String mACAddress) {
		MACAddress = mACAddress;
	}
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		this.Id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
