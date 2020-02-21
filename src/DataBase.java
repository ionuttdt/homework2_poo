import java.util.*;

public class DataBase {
	
	private String name;//nuemle bazei de date
	private int no_nodes;//numarul curent de noduri
	private int max_capacity;//capacitatea fiecarui nod
	private List<Entitate> entities;//entitatile sunt separate de elementele proproiu-zise
	private List<Node> nodes;//lista de noduri in care am elementele
	
	
	//functia care sterge un element cu mai vechi ca timpul dat
	public void removeTime(long time)
	{
		//parcurg nodurile
		for(Node nod:this.nodes)
		{
			//parcurg fiecare element
			for(int i = 0 ; i< nod.getElemente().size();i++)
			{
				//verific daca elementul curent trebuie sters
				if(nod.getElemente().get(i).timecmp(time)) {
					nod.getElemente().remove(i);
					i--;
				}
			}
		}
	}
	//verific de cat noduri am nevoie ca sa pot introduce un element
	//verificarea se face in functie de RF-ul elementului 
	public int space(Element e)
	{
		int nr = e.getRF();
		for(Node n:this.getNodes())
			if(n.getElemente().size() < this.max_capacity)
				nr --;//scad de fiecare data cand am spatiu intr-un nod
		if(nr > 0)
			return nr;//construiec nr noduri
		return 0;//nu trebuie sa creez niciun nod
	}
	
	public void bonusfull(Element e)
	{
		int test = space(e);//iar numarul de noduri pe care trebuie sa le introduc in DB
		while(test > 0)
		{
			this.getNodes().add(new Node(max_capacity));//adaug un nou nod
			this.getNodes().get(this.getNo_nodes()).setIndex_N(this.getNo_nodes()+1);//setez id ul nodului
			this.setNo_nodes(this.getNo_nodes() + 1);//cresc numarul curent de noduri
			test --;
		}
	}
	
	//coonstructor pentru DB
	DataBase(String name, int no_nodes, int max_capacity)
	{
		this.name = name;//numele bazei de date
		this.no_nodes = no_nodes;//numarul curent de noduri
		this.max_capacity = max_capacity;
		nodes = new ArrayList<>();
		//introduc no_nodes noduri si le initializez
		for(int i = 0; i < no_nodes;i++)
		{
			nodes.add(new Node(max_capacity));
			nodes.get(i).setIndex_N(i+1);//setez id ul fiecarui nod
		}
		this.entities = new ArrayList<>();
	}
	
	public void addElement(Element e)
	{
		Element aux =null;
		try {
			aux = (Element)e.clone();//clonez elementul ca sa il adaug 
		}
		catch(CloneNotSupportedException c){}  
		this.bonusfull(e);//verific daca am suficient spatiu pentru a adauga elmentul de RF ori
		int no_add = e.getRF();
		for(Node nod:nodes)
		{
			if(nod.getElemente().size() == this.max_capacity)//daca nodul nu mai are locuri disponibile merg la urmatorul
				continue;
			if(no_add == 0)
				break;
			nod.getElemente().add(aux);
			this.bonusfull(e);//verific daca am suficient spatiu pentru a adauga elmentul de RF ori
			no_add --;
		}
	}
	
	public void addElementIndex(Element e)
	{
		Element aux =null;
		try {
			aux = (Element)e.clone();//clonez elementul ca sa il adaug 
		}
		catch(CloneNotSupportedException c){} 
		this.bonusfull(e);//verific daca am suficient spatiu pentru a adauga elmentul de RF ori
		int no_add = e.getRF();
		for(Node nod:nodes)
		{
			if(nod.getElemente().size() == this.max_capacity)//nodul curent este plin si continui 
				continue;
			if(no_add == 0)//am adaugat elementul de RF ori
				break;
			nod.getElemente().add(0, aux);
			no_add --;
		}
	}
	
	//functia care stegre un element din DB
	public boolean removeElement(String Entity, String Key)
	{
		boolean verify = true;//true daca nu am element pe care sa il sterg
		for(Node nod:nodes)
		{
			if(nod == null)//nu am elemente in nodul curent
				continue;
			for(Element e:nod.getElemente())
			{
				if(String.valueOf(e.getName_enitate()).equals(Entity))//verific entitatea
					if(e.getObject().get(0).equals(Key))//verific key
					{
						nod.getElemente().remove(e);//sterg elementul
						verify = false;//false daca am sters un element
						break;
					}
			}
		}
		return verify;
	}
	
	/*Mai jos am get si set pentru fiecare variabila din aceasta clasa*/
	public List<Entitate> getEntitati() {
		return entities;
	}

	public void setEntitati(List<Entitate> entitati) {
		this.entities = entitati;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}

	public String getName() {
		return name;
	}

	public int getNo_nodes() {
		return no_nodes;
	}

	public int getMax_capacity() {
		return max_capacity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNo_nodes(int no_nodes) {
		this.no_nodes = no_nodes;
	}

	public void setMax_capacity(int max_capacity) {
		this.max_capacity = max_capacity;
	}
	
}
