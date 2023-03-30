package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static at.jku.swtesting.utils.TestUtils.FISRT_ITEM;
import static at.jku.swtesting.utils.TestUtils.SECOND_ITEM;
import static at.jku.swtesting.utils.TestUtils.THIRD_ITEM;
import static at.jku.swtesting.utils.TestUtils.repeat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RingBufferIteratorTest {

    @Test
    public void iterator_test() {
        //GIVEN
        var buffer = new RingBuffer<String>((short)4);
        var iterator = buffer.iterator();

        //WHEN
        buffer.enqueue(FISRT_ITEM);
        buffer.enqueue(SECOND_ITEM);
        buffer.enqueue(THIRD_ITEM);

        //THEN
        var next = iterator.next();
        var next2 = iterator.next();
        var next3 = iterator.next();
        assertEquals(FISRT_ITEM, next);
        assertEquals(SECOND_ITEM, next2);
        assertEquals(THIRD_ITEM, next3);
    }

    @Test
    public void noMoreElements_iterator_test() {
        //GIVEN
        var buffer = new RingBuffer<String>((short)4);
        var iterator = buffer.iterator();

        //WHEN
        buffer.enqueue(FISRT_ITEM);
        var next = iterator.next();

        //THEN
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    public void hasNext_iterator_test() {
        //GIVEN
        var buffer = new RingBuffer<String>((short)4);
        var iterator = buffer.iterator();

        //WHEN
        buffer.enqueue(FISRT_ITEM);

        //THEN
        assertTrue(iterator.hasNext());
    }

    @Test
    public void hasNext_noNext_iterator_test() {
        //GIVEN
        var buffer = new RingBuffer<String>((short)4);
        var iterator = buffer.iterator();

        //THEN
        assertFalse(iterator.hasNext());
    }


    @Test
    public void capacity_exceeded_iterator_test() {
        //GIVEN
        var buffer = new RingBuffer<String>((short)2);
        var iterator = buffer.iterator();

        //WHEN
        repeat(5, n -> {
            buffer.enqueue(FISRT_ITEM);
            buffer.enqueue(SECOND_ITEM);
            buffer.enqueue(THIRD_ITEM);
        });

        iterator.next();
        iterator.next();

        //THEN
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    //not sure if this need testing but i added it for coverage's sake
    public void remove_iterator_test() {
        var buffer = new RingBuffer<String>((short)2);
        var iterator = buffer.iterator();

        assertThrows(UnsupportedOperationException.class, iterator::remove);
    }

}
