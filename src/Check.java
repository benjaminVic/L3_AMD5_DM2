public class Check {
	public static void main(String[] args) {
		//Laby myLaby = new Laby(30, 10);//two dimensional labyrinth
		//myLaby.makeLaby();
		Grid myLaby = Laby.makeLabyA(30, 10);//two dimensional labyrinth
		System.out.println("Check passed");
	}
}
