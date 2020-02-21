import java.util.*;

public class Entitate {
	
	private String name_enitate;//numele entitatii
	private int RF;//RF ul elementelor din entitate
	private int No_Attributes;//cate atribute are fiecare entitate
	private List<TipEntitate> attributes;//lista cu atributele si tipurile
	
	//constrctor
	public Entitate(String name, int RF, int No_A){
		this.name_enitate = name; //setez numele
		this.RF = RF; //RF-ul
		this.No_Attributes = No_A;//numarul de atribute din entitate
		attributes  = new ArrayList<>(No_A);//si lista cu No_A elmente
	}
	
	public List<TipEntitate> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<TipEntitate> attributes) {
		this.attributes = attributes;
	}

	public int getRF() {
		return RF;
	}

	public int getNo_Attributes() {
		return No_Attributes;
	}

	public void setRF(int rF) {
		RF = rF;
	}

	public void setNo_Attributes(int no_Attributes) {
		No_Attributes = no_Attributes;
	}

	public String getName_enitate() {
		return name_enitate;
	}

	public void setName_enitate(String name_enitate) {
		this.name_enitate = name_enitate;
	}
	
}
