package gdw.collisionDetection;

import gdw.entityCore.Entity;
import gdw.utils.LinearIstGut;

import java.util.ArrayList;

public class PointCalculationUnit
{
	private PointCalculationUnit pointCalculationUnit;
	
	private PointCalculationUnit()
	{
	}
	
	public PointCalculationUnit getInstance()
	{
		if (pointCalculationUnit == null) pointCalculationUnit = new PointCalculationUnit();
		return pointCalculationUnit;
	}
	
	public ArrayList<float[]> getIntersectionPoints(Entity e1, Entity e2)
	{
		ArrayList<float[]> points = new ArrayList<float[]>();
		
		CollisionDetectionComponent comp1 = (CollisionDetectionComponent) e1.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		CollisionDetectionComponent comp2 = (CollisionDetectionComponent) e2.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		
		if (comp1 != null && comp2 != null)
		{
			int comp1Type = comp1.getSubClassType();
			int comp2Type = comp2.getSubClassType();
			
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			if (comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE &&
				(comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX ||
				 comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX))
			{
				CircleCollisionDetectionComponent circle = (CircleCollisionDetectionComponent) comp1;
				float[] rectExt = {0.0f, 0.0f};
				
				if (comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX)
				{
					AABoxCollisionDetectionComponent rect = (AABoxCollisionDetectionComponent) comp2;
					rectExt[0] = rect.getHalfExtentX();
					rectExt[1] = rect.getHalfExtentY();
				}
				else
				{
					OOBoxCollisionDetectionComponent rect = (OOBoxCollisionDetectionComponent) comp2;
					rectExt[0] = rect.getHalfExtentX();
					rectExt[1] = rect.getHalfExtentY();
				}
				
				calculateFromCircleRect(points, new float[]{e1.getPosX(), e1.getPosY()}, circle.getRadius(), 
						new float[]{e2.getPosX(), e2.getPosY()}, rectExt, e2.getOrientation());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if	(comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE &&
					 comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE)
			{
				CircleCollisionDetectionComponent circle1 = (CircleCollisionDetectionComponent) comp1;
				CircleCollisionDetectionComponent circle2 = (CircleCollisionDetectionComponent) comp2;
				calculateFromCircleCircle(points, new float[]{e1.getPosX(), e1.getPosY()}, circle1.getRadius(), 
												  new float[]{e2.getPosX(), e2.getPosY()}, circle2.getRadius());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if ((comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX ||
					 comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX) &&
					(comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX ||
					 comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX))
			{
				float[] rectExt1 = {0.0f, 0.0f};
				float[] rectExt2 = {0.0f, 0.0f};
				
				if (comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX)
				{
					AABoxCollisionDetectionComponent rect = (AABoxCollisionDetectionComponent) comp1;
					rectExt1[0] = rect.getHalfExtentX();
					rectExt1[1] = rect.getHalfExtentY();
				}
				else
				{
					OOBoxCollisionDetectionComponent rect = (OOBoxCollisionDetectionComponent) comp1;
					rectExt1[0] = rect.getHalfExtentX();
					rectExt1[1] = rect.getHalfExtentY();
				}
				
				if (comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX)
				{
					AABoxCollisionDetectionComponent rect = (AABoxCollisionDetectionComponent) comp2;
					rectExt2[0] = rect.getHalfExtentX();
					rectExt2[1] = rect.getHalfExtentY();
				}
				else
				{
					OOBoxCollisionDetectionComponent rect = (OOBoxCollisionDetectionComponent) comp2;
					rectExt2[0] = rect.getHalfExtentX();
					rectExt2[1] = rect.getHalfExtentY();
				}
				
				calculateFromRectRect(points, new float[] {e1.getPosX(), e1.getPosY()}, rectExt1, 
											  new float[] {e2.getPosX(), e2.getPosY()}, rectExt2, 
											  e1.getOrientation(), e2.getOrientation());
			}
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////////////////////////////////////////
			else if ((comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX ||
					 comp1Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_OOBOX) &&
					 comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_CIRCLE)
			{
				CircleCollisionDetectionComponent circle = (CircleCollisionDetectionComponent) comp2;
				float[] rectExt = {0.0f, 0.0f};
				
				if (comp2Type == CollisionDetectionComponent.COLLISION_COMPONENT_SUBCLASS_AABOX)
				{
					AABoxCollisionDetectionComponent rect = (AABoxCollisionDetectionComponent) comp1;
					rectExt[0] = rect.getHalfExtentX();
					rectExt[1] = rect.getHalfExtentY();
				}
				else
				{
					OOBoxCollisionDetectionComponent rect = (OOBoxCollisionDetectionComponent) comp1;
					rectExt[0] = rect.getHalfExtentX();
					rectExt[1] = rect.getHalfExtentY();
				}
				
				calculateFromCircleRect(points, new float[]{e2.getPosX(), e2.getPosY()}, circle.getRadius(), 
						new float[]{e1.getPosX(), e1.getPosY()}, rectExt, e1.getOrientation());
			}
		}
		
		return points;
	}
	
	private void calculateFromCircleRect(ArrayList<float[]> points, float[] circlePos, float radius, float[] rectPos, float[] rectExtents, float angle)
	{
	}
	
	private void calculateFromCircleCircle(ArrayList<float[]> points, float[] circlePos1, float radius1, float[] circlePos2, float radius2)
	{
		float distX = (float) Math.abs(circlePos1[0] - circlePos2[0]);
		float distY = (float) Math.abs(circlePos1[1] - circlePos2[1]);
		
		float distance = (float) Math.sqrt(distX * distX + distY * distY);
		if (distance > radius1 + radius2) return;
		if (!(radius1 + distance > radius2 && radius2 + distance > radius1)) return;
		
		float eps = (float) Math.acos(distX / distance);
		
		float alpha = (float) Math.acos((radius2 * radius2 - radius1 * radius1 - distance * distance) / (-2 * distance * radius1));
		float point1[] = new float[] { (float) (Math.cos(alpha) * radius1), (float) (Math.sin(alpha) * radius1) };
		float point2[] = new float[] { point1[0], -point1[1] };
		
		if (circlePos1[0] > circlePos2[0])
		{
			point1[0] *= -1;
			point2[0] *= -1;
		}
		
		double rot[][] = new double[][] {{Math.cos(eps), -Math.sin(eps)}, {Math.sin(eps), Math.cos(eps)}};
		
		double cPos1[] = LinearIstGut.matMult(rot, new double[] { point1[0], point1[1]} );
		double cPos2[] = LinearIstGut.matMult(rot, new double[] { point2[0], point2[1]} );
		
		
		point1[0] = (float) cPos1[0] + circlePos1[0];
		point1[1] = (float) cPos1[1] + circlePos1[1];
		point2[0] = (float) cPos2[0] + circlePos1[0];
		point2[1] = (float) cPos2[1] + circlePos1[1];
		
		if (circlePos1[0] > circlePos2[0] && circlePos1[1] < circlePos2[1])
		{
			float diffY = circlePos1[1] - point1[1];
			point1[1] += 2 * diffY;
			
			diffY = circlePos1[1] - point2[1];
			point2[1] += 2 * diffY;
		}
		
		if (circlePos1[0] <= circlePos2[0] && circlePos1[1] >= circlePos2[1])
		{
			float diffY = circlePos1[1] - point1[1];
			point1[1] += 2 * diffY;
			
			diffY = circlePos1[1] - point2[1];
			point2[1] += 2 * diffY;
		}
		
		points.add(point1);
		
		if (point1[0] != point2[0] && point1[1] != point2[1])
		{
			points.add(point2);
			
		}
	}
	
	private void calculateFromRectRect(ArrayList<float[]> points, float[] rectPos1, float[] rectExtents1, float[] rectPos2, float[] rectExtents2, float angle1, float angle2)
	{
		angle1 = (float) Math.toRadians(angle1);
		angle2 = (float) Math.toRadians(angle2);
		
		double point11[] = new double[] { rectExtents1[0], rectExtents1[1] };
		double point12[] = new double[] { rectExtents1[0], -rectExtents1[1] };
		double point13[] = new double[] { -rectExtents1[0], -rectExtents1[1] };
		double point14[] = new double[] { -rectExtents1[0], rectExtents1[1] };
		
		double point21[] = new double[] { rectExtents2[0], rectExtents2[1] };
		double point22[] = new double[] { rectExtents2[0], -rectExtents2[1] };
		double point23[] = new double[] { -rectExtents2[0], -rectExtents2[1] };
		double point24[] = new double[] { -rectExtents2[0], rectExtents2[1] };
		
		double transform1[][] = new double[][] {{ Math.cos(angle1), -Math.sin(angle1)}, {Math.sin(angle1), Math.cos(angle1)}};
		double transform2[][] = new double[][] {{ Math.cos(angle2), -Math.sin(angle2)}, {Math.sin(angle2), Math.cos(angle2)}};
		
		point11 = LinearIstGut.matMult(transform1, point11);
		point12 = LinearIstGut.matMult(transform1, point12);
		point13 = LinearIstGut.matMult(transform1, point13);
		point14 = LinearIstGut.matMult(transform1, point14);
		
		point21 = LinearIstGut.matMult(transform2, point21);
		point22 = LinearIstGut.matMult(transform2, point22);
		point23 = LinearIstGut.matMult(transform2, point23);
		point24 = LinearIstGut.matMult(transform2, point24);
		
		point11[0] += rectPos1[0];
		point11[1] += rectPos1[1];
		point12[0] += rectPos1[0];
		point12[1] += rectPos1[1];
		point13[0] += rectPos1[0];
		point13[1] += rectPos1[1];
		point14[0] += rectPos1[0];
		point14[1] += rectPos1[1];
		
		point21[0] += rectPos2[0];
		point21[1] += rectPos2[1];
		point22[0] += rectPos2[0];
		point22[1] += rectPos2[1];
		point23[0] += rectPos2[0];
		point23[1] += rectPos2[1];
		point24[0] += rectPos2[0];
		point24[1] += rectPos2[1];
		
		float lines1[][][] = new float[][][] {{{(float)(point11[0]), 			  (float)(point11[1])}, 
										       {(float)(point12[0] - point11[0]), (float)(point12[1] - point11[1])}},
										      {{(float)(point12[0]), 			  (float)(point12[1])}, 
										       {(float)(point13[0] - point12[0]), (float)(point13[1] - point12[1])}},
		                                      {{(float)(point13[0]), 			  (float)(point13[1])}, 
				  						       {(float)(point14[0] - point13[0]), (float)(point14[1] - point13[1])}},
				  						  	  {{(float)(point14[0]), 			  (float)(point14[1])}, 
				  						       {(float)(point11[0] - point14[0]), (float)(point11[1] - point14[1])}}};
		
		float lines2[][][] = new float[][][] {{{(float)(point21[0]), 			  (float)(point21[1])}, 
		       								   {(float)(point22[0] - point21[0]), (float)(point22[1] - point21[1])}},
		       								  {{(float)(point22[0]), 			  (float)(point22[1])}, 
		       								   {(float)(point23[0] - point22[0]), (float)(point23[1] - point22[1])}},
		       								  {{(float)(point23[0]), 			  (float)(point23[1])}, 
		       								   {(float)(point24[0] - point23[0]), (float)(point24[1] - point23[1])}},
		       								  {{(float)(point24[0]), 			  (float)(point24[1])}, 
		       								   {(float)(point21[0] - point24[0]), (float)(point21[1] - point24[1])}}};

		
		float result[] = new float[] {0, 0};
		
		for (int i = 0; i < 4; i++)
		{
			for (int j = 0; j < 4; j++)
			{
				result = LinearIstGut.calculateIntersectionPoint(lines1[i][0], lines1[i][1], lines2[j][0], lines2[j][1]);
				if (result[0] != 0.0f && result[1] != 0.0f)
					points.add(result);
			}
		}
	}
}
