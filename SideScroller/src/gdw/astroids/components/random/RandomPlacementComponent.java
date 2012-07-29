package gdw.astroids.components.random;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.EntityConstructedMessage;
import gdw.entityCore.Message;

public class RandomPlacementComponent extends Component {

	
	public static final int COMPONENT_TYPE = 1005;
	
	private int placementPosX;
	private int placementPosY;
	private int boxWidth;
	private int boxHeight;
	private boolean isLocal;
	
	private boolean ignoreRandom;
	
	public RandomPlacementComponent(ComponentTemplate template) {
		super(template);
		RandomPlacementComponentTemplate t =(RandomPlacementComponentTemplate)template;
		placementPosX = t.placementPosX;
		placementPosY = t.placementPosY;
		boxWidth = t.boxWidth;
		boxHeight = t.boxHeight;
		isLocal = t.isLocal;
		setIgnoreRandom(false);
	}

	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}
	
	@Override
	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		if(ignoreRandom)
			return;
		if(msg instanceof EntityConstructedMessage) {
			float xOffset = (float)Math.random()*boxWidth;
			if(isLocal) {
				float spawnY = getOwner().getPosY()-boxHeight*0.5f;
				float spawnX = getOwner().getPosX()-boxWidth*0.5f;
				this.getOwner().setPosX(spawnX+placementPosX+xOffset);
				this.getOwner().setPosY(spawnY+placementPosY+(float)Math.random()*boxHeight);
			}
			else {
				this.getOwner().setPosX(placementPosX+xOffset);
				this.getOwner().setPosY(placementPosY+(float)Math.random()*boxHeight);
			}
		}
	}

	public boolean isIgnoreRandom() {
		return ignoreRandom;
	}

	public void setIgnoreRandom(boolean ignoreRandom) {
		this.ignoreRandom = ignoreRandom;
	}

}
