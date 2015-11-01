import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Laby {

	public static boolean debug = false;

	/**
	 * Simple examples
	 */
	public static void main(String[] args) {
		Grid myGrid = new Grid(30, 10);
		Cellule cel = myGrid.cell[0][0];
		while (cel.hasNeighbor(Cellule.SOUTHEAST)) {
			cel.breakWallWith(Cellule.SOUTHEAST);
			cel = cel.getNeighbor(Cellule.SOUTHEAST).getCell();
		}
		cel = myGrid.cell[myGrid.rows - 1][myGrid.columns - 1];
		while (cel.hasNeighbor(Cellule.NORTHWEST)) {
			cel.breakWallWith(Cellule.NORTHWEST);
			cel = cel.getNeighbor(Cellule.NORTHWEST).getCell();
		}
		cel = myGrid.cell[0][myGrid.columns - 1];
		while (cel.hasNeighbor(Cellule.SOUTHWEST)) {
			cel.breakWallWith(Cellule.SOUTHWEST);
			cel = cel.getNeighbor(Cellule.SOUTHWEST).getCell();
		}
		cel = myGrid.cell[myGrid.rows - 1][0];
		while (cel.hasNeighbor(Cellule.NORTHEAST)) {
			cel.breakWallWith(Cellule.NORTHEAST);
			cel = cel.getNeighbor(Cellule.NORTHEAST).getCell();
		}
		cel = myGrid.cell[myGrid.rows / 3][0];
		while (cel.hasNeighbor(Cellule.EAST)) {
			cel.breakWallWith(Cellule.EAST);
			cel = cel.getNeighbor(Cellule.EAST).getCell();
		}
		cel = myGrid.cell[(myGrid.rows / 3) * 2][myGrid.columns - 1];
		while (cel.hasNeighbor(Cellule.WEST)) {
			cel.breakWallWith(Cellule.WEST);
			cel = cel.getNeighbor(Cellule.WEST).getCell();
		}
		myGrid.showGrid();

		for (int i = 0; i < 4; i++)
			System.out.println();

		// Laby.debug = true;
		myGrid = new Grid(40, 40);
		myGrid.cell[0][0].breakWallWith(Cellule.WEST);
		Random rand = new java.util.Random();
		// rand.setSeed(0);
		for (int i = 0; i < myGrid.rows; i++)
			for (int j = 0; j < myGrid.columns; j++)
				myGrid.cell[i][j].breakWallWith(Math.abs(rand.nextInt() % 6));

		myGrid.showGrid();
		LabyCellList lcl = new LabyCellList(myGrid);
	}

	/**
	 * Create the laby with the "Algorithme 1"
	 * @param rows : number of rows
	 * @param colums : number of colums
	 * @return : the constructed labyrinth
	 */
	public static Grid makeLabyA(int rows, int colums) {
		Grid laby = new Grid(rows, colums);
		LabyCellList lcl = new LabyCellList(laby);

		/* tant que il existe deux cellules non connectées faire */
		while (!lcl.isALaby()) {

			/* pour chaque cellule c dans un certain ordre faire */
			for (int i = 0; i < laby.rows; ++i) {
				for (int j = 0; j < laby.columns; ++j) {
					/*
					 * si il existe un voisin de c non connecté à c alors on
					 * cree une liste des directions possibles contenant l'int
					 * des direction
					 */
					List<Integer> possible = Laby.probabiltySpace(laby.cell[i][j]);
					Cellule currentCell = laby.cell[i][j];

					Random r = new Random();
					int randomValue;
					int idCell;					
					int idVoisin;
					
					do {
						randomValue = r.nextInt(possible.size());
						idCell = currentCell.getId();
						idVoisin = currentCell.getNeighborId(possible
								.get(randomValue));

						if (lcl.areNotLinked(idCell, idVoisin)) {
							currentCell.breakWallWith(possible.get(randomValue));
							lcl.unionFind(idCell, idVoisin);
							break;
						} else {
							possible.remove(randomValue);
						}
					} while (possible.isEmpty() == false);
				}
			}
		}
		setEntrenceExit(laby, rows, colums);	
		return laby;
	}
	
	/**
	 * Brake the wall of the entrance and the exit of the laby
	 * @param laby : Constructed labyrinth
	 * @param rows:  number of rows
	 * @param colums : number of colums
	 */
	private static void setEntrenceExit(Grid laby, int rows, int colums){
		laby.cell[0][0].breakWallWith(laby.cell[0][0].WEST);
		laby.cell[rows - 1][colums - 1]
				.breakWallWith(laby.cell[rows - 1][colums - 1].EAST);
	}
	
	/**
	 * Verify the neighboring Cellules that can be visited
	 * @param c : Cell from which we need the possible neighbors
	 * @return : The list of possibilities
	 */
	private static ArrayList<Integer> probabiltySpace(Cellule c){
		ArrayList<Integer> possible = new ArrayList<Integer>();
		for (int k = 0; k < 6; ++k) {
			if (c.hasNeighbor(k)) {
				possible.add(k);
			}
		}
		return possible;
	}
	
	/**
	 * Create the laby with the "Algorithme 2"
	 * @param rows : number of rows
	 * @param colums : number of colums
	 * @return : the Grid of a laby
	 */
	public static Grid makeLaby(int rows, int colums){
		Grid laby = new Grid(rows, colums);
		LabyCellList lcl = new LabyCellList(laby);		
		List<Integer> ordreAleatoire = randomGeneration(rows*colums);
		for (int i : ordreAleatoire){
			
			for (Cellule[] cLine : laby.cell){
				for (Cellule c : cLine){
					if (c.getId() == i){
						List<Integer> possible = Laby.probabiltySpace(c);
						
						Random r = new Random();
						int randomValue;
						int idCell;					
						int idVoisin;
						
						do {
							randomValue = r.nextInt(possible.size());
							idCell = c.getId();
							idVoisin = c.getNeighborId(possible
									.get(randomValue));

							if (lcl.areNotLinked(idCell, idVoisin)) {
								c.breakWallWith(possible.get(randomValue));
								lcl.unionFind(idCell, idVoisin);
								break;
							} else {
								possible.remove(randomValue);
							}
						} while (possible.isEmpty() == false);
					}
				}
			}
		}
		
		setEntrenceExit(laby, rows, colums);
		laby.showGrid(true);
		return laby;
	}
	
	/**
	 * Generate a shuffled table of ids
	 * @param n : number of Cellule
	 * @return : a table containing in a random order the cell ids
	 */
	public static ArrayList<Integer> randomGeneration(int n) {
		ArrayList<Integer> listRand = new ArrayList<Integer>();
		for (int i = 0; i < n; ++i) {
			listRand.add(i);
		}
		Random r = new Random();
		int j;
		for (int i = 0; i < n; ++i) {
			j = r.nextInt(listRand.size() - i) + i;
			Collections.swap(listRand, i, j);
		}
		return listRand;
	}

	//
	// public static void main(String[] args){
	// HexGrid myHexGrid=new HexGrid(8,10);
	//
	//
	//
	// /*test*/
	// myHexGrid.print();
	// System.out.println("-----------");
	// myHexGrid.getCell(5).printNeighborhood();
	// System.out.println("-----------");
	// myHexGrid.getCell(1).printNeighborhood();
	// System.out.println("-----------");
	// myHexGrid.getCell(8).printNeighborhood();
	// System.out.println("-----------");
	// }
	//
}
