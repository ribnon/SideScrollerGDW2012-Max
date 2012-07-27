package gdw.gameplay.levelObjects;

import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;

import java.io.IOException;
import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class PathFollowTest extends BasicGame
{
	// EntityManager entityManager;

	Entity entity1;

	public PathFollowTest()
	{
		super("Path Follow Test");
		EntityManager.getInstance().setOfflineMode(true);

		try
		{
			EntityTemplateManager.getInstance().loadEntityTemplates(
					"src/gdw/gameplay/levelObjects/PathFollowTest.txt");
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		HashMap<String, String> foo = new HashMap<String, String>();
//		foo.put("wayPoints", "300;100 200;500 500;300 100;300 400;500");
//		foo.put("speed", "2");
		entity1 = EntityTemplateManager.getInstance()
				.getEntityTemplate("Mein_Testtemplate")
				.createEntity(300, 100, 0);
//		HashMap<String, HashMap<String, String>> bar = new HashMap<String, HashMap<String,String>>();
//		bar.put("PathFollow", foo);
//		EntityTemplate et = new EntityTemplate("Foo", null, bar);
//		entity1 = et.createEntity(300, 100, 0);
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException
	{
		g.drawRect(entity1.getPosX(), entity1.getPosY(), 2, 2);
		//300;100 200;500 500;300 100;300 400;500
		g.drawRect(300, 100, 2, 2);
		g.drawRect(200, 500, 2, 2);
		g.drawRect(500, 300, 2, 2);
		g.drawRect(100, 300, 2, 2);
		g.drawRect(400, 500, 2, 2);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException
	{
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException
	{
//		EntityManager.getInstance().
		entity1.getComponent(PathFollowComponent.COMPONENT_TYPE).tick(
				arg1 / 1000.0F);
	}

	public static void main(String[] args) throws SlickException
	{
		AppGameContainer app = new AppGameContainer(new PathFollowTest());
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}