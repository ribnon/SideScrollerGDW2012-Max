package gdw.network;

import java.util.LinkedList;

import Physics.SimulationComponent;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.Message;
import gdw.network.messageType.DeadReckoningNetMessage;

public class NetComponent extends Component
{
	public static final int COMPONENT_TYPE = 8;
	private final Ghost ghost;
	private int sequenceID;

	protected NetComponent(ComponentTemplate template)
	{
		super(template);
		this.ghost = new Ghost();
		sequenceID = 0;
		NetSubSystem.getInstance().addNetComponentToList(this);
	}

	@Override
	public int getComponentTypeID()
	{
		return 8;
	}

	@Override
	protected void destroy()
	{
		super.destroy();
		NetSubSystem.getInstance().removeNetComponentToList(this);
	}
	
	public void simulateGhost(float deltaT)
	{
		this.ghost.simulate(deltaT);
	}
	
	public void deadReckoningMessageReceive(DeadReckoningNetMessage msg)
	{
		//TODO auffüllen
	}
	
	@Override
	public void onMessage(Message msg)
	{
		//TODO warten auf implementierung
		//if(msg instanceof EntityConstructedMessage)
		{
			Entity owner = this.getOwner();
			this.ghost.initialise(owner.getPosX(), owner.getPosY());
		}	
	}
	
	//TODO überprüfung der id sonst dropen

	public void sendNetworkMessage(Message msg)
	{
		// TODO SubSystem senden
	}

	
	public void addDeadReckoningNetMessageToList(LinkedList<DeadReckoningNetMessage> list)
	{
		Entity ent = this.getOwner();
		//prüfe ob thing und ghost zu sehr abweicht
		if(this.ghost.checkAgainstThing(ent))
		{
			//korrigier ghost und sende nachricht
			SimulationComponent simComp = (SimulationComponent) ent.getComponent(SimulationComponent.COMPONENT_TYPE);
			this.ghost.correct(ent.getPosX(), ent.getPosY(), simComp.getVelocityX(), simComp.getVelocityY(), 0.0f);
			DeadReckoningNetMessage msg = new DeadReckoningNetMessage(ent.getID(), ++this.sequenceID, 
					ent.getPosX(), ent.getPosY(), simComp.getVelocityX(), simComp.getVelocityY());
			list.add(msg);
		}
	}
}
