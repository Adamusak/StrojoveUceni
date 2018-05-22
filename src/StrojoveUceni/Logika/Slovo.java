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

}
