package dk.itu.oop.Counter;

public class CounterView
{
	Thread thread;

	public CounterView(CounterStorage storage, Object barier)
	{
		thread = new Thread(new Runnable()
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
							barier.wait(100);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						};

						System.out.println(String
								.format("CounterGenerator: Reading value %d from CounterStorage",
										storage.getValue()));
						barier.notifyAll();
					}
				}
			}
		});
		thread.start();
	}

}
