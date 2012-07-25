package gdw;

import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;
import gdw.network.NetSubSystem;

import java.util.ArrayList;
import java.util.Random;

import Physics.SimulationComponent;

public class TestServerCoreLoop extends Thread
{
	private final TestServer ref;
	private ArrayList<Entity> flyingThings;
	private final static long SLEEPCONST = 50L;
	
	
	//hässliches konstanten
	private final static float[] offset = {250f,250f};
	
	private final static float[] cage = {200f,200f};
	
	private boolean gameStarted = false;
	
	private boolean triggerStartup = true;
	
	private final String fusselString = "bla";
	
	private final String clientEntity = "hirsch";
		
	
	public TestServerCoreLoop (TestServer ref)
	{
		this.ref = ref;
		this.flyingThings = new ArrayList<Entity>();
		NetSubSystem.initalise(1, true, null, ref);
		
		
		
		this.start();
	}
	
	public void startGame()
	{
		this.gameStarted = true;
	}
	
	public void killMe()
	{
		this.interrupt();
	}
	
	@Override
	public void run()
	{
		long oldVal = System.currentTimeMillis();
		while(!this.isInterrupted())
		{
			try
			{
				sleep(SLEEPCONST);
			} catch (InterruptedException e)
			{
				return;
			}
			if(!this.gameStarted)
				continue;
			
			if(triggerStartup)
			{
				this.triggerStartup = false;
				//net entity feueren
				EntityTemplate temp = EntityTemplateManager.getInstance().getEntityTemplate(clientEntity);
				temp.createEntity(0f, 0f, 0f);
				//bissle zeug spawn
				Random rand = new Random();
				temp = EntityTemplateManager.getInstance().getEntityTemplate(fusselString);
				
				for(int i=0;i<3;++i)
				{
					Entity ent = temp.createEntity(offset[0],offset[1],0.0f);
					((SimulationComponent)ent.getComponent(SimulationComponent.COMPONENT_TYPE)).addForce(rand.nextFloat(), rand.nextFloat());
					this.flyingThings.add(ent);
				}
			}
			
			long curVal = System.currentTimeMillis();
			float delta = curVal -  oldVal;
			//sim
			for(Entity ent : this.flyingThings)
			{
				SimulationComponent simComp = (SimulationComponent) ent.getComponent(SimulationComponent.COMPONENT_TYPE);
				//X richtung
				if(ent.getPosX() > (offset[0]+cage[0]) ||(ent.getPosX() < (offset[0]-cage[0]))||
						(ent.getPosY() > (offset[1]+offset[1]))||(ent.getPosY() > (offset[1]-offset[1])))
				{
					//richtung bekommen
					float richtX = offset[0] - ent.getPosX();
					float richtY = offset[1] - ent.getPosY();
					
					float lengthCor = (float) Math.sqrt(richtX * richtX + richtY * richtY);
					richtX /= lengthCor;
					richtY /= lengthCor;
					
					float speed = (float)Math.random();
					simComp.setAccelerationX(speed * richtX);
					simComp.setAccelerationY(speed * richtY);
				}
			}
			NetSubSystem subRef = NetSubSystem.getInstance();
			subRef.simulateGhosts(delta);
			subRef.sendBufferedMessages();			
		}
	}
}


