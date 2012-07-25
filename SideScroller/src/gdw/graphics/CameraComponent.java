package gdw.graphics;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class CameraComponent extends Component {

	private int playerNumber;
	
	public final static int COMPONENT_TYPE = 6;
	protected CameraComponent(ComponentTemplate template) {
		super(template);
		CameraComponentTemplate t = (CameraComponentTemplate) template;
		playerNumber = t.getPlayerNumber();
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

}
