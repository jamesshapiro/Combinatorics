package debruijnMorselt;

import java.util.Set;
import java.util.TreeSet;

public class SurveyOrbits {

	private static Set<String> allDyckPaths;
	private static Set<String> uniqueDyckPaths;
	private static int totalOrbits = 0;
	private static int maxOrbitSize = 0;
	private static int numNodes;

	/**
	 * A Dyck path is a bit-string where the tally of 1's always exceeds the
	 * tally of 0's, but the number of ones equals the number of zeroes. There
	 * is a one-to-one correspondence between Dyck Paths and rooted trees. We
	 * will use Dyck Paths to encode rooted trees.
	 *
	 * The number of Dyck Paths of a certain length gives us the Catalan
	 * numbers.
	 * 
	 * @param bitString
	 * @return
	 */
	private static boolean isValidDyckPath(String bitString) {
		int total = 0;
		for (int i = 0; i < bitString.length(); i++) {
			if (bitString.charAt(i) == '1') {
				total++;
			} else {
				total--;
			}
			if (total < 0) {
				return false;
			}
		}
		if (total > 0) {
			return false;
		}
		return true;
	}

	/**
	 * TODO: replace this brute force method with Knuth's algorithm for
	 * generating all ordered trees.
	 */

	private static void populateDyckPathSets() {
		allDyckPaths = new TreeSet<String>();
		uniqueDyckPaths = new TreeSet<String>();
		int x = 1;
		x <<= (numNodes * 2) - 1;
		String bitString;
		for (int i = x; i < (x << 1); i++) {
			bitString = Integer.toBinaryString(i);
			if (isValidDyckPath(bitString)) {
				allDyckPaths.add(bitString);
				uniqueDyckPaths.add(bitString);
			}
		}
	}

	/**
	 * Records relevant statistics about the Debruijn-Morselt cycles described here: 
	 * http://www.fq.math.ca/Scanned/17-3/shapiro.pdf
	 */
	private static void surveyOrbits() {
		totalOrbits = 0;
		maxOrbitSize = 0;
		for (String dyckPath : allDyckPaths) {
			if (uniqueDyckPaths.contains(dyckPath)) {
				totalOrbits++;
				int currOrbitSize = 0;
				Node originalTree = new Node(dyckPath, 0);
				Node treeAfterTransform = new Node(originalTree.toDyckPath(), 0);
				do {
					treeAfterTransform = treeAfterTransform.transform();
					uniqueDyckPaths.remove(treeAfterTransform.toDyckPath());
					currOrbitSize++;
				} while (!originalTree.toDyckPath().equals(
						treeAfterTransform.toDyckPath()));
				maxOrbitSize = Math.max(maxOrbitSize, currOrbitSize);
			}
		}
	}

	private static void printResults() {
		System.out.println("Results for " + numNodes + " nodes:\n");
		System.out.println("   Distinct rooted trees: " + allDyckPaths.size() + "\n");
		System.out.println("   Number of orbits     : " + totalOrbits + "\n");
		System.out.println("   Size of largest orbit: " + maxOrbitSize + "\n\n");
	}

	public static void main(String[] args) {
		for (int i = 1; i <= 12; i++) {
			numNodes = i;
			populateDyckPathSets();
			surveyOrbits();
			printResults();
		}
	}
}
