package gdw.network;

import java.util.LinkedList;

import Physics.SimulationComponent;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityConstructedMessage;
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
		
		if((template != null)&&(template instanceof NetComponentTemplate))
		{
			NetComponentTemplate temp = (NetComponentTemplate) template;
			this.ghost = temp.getGhost().clone();
			sequenceID = 0;
			NetSubSystem.getInstance().addNetComponentToList(this);
		}else
		{
			this.ghost = null;
		}
	}

	@Override
	public int getComponentTypeID()
	{
		return NetComponent.COMPONENT_TYPE;
	}

	@Override
	protected void destroy()
	{
		NetSubSystem.getInstance().removeNetComponentToList(this);
	}
	
	public void simulateGhost(float deltaT)
	{
		this.ghost.simulate(deltaT);
	}
	
	public void deadReckoningMessageReceive(DeadReckoningNetMessage msg)
	{
		if(msg.sequenceID < this.sequenceID)
		{
			return;
		}else
		{
			this.sequenceID = msg.sequenceID;
			//update ghost
		
			this.ghost.correct(msg.posX, msg.posY, msg.velocityX, msg.velocityY,msg.roundTipTime);
		}
	}
	
	@Override
	public void onMessage(Message msg)
	{
		if(msg instanceof EntityConstructedMessage)
		{
			Entity owner = this.getOwner();
			this.ghost.initialise(owner.getPosX(), owner.getPosY());
		}	
	}

	public void sendNetworkMessage(Message msg)
	{
		//tunnel
		NetSubSystem.getInstance().sendBusMessage(this.getOwner().getID(),msg);
	}

	
	public void addDeadReckoningNetMessageToList(LinkedList<DeadReckoningNetMessage> list)
	{
		Entity ent = this.getOwner();
		//prüfe ob thing und ghost zu sehr abweicht
		if(this.ghost.checkAgainstThing(ent))
		{
			this.sequenceID++;
			//korrigier ghost und sende nachricht
			SimulationComponent simComp = (SimulationComponent) ent.getComponent(SimulationComponent.COMPONENT_TYPE);
			this.ghost.correct(ent.getPosX(), ent.getPosY(), simComp.getVelocityX(), simComp.getVelocityY(), 0.0f);
			
			DeadReckoningNetMessage msg = new DeadReckoningNetMessage(ent.getID(), ++this.sequenceID, 
				ent.getPosX(), ent.getPosY(), simComp.getVelocityX(), simComp.getVelocityY(),0.0f);
			list.add(msg);
		}
	}
}
