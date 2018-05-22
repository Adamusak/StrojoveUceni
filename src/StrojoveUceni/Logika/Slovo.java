package StrojoveUceni.Logika;

import java.util.Collection;

public class Slovo {

	private int Veta_ID;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 30
	 */
	private String Slovo;

	/**
	 * NOT NULL true 
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 
	 */
	private int ID_Slovo;

	public Slovo() {
		this.Veta_ID = 0;
		this.Slovo = "";
		this.ID_Slovo = 0;
	}

	public int getVeta_ID() {
		return Veta_ID;
	}

	public void setVeta_ID(int veta_ID) {
		Veta_ID = veta_ID;
	}

	public String getSlovo() {
		return Slovo;
	}

	public void setSlovo(String slovo) {
		Slovo = slovo;
	}

	public int getID_Slovo() {
		return ID_Slovo;
	}

	public void setID_Slovo(int iD_Slovo) {
		ID_Slovo = iD_Slovo;
	}

}
