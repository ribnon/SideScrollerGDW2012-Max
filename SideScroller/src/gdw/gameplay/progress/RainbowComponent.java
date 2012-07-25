package gdw.gameplay.progress;

import gdw.entityCore.*;

public class RainbowComponent extends Component {

	private int checkPointNumber;
	private boolean active = false;
	
	public final static int COMPONENT_TYPE = 18;
	
	protected RainbowComponent(ComponentTemplate template) {
		super(template);
		checkPointNumber = template.getIntegerParam("CheckPointNumber");
		GameplayProgressManager.getInstance().addRainbow(this);
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
