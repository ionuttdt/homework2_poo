import java.util.*;
public class Node {
	
	private int Index_N;
	private List<Element> list = null;
	
	//constructor pentru nod cu capacitate max
	//indexul il setez cand construiesc DB ul
	public Node(int max){
		list = new ArrayList<>(max);
	}

	public int getIndex_N() {
		return Index_N;
	}

	public void setIndex_N(int index_N) {
		Index_N = index_N;
	}
	
	public List<Element> getElemente() {
		return list;
	}
	
	public void setElemente(List<Element> elemente) {
		this.list = elemente;
	}
	
	 public int compareTo(Node n) {
	        return n.getElemente().size()>= this.list.size() ? -1 : 0;
	    }
	
}
