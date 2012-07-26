package gdw.genericBehavior;

import Physics.SimulationComponent;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityReference;

public class FollowComponent extends Component
{

	public static final int COMPONENT_TYPE = 7;
	
	private EntityReference targetEntityID;
	
	protected FollowComponent(ComponentTemplate template)
	{
		super(template);
		FollowComponentTemplate tmp = (FollowComponentTemplate) template;
		targetEntityID = tmp.getTargetEntityID();
		
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
	
	public void tick(float deltaTime)
	{
		if(targetEntityID == null)
		{
			return;
		}
		
		SimulationComponent sim = (SimulationComponent) this.getOwner().getComponent(SimulationComponent.COMPONENT_TYPE);
		
		if(sim != null && sim instanceof SimulationComponent)
		{
			Entity start = this.getOwner();
			Entity target = EntityManager.getInstance().getEntity(targetEntityID.getID());
			sim.addForce((target.getPosX() - start.getPosX())*deltaTime, (target.getPosY() - start.getPosY())*deltaTime);
		}
	}

	public EntityReference getTargetEntityID()
	{
		return targetEntityID;
	}

	public void setTargetEntityID(EntityReference targetEntityID)
	{
		this.targetEntityID = targetEntityID;
	}

}
