package StrojoveUceni.Logika;

import java.util.Collection;

public class Veta {

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 200
	 */
	private String Veta;

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 
	 */
	private int ID_Veta;

	public Veta() {
		this.Veta = "";
		this.ID_Veta = 0;
	}

	public String getVeta() {
		return Veta;
	}

	public void setVeta(String veta) {
		Veta = veta;
	}

	public int getID_Veta() {
		return ID_Veta;
	}

	public void setID_Veta(int iD_Veta) {
		ID_Veta = iD_Veta;
	}
	
	
}
