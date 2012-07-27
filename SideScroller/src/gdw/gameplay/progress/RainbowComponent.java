package gdw.gameplay.progress;

import collisionDetection.CollisionDetectionMessage;
import gdw.entityCore.*;

public class RainbowComponent extends Component {

	private int checkPointNumber;
	private boolean active;
	
	//Oliver: Wird ignoriert, die RainbowComponent macht nen Instant Fullheal
	private float healthIncrement;
	
	public final static int COMPONENT_TYPE = 18;
	
	protected RainbowComponent(ComponentTemplate template) {
		super(template);
		active = ((RainbowComponentTemplate) template).isActive();
		checkPointNumber = ((RainbowComponentTemplate) template).getCheckPointNumber();
	}
	
	public float getHealthIncrement()
	{
		return healthIncrement;
	}

	public int getCheckPointNumber()
	{
		return checkPointNumber;
	}
	
	public void setCheckPointNumber(int num)
	{
		checkPointNumber = num;
	}
	
	public boolean getActive()
	{
		return active;
	}
	
	protected void destroy()
	{
		GameplayProgressManager.getInstance().removeRainbow(this);
	}
	
	public void onMessage(Message msg)
	{
		//TODO: react to messages
		if (msg instanceof CollisionDetectionMessage)
		{
//			Entity
			//gegner gluecklichmachen
		}
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

	
}
