package coinbase;

import java.util.TreeMap;

/**
 * @author priyamvora
 * @created 10/05/2021
 */
class TrieNode {
    TreeMap<String, TrieNode> children;
    boolean isFile;
    String fileContent;

    public TrieNode() {
        children = new TreeMap<>();
        isFile = false;
        fileContent = "";
    }

    @Override
    public String toString() {
        return "TrieNode{" +
                "isFile=" + isFile +
                ", fileContent='" + fileContent + '\'' +
                '}';
    }
}

public class Coinbase {
    private static TrieNode root;

    public static void main(String[] args) {
        root = new TrieNode();
        mkdir("/a");
        ls("/");
        mkdir("/a/b/c");
        mkdir("/a/b/e");
        mkdir("/a/b/f");
        ls("/a/b");
        addContentToFile("/a/b/c/d", "hello");
        readContentFromFile("/a/b/c/d");
    }

    public static void mkdir(String path) {
        String[] split = path.split("/");
        TrieNode current = root;
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() > 0) {
                TrieNode node = current.children.getOrDefault(split[i], null);
                if (node == null) {
                    node = new TrieNode();
                    current.children.put(split[i], node);
                }
                current = node;
            }
        }
    }

    public static void addContentToFile(String path, String content) {
        String[] split = path.split("/");
        TrieNode current = root;
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() > 0) {
                TrieNode node = current.children.getOrDefault(split[i], null);
                if (node == null) {
                    node = new TrieNode();
                    current.children.put(split[i], node);
                } else if (node.isFile && i < split.length - 1) {
                    return;
                }
                current = node;
            }
        }
        System.out.println("here");
        current.isFile = true;
        current.fileContent += content;
    }

    public static void ls(String path) {
        String[] split = path.split("/");
        TrieNode current = root;
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() > 0) {
                TrieNode node = current.children.getOrDefault(split[i], null);
                if (node == null) {
                    return;
                }
                current = node;
            }
        }
        System.out.println(current.children);
    }

    public static String readContentFromFile(String path) {
        String[] split = path.split("/");
        TrieNode current = root;
        for (int i = 0; i < split.length; i++) {
            if (split[i].length() > 0) {
                TrieNode node = current.children.getOrDefault(split[i], null);
                if (node == null) {
                    return null;
                }
                current = node;
            }
        }
        System.out.println("current: " + current);
        if (current.isFile) {
            return current.fileContent;
        }
        return null;
    }


}
