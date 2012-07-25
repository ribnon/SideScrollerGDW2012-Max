package Physics;

import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;

import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import collisionDetection.AABoxCollisionDetectionComponent;
import collisionDetection.CircleCollisionDetectionComponent;
import collisionDetection.CollisionDetectionComponent;
import collisionDetection.CollisionDetectionComponentManager;


public class SimulationTest extends BasicGame {
	EntityManager entityManager;
	
	Entity entity1;
	Entity wall;
	
	public SimulationTest() {
		super("SimTest");
//		
//		size = 10.0f;
//		
//		temp = new SimulationComponentTemplate(params);
//		
//		comp = new SimulationComponent(temp);
//		comp.setMass(1.0f);
//		comp.setFriction(0.2f);
//		
//		CollisionDetectionComponentManager colMng = CollisionDetectionComponentManager.getInstance();
//		
//		AABoxCollisionDetectionComponent box1 = new AABoxCollisionDetectionComponent(null);
//		box1.setHalfExtentX(size);
//		box1.setHalfExtentY(size);
//		colMng.registerAABoxCollisionDetectionComponent(box1);

		
		HashMap<String, String> simParams = new HashMap<String, String>();
		simParams.put("mass", "1.0");
		simParams.put("friction","0.2");
		
		entityManager = EntityManager.getInstance();
		
		HashMap<String, HashMap<String, String> > compParamMap = new HashMap<String, HashMap<String,String>>();
		compParamMap.put("SimulationComponent", simParams);
		
		
		HashMap<String, String> colParams = new HashMap<String, String>();
		colParams.put("halfExtentX", "10.0");
		colParams.put("halfExtentY", "10.0");
		colParams.put("radius", "25.0");
		
//		compParamMap.put("AABoxCollisionDetectionComponent",colParams);
		compParamMap.put("CircleCollisionDetectionComponent", colParams);
		
		
		
		EntityTemplate entity = new EntityTemplate("Ball", null, compParamMap);
		entity1 = entity.createEntity(50, 50, 0);
		wall = entity.createEntity(400, 300, 0);
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		drawEntity(g, entity1);
		drawEntity(g, wall);
		g.drawString(simComp.isActive()+"", 10, 80);
		g.drawString(simComp.getVelocityX()+"", 10, 100);
		g.drawString(simComp.getVelocityY()+"", 10, 140);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		simComp.addForce(10, 0);
	}

	//debug draw for entities
	public void drawEntity(Graphics g, Entity e) {
		g.drawString(""+e.getID(), e.getPosX()-4, e.getPosY()-8);
		CollisionDetectionComponent colComp = (CollisionDetectionComponent) e.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		if(colComp!=null) {
			g.setColor(Color.pink);
			if(colComp instanceof AABoxCollisionDetectionComponent) {
				AABoxCollisionDetectionComponent box = (AABoxCollisionDetectionComponent) colComp;
				g.drawRect(e.getPosX() - box.getHalfExtentX(), e.getPosY() - box.getHalfExtentY(), 2*box.getHalfExtentX(), 2*box.getHalfExtentY());
			}
			if(colComp instanceof CircleCollisionDetectionComponent) {
				CircleCollisionDetectionComponent circle = (CircleCollisionDetectionComponent) colComp;
				g.drawOval(e.getPosX()-circle.getRadius(), e.getPosY() - circle.getRadius(), 2*circle.getRadius(), 2*circle.getRadius(),32);
			}
			g.setColor(Color.white);
		}
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		// TODO Auto-generated method stub
		float forcePower = 10.f; 
		Input inp = arg0.getInput();
		if(inp.isKeyDown(Input.KEY_LSHIFT))
			forcePower *= 0.1f;
		if(inp.isKeyDown(Input.KEY_A)) {
			simComp.addForce(-forcePower, 0);
		}
		if(inp.isKeyDown(Input.KEY_D)) {
			simComp.addForce(+forcePower, 0);
		}
		if(inp.isKeyDown(Input.KEY_W)) {
			simComp.addForce(0, -forcePower);
		}
		if(inp.isKeyDown(Input.KEY_S)) {
			simComp.addForce(0, forcePower);
		}
		
		
		CollisionDetectionComponentManager.getInstance().detectCollisionsAndNotifyEntities();
		SimulationComponentManager.getInstance().simulate(16/1000.f);
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SimulationTest());
		app.start();
	}
}
