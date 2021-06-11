package linkedlist;

/**
 * @author priyamvora
 * @created 11/04/2021
 */

class Node {
    int data;
    Node next;

    public Node(int data) {
        this.data = data;
        this.next = null;
    }

    public Node() {
    }
}

public class LinkedList {
    // Tortoise hare method
    // https://math.stackexchange.com/questions/913499/proof-of-floyd-cycle-chasing-tortoise-and-hare
    public boolean detectLoop(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
        return false;
    }

    //https://www.geeksforgeeks.org/find-first-node-of-loop-in-a-linked-list/
    public Node firstNodeInLoop(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (slow != fast) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    public boolean detectLoop2(Node head) {
        Node temp = new Node();
        if (head != null) {
            if (head.next == temp) {
                return true;
            }
            if (head.next == null) {
                // gives first node in loop
                return false;
            }
            Node next = head.next;
            head.next = temp;
            head = next;
        }
        return false;
    }

    public void removeLoop(Node head) {
        Node slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (slow != fast) {
            return;
        }
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        fast = slow;
        while (fast.next != slow) {
            fast = fast.next;
        }
        fast.next = null;
    }

    public Node reverseList(Node head) {
        Node current = head, prev = null, next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        return prev;
    }

    public Node mergeTwoLists(Node node1, Node node2) {
        if (node1 == null && node2 != null) {
            return node2;
        }
        if (node1 != null && node2 == null) {
            return node1;
        }
        Node finalHead = node1;
        Node temp1 = node1;
        Node temp2 = node2;
        Node prevTemp2 = null;
        while (temp1 != null && temp2 != null && temp1.data >= temp2.data) {
            prevTemp2 = temp2;
            temp2 = temp2.next;
        }

        if (prevTemp2 != null) {
            prevTemp2.next = finalHead;
            finalHead = node2;
        }
        Node prevTemp1 = null;
        while (temp1 != null && temp2 != null) {
            if (temp1.data > temp2.data) {
                prevTemp1.next = temp2;
                prevTemp1 = temp2;
                Node next = temp2.next;
                temp2.next = temp1;
                temp2 = next;
            } else {
                prevTemp1 = temp1;
                temp1 = temp1.next;
            }
        }
        if (prevTemp1 != null && temp2 != null) {
            prevTemp1.next = temp2;
        }

        return finalHead;
    }

    public Node reverse(Node node, int k) {
        if (node == null) {
            return null;
        }
        Node curr = node;
        Node prev = null;
        Node next = null;
        for (int i = 0; i < k && curr != null; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        if (curr != null) {
            node.next = reverse(curr, k);
        }
        return prev;
    }

    public Node kAlReverse(Node node, int k) {
        if (node == null) {
            return null;
        }
        Node curr = node;
        Node prev = null;
        Node next = null;
        for (int i = 0; i < k && curr != null; i++) {
            next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        node.next = curr;
        for (int i = 0; i < k - 1 && curr != null; i++) {
            curr = curr.next;
        }
        if (curr != null) {
            curr.next = kAlReverse(curr.next, k);
        }
        return prev;
    }

    public Node sortLLofZeroOneTwo(Node head) {
        if (head == null) {
            return null;
        }
        Node zeroD = new Node(0);
        Node oneD = new Node(0);
        Node twoD = new Node(0);
        Node zero = zeroD;
        Node one = oneD;
        Node two = twoD;
        Node curr = head;
        while (curr != null) {
            if (curr.data == 0) {
                zero.next = curr;
                zero = zero.next;
            } else if (curr.data == 1) {
                one.next = curr;
                one = oneD.next;
            } else if (curr.data == 2) {
                two.next = curr;
                two = two.next;
            }
            curr = curr.next;
        }

        zero.next = oneD.next != null ? oneD.next : twoD.next;
        one.next = twoD.next;
        two.next = null;
        head = zeroD.next;
        return head;
    }
}
