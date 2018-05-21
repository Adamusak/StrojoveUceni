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

	private Collection<ZaznamOdpovedi> zaznamOdpovedi;

}