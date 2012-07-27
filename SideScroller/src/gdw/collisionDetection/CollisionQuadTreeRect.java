package gdw.collisionDetection;

import gdw.entityCore.Entity;

public class CollisionQuadTreeRect
{
	private CollisionDetectionComponent componentReference;
	private CollisionQuadTree treeReference;
	
	private int boundL;
	private int boundR;
	private int boundT;
	private int boundB;
	
	protected CollisionQuadTreeRect(CircleCollisionDetectionComponent circle)
	{
		Entity owner = circle.getOwner();
		update(owner.getPosX(), owner.getPosY(), circle.getRadius());
		addReference(circle);
	}

	protected CollisionQuadTreeRect(AABoxCollisionDetectionComponent aaBox)
	{
		Entity owner = aaBox.getOwner();
		float halfX = aaBox.getHalfExtentX();
		float halfY = aaBox.getHalfExtentY();
		update(owner.getPosX(), owner.getPosY(), (float) Math.sqrt(halfX * halfX + halfY * halfY));
		addReference(aaBox);
	}
	
	protected CollisionQuadTreeRect(OOBoxCollisionDetectionComponent ooBox)
	{
		Entity owner = ooBox.getOwner();
		float halfX = ooBox.getHalfExtentX();
		float halfY = ooBox.getHalfExtentY();
		update(owner.getPosX(), owner.getPosY(), (float) Math.sqrt(halfX * halfX + halfY * halfY));
		addReference(ooBox);
	}
	
	private void addReference(CollisionDetectionComponent ref)
	{
		componentReference = ref;
		ref.attachTreeRect(this);
	}
	
	protected CollisionDetectionComponent getComponentReference()
	{
		return componentReference;
	}
	
	protected void update(float x, float y, float r)
	{
		int posX = Math.round(x);
		int posY = Math.round(y);
		int radius = (int) r;
		
		boundL = posX - radius;
		boundR = posX + radius;
		boundT = posY - radius;
		boundB = posY + radius;
	}
	
	protected void updateFromComponent()
	{
		float x = componentReference.getOwner().getPosX();
		float y = componentReference.getOwner().getPosY();
		float r = 0;
		
		switch (componentReference.getSubClassType())
		{
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE:
				r = ((CircleCollisionDetectionComponent)componentReference).getRadius();
				break;
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX:
				float halfX = ((OOBoxCollisionDetectionComponent)componentReference).getHalfExtentX();
				float halfY = ((OOBoxCollisionDetectionComponent)componentReference).getHalfExtentY();
				r = (float) Math.sqrt(halfX * halfX + halfY * halfY);
				break;
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX:
				halfX = ((OOBoxCollisionDetectionComponent)componentReference).getHalfExtentX();
				halfY = ((OOBoxCollisionDetectionComponent)componentReference).getHalfExtentY();
				r = (float) Math.sqrt(halfX * halfX + halfY * halfY);
				break;
				
			default:
				break;
		}
		
		update(x, y, r);
	}
	
	// Returns whether a collision occurs
	protected boolean testRect(int x, int y, int w, int h)
	{
		return x >= boundL || x + w <= boundR ||
			   y >= boundT || y + h <= boundB;
	}
	
	protected void updateTreeReference(CollisionQuadTree treeRef)
	{
		treeReference = treeRef;
	}
	
	protected CollisionQuadTree getTreeReference()
	{
		return treeReference;
	}
}
