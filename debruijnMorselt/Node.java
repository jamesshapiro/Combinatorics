package debruijnMorselt;

import java.util.ArrayList;
import java.util.List;

/**
 * Node creates an ordered tree from a Dyck Path encoding and performs
 * Debruijn-Morselt transformations on ordered trees.
 * 
 * @author jamesshapiro
 *
 */

public class Node {
	private List<Node> children = new ArrayList<Node>();
	private int height = 0;
	private int index;

	public Node() {
		height = 0;
	}

	/**
	 * Given a Dyck Path encoding, constructs the corresponding ordered tree. A
	 * "1" corresponds to creating a new edge to a new child node, and a "0"
	 * corresponds to following an existing edge from the current node to its
	 * parent.
	 * 
	 * For example "1100" encodes a 3-node tree where the root has one child and
	 * one grandchild, but "1010" encodes a 3-node tree where the root has two
	 * children.
	 * 
	 * @param dyckPath
	 * @param argIndex
	 */
	public Node(String dyckPath, int argIndex) {
		index = argIndex;
		while (index < dyckPath.length() && dyckPath.charAt(index) == '1') {
			Node child = new Node(dyckPath, index + 1);
			children.add(child);
			index = child.getIndex() + 1;
			height = Math.max(height, child.getHeight() + 1);
		}
	}

	/**
	 * Transform-constructor. Given a Dyck Path encoding, constructs the ordered
	 * tree corresponding to its Debruijn-Morselt transformation described here:
	 * http://www.fq.math.ca/Scanned/17-3/shapiro.pdf (see page 254 for an
	 * example).
	 * 
	 * For example "1100" encodes a 3-node tree where the root has two children,
	 * but with "1010", the root has one child and one grandchild.
	 * 
	 * @param argIndex
	 * @param dyckPath
	 */
	public Node(int argIndex, String dyckPath) {
		index = argIndex;
		int branchHeight = 0;
		while (index < dyckPath.length() && dyckPath.charAt(index) == '1') {
			children.add(new Node());
			branchHeight++;
			index++;
		}

		index++;
		branchHeight--;
		while (index < dyckPath.length()) {
			if (branchHeight < 0) {
				return;
			}

			if (dyckPath.charAt(index) == '1') {
				Node child = new Node(index, dyckPath);
				children.set((children.size() - 1) - branchHeight, child);
				index = child.getIndex();
			} else {
				index++;
			}
			branchHeight--;
		}
	}

	/**
	 * The transform method performs the Debruijn-Morselt transformation as
	 * described here: http://www.fq.math.ca/Scanned/17-3/shapiro.pdf (see page
	 * 254 for an example).
	 */
	public Node transform() {
		return new Node(0, this.toDyckPath());
	}

	public int getIndex() {
		return index;
	}

	public int getHeight() {
		return height;
	}

	public int getNumChildren() {
		return children.size();
	}

	public List<Node> getChildren() {
		return children;
	}

	public String toString() {
		String result = "index=" + index + ". height=" + height
				+ ". numChildren=" + children.size();
		return result;
	}

	public String toDyckPath() {
		StringBuilder builder = new StringBuilder();
		for (Node child : children) {
			builder.append("1");
			builder.append(child.toDyckPath());
			builder.append("0");
		}
		return builder.toString();
	}

}
