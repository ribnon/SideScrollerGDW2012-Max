package collisionDetection;

import java.util.ArrayList;

public class CollisionQuadTree
{
	private CollisionQuadTree[] child;
	private CollisionQuadTree parent;
	private CollisionQuadTree root;
	
	private ArrayList<CollisionQuadTreeRect> rectList;
	
	private int level;
	private int xDimension;
	private int yDimension;
	private int xOffset;
	private int yOffset;
	
	protected CollisionQuadTree(int levelCount, int xDimension, int yDimension)
	{
		this(levelCount, levelCount, xDimension, yDimension, 0, 0, null);
	}
	
	private CollisionQuadTree(int levelCount, int level, int xDimension, int yDimension, int xOffset, int yOffset, CollisionQuadTree parent)
	{
		this.parent = parent;
		this.level = levelCount - level;
		this.xDimension = xDimension;
		this.yDimension = yDimension;
		
		child = new CollisionQuadTree[4];
		rectList = new ArrayList<CollisionQuadTreeRect>();
		
		xDimension /= 2;
		yDimension /= 2;
		
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		
		if (level > 0)
		{	
			child[0] = new CollisionQuadTree(levelCount, level - 1, xDimension, yDimension, xOffset, yOffset, this);
			child[1] = new CollisionQuadTree(levelCount, level - 1, xDimension, yDimension, xOffset + xDimension, yOffset, this);
			child[2] = new CollisionQuadTree(levelCount, level - 1, xDimension, yDimension, xOffset, yOffset + yDimension, this);
			child[3] = new CollisionQuadTree(levelCount, level - 1, xDimension, yDimension, xOffset + xDimension, yOffset + yDimension, this);
		}
	}
	
	protected void insert(CollisionQuadTreeRect rect)
	{
		rect.updateTreeReference(this);
		
		if (child[0] != null)
		{
			if 		(!child[0].testCollisionOnTree(rect)) child[0].insert(rect);
			else if (!child[1].testCollisionOnTree(rect)) child[1].insert(rect);
			else if (!child[2].testCollisionOnTree(rect)) child[2].insert(rect);
			else if (!child[3].testCollisionOnTree(rect)) child[3].insert(rect);
			else rectList.add(rect);
		}
		else rectList.add(rect);
	}
	
	private void insertParent(CollisionQuadTreeRect rect)
	{
		if (parent == null)
			rectList.add(rect);
		else
			parent.insertParent(rect);
	}
	
	protected void updateRect(CollisionQuadTreeRect rect)
	{
		rect.updateFromComponent();
		CollisionQuadTree rectTree = rect.getTreeReference();
		CollisionQuadTree rectChildTree = null;
		
		if(rectTree.child[0] != null)
		{
			if     (!rectTree.child[0].testCollisionOnTree(rect)) rectChildTree = child[0];
			else if(!rectTree.child[1].testCollisionOnTree(rect)) rectChildTree = child[1];
			else if(!rectTree.child[2].testCollisionOnTree(rect)) rectChildTree = child[2];
			else if(!rectTree.child[3].testCollisionOnTree(rect)) rectChildTree = child[3];
			
			if (rectChildTree != null)
			{
				rectTree.rectList.remove(rect);
				rectChildTree.insert(rect);
				return;
			}
		}
		
		if(rectTree.testCollisionOnTree(rect))
		{
			rectTree.rectList.remove(rect);
			rectTree.insertParent(rect);
		}
	}
	
	protected void delete(CollisionQuadTreeRect rect)
	{
		rect.getTreeReference().rectList.remove(rect);
	}
	
	protected ArrayList<CollisionQuadTreeRect> getColliders(CollisionQuadTreeRect rect)
	{
		ArrayList<CollisionQuadTreeRect> colliders = new ArrayList<CollisionQuadTreeRect>();
		
		CollisionQuadTree tree = rect.getTreeReference();
		tree.addLevelToColliderList(rect, colliders);
		tree.getCollidersFromChild(colliders);
		tree.getCollidersFromParent(colliders);
		return colliders;
	}
	
	private void getCollidersFromChild(ArrayList<CollisionQuadTreeRect> colliders)
	{
		if (child[0] != null)
		{
			for (CollisionQuadTreeRect rect : child[0].rectList)
				colliders.add(rect);
			for (CollisionQuadTreeRect rect : child[1].rectList)
				colliders.add(rect);
			for (CollisionQuadTreeRect rect : child[2].rectList)
				colliders.add(rect);
			for (CollisionQuadTreeRect rect : child[3].rectList)
				colliders.add(rect);
			
			child[0].getCollidersFromChild(colliders);
			child[1].getCollidersFromChild(colliders);
			child[2].getCollidersFromChild(colliders);
			child[3].getCollidersFromChild(colliders);
		}
	}
	
	private void getCollidersFromParent(ArrayList<CollisionQuadTreeRect> colliders)
	{
		if (parent != null)
		{
			for (CollisionQuadTreeRect rect : parent.rectList)
				colliders.add(rect);
			
			parent.getCollidersFromParent(colliders);
		}
	}
	
	private void addLevelToColliderList(CollisionQuadTreeRect rect1, ArrayList<CollisionQuadTreeRect> colliders)
	{
		for(CollisionQuadTreeRect rect2 : rectList)
		{
			if (rect1 == rect2) continue;
			colliders.add(rect2);
		}
	}
	
	// Returns true, if collision occurs
	private boolean testCollisionOnTree(CollisionQuadTreeRect rect)
	{
		return rect.testRect(xOffset, yOffset, xDimension, yDimension);
	}
}
