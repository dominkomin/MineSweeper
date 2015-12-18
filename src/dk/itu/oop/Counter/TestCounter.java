package dk.itu.oop.Counter;


public class TestCounter
{

	public static void main(String[] args)
	{
		Object barier = new Object();
		CounterStorage storage = new CounterStorage();
		CouunterGenerator generator = new CouunterGenerator(storage, barier);
		CounterView view = new CounterView(storage, barier);
	}

}
