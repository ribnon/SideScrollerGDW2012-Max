package gdw.astroids.components.random;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityConstructedMessage;
import gdw.entityCore.Message;

public class RandomPlacementComponent extends Component {

	
	private static final int COMPONENT_TYPE = 1005;
	
	private int placementPosX;
	private int placementPosY;
	private int boxWidth;
	private int boxHeight;
	private boolean isLocal;
	
	public RandomPlacementComponent(ComponentTemplate template) {
		super(template);
		RandomPlacementComponentTemplate t =(RandomPlacementComponentTemplate)template;
		placementPosX = t.placementPosX;
		placementPosY = t.placementPosY;
		boxWidth = t.boxWidth;
		boxHeight = t.boxHeight;
		isLocal = t.isLocal;
	}

	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}
	
	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		if(msg instanceof EntityConstructedMessage) {
			if(isLocal) {
				this.getOwner().setPosX(getOwner().getPosX()+placementPosX+((float)Math.random()-0.5f)*boxWidth);
				this.getOwner().setPosY(getOwner().getPosX()+placementPosY+((float)Math.random()-0.5f)*boxHeight);
			}
			else {
				this.getOwner().setPosX(placementPosX+(float)Math.random()*boxWidth);
				this.getOwner().setPosY(placementPosY+(float)Math.random()*boxHeight);
			}
		}
	}

}
