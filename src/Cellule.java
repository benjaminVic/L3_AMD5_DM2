public class Cellule {
	public final static int WEST = 0;
	public final static int EAST = 5;
	public final static int SOUTHEAST = 2;
	public final static int SOUTHWEST = 4;
	public final static int NORTHEAST = 1;
	public final static int NORTHWEST = 3;

	public static int neighborTo(int i) {
		return (5 - i);
	}

	private Neighbor[] neighbors = new Neighbor[6];
	private Integer id;

	/* Constructors */
	public Cellule(Integer n) {
		this.id = n;
	}

	public Cellule(Integer n, Cellule[] cells) {
		this.id = n;
		for (int i = 0; i < 6; i++)
			if (cells[i] != null)
				this.setNeighbor(i, cells[i]);
	}

	/* Get methods */
	public Integer getId() {
		return this.id;
	}

	public Boolean hasNeighbor(int i) {
		return (this.neighbors[i].getCell() != null);
	}

	public Neighbor getNeighbor(int i) {
		return this.neighbors[i];
	}

	public Integer getNeighborId(int i) {
		if (this.hasNeighbor(i))
			return this.neighbors[i].getCell().getId();
		else
			return -1;
	}

	public void setNeighbor(int orientation, Cellule nbr) {
		this.neighbors[orientation] = new Neighbor(nbr);
	}

	/**
	 * Break wall with a neighbor and the neighbor's wall too
	 * @param i : Direction of the neighbor
	 */
	public void breakWallWith(int i) {
		if (this.hasNeighbor(i)) {
			this.neighbors[i].breakWall();
			this.neighbors[i].getCell().neighbors[neighborTo(i)].breakWall();
		} else if (Laby.debug) {
			System.err.println("No such neighbor");
		}
	}

	/* Print method */
	public void printNeighborhood() {
		System.out.println("     / \\ / \\");
		System.out.println("    |"
				+ _Aux.numInThree(this.getNeighborId(Cellule.NORTHEAST)) + "|"
				+ _Aux.numInThree(this.getNeighborId(Cellule.NORTHWEST)) + "|");
		System.out.println("   / \\ / \\ / \\");
		System.out.println("  |"
				+ _Aux.numInThree(this.getNeighborId(Cellule.WEST)) + "|"
				+ _Aux.numInThree(this.getId()) + "|"
				+ _Aux.numInThree(this.getNeighborId(Cellule.EAST)) + "|");
		System.out.println("   \\ / \\ / \\ /");
		System.out.println("    |"
				+ _Aux.numInThree(this.getNeighborId(Cellule.SOUTHEAST)) + "|"
				+ _Aux.numInThree(this.getNeighborId(Cellule.SOUTHWEST)) + "|");
		System.out.println("     \\ / \\ /");
	}
}
