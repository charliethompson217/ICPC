import java.util.Arrays;

public class QuestionD {

	public static void main(String[] args) {
		// constants
		char[] chrs = { 'A', 'T', 'C', 'G' };

		// all this is given per test case
		String str = "TATATGCTCT";
		int[][] ranges = { { 1, 10 }, { 6, 10 }, { 6, 6 } };
		int n = str.length();
		int m = ranges.length;

		// cumulative frequencies buckets
		int Al = 0, Ar = 0, Tl = 0, Tr = 0, Cl = 0, Cr = 0, Gl = 0, Gr = 0;
		// the magic line
		int[][] arr = new int[8][n + 2];

		// traverse the array forward and backward
		// while filling the buckets and plotting cumulative frequencies
		for (int i = 0, j = n - 1; i < n && j >= 0; i++, j--) {
			int left = i + 2;
			int right = j;
			char chLeft, chRight;
			chLeft = str.charAt(i);
			chRight = str.charAt(j);
			switch (chLeft) {
			case ('A'):
				Al++;
				break;
			case ('T'):
				Tl++;
				break;
			case ('C'):
				Cl++;
				break;
			case ('G'):
				Gl++;
				break;
			}
			switch (chRight) {
			case ('A'):
				Ar++;
				break;
			case ('T'):
				Tr++;
				break;
			case ('C'):
				Cr++;
				break;
			case ('G'):
				Gr++;
				break;
			}
			arr[0][left] = Al;
			arr[2][left] = Tl;
			arr[4][left] = Cl;
			arr[6][left] = Gl;
			arr[1][right] = Ar;
			arr[3][right] = Tr;
			arr[5][right] = Cr;
			arr[7][right] = Gr;
		}
		// this is the only portion of the program that runs for each pair of
		// points--->>( m )
		for (int i = 0; i < m; i++) {
			System.out.println(ranges[i][0] + ", " + ranges[i][1]);
			int[] sums = sumsAcrossRange(arr, ranges[i][0], ranges[i][1], Ar, Tr, Cr, Gr);
			/*
			 * Here is where you have to sort the final frequencies and print to screen, so
			 * now we have something that looks like [2, 5, 2, 1] 
			 					  !  !  !  ! 
								  A  T  C  G
			 */
			String answer = "";
			int[] accendingSortedSums = new int[4];
			for (int j = 0; j < 4; j++) {
				accendingSortedSums[j] = sums[j];
			}
			Arrays.sort(accendingSortedSums);
			int[] sortedSums = new int[4];
			int b = 3;
			for (int j = 0; j < 4; j++) {
				sortedSums[j] = accendingSortedSums[b];
				b--;
			}
			for (int j = 0; j < 4; j++) {
				for (int k = 0; k < 4; k++) {
					if (sums[k] == sortedSums[j]) {
						answer = answer + chrs[k];
						sums[k] = -1;
					}
				}
			}
			System.out.println(answer);
		}
	}

	// when combined with this lol
	public static int[] sumsAcrossRange(int[][] arr, int leftSum, int rightSum, int A, int T, int C, int G) {
		/*
		 * to find the sum of each letter across a range, we subtract the the frequency
		 * on the left and on the right from the total frequency
		 */
		int A_sInRange = A - arr[0][leftSum] - arr[1][rightSum];
		int T_sInRange = T - arr[2][leftSum] - arr[3][rightSum];
		int C_sInRange = C - arr[4][leftSum] - arr[5][rightSum];
		int G_sInRange = G - arr[6][leftSum] - arr[7][rightSum];
		int[] frequencyAcroossRange = new int[] { A_sInRange, T_sInRange, C_sInRange, G_sInRange };
		return frequencyAcroossRange;
	}
}
