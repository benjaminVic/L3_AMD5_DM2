import java.util.ArrayList;
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
	 * 
	 * @param rows
	 * @param colums
	 * @return
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
					List<Integer> possible = new ArrayList<Integer>();
					for (int k = 0; k < 6; ++k) {
						if (laby.cell[i][j].hasNeighbor(k)) {
							possible.add(k);
						}
					}

					Random r = new Random();
					
					do {
						int randomValue = r.nextInt(possible.size());
						int idCell = laby.cell[i][j].getId();
						int idVoisin = laby.cell[i][j].getNeighborId(possible
								.get(randomValue));

						if (lcl.areNotLinked(idCell, idVoisin)) {
							laby.cell[i][j].breakWallWith(possible.get(randomValue));
							lcl.unionFind(idCell, idVoisin);
							break;
						} else {
							possible.remove(randomValue);
						}
					} while (possible.isEmpty() == false);

				}
			}
		}
		
		//Marks the entrance and exit of the labyrinth
		laby.cell[0][0].breakWallWith(laby.cell[0][0].WEST);
		laby.cell[rows - 1][colums - 1]
				.breakWallWith(laby.cell[rows - 1][colums - 1].EAST);
		laby.showGrid(true);

		return laby;
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
