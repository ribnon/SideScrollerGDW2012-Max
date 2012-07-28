package tiled.test.entities;

import org.newdawn.slick.Color;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.geom.Shape;

public class Enemy extends GameObject {

	public Enemy(Shape s) {
		super(s, new Color(0xAA, 0x00, 0x7F));
	}

	public Enemy(float[] points) {
		this(new Polygon(points));
	}

	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub

	}

}
