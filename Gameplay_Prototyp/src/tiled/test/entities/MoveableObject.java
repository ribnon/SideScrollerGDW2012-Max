package tiled.test.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.Vector2f;

public class MoveableObject extends GameObject {
	private Vector2f minPosition, maxPosition;
	private float acceleration;
	private MovementDirection direction;
	 
	public MoveableObject(Shape s, Vector2f minPosition, Vector2f maxPosition, float acceleration, MovementDirection initialDirection)
	{
		super(s, new Color(0xFF, 0x00, 0x00));
		this.minPosition  = new Vector2f(minPosition);
		this.maxPosition  = new Vector2f(maxPosition);
		this.acceleration = acceleration;
		direction = initialDirection;
	}

	@Override
	public void update(long delta)
	{
		if (direction.equals(MovementDirection.RIGHT))
		{
			shape.setX(shape.getX() + acceleration * delta);
			if (shape.getX() > maxPosition.x)
			{
				shape.setX(maxPosition.x);
				direction = MovementDirection.LEFT;
			}
		}
		else if (direction.equals(MovementDirection.LEFT))
		{
			shape.setX(shape.getX() - acceleration * delta);
			if (shape.getX() < minPosition.x)
			{
				shape.setX(minPosition.x);
				direction = MovementDirection.RIGHT;
			}
		}
		
		if (direction.equals(MovementDirection.UP))
		{
			shape.setY(shape.getY() + acceleration * delta);
			if (shape.getY() >= maxPosition.y)
			{
				shape.setY(maxPosition.y);
				direction = MovementDirection.DOWN;
			}
		}
		else if (direction.equals(MovementDirection.DOWN))
		{
			shape.setY(shape.getY() - acceleration * delta);
			if (shape.getY() <= minPosition.y)
			{
				shape.setY(minPosition.y);
				direction = MovementDirection.UP;
			}
		}
	}

}
