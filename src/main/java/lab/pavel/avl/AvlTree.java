package lab.pavel.avl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AvlTree {
    private Node root;
    private final List<TracePoint> trace = new ArrayList<>();

    public void insert(int key) {
        trace.add(TracePoint.INSERT);
        root = insert(root, key);
    }

    public boolean contains(int key) {
        Node current = root;
        while (current != null) {
            if (key == current.key) {
                trace.add(TracePoint.SEARCH_HIT);
                return true;
            }
            if (key < current.key) {
                trace.add(TracePoint.GO_LEFT);
                current = current.left;
            } else {
                trace.add(TracePoint.GO_RIGHT);
                current = current.right;
            }
        }
        trace.add(TracePoint.SEARCH_MISS);
        return false;
    }

    public List<TracePoint> getTrace() {
        return Collections.unmodifiableList(trace);
    }

    public void clearTrace() {
        trace.clear();
    }

    public int rootKey() {
        if (root == null) {
            throw new IllegalStateException("tree is empty");
        }
        return root.key;
    }

    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }
        if (key < node.key) {
            trace.add(TracePoint.GO_LEFT);
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            trace.add(TracePoint.GO_RIGHT);
            node.right = insert(node.right, key);
        } else {
            trace.add(TracePoint.DUPLICATE_IGNORED);
            return node;
        }

        updateHeight(node);
        trace.add(TracePoint.UPDATE_HEIGHT);
        return rebalance(node, key);
    }

    private Node rebalance(Node node, int insertedKey) {
        trace.add(TracePoint.REBALANCE);
        int balance = balanceFactor(node);

        if (balance > 1) {
            if (insertedKey > node.left.key) {
                trace.add(TracePoint.LEFT_RIGHT_CASE);
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
            trace.add(TracePoint.LEFT_LEFT_CASE);
            return rotateRight(node);
        }
        if (balance < -1) {
            if (insertedKey < node.right.key) {
                trace.add(TracePoint.RIGHT_LEFT_CASE);
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
            trace.add(TracePoint.RIGHT_RIGHT_CASE);
            return rotateLeft(node);
        }
        return node;
    }

    private Node rotateLeft(Node node) {
        trace.add(TracePoint.ROTATE_LEFT);
        Node newRoot = node.right;
        Node movedSubtree = newRoot.left;

        newRoot.left = node;
        node.right = movedSubtree;

        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private Node rotateRight(Node node) {
        trace.add(TracePoint.ROTATE_RIGHT);
        Node newRoot = node.left;
        Node movedSubtree = newRoot.right;

        newRoot.right = node;
        node.left = movedSubtree;

        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    private int balanceFactor(Node node) {
        return height(node.left) - height(node.right);
    }

    private int height(Node node) {
        return node == null ? 0 : node.height;
    }

    private static final class Node {
        private final int key;
        private int height = 1;
        private Node left;
        private Node right;

        private Node(int key) {
            this.key = key;
        }
    }
}

