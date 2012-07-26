package gdw.genericBehavior;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class PivotRotationComponent extends Component
{
	public static final int COMPONENT_TYPE = 0;
	
	private float pivotX;
	private float pivotY;
	
	protected PivotRotationComponent(ComponentTemplate template)
	{
		super(template);
		pivotX = template.getFloatParam("pivotX", 0.0f);
		pivotY = template.getFloatParam("pivotY", 0.0f);
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}
	
	public void rotate(float angle)
	{
		float posX = getOwner().getPosX();
		float posY = getOwner().getPosY();
		float orientation = (float) Math.toRadians(getOwner().getOrientation());
		
		float pivotXWorld = pivotX + posX;
		float pivotYWorld = pivotY + posY;
		
		angle = (float) Math.toRadians(angle);
		
		float posXnew = (float)(Math.cos(angle) * posX - Math.sin(angle) * posY);
		float posYnew = (float)(Math.sin(angle) * posX + Math.cos(angle) * posY);
		
		posXnew += pivotXWorld;
		posYnew += pivotYWorld;
		
		orientation += angle;
		
		getOwner().setPos(posXnew, posYnew)
	}
}
