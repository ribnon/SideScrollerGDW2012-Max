package tiled.test.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.ShapeFill;
import org.newdawn.slick.fills.GradientFill;
import org.newdawn.slick.geom.Shape;

public abstract class GameObject {
	protected Shape shape;
	protected Color color;
	protected ShapeFill shapeFill;
	
	public GameObject(Shape s, Color color)
	{
		super();
		this.shape = s;
		this.color = color;
		if (this.shape != null)
		{
			this.shapeFill =
				new GradientFill( s.getX(), s.getY(), Color.lightGray,
								  s.getMaxX(), s.getMaxY(), color);
		}
	};
	
	public abstract void update (long delta);
	public void draw(Graphics g)
	{
		if (this.shape != null)
		{
			g.fill(shape, shapeFill);
			g.draw(shape, shapeFill);
		}
	}
	
	public Color getColor() { return color; }
	public Shape getShape() { return shape; }
}
