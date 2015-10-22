import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;


public class LabyCellListTest {

	@Test
	public void test() {
		Grid g = new Grid(10, 10);
		
		LabyCellList lcl = new LabyCellList(g);
		if(lcl.isALaby()){
			fail("Laby not yet bult !");
		}
		for (int i = 0 ; i<100 ; ++i){
			if (lcl.areNotLinked(0, i)){
			lcl.unionFind(0, i);
			}
		}
		if(!lcl.isALaby()){
			fail("Should be complete!");
		}
		g.cell[0][0].breakWallWith(g.cell[0][0].WEST);
		g.showGrid();
		/*List<Integer> possible = new ArrayList<Integer>();
		possible.add(1);
		possible.add(5);
		possible.add(12);
		possible.add(14);
		possible.add(6);
		Random r = new Random(possible.size()-1);
		
		for (int i =0 ; i<10 ; ++i){
			System.out.println("num");
			int randomValue = r.nextInt(possible.size());
			System.out.println(possible.get(randomValue));
		}*/
	}

}
