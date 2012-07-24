package collisionDetection;

import java.util.ArrayList;

public class CollisionDetectionComponentManager
{
	private static CollisionDetectionComponentManager collisionDetectionComponentManager = null;
	
	private ArrayList<AABoxCollisionDetectionComponent> aaBoxCollisionDetectionList;
	private ArrayList<OOBoxCollisionDetectionComponent> ooBoxCollisionDetectionList;
	private ArrayList<CircleCollisionDetectionComponent> circleCollisionDetectionList;
	
	private CollisionDetectionComponentManager()
	{
		aaBoxCollisionDetectionList = new ArrayList<AABoxCollisionDetectionComponent>();
		ooBoxCollisionDetectionList = new ArrayList<OOBoxCollisionDetectionComponent>();
		circleCollisionDetectionList = new ArrayList<CircleCollisionDetectionComponent>();
	}
	
	
	
	/////////////////////////////////////////////////////////////////
	// Methods for public use
	
	public CollisionDetectionComponentManager getInstance()
	{
		if (collisionDetectionComponentManager == null) collisionDetectionComponentManager = new CollisionDetectionComponentManager();
		return collisionDetectionComponentManager;
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
	
	
	
	/////////////////////////////////////////////////////////////////
	// Methods for Iterating the Collision candidates
	
	private void detectAAAACollisionsAndNotifyCandidates()
	{
		AABoxCollisionDetectionComponent candidate1 = null;
		AABoxCollisionDetectionComponent candidate2 = null;
		
		for (int i = 0; i < aaBoxCollisionDetectionList.size(); i++)
		{
			for (int j = 1; j <= i; j++)
			{
				candidate1 = aaBoxCollisionDetectionList.get(i);
				candidate2 = aaBoxCollisionDetectionList.get(j);
				
				Entity ownerCandidate1 = candidate1.getOwner();
				Entity ownerCandidate2 = candidate2.getOwner();
				
				float posX1 = ownerCandidate1.getPosX();
				float posY1 = ownerCandidate1.getPosY();
				float posX2 = ownerCandidate2.getPosX();
				float posY2 = ownerCandidate2.getPosY();
				
				if (testAAAA(posX1, posY1, posX2, posY2, candidate1.getHalfExtentX(), candidate1.getHalfExtentY(), candidate2.getHalfExtentX(), candidate2.getHalfExtentY()))
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
				float angle = Math.toRadians(boxOwner.getOrientation());
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
			for (int j = 1; j <= i; j++)
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
			for (int j = 1; j <= i; j++)
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
	
	private boolean testAAAA(float c1PosX, float c1PosY, float c1HalfExtentX, float c1HalfExtentY, float c2PosX, float c2PosY, float c2HalfExtentX, float c2HalfExtentY)
	{
		return ((Math.abs(c1PosX - c2PosX) < c1HalfExtentX + c2HalfExtentX) &&
				(Math.abs(c1PosY - c2PosY) < c1HalfExtentY + c2HalfExtentY));
	}

	private boolean testAACircle(float rectX, float rectY, float rectExtX, float rectExtY, float circleX, float circleY, float circleRadius)
	{
		float distanceX = Math.abs(circleX - rectX);
		float distanceY = Math.abs(circleY - rectY);
		
		if ((distanceX >= circleRadius + rectExtX) ||
			(distanceY >= circleRadius + rectExtY))
			return false;
		
		if ((rectExtX + circleRadius > distanceX) ||
			(rectExtY + circleRadius > distanceY))
			return true;
		
		float distanceCircleRectX = distanceX - rectExtX;
		float distanceCircleRectY = distanceY - rectExtY;
		float distanceCircleRect = distanceCircleRectX * distanceCircleRectX + distanceCircleRectY * distanceCircleRectY;
		return distanceCircleRect < circleRadius * circleRadius;
	}
	
	private boolean testOOOO(float posX1, float posY1, float posX2, float posY2, float halfX1, float halfY1, float halfX2, float halfY2, float angle1, float angle2)
	{
		// Local Candidate 1
		float angleDiff = angle2 - angle1;
		float posX1Local1 = (float)(posX1 * Math.cos(-angle1) - posY1 * Math.sin(-angle1));
		float posY1Local1 = (float)(posX1 * Math.sin(-angle1) + posY1 * Math.cos(-angle1));
		float posX2Local1 = (float)(posX2 * Math.cos(-angle1) - posY2 * Math.sin(-angle1));
		float posY2Local1 = (float)(posX2 * Math.sin(-angle1) + posY2 * Math.cos(-angle1));
		
		float half1X2Local1 = (float)(halfX2 * Math.cos(angleDiff));
		float half1Y2Local1 = (float)(halfX2 * Math.sin(angleDiff));
		float half2X2Local1 = (float)(-halfY2 * Math.sin(angleDiff));
		float half2Y2Local1 = (float)(halfY2 * Math.cos(angleDiff));
		
		// Local Candidate 2
		angleDiff *= -1;
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
				 testOOAxis(posY2Local2, posY1Local2, halfY2, half1Y1Local2 + half2Y1Local2, half1Y1Local2 - half2Y1Local2)
				);
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
		candidate2.getOwner().message(new CollisionDetectionMessage(candidate1.getOwner().getID(), candidate2.getOwner().getID()));
	}
}
