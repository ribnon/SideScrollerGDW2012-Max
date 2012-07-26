package gdw.network;

import java.io.IOException;

import Physics.SimulationComponentManager;

import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplateManager;

public class ServerCoreLoop extends Thread
{
	
	private final long SLEEPCONST = 250L;
	
	private SideScrollerServer ref;
	
	public ServerCoreLoop(SideScrollerServer ref)
	{
		this.ref = ref;
	}
	
	@Override
	public void run()
	{
		long oldVal = System.currentTimeMillis();

		while(!this.isInterrupted())
		{
			if(this.ref.getCurState()== SideScrollerServer.ServerGameStates.WAITING)
			{
				try
				{
					sleep(SLEEPCONST);
				} catch (InterruptedException e)
				{
					return;
				}
			}else if(this.ref.getCurState() == SideScrollerServer.ServerGameStates.START)
			{
				//init
				EntityTemplateManager entTempMan = EntityTemplateManager.getInstance();
				
				try
				{
					entTempMan.loadEntityTemplates("../EntityTemplates.txt");
					entTempMan.getEntityTemplate("slidingPlatform").createEntity(200f, 200f, 0f);
				} catch (IOException e)
				{
					
				}
				NetSubSystem.initalise(1, true, ref);
				this.ref.startComplete();
			}
			long curVal = System.currentTimeMillis();
			float delta = curVal -  oldVal;
			
			//updates laufen lassen
			NetSubSystem.getInstance().pollMessages();
		
			SimulationComponentManager.getInstance().simulate(delta);
			EntityManager.getInstance().tick(delta);
		
			NetSubSystem.getInstance().checkDeadReck();
			NetSubSystem.getInstance().sendBufferedMessages();
				
				
		}
	}
	
}
