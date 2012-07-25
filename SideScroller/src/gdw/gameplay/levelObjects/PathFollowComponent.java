package gdw.gameplay.levelObjects;

import java.util.ArrayList;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

/**
 * Makes the Entity follow a path.
 * 
 * @author Oliver Waidler
 */
public class PathFollowComponent extends Component
{
	/**
	 * Used on the waypoint-list
	 */
	class Point
	{
		public float x, y;
	}

	/**
	 * The list of way points to follow. When at end, the next way point is the
	 * first one
	 */
	private ArrayList<Point> wayPoints = new ArrayList<Point>();
	/**
	 * The way point this entity is currently heading towards
	 */
	private int currentWaySegment = 0;
	/**
	 * The speed at which the entity moves
	 */
	private float speed = 1.0F;
	/**
	 * The distance from the way point at which the next way point should be
	 * targeted
	 */
	private static final float WAYPOINT_CHANGE_DISTANCE = 5.0F;
	public static final int COMPONENT_TYPE = 23;

	protected PathFollowComponent(ComponentTemplate template)
	{
		super(template);
		// Waypoints are in the format:
		// x;y x;y x;y
		String[] points = template.getStringParam("wayPoints").split(" ");
		for (String tmpPoint : points)
		{
			String[] values = tmpPoint.split(";", 2);
			float x = Float.parseFloat(values[0]);
			float y = Float.parseFloat(values[1]);
			Point created = new Point();
			created.x = x;
			created.y = y;
			wayPoints.add(created);
		}
		speed = template.getFloatParam("speed");
	}

	/**
	 * Sets the position of the Entity based on the targeted way point, the
	 * speed and the deltaTime.
	 * 
	 * When the target way point is close-by, the next way point is targeted.
	 * If no way points are set, this function does nothing
	 * 
	 * @param deltaTime
	 *            time passed since last frame
	 */
	public void tick(float deltaTime)
	{
		if (getWayPointCount() == 0)
			return;

		float xPos = getOwner().getPosX();
		float yPos = getOwner().getPosY();
		float xTarget = wayPoints.get(currentWaySegment).x;
		float yTarget = wayPoints.get(currentWaySegment).y;

		float xDiff = xTarget - xPos;
		float yDiff = yTarget - yPos;
		float vectorLength = calculateVectorLength(xDiff, yDiff);

		float xPosNew = xPos + (xDiff / vectorLength) * speed * deltaTime;
		float yPosNew = yPos + (yDiff / vectorLength) * speed * deltaTime;

		getOwner().setPos(xPosNew, yPosNew);

		// Change the wayPoint if the distance is smaller than the threshold
		if (vectorLength < WAYPOINT_CHANGE_DISTANCE)
			currentWaySegment = (currentWaySegment + 1) % getWayPointCount();
	}

	@Override
	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	/**
	 * Returns the amount of way points
	 * @return
	 */
	public int getWayPointCount()
	{
		return wayPoints.size();
	}

	/**
	 * Calculates the length of a "vector".
	 * 
	 * A sqrt is used to calculate the correct length.
	 * @param x The x value of the vector
	 * @param y The y value of the vector
	 * @return The length of the vector
	 */
	private float calculateVectorLength(float x, float y)
	{
		return (float) Math.sqrt(x * x + y * y);
	}

}
