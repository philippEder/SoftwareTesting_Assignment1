package at.jku.swtesting;

import org.junit.jupiter.api.Test;

import static at.jku.swtesting.utils.TestUtils.FISRT_ITEM;
import static at.jku.swtesting.utils.TestUtils.SECOND_ITEM;
import static at.jku.swtesting.utils.TestUtils.THIRD_ITEM;
import static at.jku.swtesting.utils.TestUtils.repeat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RingBufferTest {

	@Test
	public void capacity_test() {
		var buffer = new RingBuffer<Integer>((short)5);

		assertEquals(5, buffer.capacity());
	}

	@Test
	public void negative_capacity_test() {
		assertThrows(IllegalArgumentException.class, () -> new RingBuffer<Integer>((short)-1));
	}

	@Test
	//we discovered the bug with this test.
	//RingBuffer constructor accepts short now instead of integer.
	//This avoids out of memory exception (at least on our machine)
	public void large_capacity_test() {
		assertDoesNotThrow(() -> new RingBuffer<Integer>(Short.MAX_VALUE));
	}

	@Test
	public void empty_size_test() {
		var buffer = new RingBuffer<Integer>((short)5);

		assertEquals(0, buffer.size());
	}

	@Test
	public void size_test() {
		var buffer = new RingBuffer<Integer>((short)3);

		repeat(2, buffer::enqueue);

		assertEquals(2, buffer.size());
	}

	@Test
	public void capacity_exceeded_size_test() {
		var buffer = new RingBuffer<Integer>((short)3);

		repeat(5, buffer::enqueue);

		assertEquals(3, buffer.size());
	}

	@Test
	public void isEmpty_test() {
		var buffer = new RingBuffer<Integer>((short)3);

		assertTrue(buffer.isEmpty());
	}

	@Test
	public void isEmpty_notEmpty_test() {
		var buffer = new RingBuffer<Integer>((short)3);

		buffer.enqueue(1);

		assertFalse(buffer.isEmpty());
	}

	@Test
	public void isFull_test() {
		var buffer = new RingBuffer<Integer>((short)3);

		repeat(3, buffer::enqueue);

		assertTrue(buffer.isFull());
	}

	@Test
	public void isFull_notFull_test() {
		var buffer = new RingBuffer<Integer>((short)3);

		assertFalse(buffer.isFull());
	}

	@Test
	public void peek_test() {
		var buffer = new RingBuffer<String>((short)3);

		buffer.enqueue(FISRT_ITEM);
		buffer.enqueue(SECOND_ITEM);
		buffer.enqueue(THIRD_ITEM);

		assertEquals(FISRT_ITEM, buffer.peek());
	}

	@Test
	public void capacity_exceeded_peek_test() {
		var buffer = new RingBuffer<String>((short)2);

		buffer.enqueue(FISRT_ITEM);
		buffer.enqueue(SECOND_ITEM);
		buffer.enqueue(THIRD_ITEM);

		assertEquals(SECOND_ITEM, buffer.peek());
	}

	@Test
	public void empty_peek_test() {
		var buffer = new RingBuffer<String>((short)3);

		assertThrows(RuntimeException.class, buffer::peek);
	}

	@Test
	public void dequeue_test() {
		var buffer = new RingBuffer<String>((short)3);

		buffer.enqueue(FISRT_ITEM);
		buffer.enqueue(SECOND_ITEM);

		assertEquals(FISRT_ITEM, buffer.dequeue());
	}

	@Test
	public void empty_dequeue_test() {
		var buffer = new RingBuffer<String>((short)3);

		assertThrows(RuntimeException.class, buffer::dequeue);
	}

	@Test
	public void capacity_exceeded_dequeue_test() {
		var buffer = new RingBuffer<String>((short)2);

		buffer.enqueue(FISRT_ITEM);
		buffer.enqueue(SECOND_ITEM);
		buffer.enqueue(THIRD_ITEM);

		assertEquals(SECOND_ITEM, buffer.dequeue());
	}

	@Test
	public void capacity_exceeded_multiple_times_dequeue_test() {
		var buffer = new RingBuffer<String>((short)2);

		repeat(3, n -> {
			buffer.enqueue(FISRT_ITEM);
			buffer.enqueue(SECOND_ITEM);
			buffer.enqueue(THIRD_ITEM);
		});

		assertEquals(SECOND_ITEM, buffer.dequeue());
	}

	@Test
	public void dequeue_size_test() {
		var buffer = new RingBuffer<Integer>((short)10);

		repeat(5, buffer::enqueue);
		buffer.dequeue();

		assertEquals(4, buffer.size());
	}

	@Test
	public void capacity_exceeded_dequeue_size_test() {
		var buffer = new RingBuffer<Integer>((short)4);

		repeat(5, buffer::enqueue);
		buffer.dequeue();

		assertEquals(3, buffer.size());
	}

}
