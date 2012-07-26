package gdw.graphics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponent extends Component {

	private int playerNumber;
	private int viewPortX;
	private int viewPortY;
	
	public final static int COMPONENT_TYPE = 6;
	protected CameraComponent(ComponentTemplate template) {
		super(template);
		CameraComponentTemplate t = (CameraComponentTemplate) template;
		playerNumber = t.getPlayerNumber();
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
