import java.util.ArrayList;
import java.util.List;

public class LabyCellList {
	private List<List<Integer>> ensemblesDeCases;
	private final int sizeMax;
	private final int firstId = 0;

	/**
	 * Constructor of the object
	 * @param g : Grid in need of a laby
	 */
	public LabyCellList(Grid g){
		int k = 0;
		ensemblesDeCases = new ArrayList<List<Integer>>();
		for (int i = 0 ; i<g.rows ; ++i){
			for (int j = 0 ; j<g.columns; ++j){
				ensemblesDeCases.add(new ArrayList<Integer>());
				ensemblesDeCases.get(k).add(g.cell[i][j].getId());
				k++;
			}
		}
		sizeMax = g.rows*g.columns;
	}
	
	/**
	 * Find the list relative to an id
	 * @param id : cell.id to be found
	 * @return : number of the ArrayList containing the id 
	 */
	private int find(int id) {
		for (int i = 0; i < ensemblesDeCases.size(); ++i) {
			if (ensemblesDeCases.get(i).contains(id)) {
				return i;
			}
		}
		return id;
	}
	
	/**
	 * Verify that the two cells are in different lists
	 * @param id1 : first id to evaluate
	 * @param id2 : second id to evaluate
	 * @return : true if not in the same list
	 */
	public boolean areNotLinked(int id1, int id2){
		return (find(id1) != find(id2));
	}

	/**
	 * Merges two Lists
	 * @param list1 : list from ensemblesDeCases
	 * @param list2 : list from ensemblesDeCases
	 */
	private void fusion(int list1, int list2) {
		//stacks everything of the first line so that
		//isALaby is quicker
		//if (ensemblesDeCases.get(list2).contains(firstId)) {
			ensemblesDeCases.get(list2).addAll(ensemblesDeCases.get(list1));
			ensemblesDeCases.remove(list1);
		/*} else {
			ensemblesDeCases.get(list1).addAll(ensemblesDeCases.get(list2));
			ensemblesDeCases.remove(list2);
		}*/
	}

	/**
	 * Given two ids of cell will merge their lists if different
	 * @param id1
	 * @param id2
	 */
	public void unionFind(int id1, int id2) {
		assert (id1 != id2);
		fusion(find (id1), find (id2));
	}
	
	/**
	 * Verfiy if the laby is built
	 * @return : completion
	 */
	public boolean isALaby(){
		if (ensemblesDeCases.get(0).size() == sizeMax){
			return true;
		}
		return false;
	}
}
