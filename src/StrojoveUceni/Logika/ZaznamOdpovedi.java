package StrojoveUceni.Logika;

public class ZaznamOdpovedi {

	private int Slovo_ID;

	private int Uzivatel_ID;

	/**
	 * NOT NULL false
	 * DomainName 
	 * DatatypeName VARCHAR
	 * DataTypeLength/Precision 50
	 */
	private String Vyznam;

	/**
	 * NOT NULL true
	 * DomainName 
	 * DatatypeName INT
	 * DataTypeLength/Precision 
	 */
	private int ID_Zaznam;

	private int ID_Veta;

	public ZaznamOdpovedi() {
		this.Uzivatel_ID = 0;
		this.Vyznam = "";
		this.ID_Zaznam = 0;
		this.ID_Veta = 0;
	}
}
