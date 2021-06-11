package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * @author priyamvora
 * @created 24/03/2021
 */
public class DP {
    public int goldMine(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int[][] dp = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[j][i] = mat[j][i];
                int temp = 0;
                // right down
                if (i - 1 >= 0 && j - 1 >= 0) {
                    temp = Math.max(temp, dp[j - 1][i - 1]);
                }
                // right up
                if (i - 1 >= 0 && j + 1 < n) {
                    temp = Math.max(temp, dp[j + 1][i - 1]);
                }
                // right
                if (i - 1 >= 0) {
                    temp = Math.max(temp, dp[j][i - 1]);
                }
                dp[j][i] = Math.max(dp[j][i], dp[j][i] + temp);
            }
        }
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, dp[i][m - 1]);
        }
        return ans;
    }

    public int coinChange(int[] values, int N) {
        int n = values.length;
        int[] dp = new int[N + 1];
        dp[0] = 0;
        for (int i = 0; i < n; i++) {
            for (int j = values[i]; j <= N; j++) {
                dp[j] += (dp[j - values[i]]);
            }
        }
        return dp[N];
    }

    public boolean subSetSum(int[] val, int sum) {
        int n = val.length;
        boolean[][] dp = new boolean[n + 1][sum + 1];
        for (int i = 0; i <= n; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                if (j < val[i - 1]) {
                    dp[i][j] |= dp[i - 1][j];
                } else {
                    dp[i][j] |= dp[i - 1][j - val[i - 1]];
                }
            }
        }
        return dp[n][sum];
    }

    public void display(ArrayList<Integer> v) {
        System.out.println(v);
    }

    public void printAllSubsetsWithGivenSum(int[] val, int i, int sum, ArrayList<Integer> p, boolean[][] dp) {
        if (i == 1 && sum != 0 && dp[1][sum]) {
            p.add(val[i]);
            display(p);
            p.clear();
            return;
        }
        if (i == 1 && sum == 0) {
            display(p);
            p.clear();
            return;
        }
        if (dp[i - 1][sum]) {
            ArrayList<Integer> b = new ArrayList<>();
            b.addAll(p);
            printAllSubsetsWithGivenSum(val, i - 1, sum, b, dp);
        }
        if (sum >= val[i] && dp[i - 1][sum - val[i]]) {
            p.add(val[i]);
            printAllSubsetsWithGivenSum(val, i - 1, sum - val[i], p, dp);
        }
    }

    public boolean subSetSumSpaceOptimized(int[] val, int sum) {
        int n = val.length;
        boolean[][] dp = new boolean[2][sum + 1];
        for (int i = 0; i < 2; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= sum; j++) {
                dp[i % 2][j] = dp[(i + 1) % 2][j];
                if (j >= val[i - 1]) {
                    dp[i % 2][j] |= dp[(i + 1) % 2][j - val[i - 1]];
                }
            }
        }
        return dp[n % 2][sum];
    }

    public boolean subsetSumDivisibleByM(int[] val, int m) {
        int n = val.length;
        if (n > m) {
            return true; //Pigeon hole principle -> https://stackoverflow.com/questions/59617741/check-whether-a-subset-with-sum-divisible-by-m-exists
        }
        boolean[] dp = new boolean[m];
        for (int i = 0; i < n; i++) {
            if (dp[0]) {
                return true;
            }
            boolean[] temp = new boolean[m];
            for (int j = 0; j < m; j++) {
                if (dp[j] && dp[(j + val[i]) % m] == false) {
                    temp[(j + val[i]) % m] = true;
                }
            }
            for (int j = 0; j < m; j++) {
                if (temp[j]) {
                    dp[j] = true;
                }
            }
            dp[val[i] % m] = true;
        }
        return dp[0];
    }

    public int largestDivisiblePairsSubset(int[] arr, int n) {
        Arrays.sort(arr);
        int[] dp = new int[n];
        int ans = 1;
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                if (arr[i] % arr[j] == 0) {
                    max = Math.max(max, dp[j]);
                }
            }
            dp[i] = max + 1;
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public int choiceOfArea(int A, int B, int x_a, int x_b, int y_a, int y_b, int z_a, int z_b) {
        return Math.max(Math.max(choiceOfAreaUtil(A + x_a, B + x_b, x_a, x_b, y_a, y_b, z_a, z_b, 1),
                choiceOfAreaUtil(A + y_a, B + y_b, x_a, x_b, y_a, y_b, z_a, z_b, 2)),
                choiceOfAreaUtil(A + z_a, B + z_b, x_a, x_b, y_a, y_b, z_a, z_b, 3));
    }

    public int choiceOfAreaUtil(int A, int B, int x_a, int x_b, int y_a, int y_b, int z_a, int z_b, int last) {
        if (A <= 0 || B <= 0) {
            return 0;
        }
        return Math.max(Math.max(
                last != 1 ? choiceOfAreaUtil(A + x_a, B + x_b, x_a, x_b, y_a, y_b, z_a, z_b, 1) : Integer.MIN_VALUE / 100,
                last != 2 ? choiceOfAreaUtil(A + y_a, B + y_b, x_a, x_b, y_a, y_b, z_a, z_b, 2) : Integer.MIN_VALUE / 100),
                last != 3 ? choiceOfAreaUtil(A + z_a, B + z_b, x_a, x_b, y_a, y_b, z_a, z_b, 3) : Integer.MIN_VALUE / 100) + 1;
    }

    public int cuttingRod(int[] val, int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = val[1];
        for (int i = 2; i <= n; i++) {
            dp[i] = val[i - 1];
            for (int j = i - 1; j > 0; j--) {
                dp[i] = Math.max(dp[i], val[j - 1] + dp[i - j]);
            }
        }
        return dp[n];
    }

    public int LCS(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = (i == 0 || j == 0) ? 1 : dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        printLCS(a, b, a.length(), b.length(), dp, new Stack<>());
        return dp[a.length()][b.length()];
    }

    private void printLCS(String s1, String s2, int i, int j, int[][] dp, Stack<Character> st) {
        if (i == 0 || j == 0) {
            while (!st.isEmpty()) {
                System.out.print(st.pop());
            }
            return;
        }
        if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
            st.push(s1.charAt(i - 1));
            printLCS(s1, s2, i - 1, j - 1, dp, st);
        } else {
            if (dp[i - 1][j] > dp[i][j - 1]) {
                printLCS(s1, s2, i - 1, j, dp, st);
            } else {
                printLCS(s1, s2, i, j - 1, dp, st);
            }
        }
    }

    private int longestCommonSubstr(String S1, String S2, int n, int m) {

        int[][] dp = new int[n + 1][m + 1];

        int ans = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (S1.charAt(i - 1) == S2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    ans = Math.max(ans, dp[i][j]);
                }

            }
        }
        return ans;
    }

    private int shortestCommonSuperSequence(String s1, String s2) {
        printShortestCommonSuperSequence(s1, s2);
        return s1.length() + s2.length() - LCS(s1, s2);
    }

    private void printShortestCommonSuperSequence(String a, String b) {
        int[][] dp = new int[a.length() + 1][b.length() + 1];
        for (int i = 1; i <= a.length(); i++) {
            for (int j = 1; j <= b.length(); j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = (i == 0 || j == 0) ? 1 : dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        int i = a.length(), j = b.length();
        Stack<Character> st = new Stack<>();
        while (i > 0 && j > 0) {
            if (a.charAt(i - 1) == b.charAt(j - 1)) {
                st.push(a.charAt(i - 1));
                i--;
                j--;
            } else {
                if (dp[i - 1][j] > dp[i][j - 1]) {
                    st.push(a.charAt(i - 1));
                    i--;
                } else {
                    st.push(b.charAt(j - 1));
                    j--;
                }
            }
        }

        while (i > 0) {
            st.push(a.charAt(i - 1));
            i--;
        }

        // If X reaches its end, put remaining characters
        // of Y in the result string
        while (j > 0) {
            st.push(b.charAt(j - 1));
            j--;
        }

        while (!st.isEmpty()) {
            System.out.print(st.pop());
        }
    }

    private int minNumberOfInsertionsAndDeletionsToConvertStringAToB(String s1, String s2) {
        int lcs = LCS(s1, s2);
        int deletions = s1.length() - lcs;
        int insertions = s2.length() - lcs;
        return insertions + deletions;
    }

    private int longestPalindromicSubsequence(String s) {
        String reverse = new StringBuilder(s).reverse().toString();
        return LCS(s, reverse);
    }

    private int minNumberOfDeletionsToMakeStringPalindrome(String s) {
        return s.length() - longestPalindromicSubsequence(s);
    }

    private int longestRepeatingSubsequence(String s) {
        int n = s.length();
        int[][] dp = new int[n + 1][n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && s.charAt(i) == s.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[n][n];
    }

    public int[][] dp = new int[1001][1001];

    public int mcmRec(int[] arr, int i, int j) {
        if (i >= j) {
            return 0;
        }
        if (dp[i][j] != -1) {
            return dp[i][j];
        }

        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            min = Math.min(min, mcmRec(arr, i, k) + mcmRec(arr, k + 1, j) + arr[i - 1] * arr[k] * arr[j]);
        }
        dp[i][j] = min;
        return dp[i][j];
    }

    public int mcmIte(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        // for L = 1 answer would be 0 as you can't multiply matrix with itself
        for (int L = 2; L < n; L++) {
            for (int i = 1; i < n - L + 1; i++) {
                int j = i + L - 1;
                if (j >= n) {
                    continue;
                }
                dp[i][j] = Integer.MAX_VALUE;
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j]);
                }
            }
        }
        return dp[1][n - 1];
    }

    public int palindromicPartitioning(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        boolean[][] palindrome = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            palindrome[i][i] = true;
        }

        for (int L = 2; L <= n; L++) {
            for (int i = 0; i < n - L + 1; i++) {
                int j = i + L - 1;
                if (L == 2) {
                    palindrome[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    palindrome[i][j] = s.charAt(i) == s.charAt(j) && palindrome[i + 1][j - 1];
                }
                if (palindrome[i][j]) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = Integer.MAX_VALUE;
                    for (int k = i; k < j; k++) {
                        dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + 1);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    public int palindromePartitionN2(String s) {
        int n = s.length();
        boolean[][] palindrome = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            palindrome[i][i] = true;
        }

        for (int L = 2; L <= n; L++) {
            for (int i = 0; i < n - L + 1; i++) {
                int j = i + L - 1;
                if (L == 2) {
                    palindrome[i][j] = s.charAt(i) == s.charAt(j);
                } else {
                    palindrome[i][j] = s.charAt(i) == s.charAt(j) && palindrome[i + 1][j - 1];
                }
            }
        }

        int[] cost = new int[n];
        for (int i = 0; i < n; i++) {
            if (palindrome[0][i]) {
                continue;
            }
            cost[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (palindrome[j + 1][i] && 1 + cost[j] < cost[i]) {
                    cost[i] = 1 + cost[j];
                }
            }
        }
        return cost[n - 1];
    }

//    public int palindromePartitioning(String s, int i, int j) {
//        if (i >= j) {
//            return 0;
//        }
//        if (dp[i][j] != -1) {
//            return dp[i][j];
//        }
//        if (isPalindrome(i, j)) {
//            dp[i][j] = 0;
//            return 0;
//        }
//        int ans = Integer.MAX_VALUE;
//        for (int k = i; k < j; k++) {
//            ans = Math.min(palindromePartitioning(s, i, k) + palindromePartitioning(s, k + 1, j) + 1, ans);
//        }
//        dp[i][j] = ans;
//    }

    public int booleanParenthisation(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
            dp[i][i] = 0;
        }
        for (int i = 0; i < n - 2; i += 2) {
            boolean one = s.charAt(i) == 'T';
            boolean two = s.charAt(i + 2) == 'T';
            switch (s.charAt(i + 1)) {
                case '&':
                    dp[i][i + 2] = one & two ? 1 : 0;
                    break;
                case '|':
                    dp[i][i + 2] = one || two ? 1 : 0;
                    break;
                case '^':
                    dp[i][i + 2] = one ^ two ? 1 : 0;
                    break;
            }
        }

        for (int L = 3; L < n; L++) {
            for (int i = 0; i < n - L + 1; i += 2) {
                int j = i + (2 * L) - 1;
                int min = Integer.MIN_VALUE;
                for (int k = i + 1; k < j; k += 2) {
                    boolean one = dp[i][k - 1] != Integer.MAX_VALUE;
                    boolean two = dp[k + 1][j] != Integer.MAX_VALUE;
                    switch (s.charAt(k)) {
                        case '&':
                            min = Math.max(min, one & two ? dp[i][k - 1] * dp[k + 1][j] : Integer.MIN_VALUE);
                            break;
                        case '|':
                            min = Math.max(min, one || two ? dp[i][k - 1] * dp[k + 1][j] : Integer.MIN_VALUE);
                            break;
                        case '^':
                            min = Math.max(min, one ^ two ? dp[i][k - 1] * dp[k + 1][j] : Integer.MIN_VALUE);
                            break;
                    }
                }
                if (min != Integer.MIN_VALUE) {
                    dp[i][j] = Math.max(min, dp[i][j]);
                }
            }
        }
        return dp[0][n - 1];
    }


    public int findInRotatedSortedArray(int[] arr, int findElement) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (arr[mid] == findElement) {
                return mid;
            }
            if (arr[low] <= arr[mid]) {
                if (findElement >= arr[low] && findElement <= arr[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else {
                if (findElement >= arr[mid] && findElement <= arr[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    private static void mcm(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 1000);
            dp[i][i] = 0;
        }
        for (int L = 2; L < n; L++) {
            for (int i = 1; i < n - L + 1; i++) {
                int j = i + L - 1;
                if (j >= n) {
                    continue;
                }
                for (int k = i; k < j; k++) {
                    dp[i][j] = Math.min(dp[i][j], dp[i][k] + dp[k + 1][j] + arr[i - 1] * arr[k] * arr[j]);
                }
            }
        }

    }


    private int lisNLogN(int arr[]){
        int n = arr.length;
        int dp[] = new int[n];
        dp[0] = arr[0];
        int len = 1;
        for(int i=1;i<n;i++){
            // Replace first element itself
            if(arr[i] < dp[0]){
                dp[0] = arr[i];
                // Check if we can extend
            }else if(arr[i] > dp[len-1]){
                dp[len++] = arr[i];
            }else{
                // find next greatest number for arr[i] and replace it.
                int low = 0, high = len-1, ans=-1;
                while(low <= high){
                    int mid = (low + high)/2;
                    if(dp[mid] >= arr[i]){
                        ans = mid;
                        high = mid-1;
                    }else{
                        low = mid+1;
                    }
                }
                if(ans != -1){
                    dp[ans] = arr[i];
                }
            }
        }
        System.out.println(Arrays.toString(dp));
        System.out.println(len);
        return len;
    }

    public static void main(String[] args) {
        DP dp = new DP();
        dp.lisNLogN(new int[]{2,6, 3, 4, 1, 2, 9, 5, 8});
    }

}
