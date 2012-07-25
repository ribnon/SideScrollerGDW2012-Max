package Physics;

import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;

import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import collisionDetection.AABoxCollisionDetectionComponent;


public class SimulationTest extends BasicGame {
	EntityManager entityManager;
	
	Entity entity1;
	
	public SimulationTest() {
		super("SimTest");
		// TODO Auto-generated constructor stub
		
		HashMap<String, String> simParams = new HashMap<String, String>();
		simParams.put("mass", "1.0");
		simParams.put("friction","0.0");
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

		entityManager = EntityManager.getInstance();
		
		HashMap<String, HashMap<String, String> > compParamMap = new HashMap<String, HashMap<String,String>>();
		compParamMap.put("SimulationComponent", simParams);
		
		
		HashMap<String, String> colParams = new HashMap<String, String>();
		colParams.put("halfExtentX", "10.0");
		colParams.put("halfExtentY", "10.0");
		
		
		compParamMap.put("AABoxCollisionDetectionComponent",colParams);
		
		
		EntityTemplate entity = new EntityTemplate("Ball", null, compParamMap);
		entity.createEntity(50, 50, 0);
		
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		AABoxCollisionDetectionComponent colComp = (AABoxCollisionDetectionComponent) entity1.getComponent(AABoxCollisionDetectionComponent.COMPONENT_TYPE);
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		g.drawRect(entity1.getPosX(), entity1.getPosY(), colComp.getHalfExtentX(), colComp.getHalfExtentY());
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

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		// TODO Auto-generated method stub
		final float forcePower = 10.f;
		Input inp = arg0.getInput();
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
		
		simComp.addForce(0.0f, -9.81f);
		
//		SimulationComponentManager.getInstance().simulate(16/1000.f);
		float newPosX = entity1.getPosX() + simComp.getVelocityX() * 16/1000.f;
		float newPosY = entity1.getPosY() + simComp.getVelocityY() * 16/1000.f;
		
		
		entity1.setPos(newPosX, newPosY);
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SimulationTest());
		app.start();
	}
}
