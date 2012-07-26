package gdw.gameplay.progress;

import gdw.entityCore.*;

public class RainbowComponent extends Component {

	private int checkPointNumber;
	private boolean active;
	
	private float healthIncrement;
	
	public final static int COMPONENT_TYPE = 18;
	
	protected RainbowComponent(ComponentTemplate template) {
		super(template);
		RainbowComponentTemplate t = (RainbowComponentTemplate) template;
		active = t.isActive();
		checkPointNumber = t.getCheckPointNumber();
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
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

	
}
