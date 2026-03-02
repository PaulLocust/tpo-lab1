package lab.pavel.avl;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AvlTreeTest {

    @Test
    void insert_shouldProduceLlRotationTrace() {
        AvlTree tree = new AvlTree();

        tree.insert(30);
        tree.insert(20);
        tree.insert(10);

        List<TracePoint> expected = List.of(
                TracePoint.INSERT,
                TracePoint.INSERT,
                TracePoint.GO_LEFT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.INSERT,
                TracePoint.GO_LEFT,
                TracePoint.GO_LEFT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.LEFT_LEFT_CASE,
                TracePoint.ROTATE_RIGHT
        );

        assertEquals(expected, tree.getTrace());
        assertEquals(20, tree.rootKey());
    }

    @Test
    void insert_shouldProduceRrRotationTrace() {
        AvlTree tree = new AvlTree();

        tree.insert(10);
        tree.insert(20);
        tree.insert(30);

        List<TracePoint> expected = List.of(
                TracePoint.INSERT,
                TracePoint.INSERT,
                TracePoint.GO_RIGHT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.INSERT,
                TracePoint.GO_RIGHT,
                TracePoint.GO_RIGHT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.RIGHT_RIGHT_CASE,
                TracePoint.ROTATE_LEFT
        );

        assertEquals(expected, tree.getTrace());
        assertEquals(20, tree.rootKey());
    }

    @Test
    void insert_shouldProduceLrRotationTrace() {
        AvlTree tree = new AvlTree();

        tree.insert(30);
        tree.insert(10);
        tree.insert(20);

        List<TracePoint> expected = List.of(
                TracePoint.INSERT,
                TracePoint.INSERT,
                TracePoint.GO_LEFT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.INSERT,
                TracePoint.GO_LEFT,
                TracePoint.GO_RIGHT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.LEFT_RIGHT_CASE,
                TracePoint.ROTATE_LEFT,
                TracePoint.ROTATE_RIGHT
        );

        assertEquals(expected, tree.getTrace());
        assertEquals(20, tree.rootKey());
    }

    @Test
    void insert_shouldProduceRlRotationTrace() {
        AvlTree tree = new AvlTree();

        tree.insert(10);
        tree.insert(30);
        tree.insert(20);

        List<TracePoint> expected = List.of(
                TracePoint.INSERT,
                TracePoint.INSERT,
                TracePoint.GO_RIGHT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.INSERT,
                TracePoint.GO_RIGHT,
                TracePoint.GO_LEFT,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.UPDATE_HEIGHT,
                TracePoint.REBALANCE,
                TracePoint.RIGHT_LEFT_CASE,
                TracePoint.ROTATE_RIGHT,
                TracePoint.ROTATE_LEFT
        );

        assertEquals(expected, tree.getTrace());
        assertEquals(20, tree.rootKey());
    }

    @Test
    void insert_shouldIgnoreDuplicate() {
        AvlTree tree = new AvlTree();
        tree.insert(10);
        tree.insert(10);
        assertEquals(List.of(TracePoint.INSERT, TracePoint.INSERT, TracePoint.DUPLICATE_IGNORED), tree.getTrace());
    }

    @Test
    void contains_shouldRecordLeftRightHitAndMiss() {
        AvlTree tree = new AvlTree();
        tree.insert(20);
        tree.insert(10);
        tree.insert(30);
        tree.clearTrace();

        assertTrue(tree.contains(10));
        assertTrue(tree.contains(30));
        assertFalse(tree.contains(99));

        assertEquals(
                List.of(
                        TracePoint.GO_LEFT,
                        TracePoint.SEARCH_HIT,
                        TracePoint.GO_RIGHT,
                        TracePoint.SEARCH_HIT,
                        TracePoint.GO_RIGHT,
                        TracePoint.GO_RIGHT,
                        TracePoint.SEARCH_MISS
                ),
                tree.getTrace()
        );
    }

    @Test
    void rootKey_shouldThrowWhenEmpty() {
        AvlTree tree = new AvlTree();
        assertThrows(IllegalStateException.class, tree::rootKey);
    }
}

