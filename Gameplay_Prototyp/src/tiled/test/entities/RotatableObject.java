package tiled.test.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Transform;
import org.newdawn.slick.geom.Vector2f;

public class RotatableObject extends GameObject {
	private Vector2f pivotPoint;
	private boolean isRotating;
	private float angle;
	private float acceleration;
	private MovementDirection direction;
	private String triggeredBy;
	
	public RotatableObject(Shape s, Color color, float pivotX, float pivotY, float acceleration, MovementDirection direction, String triggeredBy) {
		super(s, color);
		
		isRotating = false;
		angle = 0.0f;
		this.acceleration = acceleration;
		this.direction = direction;
		this.triggeredBy = triggeredBy;
		if ((pivotX >= 0.0f) && (pivotY >= 0.0f)
			&& (pivotX <= 1.0f) && (pivotY <= 1.0f))
		{
			pivotPoint = new Vector2f(s.getMinX() + pivotX * s.getWidth(), s.getMinY() + pivotY * s.getHeight());
		}
		else
		{
			pivotPoint = new Vector2f(s.getMinX(), s.getMinY());
		}
	}

	@Override
	public void update(long delta) {
		if (isRotating)
		{
			angle = (angle + delta * acceleration) / 360.0f;
			if (direction.equals(MovementDirection.RIGHT))
			{
				shape = shape.transform(Transform.createRotateTransform(-angle, pivotPoint.x, pivotPoint.y));
			}
			else if(direction.equals(MovementDirection.LEFT))
			{
				shape = shape.transform(Transform.createRotateTransform(angle, pivotPoint.x, pivotPoint.y));
			}
		}

	}
	
	public void startRotation() { isRotating = true; }
	public void stopRotation() { isRotating = false; }
}
