package btree;

import java.util.*;

/**
 * @author priyamvora
 * @created 24/02/2021
 */
public class BinaryTree {
    public static void main(String[] args) {

    }

    public static void inorderTraversal(TNode root) {
        if (root == null) {
            return;
        }
        inorderTraversal(root.left);
        System.out.println(root.data);
        inorderTraversal(root.right);
    }

    public static void preorderTraversal(TNode root) {
        if (root == null) return;
        System.out.println(root.data);
        preorderTraversal(root.left);
        preorderTraversal(root.right);
    }

    public static void postorderTraversal(TNode root) {
        if (root == null) return;
        postorderTraversal(root.left);
        postorderTraversal(root.right);
        System.out.println(root.data);
    }

    public static void levelOrderTraversal(TNode root) {
        if (root == null) {
            return;
        }
        Queue<TNode> queue = new LinkedList();
        queue.add(root);
        while (!queue.isEmpty()) {
            TNode top = queue.poll();
            System.out.println(top.data);
            if (top.left != null)
                queue.add(top.left);
            if (top.right != null)
                queue.add(top.right);
        }
    }

    public static void spiralLevelOrderTraversal(TNode root) {
        if (root == null)
            return;
        Queue<TNode> queue = new LinkedList<>();
        queue.add(root);
        Stack<TNode> s = new Stack();
        int lev = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                TNode top = queue.poll();
                if (lev % 2 != 0) {
                    s.push(top);
                } else {
                    System.out.println(top.data);
                }
                if (top.left != null) {
                    queue.add(top.left);
                }
                if (top.right != null) {
                    queue.add(top.right);
                }
            }
            while (!s.isEmpty()) {
                System.out.println(s.pop().data);
            }
            lev++;
        }
    }

    private static HashMap<Integer, List<TNode>> diagonalMap;

    public static void diagonalTraversal(TNode root) {
        diagonalMap = new HashMap<>();
        diagonalTraversalUtil(root, 0);
        for (int diagLev : diagonalMap.keySet()) {
            System.out.println(diagonalMap.get(diagLev));
        }
    }

    private static void diagonalTraversalUtil(TNode root, int diagLev) {
        if (root == null) {
            return;
        }
        List<TNode> currentList;
        if (diagonalMap.containsKey(diagLev)) {
            currentList = diagonalMap.get(diagLev);
        } else {
            currentList = new ArrayList<>();

        }
        currentList.add(root);
        diagonalMap.put(diagLev, currentList);
        diagonalTraversalUtil(root.left, diagLev + 1);
        diagonalTraversalUtil(root.right, diagLev);
    }


    public static void boundaryTraversal(TNode root) {

    }

    private static void printLeftSide(TNode root) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            System.out.println(root.data);
            printLeftSide(root.left);
        } else if (root.right != null) {
            System.out.println(root.data);
            printLeftSide(root.right);
        }
    }

    private static void printLeaves(TNode root) {
        if (root == null) return;

        printLeaves(root.left);
        if (root.left == null && root.right == null) {
            System.out.println(root.data);
        }
        printLeaves(root.right);
    }

    private static void printRightSide(TNode root) {
        if (root == null) {
            return;
        }
        if (root.right != null) {
            printLeftSide(root.right);
            System.out.println(root.data);
        } else if (root.left != null) {
            printLeftSide(root.left);
            System.out.println(root.data);
        }
    }

    private static TNode head, tail;

    private static void convertBinaryTreeToDoublyLinkedList(TNode root) {
        if (root == null) {
            return;
        }
        convertBinaryTreeToDoublyLinkedList(root.left);
        if (head == null) {
            head = root;
        } else {
            tail.right = root;
            root.left = tail;
        }
        tail = root;
        convertBinaryTreeToDoublyLinkedList(root.right);
    }


    public static void convertBinaryTreeToChildrenSumPropertyTree(TNode root) {
        if (root == null) return;

        if (root.left == null && root.right == null) return;

        convertBinaryTreeToChildrenSumPropertyTree(root.left);
        convertBinaryTreeToChildrenSumPropertyTree(root.right);
        int leftData = 0, rightData = 0;
        if (root.left != null)
            leftData = root.left.data;
        if (root.right != null)
            rightData = root.right.data;

        int diff = leftData + rightData - root.data;
        if (diff > 0) {
            root.data += diff;
        }
        if (diff < 0) {
            increment(root, -diff);
        }
    }

    private static void increment(TNode root, int diff) {
        if (root.left != null) {
            root.left.data += diff;
            increment(root.left, diff);
        } else if (root.right != null) {
            root.right.data += diff;
            increment(root.right, diff);
        }
    }

    public static void convertToMirrorTree(TNode root) {
        if (root == null)
            return;
        convertToMirrorTree(root.left);
        convertToMirrorTree(root.right);
        TNode temp = root.left;
        root.left = root.right;
        root.right = temp;
    }

    // O(N) complexity
    public static void topView(TNode root) {
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(root, 0));
        int leftMost = 0, rightMost = 0;
        Stack<Integer> left = new Stack<>();
        List<Integer> right = new ArrayList<>();
        while (!queue.isEmpty()) {
            Pair top = queue.poll();
            int hd = top.second;
            if (hd < leftMost) {
                left.push(top.first.data);
                leftMost = hd;
            } else if (hd > rightMost) {
                right.add(top.first.data);
                rightMost = hd;
            }
            if (top.first.left != null) {
                queue.add(new Pair(top.first.left, hd - 1));
            }
            if (top.first.right != null) {
                queue.add(new Pair(top.first.right, hd + 1));
            }
        }
        while (!left.isEmpty()) {
            System.out.println(left.pop());
        }
        System.out.println(root.data);
        for (int val : right) {
            System.out.println(val);
        }


    }

    //https://www.geeksforgeeks.org/minimum-time-to-burn-a-tree-starting-from-a-leaf-node/
    // https://www.geeksforgeeks.org/burn-the-binary-tree-starting-from-the-target-node/
    private int ans = 0;

    public void minTimeToBurnBTreeFromLeafNode(int leafNode, TNode root, Data data) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            if (root.data == leafNode) {
                data.contains = true;
                data.timeTaken = 0;
            }
            return;
        }

        Data leftData = new Data();
        minTimeToBurnBTreeFromLeafNode(leafNode, root.left, leftData);

        Data rightData = new Data();
        minTimeToBurnBTreeFromLeafNode(leafNode, root.right, rightData);

        data.timeTaken = leftData.contains ? leftData.timeTaken + 1 : -1;
        if (data.timeTaken == -1) {
            data.timeTaken = rightData.contains ? rightData.timeTaken + 1 : -1;
        }
        data.contains = leftData.contains || rightData.contains;

        // Calculate the maximum depth of left subtree
        data.leftDepth = (root.left == null) ? 0 : (1 + Math.max(leftData.leftDepth, leftData.rightDepth));

        // Calculate the maximum depth of right subtree
        data.rightDepth = (root.right == null) ? 0 : (1 + Math.max(rightData.leftDepth, rightData.rightDepth));

        if (data.contains) {
            if (leftData.contains) {
                ans = Math.max(ans, leftData.timeTaken + data.rightDepth);
            }
            if (rightData.contains) {
                ans = Math.max(ans, rightData.timeTaken + data.leftDepth);
            }
        }

    }

    public void burnSequenceFromTargetNode(TNode root, Integer num) {


    }

    private int search(TNode root, int num, Map<Integer, Set<Integer>> levelOrderMap) {
        if (root == null) {
            return -1;
        }
        if (root.data == num) {
            storeInLevelOrderMap(root.left, 1, levelOrderMap);
            storeInLevelOrderMap(root.right, 1, levelOrderMap);
            return 1;
        } else {
            int searchAns = search(root.left, num, levelOrderMap);
            if (searchAns > 0) {
                storeRootAtK(root, searchAns, levelOrderMap);
                storeInLevelOrderMap(root.right, searchAns + 1, levelOrderMap);
                return searchAns + 1;
            }
            searchAns = search(root.right, num, levelOrderMap);
            if (searchAns > 0) {
                storeRootAtK(root, searchAns, levelOrderMap);
                storeInLevelOrderMap(root.left, searchAns + 1, levelOrderMap);
                return searchAns + 1;
            }
            return -1;
        }
    }

    private void storeInLevelOrderMap(TNode root, int k, Map<Integer, Set<Integer>> levelOrderMap) {
        if (root != null) {
            storeRootAtK(root, k, levelOrderMap);
            storeInLevelOrderMap(root.left, k + 1, levelOrderMap);
            storeInLevelOrderMap(root.right, k + 1, levelOrderMap);
        }
    }

    private void storeRootAtK(TNode root, int k, Map<Integer, Set<Integer>> levelOrderMap) {
        if (levelOrderMap.containsKey(k)) {
            levelOrderMap.get(k).add(root.data);
        } else {
            Set<Integer> set = new HashSet<>();
            set.add(root.data);
            levelOrderMap.put(k, set);
        }
    }

    public TNode getRighmostNodeInCompleteBinaryTree(TNode root) {
        int h = getLeftHeight(root);
        if (h == 1) {
            return root;
        }
        if ((h - 1) == getLeftHeight(root.right)) {
            return getRighmostNodeInCompleteBinaryTree(root.right);
        } else {
            return getRighmostNodeInCompleteBinaryTree(root.left);
        }
    }

    public int getLeftHeight(TNode root) {
        int hei = 0;
        while (root != null) {
            hei++;
            root = root.left;
        }
        return hei;
    }

}

class Data {
    int leftDepth, rightDepth, timeTaken;
    boolean contains;
}

class Pair {
    TNode first;
    int second;

    public Pair(TNode first, int second) {
        this.first = first;
        this.second = second;
    }
}
