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

	public int getSlovo_ID() {
		return Slovo_ID;
	}

	public void setSlovo_ID(int slovo_ID) {
		Slovo_ID = slovo_ID;
	}

	public int getUzivatel_ID() {
		return Uzivatel_ID;
	}

	public void setUzivatel_ID(int uzivatel_ID) {
		Uzivatel_ID = uzivatel_ID;
	}

	public String getVyznam() {
		return Vyznam;
	}

	public void setVyznam(String vyznam) {
		Vyznam = vyznam;
	}

	public int getID_Zaznam() {
		return ID_Zaznam;
	}

	public void setID_Zaznam(int iD_Zaznam) {
		ID_Zaznam = iD_Zaznam;
	}

	public int getID_Veta() {
		return ID_Veta;
	}

	public void setID_Veta(int iD_Veta) {
		ID_Veta = iD_Veta;
	}
	
	
}
