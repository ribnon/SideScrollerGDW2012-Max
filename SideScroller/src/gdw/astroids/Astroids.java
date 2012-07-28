package gdw.astroids;

import java.io.IOException;

import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Level;
import gdw.graphics.SpriteManager;
import gdw.physics.SimulationComponentManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Astroids extends BasicGame {

	public Astroids() {
		super("Astroids");
	}

	@Override
	public void render(GameContainer container, Graphics g)
			throws SlickException {
		SpriteManager.getInstance().render();
		CollisionDetectionComponentManager.getInstance().render(g);
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		EntityManager.getInstance().setOfflineMode(true);
		Level.getInstance().start();
		EntityTemplateManager etm = EntityTemplateManager.getInstance();
		try {
			etm.loadEntityTemplates("astroids/Astroids.templates");
			EntityManager.getInstance().loadEntities("astroids/Astroids.ent");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		SimulationComponentManager.getInstance().setGravity(0.0f);

	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException {
		SimulationComponentManager.getInstance().simulate(delta/1000.f);
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Astroids());
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}
