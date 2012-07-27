package collisionDetection;

import gdw.entityCore.Entity;
import gdw.entityCore.Level;

import java.util.ArrayList;

public class CollisionDetectionComponentManager
{
	private static CollisionDetectionComponentManager collisionDetectionComponentManager = null;
	
	private ArrayList<AABoxCollisionDetectionComponent> aaBoxCollisionDetectionList;
	private ArrayList<OOBoxCollisionDetectionComponent> ooBoxCollisionDetectionList;
	private ArrayList<CircleCollisionDetectionComponent> circleCollisionDetectionList;
	
	private CollisionQuadTree quadTree;
	
	private CollisionDetectionComponentManager(boolean useQuadTree)
	{
		aaBoxCollisionDetectionList = new ArrayList<AABoxCollisionDetectionComponent>();
		ooBoxCollisionDetectionList = new ArrayList<OOBoxCollisionDetectionComponent>();
		circleCollisionDetectionList = new ArrayList<CircleCollisionDetectionComponent>();
		
		if (useQuadTree) 
		{
			int mapWidth = Level.getInstance().getMapWidth();
			int mapHeight = Level.getInstance().getMapHeight();
			
			int level = 1;
			int levelDivider = Math.max(mapWidth, mapHeight);
			
			while (levelDivider > 128)
			{
				levelDivider /= 2;
				level++;
			}
			
			quadTree = new CollisionQuadTree(3, mapWidth, mapHeight);
		}
		
		else quadTree = null;
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	// Methods for public use
	
	public static CollisionDetectionComponentManager getInstance()
	{
		if (collisionDetectionComponentManager == null) collisionDetectionComponentManager = new CollisionDetectionComponentManager(false);
		return collisionDetectionComponentManager;
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	// Check all objects for collision (WITHOUT quadtree)
	
	public void detectCollisionsAndNotifyEntities()
	{
		if (quadTree != null) return;
		detectAAAACollisionsAndNotifyCandidates();
		detectAACircleCollisionsAndNotifyCandidates();
		detectAAOOCollisionsAndNotifyCandidates();
		detectCircleCircleCollisionsAndNotifyCandidates();
		detectOOCircleCollisionsAndNotifyCandidates();
		detectOOOOCollisionsAndNotifyCandidates();
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	// Methods for collision detection using a quadtree
	
	public void detectCollisions(Entity e)
	{
		if (quadTree == null) return;
		CollisionDetectionComponent comp = (CollisionDetectionComponent) e.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		if (comp == null) return;
		
		quadTree.updateRect(comp.getTreeRect());
		ArrayList<CollisionQuadTreeRect> candidates = quadTree.getColliders(comp.getTreeRect());
		
		boolean result = false;
		
		for (CollisionQuadTreeRect rect : candidates)
		{
			CollisionDetectionComponent current = rect.getComponentReference();
			switch (comp.getSubClassType())
			{
				case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE:
					result = detectCollisionCircle((CircleCollisionDetectionComponent)comp, current);
					break;
				case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX:
					result = detectCollisionAABox((AABoxCollisionDetectionComponent)comp, current);
					break;
				case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX:
					result = detectCollisionOOBox((OOBoxCollisionDetectionComponent)comp, current);
					break;
				default:
			}
			
			if (result) notifyCollisionHasOccured(comp, current);
			result = false;
		}
	}
	
	public boolean detectCollisionCircle(CircleCollisionDetectionComponent comp1, CollisionDetectionComponent comp2)
	{
		Entity e1 = comp1.getOwner();
		Entity e2 = comp2.getOwner();
		
		switch (comp2.getSubClassType())
		{
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE:
				return testCircleCircle(e1.getPosX(), e1.getPosY(), e2.getPosX(), e2.getPosY(), 
						comp1.getRadius(), ((CircleCollisionDetectionComponent)comp2).getRadius());
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX:
				AABoxCollisionDetectionComponent aabox = (AABoxCollisionDetectionComponent) comp2;
				return testAACircle(e2.getPosX(), e2.getPosY(), aabox.getHalfExtentX(), aabox.getHalfExtentY(),
						e1.getPosX(), e1.getPosY(), comp1.getRadius());
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX:
				OOBoxCollisionDetectionComponent oobox = (OOBoxCollisionDetectionComponent) comp2;
				return testOOCircle(e2.getPosX(), e2.getPosY(), oobox.getHalfExtentX(), oobox.getHalfExtentY(), (float) Math.toRadians(e2.getOrientation()),
						e1.getPosX(), e1.getPosY(), comp1.getRadius());
				
			default:
				break;
		}
		return false;
	}
	
	public boolean detectCollisionAABox(AABoxCollisionDetectionComponent comp1, CollisionDetectionComponent comp2)
	{
		Entity e1 = comp1.getOwner();
		Entity e2 = comp2.getOwner();
		
		switch (comp2.getSubClassType())
		{
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE:
				CircleCollisionDetectionComponent circle = (CircleCollisionDetectionComponent) comp2;
				return testAACircle(e1.getPosX(), e1.getPosY(), comp1.getHalfExtentX(), comp1.getHalfExtentY(),
						e2.getPosX(), e2.getPosY(), circle.getRadius());
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX:
				AABoxCollisionDetectionComponent aabox = (AABoxCollisionDetectionComponent) comp2;
				return testAAAA(e1.getPosX(), e1.getPosY(), comp1.getHalfExtentX(), comp1.getHalfExtentY(), 
						e2.getPosX(), e2.getPosY(), aabox.getHalfExtentX(), aabox.getHalfExtentY());
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX:
				OOBoxCollisionDetectionComponent oobox = (OOBoxCollisionDetectionComponent) comp2;
				return testOOOO(e1.getPosX(), e1.getPosY(), e2.getPosX(), e2.getPosY(), comp1.getHalfExtentX(),
						comp1.getHalfExtentY(), oobox.getHalfExtentX(), oobox.getHalfExtentY(), 0.0f, (float) Math.toRadians(e2.getOrientation()));
				
			default:
				break;
		}
		return false;
	}
	
	public boolean detectCollisionOOBox(OOBoxCollisionDetectionComponent comp1, CollisionDetectionComponent comp2)
	{
		Entity e1 = comp1.getOwner();
		Entity e2 = comp2.getOwner();
		
		switch (comp2.getSubClassType())
		{
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE:
				CircleCollisionDetectionComponent circle = (CircleCollisionDetectionComponent) comp2;
				return testOOCircle(e1.getPosX(), e1.getPosY(), comp1.getHalfExtentX(), comp1.getHalfExtentY(), (float) Math.toRadians(e1.getOrientation()),
						e2.getPosX(), e2.getPosY(), circle.getRadius());
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX:
				AABoxCollisionDetectionComponent aabox = (AABoxCollisionDetectionComponent) comp2;
				return testOOOO(e1.getPosX(), e1.getPosY(), e2.getPosX(), e2.getPosY(), comp1.getHalfExtentX(),
						comp1.getHalfExtentY(), aabox.getHalfExtentX(), aabox.getHalfExtentY(), (float) Math.toRadians(e1.getOrientation()), 0.0f);
				
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX:
				OOBoxCollisionDetectionComponent oobox = (OOBoxCollisionDetectionComponent) comp2;
				return testOOOO(e1.getPosX(), e1.getPosY(), e2.getPosX(), e2.getPosY(), comp1.getHalfExtentX(),
						comp1.getHalfExtentY(), oobox.getHalfExtentX(), oobox.getHalfExtentY(), (float) Math.toRadians(e1.getOrientation()), (float) Math.toRadians(e2.getOrientation()));
				
			default:
				break;
		}
		return false;
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	// Methods for Iterating the Collision candidates NOT using a quadtree
	
	private void detectAAAACollisionsAndNotifyCandidates()
	{
		AABoxCollisionDetectionComponent candidate1 = null;
		AABoxCollisionDetectionComponent candidate2 = null;
		
		for (int i = 0; i < aaBoxCollisionDetectionList.size(); i++)
		{
			for (int j = i+1; j < aaBoxCollisionDetectionList.size(); j++)
			{
				candidate1 = aaBoxCollisionDetectionList.get(i);
				candidate2 = aaBoxCollisionDetectionList.get(j);
				
				Entity ownerCandidate1 = candidate1.getOwner();
				Entity ownerCandidate2 = candidate2.getOwner();
				
				float posX1 = ownerCandidate1.getPosX();
				float posY1 = ownerCandidate1.getPosY();
				float posX2 = ownerCandidate2.getPosX();
				float posY2 = ownerCandidate2.getPosY();
				
				if (testAAAA(posX1, posY1, candidate1.getHalfExtentX(), candidate1.getHalfExtentY(), posX2, posY2,  candidate2.getHalfExtentX(), candidate2.getHalfExtentY()))
					notifyCollisionHasOccured(candidate1, candidate2);
			}
		}
	}
	
	private void detectAAOOCollisionsAndNotifyCandidates()
	{
		for (AABoxCollisionDetectionComponent candidate1 : aaBoxCollisionDetectionList)
		{
			for (OOBoxCollisionDetectionComponent candidate2 : ooBoxCollisionDetectionList)
			{
				Entity boxOwner1 = candidate1.getOwner();
				Entity boxOwner2 = candidate2.getOwner();
				
				float posX1 = boxOwner1.getPosX();
				float posY1 = boxOwner1.getPosY();
				float posX2 = boxOwner2.getPosX();
				float posY2 = boxOwner2.getPosY();
				
				float halfX1 = candidate1.getHalfExtentX();
				float halfY1 = candidate1.getHalfExtentY();
				float halfX2 = candidate2.getHalfExtentX();
				float halfY2 = candidate2.getHalfExtentY();
				
				float angle1 = boxOwner1.getOrientation();
				float angle2 = boxOwner2.getOrientation();
				
				if (testOOOO(posX1, posY1, posX2, posY2, halfX1, halfY1, halfX2, halfY2, angle1, angle2))
					notifyCollisionHasOccured(candidate1, candidate2);
			}
		}
	}
	
	private void detectAACircleCollisionsAndNotifyCandidates()
	{
		for (AABoxCollisionDetectionComponent candidate1 : aaBoxCollisionDetectionList)
		{
			for (CircleCollisionDetectionComponent candidate2 : circleCollisionDetectionList)
			{
				Entity circleOwner = candidate2.getOwner();
				Entity rectOwner = candidate1.getOwner();
				
				float circleMX = circleOwner.getPosX();
				float circleMY = circleOwner.getPosY();
				float circleRad = candidate2.getRadius();
				
				if (testAACircle(rectOwner.getPosX(), rectOwner.getPosY(), candidate1.getHalfExtentX(), candidate1.getHalfExtentY(), circleMX, circleMY, circleRad))
					notifyCollisionHasOccured(candidate1, candidate2);
			}
		}
	}
	
	private void detectOOCircleCollisionsAndNotifyCandidates()
	{
		for (OOBoxCollisionDetectionComponent candidate1 : ooBoxCollisionDetectionList)
		{
			for (CircleCollisionDetectionComponent candidate2 : circleCollisionDetectionList)
			{
				Entity boxOwner = candidate1.getOwner();
				Entity circleOwner = candidate2.getOwner();
				
				float rectX = boxOwner.getPosX();
				float rectY = boxOwner.getPosY();
				float angle = (float) Math.toRadians(boxOwner.getOrientation());
				float rectHalfX = candidate1.getHalfExtentX();
				float rectHalfY = candidate1.getHalfExtentY();
				float circleX = circleOwner.getPosX();
				float circleY = circleOwner.getPosY();
				float circleRadius = candidate2.getRadius();
				
				rectX = (float)(rectX * Math.cos(-angle) - rectY * Math.sin(-angle));
				rectY = (float)(rectX * Math.sin(-angle) + rectY * Math.cos(-angle));
				circleX = (float)(circleX * Math.cos(-angle) - circleY * Math.sin(-angle));
				circleY = (float)(circleX * Math.sin(-angle) + circleY * Math.cos(-angle));
				
				if (testAACircle(rectX, rectY, rectHalfX, rectHalfY, circleX, circleY, circleRadius))
					notifyCollisionHasOccured(candidate1, candidate2);
			}
		}
	}
	
	private void detectOOOOCollisionsAndNotifyCandidates()
	{
		OOBoxCollisionDetectionComponent candidate1 = null;
		OOBoxCollisionDetectionComponent candidate2 = null;
		
		for (int i = 0; i < ooBoxCollisionDetectionList.size(); i++)
		{
			for (int j = i+1; j < ooBoxCollisionDetectionList.size(); j++)
			{
				candidate1 = ooBoxCollisionDetectionList.get(i);
				candidate2 = ooBoxCollisionDetectionList.get(j);
				
				Entity boxOwner1 = candidate1.getOwner();
				Entity boxOwner2 = candidate2.getOwner();
				
				float posX1 = boxOwner1.getPosX();
				float posY1 = boxOwner1.getPosY();
				float posX2 = boxOwner2.getPosX();
				float posY2 = boxOwner2.getPosY();
				
				float halfX1 = candidate1.getHalfExtentX();
				float halfY1 = candidate1.getHalfExtentY();
				float halfX2 = candidate2.getHalfExtentX();
				float halfY2 = candidate2.getHalfExtentY();
				
				float angle1 = boxOwner1.getOrientation();
				float angle2 = boxOwner2.getOrientation();
				
				if (testOOOO(posX1, posY1, posX2, posY2, halfX1, halfY1, halfX2, halfY2, angle1, angle2))
					notifyCollisionHasOccured(candidate1, candidate2);
			}
		}
	}
	
	private void detectCircleCircleCollisionsAndNotifyCandidates()
	{
		CircleCollisionDetectionComponent candidate1 = null;
		CircleCollisionDetectionComponent candidate2 = null;
		
		for (int i = 0; i < circleCollisionDetectionList.size(); i++)
		{
			for (int j = i+1; j < circleCollisionDetectionList.size(); j++)
			{
				candidate1 = circleCollisionDetectionList.get(i);
				candidate2 = circleCollisionDetectionList.get(j);
				
				float candidate1PosX = candidate1.getOwner().getPosX();
				float candidate1PosY = candidate1.getOwner().getPosY();
				float candidate2PosX = candidate2.getOwner().getPosX();
				float candidate2PosY = candidate2.getOwner().getPosY();
				
				float candidate1Radius = candidate1.getRadius();
				float candidate2Radius = candidate2.getRadius();
				
				if (testCircleCircle(candidate1PosX, candidate1PosY, candidate2PosX, candidate2PosY, candidate1Radius, candidate2Radius))
					notifyCollisionHasOccured(candidate1, candidate2);
			}
		}
	}

	
	
	/////////////////////////////////////////////////////////////////
	// Methods for testing the collision candidates
	
	private boolean testOOCircle(float c1PosX, float c1PosY, float c1HalfExtentX, float c1HalfExtentY, float c1Orientation, float c2PosX, float c2PosY, float c2Radius)
	{
		float c1PosX2 = (float)(c1PosX * Math.cos(-c1Orientation) - c1PosY * Math.sin(-c1Orientation));
		float c1PosY2 = (float)(c1PosX * Math.sin(-c1Orientation) + c1PosY * Math.cos(-c1Orientation));
		
		float c2PosX2 = (float)(c2PosX * Math.cos(-c1Orientation) - c2PosY * Math.sin(-c1Orientation));
		float c2PosY2 = (float)(c2PosX * Math.sin(-c1Orientation) + c2PosY * Math.cos(-c1Orientation));
		
		return testAACircle(c1PosX2, c1PosY2, c1HalfExtentX, c1HalfExtentY, c2PosX2, c2PosY2, c2Radius);
	}
	
	private boolean testAAAA(float c1PosX, float c1PosY, float c1HalfExtentX, float c1HalfExtentY, float c2PosX, float c2PosY, float c2HalfExtentX, float c2HalfExtentY)
	{
		return ((Math.abs(c1PosX - c2PosX) < c1HalfExtentX + c2HalfExtentX) &&
				(Math.abs(c1PosY - c2PosY) < c1HalfExtentY + c2HalfExtentY));
	}

	private boolean testAACircle(float rectX, float rectY, float rectExtX, float rectExtY, float circleX, float circleY, float circleRadius)
	{
		float distanceX = Math.abs(circleX - rectX);
		float distanceY = Math.abs(circleY - rectY);
		
		float pointX;
		float pointY;
		
		if ((distanceX >= circleRadius + rectExtX) ||
			(distanceY >= circleRadius + rectExtY))
			return false;
		
		if (circleX > rectX)
			pointX = rectX + rectExtX;
		else
			pointX = rectX - rectExtX;
		
		if (circleY > rectY)
			pointY = rectY + rectExtY;
		else
			pointY = rectY - rectExtY;
		
		float diffX = circleX - pointX;
		float diffY = circleY - pointY;
		
		if (diffX * diffX + diffY * diffY < circleRadius * circleRadius) return true;
		
		if ((circleX > rectX - rectExtX && circleX < rectX + rectExtX) ||
			(circleY > rectY - rectExtY && circleY < rectY + rectExtY))
		{
			if ((distanceX < circleRadius + rectExtX) ||
				(distanceY < circleRadius + rectExtY))
				return true;
		}
		
		return false;
	}
	
	private boolean testOOOO(float posX1, float posY1, float posX2, float posY2, float halfX1, float halfY1, float halfX2, float halfY2, float angle1, float angle2)
	{
		// Local Candidate 1
		if (angle1 > Math.PI / 2) angle1 -= Math.PI;
		if (angle1 < -Math.PI / 2) angle1 += Math.PI;
		if (angle2 > Math.PI / 2) angle2 -= Math.PI;
		if (angle2 < -Math.PI / 2) angle2 += Math.PI;
		
		if (angle1 > Math.PI / 2) angle1 -= Math.PI;
		if (angle1 < -Math.PI / 2) angle1 += Math.PI;
		if (angle2 > Math.PI / 2) angle2 -= Math.PI;
		if (angle2 < -Math.PI / 2) angle2 += Math.PI;
		
		float angleDiff = angle1 - angle2;
		if (angleDiff < 0) angleDiff *= -1;
		float posX1Local1 = (float)(posX1 * Math.cos(-angle1) - posY1 * Math.sin(-angle1));
		float posY1Local1 = (float)(posX1 * Math.sin(-angle1) + posY1 * Math.cos(-angle1));
		float posX2Local1 = (float)(posX2 * Math.cos(-angle1) - posY2 * Math.sin(-angle1));
		float posY2Local1 = (float)(posX2 * Math.sin(-angle1) + posY2 * Math.cos(-angle1));
		
		float half1X2Local1 = (float)(halfX2 * Math.cos(angleDiff));
		float half1Y2Local1 = (float)(halfX2 * Math.sin(angleDiff));
		float half2X2Local1 = (float)(-halfY2 * Math.sin(angleDiff));
		float half2Y2Local1 = (float)(halfY2 * Math.cos(angleDiff));
		
		// Local Candidate 2
		float posX1Local2 = (float)(posX1 * Math.cos(-angle2) - posY1 * Math.sin(-angle2));
		float posY1Local2 = (float)(posX1 * Math.sin(-angle2) + posY1 * Math.cos(-angle2));
		float posX2Local2 = (float)(posX2 * Math.cos(-angle2) - posY2 * Math.sin(-angle2));
		float posY2Local2 = (float)(posX2 * Math.sin(-angle2) + posY2 * Math.cos(-angle2));
		
		float half1X1Local2 = (float)(halfX1 * Math.cos(angleDiff));
		float half1Y1Local2 = (float)(halfX1 * Math.sin(angleDiff));
		float half2X1Local2 = (float)(-halfY1 * Math.sin(angleDiff));
		float half2Y1Local2 = (float)(halfY1 * Math.cos(angleDiff));
		
		// Test the actual axes
		return !(testOOAxis(posX1Local1, posX2Local1, halfX1, half1X2Local1 + half2X2Local1, half1X2Local1 - half2X2Local1) ||
				 testOOAxis(posY1Local1, posY2Local1, halfY1, half1Y2Local1 + half2Y2Local1, half1Y2Local1 - half2Y2Local1) ||
				 testOOAxis(posX2Local2, posX1Local2, halfX2, half1X1Local2 + half2X1Local2, half1X1Local2 - half2X1Local2) ||
				 testOOAxis(posY2Local2, posY1Local2, halfY2, half1Y1Local2 + half2Y1Local2, half1Y1Local2 - half2Y1Local2));
	}
	
// Tests if NO projected collision occurs
	private boolean testOOAxis(float point1, float point2, float half1, float diagPoint1, float diagPoint2)
	{
		float objectDistance = Math.abs(point1 - point2);
		float maxDist = Math.max(diagPoint1, diagPoint2);
		
		return objectDistance >= maxDist + half1;
	}

	private boolean testCircleCircle(float c1PosX, float c1PosY, float c2PosX, float c2PosY, float c1Rad, float c2Rad)
	{
		float distX = c1PosX - c2PosX;
		float distY = c1PosY - c2PosY;
		
		float distance = distX * distX + distY * distY;
		float radiusSum = c1Rad + c2Rad;
		
		return distance < radiusSum * radiusSum;
	}

	
	
	/////////////////////////////////////////////////////////////////
	// Notify Entities for Collision Reaction
	
	private void notifyCollisionHasOccured(CollisionDetectionComponent candidate1, CollisionDetectionComponent candidate2)
	{
		candidate1.getOwner().message(new CollisionDetectionMessage(candidate1.getOwner().getID(), candidate2.getOwner().getID()));
		candidate2.getOwner().message(new CollisionDetectionMessage(candidate2.getOwner().getID(), candidate1.getOwner().getID()));
	}

	
	
	/////////////////////////////////////////////////////////////////
	// CollisionObject Management Methods
	
	protected void registerCircleCollisionDetectionComponent(CircleCollisionDetectionComponent c)
	{
		circleCollisionDetectionList.add(c);
		registerTreeRect(c);
	}
	
	protected void registerAABoxCollisionDetectionComponent(AABoxCollisionDetectionComponent c)
	{
		aaBoxCollisionDetectionList.add(c);
		registerTreeRect(c);
	}
	
	protected void registerOOBoxCollisionDetectionComponent(OOBoxCollisionDetectionComponent c)
	{
		ooBoxCollisionDetectionList.add(c);
		registerTreeRect(c);
	}
	
	protected void registerTreeRect(CircleCollisionDetectionComponent comp)
	{
		if (quadTree == null) return;
		CollisionQuadTreeRect rect = new CollisionQuadTreeRect(comp);
		comp.attachTreeRect(rect);
		quadTree.insert(rect);
	}
	
	protected void registerTreeRect(AABoxCollisionDetectionComponent comp)
	{
		if (quadTree == null) return;
		CollisionQuadTreeRect rect = new CollisionQuadTreeRect(comp);
		comp.attachTreeRect(rect);
		quadTree.insert(rect);
	}
	
	protected void registerTreeRect(OOBoxCollisionDetectionComponent comp)
	{
		if (quadTree == null) return;
		CollisionQuadTreeRect rect = new CollisionQuadTreeRect(comp);
		comp.attachTreeRect(rect);
		quadTree.insert(rect);
	}
	
	protected void removeCircleCollisionDetectionComponent(CircleCollisionDetectionComponent c)
	{
		circleCollisionDetectionList.remove(c);
		removeTreeRect(c);
	}
	
	protected void removeAABoxCollisionDetectionComponent(AABoxCollisionDetectionComponent c)
	{
		aaBoxCollisionDetectionList.remove(c);
		removeTreeRect(c);
	}
	
	protected void removeOOBoxCollisionDetectionComponent(OOBoxCollisionDetectionComponent c)
	{
		ooBoxCollisionDetectionList.remove(c);
		removeTreeRect(c);
	}
	
	protected void removeTreeRect(CollisionDetectionComponent comp)
	{
		if (quadTree == null) return;
		quadTree.delete(comp.getTreeRect());
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	// Additional test Interfaces
	
	public boolean checkEntitiesForCollision(Entity e1, Entity e2)
	{
		CollisionDetectionComponent comp1 = (CollisionDetectionComponent) e1.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		CollisionDetectionComponent comp2 = (CollisionDetectionComponent) e2.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
	
		if (comp1 == null || comp2 == null) return false;
		
		boolean result = false;
		
		switch (comp1.getSubClassType())
		{
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE:
				result = detectCollisionCircle((CircleCollisionDetectionComponent)comp1, comp2);
				break;
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX:
				result = detectCollisionAABox((AABoxCollisionDetectionComponent)comp1, comp2);
				break;
			case CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX:
				result = detectCollisionOOBox((OOBoxCollisionDetectionComponent)comp1, comp2);
				break;
			default:
		}
		
		return result;
	}
}
