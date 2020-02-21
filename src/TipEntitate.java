
public class TipEntitate {
	
	private String tip;//String, Integer sau Float
	private String atribut;//numele 
	
	//constructor
	public TipEntitate(String tip, String atribut) {
		this.tip = tip;
		this.atribut = atribut;
	}
	
	//nume sugestive:
	public String getTip() {
		return tip;
	}

	public String getAtribut() {
		return atribut;
	}
	
	public void setTip(String tip) {
		this.tip = tip;
	}

	public void setAtribut(String atribut) {
		this.atribut = atribut;
	}
	
}
