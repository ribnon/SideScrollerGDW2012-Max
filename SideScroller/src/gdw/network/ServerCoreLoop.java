package gdw.network;

public class ServerCoreLoop extends Thread
{
	
	private SideScrollerServer ref;
	
	public ServerCoreLoop(SideScrollerServer ref)
	{
		this.ref = ref;
	}
	
	@Override
	public void run()
	{
		while(!this.isInterrupted())
		{
			
		}
	}
	
}
