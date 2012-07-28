package gdw.astroids;

import gdw.astroids.components.DestroyableComponent;
import gdw.astroids.input.AstroidsInputComponentManager;
import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.entityCore.Component;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityReference;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.NamedEntityReference;
import gdw.gameplay.shooter.ProjectileComponentManager;
import gdw.graphics.SpriteManager;
import gdw.physics.SimulationComponentManager;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
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
		//CollisionDetectionComponentManager.getInstance().render(g);
		
		NamedEntityReference obj = new NamedEntityReference("Player1");
		
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		EntityManager.getInstance().setOfflineMode(true);
//		Level.getInstance().start();
//		EntityManager.getInstance().deleteAllEntities();
		
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
		
		
		EntityManager.getInstance().tick(delta/1000.f);
		AstroidsInputComponentManager.getInstance().proceedInput(container.getInput());
		
		SimulationComponentManager.getInstance().simulate(delta/1000.f);
		ProjectileComponentManager.getInstance().simulateProjectiles(delta/1000.f);
		
		EntityManager.getInstance().cleanUpEntities();
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new Astroids());
		app.setDisplayMode(800, 600, false);
		app.setShowFPS(false);
		app.start();
	}
}
