import java.util.*;

public class Element implements Cloneable{

	private String name_enitity;//fiecare element are atasat numele entitatii de care apartine
	private int RF;//de care ori este un element in memorie
	private int No_Attributes;//numarul de atibute din element
	private List<Object> object = null;//lista cu valorile elementelor
	private long time;//timpul elementului
	
	
	Element()
	{
		this.No_Attributes = 0;
	}
	
	//constructor pentru un element
	Element(int nr_object,int RF, String name ){
		this.object = new ArrayList<>(nr_object);
		this.name_enitity = name;
		this.No_Attributes = nr_object;
		this.RF = RF;
		time = System.nanoTime();//setez timpul unui element
	}
	
	//functie de clonare
	public Object clone()throws CloneNotSupportedException{  
		return super.clone();  
	}  

	//copiez un element pentru a introduce elementul, nu referinta
	public Element copy(Element e)
	{
		Element cop = new Element(e.getNo_Attributes(),e.getRF(),e.getName_enitate());
		for(Object o:e.getObject())
		{
			cop.getObject().add(o);
		}
		return cop;
	}
	
	//compar timpul a doua elemnte
	public boolean timecmp(long t)
	{
		long aux = t - this.time;
		if(aux < 0)
			return false;
		return true;
	}

	//introduc un element la indexul index
	public Object set(int index, Object element) {
		return object.set(index, element);
	}


	public Object remove(int index) {
		return object.remove(index);
	}
	
	/*get si set pentru fiecare lement din clasa, nume sugestive*/
	public long getTime() {
		return time;
	}
	
	public void setTime(long time) {
		this.time = time;
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

	public List<Object> getObject() {
		return object;
	}

	public void setObject(List<Object> object) {
		this.object = object;
	}

	public String getName_enitate() {
		return name_enitity;
	}

	public void setName_enitate(String name_enitate) {
		this.name_enitity = name_enitate;
	}

}
