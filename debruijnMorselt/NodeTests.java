package debruijnMorselt;
import org.junit.Assert;
import org.junit.Test;


public class NodeTests {
	
	@Test
	public void testEdgeTree() {
		Node node = new Node("", 0);
		Assert.assertEquals(0, node.getHeight());
		Assert.assertEquals(0, node.getNumChildren());
		Assert.assertEquals(0, node.getIndex());
		Assert.assertEquals("", node.toDyckPath());
	}

	@Test
	public void testTrivialTree() {
		Node node = new Node("10", 0);
		Assert.assertEquals(1, node.getHeight());
		Assert.assertEquals(1, node.getNumChildren());
		Assert.assertEquals(2, node.getIndex());
		Assert.assertEquals("10", node.toDyckPath());
	}
	
	@Test
	public void testDepth2Tree() {
		Node node = new Node("1100", 0);
		Assert.assertEquals(2, node.getHeight());
		Assert.assertEquals(1, node.getNumChildren());
		Assert.assertEquals(4, node.getIndex());
		Assert.assertEquals("1100", node.toDyckPath());
	}
	
	@Test
	public void testDepth3Tree() {
		Node node = new Node("111000", 0);
		Assert.assertEquals(3, node.getHeight());
		Assert.assertEquals(1, node.getNumChildren());
		Assert.assertEquals(6, node.getIndex());
		Assert.assertEquals("111000", node.toDyckPath());
	}
	
	@Test
	public void test2BranchTree() {
		Node node = new Node("1010", 0);
		Assert.assertEquals(1, node.getHeight());
		Assert.assertEquals(2, node.getNumChildren());
		Assert.assertEquals(4, node.getIndex());
		Assert.assertEquals("1010", node.toDyckPath());
	}
	
	@Test
	public void test2BranchMoreComplexTree() {
		Node node = new Node("110010", 0);
		Assert.assertEquals(2, node.getHeight());
		Assert.assertEquals(2, node.getNumChildren());
		Assert.assertEquals(6, node.getIndex());
		Node firstChild = node.getChildren().get(0);
		Node secondChild = node.getChildren().get(1);
		Assert.assertEquals(1, firstChild.getHeight());
		Assert.assertEquals(1, firstChild.getNumChildren());
		Assert.assertEquals(0, secondChild.getHeight());
		Assert.assertEquals(0, secondChild.getNumChildren());
		Assert.assertEquals("110010", node.toDyckPath());
	}
	
	@Test
	public void test2BranchOtherComplexTree() {
		Node node = new Node("101100", 0);
		Assert.assertEquals(2, node.getHeight());
		Assert.assertEquals(2, node.getNumChildren());
		Assert.assertEquals(6, node.getIndex());
		Node firstChild = node.getChildren().get(0);
		Node secondChild = node.getChildren().get(1);
		Assert.assertEquals(0, firstChild.getHeight());
		Assert.assertEquals(0, firstChild.getNumChildren());
		Assert.assertEquals(1, secondChild.getHeight());
		Assert.assertEquals(1, secondChild.getNumChildren());
		Assert.assertEquals("101100", node.toDyckPath());
	}
	
	@Test
	public void testTransform() {
		Node node = new Node("1100101100110010", 0);
		node = node.transform();
		Assert.assertEquals("1011101101100000", node.toDyckPath());
		node = node.transform();
		Assert.assertEquals("1111010010010100", node.toDyckPath());
		node = node.transform();
		Assert.assertEquals("1100110011100010", node.toDyckPath());
		node = node.transform();
		Assert.assertEquals("1011011010110000", node.toDyckPath());
		node = node.transform();
		Assert.assertEquals("1111101000100100", node.toDyckPath());
		node = node.transform();
		Assert.assertEquals("1100101100110010", node.toDyckPath());
	}

}
