package dk.itu.oop.Counter;


public class CouunterGenerator
{
	public CouunterGenerator(CounterStorage storage, Object barier)
	{
		Thread thread = new Thread(new Runnable()
		{

			@Override
			public void run()
			{
				synchronized (barier)
				{
					for (int i = 0; i < 100_000; i++)
					{
						try
						{
							barier.wait();
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
						storage.setValue(i);
						System.out.println(String
								.format("CounterGenerator: Storing value %d into CounterStorage",
										i));
						barier.notifyAll();
					}
				}
			}
		});
		thread.start();
	}
}
