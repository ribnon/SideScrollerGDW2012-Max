package collisionDetection;

import java.util.ArrayList;

public class CollisionDetectionComponentManager
{
	private static boolean instantiated;
	
	private ArrayList<AABoxCollisionDetectionComponent> aaBoxCollisionDetectionList;
	private ArrayList<OOBoxCollisionDetectionComponent> ooBoxCollisionDetectionList;
	private ArrayList<CircleCollisionDetectionComponent> circleCollisionDetectionList;
	
	private CollisionDetectionComponentManager()
	{
		aaBoxCollisionDetectionList = new ArrayList<AABoxCollisionDetectionComponent>();
		ooBoxCollisionDetectionList = new ArrayList<OOBoxCollisionDetectionComponent>();
		circleCollisionDetectionList = new ArrayList<CircleCollisionDetectionComponent>();
		
		instantiated = true;
	}
	
	public CollisionDetectionComponentManager instantiateCollisionDetectionComponentManager() throws RuntimeException
	{
		if (!instantiated) return new CollisionDetectionComponentManager();
		throw new RuntimeException("CollisionDetectionComponentManager is Singleton and has already been instantiated!");
	}
	
	public void detectCollisionsAndNotifyEntities()
	{
		detectAAAACollisionsAndNotifyCandidates();
		detectAACircleCollisionsAndNotifyCandidates();
		detectAAOOCollisionsAndNotifyCandidates();
		detectCircleCircleCollisionsAndNotifyCandidates();
		detectOOCircleCollisionsAndNotifyCandidates();
		detectOOOOCollisionsAndNotifyCandidates();
	}
	
	private void detectAAAACollisionsAndNotifyCandidates()
	{
		for (AABoxCollisionDetectionComponent candidate1 : aaBoxCollisionDetectionList)
		{
			for (AABoxCollisionDetectionComponent candidate2 : aaBoxCollisionDetectionList)
			{
				if (candidate1 == candidate2) continue;
				
				Entity ownerCandidate1 = candidate1.getOwner();
				Entity ownerCandidate2 = candidate2.getOwner();
				
				float posX1 = ownerCandidate1.getPosX();
				float posY1 = ownerCandidate1.getPosY();
				float posX2 = ownerCandidate2.getPosX();
				float posY2 = ownerCandidate2.getPosY();
				
				if ((Math.abs(posX1 - posX2) < candidate1.getHalfExtentX() + candidate2.getHalfExtentX()) &&
					(Math.abs(posY1 - posY2) < candidate1.getHalfExtentY() + candidate2.getHalfExtentY()))
				{
					notifyCollisionHasOccured(candidate1);
					notifyCollisionHasOccured(candidate2);
				}
			}
		}
	}
	
	private void detectAAOOCollisionsAndNotifyCandidates()
	{
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
				
				float distanceX = Math.abs(circleMX - rectOwner.getPosX());
				float distanceY = Math.abs(circleMY - rectOwner.getPosY());
				
				if ((distanceX >= candidate2.getRadius() + candidate1.getHalfExtentX()) ||
					(distanceY >= candidate2.getRadius() + candidate1.getHalfExtentY()))
					continue;
				
				if ((candidate1.getHalfExtentX() + candidate2.getRadius() > distanceX) ||
					(candidate1.getHalfExtentY() + candidate2.getRadius() > distanceY))
				{
					notifyCollisionHasOccured(candidate1);
					notifyCollisionHasOccured(candidate2);
					continue;
				}
				
				float distanceCircleRectX = distanceX - candidate1.getHalfExtentX();
				float distanceCircleRectY = distanceY - candidate1.getHalfExtentX();
				float distanceCircleRect = distanceCircleRectX * distanceCircleRectX + distanceCircleRectY * distanceCircleRectY;
				if (distanceCircleRect < candidate2.getRadius() * candidate2.getRadius())
				{
					notifyCollisionHasOccured(candidate1);
					notifyCollisionHasOccured(candidate2);
				}
			}
		}
	}
	
	private void detectOOCircleCollisionsAndNotifyCandidates()
	{
	}
	
	private void detectOOOOCollisionsAndNotifyCandidates()
	{
	}
	
	private void detectCircleCircleCollisionsAndNotifyCandidates()
	{
	
		for (CircleCollisionDetectionComponent candidate1 : circleCollisionDetectionList)
		{
			for (CircleCollisionDetectionComponent candidate2 : circleCollisionDetectionList)
			{
				if (candidate1 == candidate2) continue;
				
				float candidate1PosX = candidate1.getOwner().getPosX();
				float candidate1PosY = candidate1.getOwner().getPosY();
				float candidate2PosX = candidate2.getOwner().getPosX();
				float candidate2PosY = candidate2.getOwner().getPosY();
				
				float distX = candidate1PosX - candidate2PosX;
				float distY = candidate1PosY - candidate2PosY;
				
				float distance = distX * distX + distY * distY;
				float radiusSum = candidate1.getRadius() + candidate2.getRadius();
				if (distance < radiusSum * radiusSum)
				{
					notifyCollisionHasOccured(candidate1);
					notifyCollisionHasOccured(candidate2);
				}
			}
		}
	}
	
	private void notifyCollisionHasOccured(CollisionDetectionComponent candidate)
	{
		// TODO: MSG-Code
	}
}
