public class Neighbor {
	private Cellule cell;
	private Boolean wall;

	/* Constructors */
	public Neighbor(Cellule c) {
		this.cell = c;
		this.wall = true;
	}

	public Neighbor(Cellule c, Boolean b) {
		this.cell = c;
		this.wall = b;
	}

	/* Get methods */
	/**
	 * get a cell
	 * @return : a cell
	 */
	public Cellule getCell() {
		return this.cell;
	}

	/**
	 * If the wall exists or not
	 * @return : wall value
	 */
	public Boolean isWall() {
		return this.wall;
	}

	/* Modification methods */
	/**
	 * Remove a wall
	 */
	public void breakWall() {
		this.wall = false;
	}
}
