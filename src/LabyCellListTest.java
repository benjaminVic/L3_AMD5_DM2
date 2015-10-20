import static org.junit.Assert.*;

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
			lcl.unionFind(0, i);
		}
		if(!lcl.isALaby()){
			fail("Should be complete!");
		}
	}

}
