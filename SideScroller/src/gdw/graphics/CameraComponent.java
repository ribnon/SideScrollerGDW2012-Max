package gdw.graphics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponent extends Component {

	private int playerNumber;
	private int viewPortX;
	private int viewPortY;
	
	public int getViewPortX()
	{
		return viewPortX;
	}
	
	public int getViewPortY()
	{
		return viewPortY;
	}
	
	public final static int COMPONENT_TYPE = 6;
	
	public CameraComponent(ComponentTemplate template) {
		super(template);
		CameraComponentTemplate t = (CameraComponentTemplate) template;
		playerNumber = t.getPlayerNumber();
		viewPortX = t.getViewPortX();
		viewPortY = t.getViewPortY();
		SpriteManager.getInstance().addCamera(this);
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}
	
	public void setPlayerNumber(int pNum)
	{
		playerNumber = pNum;
	}
	
	protected void destroy()
	{
		SpriteManager.getInstance().removeCamera(this);
	}

}
