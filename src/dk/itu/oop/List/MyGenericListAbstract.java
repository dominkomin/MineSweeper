package dk.itu.oop.List;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class MyGenericListAbstract<T>
{
	protected T head;
	private MyGenericListAbstract<T> tail;
	private static int size;
	
	public MyGenericListAbstract(T head, MyGenericListAbstract<T> tail)
	{
		this.head = head;
		this.tail = tail;
	}
	
	public MyGenericListAbstract()
	{
		size = 0;
	}

	public T getHead()
	{
		return head;
	}
	
	public MyGenericListAbstract<T> getTail()
	{
		return tail;
	}
	
	public int size()
	{
		return size;
	}
	
	public T getLastElement()
	{
		// Empty list case.
				if (size < 1)
					throw new Exception("No elements in the list.");

				MyGenericListAbstract<T> current = tail;
				for (int i = 1; i < size; i++)
				{
					if (tail == null)
						throw new Ex(
								"Index outside of bounds.");

					current = current.getNext();
				}
				return current.getNumber();
	}
	
	public abstract void add(MyGenericListAbstract<T> element);
	
	public abstract void remove(MyGenericListAbstract<T> element);
	
	public abstract MyGenericListAbstract<T> concat(MyGenericListAbstract<T> list);
}
