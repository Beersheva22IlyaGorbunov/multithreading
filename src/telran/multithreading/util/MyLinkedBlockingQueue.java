package telran.multithreading.util;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class MyLinkedBlockingQueue<E> implements BlockingQueue<E> {
	LinkedList<E> list = new LinkedList<>();
	int limit;
	
	ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	ReadLock readLock = lock.readLock();
	WriteLock writeLock = lock.writeLock();
	
	Condition waitingConsumer = ((Lock) lock).newCondition();
	Condition waitingProducer = ((Lock) lock).newCondition();

	public MyLinkedBlockingQueue(int limit) {
		this.limit = limit;
	}
	
	public MyLinkedBlockingQueue() {
		this(Integer.MAX_VALUE);
	} 

	@Override
	public E remove() {
		writeLock.lock();
		try {
			return list.removeFirst();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E poll() {
		writeLock.lock();
		try {
			return list.poll();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E element() {
		readLock.lock();
		try {
			return list.getFirst();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public E peek() {
		readLock.lock();
		try {
			return list.peekFirst();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		readLock.lock();
		try {
			return list.toArray();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public <T> T[] toArray(T[] a) {
		readLock.lock();
		try {
			return list.toArray(a);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		readLock.lock();
		try {
			return list.containsAll(c);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		writeLock.lock();
		try {
			c.forEach(elem -> {
				try {
					put(elem);
				} catch (InterruptedException e) {}
			});
			return true;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		writeLock.lock();
		try {
			return list.removeAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		writeLock.lock();
		try {
			return list.retainAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void clear() {
		writeLock.lock();
		try {
			list.clear();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean add(E e) {
		writeLock.lock();
		try {
			if (size() < limit) {
				return list.add(e);
			} else {
				throw new IllegalStateException();
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e) {
		writeLock.lock();
		try {
			if (size() < limit) {
				return list.offer(e);
			} else {
				return false;
			}
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public void put(E e) throws InterruptedException {
		writeLock.lock();
		try {
			while(!list.offer(e)) {
				waitingProducer.await();
			}
			waitingConsumer.signal();
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			boolean res = false;
			while(list.size() == limit) {
				res = waitingProducer.await(timeout, unit);
			}
			if (res) {
				list.add(e);
				waitingConsumer.signal();
			}
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E take() throws InterruptedException {
		writeLock.lock();
		try {
			while(list.isEmpty()) {
				waitingConsumer.await();
			}
			E res = list.pop();
			waitingProducer.signal();
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		writeLock.lock();
		try {
			boolean isTimeout = false;
			while(list.isEmpty()) {
				isTimeout = waitingConsumer.await(timeout, unit);
			}
			E res = null;
			if (!isTimeout) {
				res = list.poll();
				waitingProducer.signal();
			}
			return res;
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public int remainingCapacity() {
		readLock.lock();
		try {
			return limit - size();
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public boolean remove(Object o) {
		writeLock.lock();
		try {
			return list.remove(o);
		} finally {
			writeLock.unlock();
		}
	}

	@Override
	public boolean contains(Object o) {
		readLock.lock();
		try {
			return list.contains(o);
		} finally {
			readLock.unlock();
		}
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new UnsupportedOperationException();
	}


}
