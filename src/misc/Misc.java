package misc;

import java.util.*;

/**
 * @author priyamvora
 * @created 01/04/2021
 */
public class Misc {
    public double[] medianInRunningStreamOfIntegers(int[] arr) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        double[] medians = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            // Add to heap
            if (maxHeap.size() == 0 || maxHeap.peek() > arr[i]) {
                maxHeap.add(arr[i]);
            } else {
                minHeap.add(arr[i]);
            }

            // Rebalance
            PriorityQueue<Integer> biggerHeap = maxHeap.size() > minHeap.size() ? maxHeap : minHeap;
            PriorityQueue<Integer> smallerHeap = maxHeap.size() < minHeap.size() ? maxHeap : minHeap;
            if (biggerHeap.size() - smallerHeap.size() > 1) {
                smallerHeap.add(biggerHeap.poll());
            }

            // get median
            biggerHeap = maxHeap.size() > minHeap.size() ? maxHeap : minHeap;
            smallerHeap = maxHeap.size() < minHeap.size() ? maxHeap : minHeap;

            if (biggerHeap.size() == smallerHeap.size()) {
                medians[i] = (biggerHeap.peek() + smallerHeap.peek()) / 2;
            } else {
                medians[i] = biggerHeap.peek();
            }
        }
        return medians;
    }

    public int minSwapsToSortArray(int[] arr) {
        int[] sortedArray = new int[arr.length];
        HashMap<Integer, Integer> hm = new HashMap<>();
        int cnt = 0;
        for (int i = 0; i < arr.length; i++) {
            hm.put(arr[i], i);
            sortedArray[i] = arr[i];
        }
        Arrays.sort(sortedArray);
        for (int i = 0; i < sortedArray.length; i++) {
            // If both not equal swap them
            if (arr[i] != sortedArray[i]) {

                int indexInOriginalArray = hm.get(sortedArray[i]);
                int tempVar = arr[indexInOriginalArray];
                arr[indexInOriginalArray] = arr[i];
                arr[i] = tempVar;
                cnt++;

                hm.put(arr[indexInOriginalArray], indexInOriginalArray);
                hm.put(sortedArray[i], i);
            }
        }
        return cnt;
    }

    // https://www.geeksforgeeks.org/find-number-of-triplets-in-array-such-that-aiajak-and-ijk/
    public int noOfTriplets(int[] arr) {
        int n = arr.length;
        int[] BIT = new int[n + 1];
        int[] leftGreater = new int[n + 1];
        int[] rightSmaller = new int[n + 1];

        convertArray(arr, n);
        for (int i = n - 1; i >= 0; i--) {
            int query = query(BIT, arr[i] - 1, n);
            update(BIT, n, arr[i], 1);
            rightSmaller[i] = query;
        }

        Arrays.fill(BIT, 0);
        for (int i = 0; i < n; i++) {
            int query = i - query(BIT, arr[i], n);
            update(BIT, n, arr[i], 1);
            leftGreater[i] = query;
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += leftGreater[i] * rightSmaller[i];
        }
        return ans;
    }

    public void convertArray(int[] arr, int n) {
        int[] temp = Arrays.copyOf(arr, n);
        Arrays.sort(temp);
        for (int i = 0; i < n; i++) {
            int low = 0, high = n - 1, ans = n;
            while (low <= high) {
                int mid = (low + high) / 2;
                if (arr[mid] >= temp[mid]) {
                    ans = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            arr[i] = ans + 1;
        }
    }

    public int query(int[] BIT, int i, int n) {
        int sum = 0;
        for (; i <= n; i -= (i & -i)) {
            sum += BIT[i];
        }
        return sum;
    }

    public void update(int[] BIT, int n, int i, int val) {
        for (; i <= n; i += (i & -i)) {
            BIT[i] += val;
        }
    }

    public int optimalAccountBalance(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < transactions.length; i++) {
            map.put(transactions[i][0], map.getOrDefault(transactions[i][0], 0) + transactions[i][2]);
            map.put(transactions[i][1], map.getOrDefault(transactions[i][1], 0) - transactions[i][2]);
        }
        List<Integer> person = new ArrayList<>();
        for (Integer id : map.keySet()) {
            if (map.get(id) != 0) {
                person.add(id);
            }
        }
        return dfs(0, person, map);
    }

    public int dfs(int k, List<Integer> person, Map<Integer, Integer> map) {
        if (k == person.size()) {
            return 0;
        }
        if (map.get(person.get(k)) == 0) {
            return dfs(k + 1, person, map);
        }
        int curr = map.get(person.get(k));
        int min = Integer.MAX_VALUE;
        for (int i = k + 1; i < person.size(); i++) {
            if (map.get(person.get(k)) * map.get(person.get(i)) < 0) {
                int next = map.get(person.get(i));
                map.put(person.get(i), next + map.get(person.get(k)));
                min = Math.min(min, 1 + dfs(k + 1, person, map));
                map.put(person.get(i), next);
                if (curr + next == 0) break;
            }
        }
        return min;
    }


    public boolean wordPatternMatch(String pat, String str, int patIndex, int strIndex, Map<Character, String> ctostr, Map<String, Character> strtoc) {
        if (patIndex == pat.length() && strIndex == str.length()) {
            return true;
        }
        if (patIndex >= pat.length() || strIndex >= str.length()) {
            return false;
        }
        char c = pat.charAt(patIndex);
        for (int i = strIndex + 1; i < str.length(); i++) {
            String sub = str.substring(strIndex, i);
            if (!ctostr.containsKey(c) || !strtoc.containsKey(sub)) {
                ctostr.put(c, sub);
                strtoc.put(sub, c);
                if (wordPatternMatch(pat, str, patIndex + 1, i, ctostr, strtoc)) {
                    return true;
                }
                ctostr.remove(c);
                strtoc.remove(sub);
            } else if (ctostr.containsKey(c) && ctostr.get(c).equals(sub)) {
                return wordPatternMatch(pat, str, patIndex + 1, i, ctostr, strtoc);
            }
            return false;
        }
    }
}
