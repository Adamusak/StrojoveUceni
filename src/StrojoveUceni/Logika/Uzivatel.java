package StrojoveUceni.Logika;

import java.util.Collection;

public class Uzivatel {

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 30
	 */
	private String Nick;

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 
	 */
	private int ID_Uzivatel;
	
	public Uzivatel() {
		this.Nick = "";
		this.ID_Uzivatel = 0;
	}

	public String getNick() {
		return Nick;
	}

	public void setNick(String nick) {
		Nick = nick;
	}

	public int getID_Uzivatel() {
		return ID_Uzivatel;
	}

	public void setID_Uzivatel(int iD_Uzivatel) {
		ID_Uzivatel = iD_Uzivatel;
	}
	
	

}
