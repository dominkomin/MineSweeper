package dk.itu.oop.List;

public class MyNonEmptyList<T> extends MyGenericListAbstract<T>
{
	public MyNonEmptyList(T value)
	{
		this.head = value;
	}

	@Override
	public T getLastElement()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
