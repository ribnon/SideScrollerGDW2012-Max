package gdw.network;

import java.io.IOException;

import javax.swing.ProgressMonitor;


import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Level;
import gdw.gameplay.progress.GameplayProgressManager;
import gdw.network.server.GDWServerLogger;
import gdw.physics.SimulationComponentManager;

public class ServerCoreLoop extends Thread
{
	
	private final long SLEEPCONST = 300L;
	
	private SideScrollerServer ref;
	
	public ServerCoreLoop(SideScrollerServer ref)
	{
		this.ref = ref;
		this.start();
	}
	
	@Override
	public void run()
	{
		long oldVal = System.currentTimeMillis();

		while(!this.isInterrupted())
		{
			NetSubSystem.getInstance().pollMessages();
			if(this.ref.getCurState()== SideScrollerServer.ServerGameStates.WAITING)
			{
				
			}else if(this.ref.getCurState() == SideScrollerServer.ServerGameStates.START)
			{
				//init
			
				Level.getInstance().start();
				//EntityTemplateManager entTempMan = EntityTemplateManager.getInstance();
				SimulationComponentManager.getInstance().setGravity(98.1f);
				
				
					GDWServerLogger.logMSG("init system");
					//entTempMan.loadEntityTemplates("general.templates");
					Entity spawn =  GameplayProgressManager.getInstance().getCurrentSpawnComponent().getOwner();
					GDWServerLogger.logMSG("Spieler sollte an X: "+spawn.getPosX()+" Y: "+spawn.getPosY());
					EntityTemplateManager.getInstance().getEntityTemplate("Player1").createEntity(spawn.getPosX(), spawn.getPosY(), 0f);
					//entTempMan.getEntityTemplate("LevelGoal").createEntity(200f, 200f, 0f);
				 
				this.ref.startComplete();
			}else
			{
			try
			{
				sleep(SLEEPCONST);
			} catch (InterruptedException e)
			{
				return;
			}	
			
			long curVal = System.currentTimeMillis();
			float delta = curVal -  oldVal;
			
			NetSubSystem netSub = NetSubSystem.getInstance();
			
			//updates laufen lassen
			netSub.pollMessages();
		
			
			SimulationComponentManager.getInstance().simulate(delta);
			netSub.simulateGhosts(delta);
			EntityManager.getInstance().tick(delta);
		
			netSub.checkDeadReck();
			netSub.sendBufferedMessages();
			
			EntityManager.getInstance().cleanUpEntities();
			
			}
				
				
		}
	}
	
}
