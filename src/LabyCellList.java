import java.util.ArrayList;

public class LabyCellList {
	private static ArrayList<ArrayList<Integer>> ensemblesDeCases;
	private final static int firstId = 1;

	/**
	 * PROBALBY USELESS SHIT
	 * 
	 * @param id
	 * @return
	 */
	public static int find(int id) {
		for (int i = 0; i < ensemblesDeCases.size(); ++i) {
			if (ensemblesDeCases.get(i).contains(id)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 
	 * @param list1
	 * @param list2
	 */
	public static void fusion(int list1, int list2) {
		if (ensemblesDeCases.get(list2).contains(firstId)) {
			ensemblesDeCases.get(list2).addAll(ensemblesDeCases.get(list1));
			ensemblesDeCases.remove(list1);
		} else {
			ensemblesDeCases.get(list1).addAll(ensemblesDeCases.get(list2));
			ensemblesDeCases.remove(list2);
		}
	}

	/**
	 * 
	 * @param id1
	 * @param id2
	 */
	public static boolean unionFind(int id1, int id2) {
		assert (id1 != id2);
		for (ArrayList<Integer> ali : ensemblesDeCases) {
			if (ali.contains(id1) && ali.contains(id2)){
				return false;
			} else {
				fusion(find (id1), find (id2));
				return true;
			}
		}
		return false;
	}
}
