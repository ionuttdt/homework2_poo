import java.io.*;
import java.text.*;
import java.util.*;

public class Read {
	
	//functia care face toata treaba
	public void functie(File inFile) {
		//fac fisierul de out
		File outFile = new File(inFile + "_out");
		Scanner sc = null ;
		String line;//string pentru a citi fiecare linie
		DataBase DB;//baza mea de date
		PrintWriter pwr = null;
		try{
			pwr = new PrintWriter(outFile);
		}
		catch (IOException e) {
            e.printStackTrace();
		}
		
		try {
			sc = new Scanner(inFile);
		}
		catch (IOException e) {
	            e.printStackTrace();
	    }
		
		line = sc.nextLine();//citesc prima linie
		String[] vec = line.split(" ");
		DB = new DataBase(vec[1],Integer.parseInt(vec[2]),Integer.parseInt(vec[3]));//construiesc baza de date
		while(sc.hasNextLine())//cat timp citesc din fisier
		{
			line = sc.nextLine();//citesc fiecare linie
			vec = line.split(" ");//impart linia citita in functie de delimitator
			if(vec[0].equals("CREATE"))//Daca trebuie sa construiesc o entitate
			{
				//apelez constructorul de entitate cu parametri cititi din fisier
				Entitate E = new Entitate(vec[1],Integer.parseInt(vec[2]),Integer.parseInt(vec[3]));
				for(int i = 4; i < vec.length - 1; i += 2)//cat timp sunt atribute neintroduse 
				{
					TipEntitate entitate = new TipEntitate(vec[i+1],vec[i]);
					E.getAttributes().add(entitate);
				}
				DB.getEntitati().add(E);//adaug entitatea construita mai sus in DB
			}
			else if(vec[0].equals("INSERT"))//"functia" de insert
			{
				Element element_aux;
				Entitate entitate_aux = null;
				for(Entitate i:DB.getEntitati())//caut entitatea corespunzatoare elementului 
					if(i.getName_enitate().equals(vec[1]))
					{
						entitate_aux = i;//memorez entitatea in entitate_aux
					}
				//construiesc un element nou
				element_aux = new Element(entitate_aux.getNo_Attributes(),entitate_aux.getRF(),entitate_aux.getName_enitate());
				for(int i = 1; i <= element_aux.getNo_Attributes(); i++)
				{
					//daca elementul este de tip Float ii elimin zerourile de la sfarsit(daca este cazul)
					//si ma asigur ca formatul este corect
					if(entitate_aux.getAttributes().get(i-1).getTip().equals("Float"))
					{
						DecimalFormat df = new DecimalFormat("#.##");
						float f = Float.parseFloat(vec[i+1]);
						element_aux.getObject().add(df.format(f));
						
						continue;
					}
					//adaug un nou atribut elementului
					element_aux.getObject().add(vec[i + 1]);
				}
				DB.addElementIndex(element_aux);//adaug elementul in DB
			}
			else if(vec[0].equals("DELETE"))//stergerea un elemt din DB
			{
				boolean verify = DB.removeElement(vec[1], vec[2]);//apelez metoda care face verificarile si stegre lementul
				if(verify)//daca este verify este true inseamna ca nu am sters niciun element
					pwr.println("NO INSTANCE TO DELETE");//si afisez mesajul de eroare
				
			}
			else if(vec[0].equals("UPDATE"))//este modificat un element din DB
			{
				//variabile ajutatoare
				Entitate aux_ent = null;
				Element aux_el = new Element();
				for(Entitate ent:DB.getEntitati())//caut entitatea elementului caruia urmeaza sa ii fac update
				{
					if(ent.getName_enitate().equals(vec[1]))
					{
						aux_ent = ent;
						break;//ma opresc cand am gasit entitatea
					}
				}
				//caut elementul si caruia trebuie sa ii fac update
				for(Node nod:DB.getNodes())
				{
					for(Element el:nod.getElemente())
						if(el.getObject().get(0).equals(vec[2]))
						{
							aux_el = aux_el.copy(el);
							break;
						}
					if(aux_el != null)
						break;
				}
				//ii fac update 
				for(int i = 3; i < vec.length; i += 2)
				{
					
					if(aux_el.getObject()==null)
						break;
					int counter = 0;
					for(TipEntitate tip:aux_ent.getAttributes())
					{
						if(tip.getAtribut().equals(vec[i]))
						{
							//verificarea pentru formatul atributelor de timp float
							if(aux_ent.getAttributes().get(counter).getTip().equals("Float"))
							{
								DecimalFormat df = new DecimalFormat("#.##");
								float f = Float.parseFloat(vec[i+1]);
								aux_el.getObject().remove(counter);
								aux_el.getObject().add(counter, df.format(f));
							}
							else
							{
								aux_el.getObject().remove(counter);
								aux_el.getObject().add(counter, vec[i+1]);
							}
						}		
						counter ++;
					}
				}
				DB.removeElement(vec[1], vec[2]);
				DB.addElementIndex(aux_el);
			}
			else if(vec[0].equals("GET"))//afisarea unui element din baza de date
			{
				String nodes_str = null;
				Element element = null;
				Entitate entity = null;
				for(Node nod:DB.getNodes())//parcurg fiecare nod
				{
					for(Element el:nod.getElemente())//parcurg fiecare element
					{
						if(el.getName_enitate().equals(vec[1]))//daca entitatea se potriveste...
						{
							if(el.getObject().get(0).equals(vec[2]))//...verific si key
							{
								element = el;
								//mai jos construiesc stringul cu nodurile unde am gasit elementul
								if(nodes_str == null)
									nodes_str = "Nod" + nod.getIndex_N() + " ";
								else 
									nodes_str += "Nod" + nod.getIndex_N() + " ";
							}
						}
					}
				}
				for(Entitate en:DB.getEntitati())
				{
					if(en.getName_enitate().equals(vec[1]))
					{
						entity = en;
						break;
					}
				}
				//daca elementul nu a fost gasit in niciun nod afisez mesajul de eroare
				if(nodes_str == null)
					pwr.println("NO INSTANCE FOUND");//mesajul de eroare
				else //iau fiecare atribut si valoarea sa si le adaug string-ului
				{
					nodes_str += element.getName_enitate() + " ";
					for(int i = 0; i < element.getObject().size(); i++)	
					{
						if(i == element.getObject().size() - 1)
						{
							nodes_str += entity.getAttributes().get(i).getAtribut() + ":" 
							+ element.getObject().get(i);
							break;
						}
						nodes_str += entity.getAttributes().get(i).getAtribut() + ":" 
						+ element.getObject().get(i) + " ";
					}
					pwr.println(nodes_str);//afisez stringul construit mai sus
				}
			}
			else if(vec[0].equals("SNAPSHOTDB"))//daca trebuie sa printez baza de date
			{
				//varibile ajutatoare
				String str = null;
				Entitate aux_ent = null;
				int nr_nod = 0;
				for(Node nod:DB.getNodes())
				{
					if(nod.getElemente().isEmpty())//nu am elemente in nodul curent si trec la urmatorul
						continue;
					pwr.println("Nod"+nod.getIndex_N());//afisez nodul curent
					for(Element el:nod.getElemente())//parcurg fiecare element din nod si il afisez
					{
						nr_nod++;
						for(Entitate en:DB.getEntitati())//caut entitatea elementului curent
							if(el.getName_enitate().equals(en.getName_enitate()))
							{
								aux_ent = en;
								break;
							}
						//iau fiecare atrinut si valoarea sa si le adaug stringului pe care il printez
						for(int i = 0; i < el.getObject().size(); i++)
						{
							if(i == 0)
								str = aux_ent.getName_enitate()+" "+ aux_ent.getAttributes().get(i).getAtribut()
								+":"+el.getObject().get(i)+" ";
							else if(i == el.getObject().size()-1)
								str += aux_ent.getAttributes().get(i).getAtribut()+":"+el.getObject().get(i);
							else
								str += aux_ent.getAttributes().get(i).getAtribut()+":"+el.getObject().get(i)+" ";
						}
						pwr.println(str);
						str = null;
					}
				}
				if(nr_nod == 0)
					pwr.println("EMPTY DB");		
			}
			else if(vec[0].equals("CLEANUP"))//functie de stergere a elementelor in functie de timestep
			{
				DB.removeTime(Long.parseLong(vec[2]) );//stransform stringul in long si apelez metoda de stergere
			}
		}
		  pwr.flush();
          pwr.close();
	}
	
}
