package Physics.testbed;

import gdw.collisionDetection.AABoxCollisionDetectionComponent;
import gdw.collisionDetection.CircleCollisionDetectionComponent;
import gdw.collisionDetection.CollisionDetectionComponent;
import gdw.collisionDetection.CollisionDetectionComponentManager;
import gdw.collisionDetection.OOBoxCollisionDetectionComponent;
import gdw.entityCore.Entity;
import gdw.entityCore.EntityManager;
import gdw.entityCore.EntityTemplate;
import gdw.entityCore.EntityTemplateManager;
import gdw.entityCore.Level;
import gdw.gameplay.color.FadeInComponent;
import gdw.graphics.SpriteManager;
import gdw.graphics.StaticSpriteComponent;
import gdw.physics.SimulationComponent;
import gdw.physics.SimulationComponentManager;
import gdw.network.NetSubSystem;

import java.io.IOException;
import java.util.HashMap;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;



public class SimulationTest extends BasicGame {
	EntityTemplateManager entityTemplateManager = null;
	
	Entity entity1;
	Entity entity2;
	Entity ground;
	Entity wall;
	Entity platform;
	Entity diag;
	
	
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

	
		// Working world without ETM
		
//		HashMap<String, String> simParams = new HashMap<String, String>();
//		simParams.put("mass", "1");
//		simParams.put("friction","0.11");
//		
//		entityManager = EntityManager.getInstance();
//		
//		HashMap<String, HashMap<String, String> > compParamMap = new HashMap<String, HashMap<String,String>>();
//		compParamMap.put("Simulation", simParams);
//		
//		
//		HashMap<String, String> colParams = new HashMap<String, String>();
//		colParams.put("halfExtentX", "10.0");
//		colParams.put("halfExtentY", "10.0");
//		colParams.put("radius", "25.0");
//		
//		compParamMap.put("AABoxCollisionDetection",colParams);
////		compParamMap.put("CircleCollisionDetection", colParams);
//		
//		HashMap<String, String> colReactParams = new HashMap<String, String>();
//		colReactParams.put("impassableFromTop", "1");
//		colReactParams.put("impassableFromSide", "1");
//		
//		compParamMap.put("CollisionReaction", colReactParams);
//		
//		EntityTemplate entity = new EntityTemplate("Ball", null, compParamMap);
//		entity1 = entity.createEntity(50, 50, 0);
////		entity2 = entity.createEntity(400, 300, 0);
//		
//		
//		//generate ground
//		HashMap<String, String> groundSimParams = new HashMap<String, String>();
//		simParams.put("mass", "0.0");
//		simParams.put("friction","0.11");
//		
//		HashMap<String, HashMap<String, String> > groundCompParamMap = new HashMap<String, HashMap<String,String>>();
////		groundCompParamMap.put("Simulation", groundSimParams);
//		
//		
//		HashMap<String, String> groundColParams = new HashMap<String, String>();
//		groundColParams.put("halfExtentX", "300.0");
//		groundColParams.put("halfExtentY", "10.0");
//		colParams.put("radius", "25.0");
//		
//		groundCompParamMap.put("AABoxCollisionDetection",groundColParams);
////		compParamMap.put("CircleCollisionDetection", colParams);
//		
//		HashMap<String, String> groundColReactParams = new HashMap<String, String>();
//		groundColReactParams.put("impassableFromTop", "1");
//		groundColReactParams.put("impassableFromSide", "1");
//		
//		groundCompParamMap.put("CollisionReaction", groundColParams);
//		entity = new EntityTemplate("Ground", null, groundCompParamMap);
//		ground = entity.createEntity(300, 400, 0);
//		
//		
//		//side wall
//		HashMap<String, HashMap<String, String> > wallCompParamMap = new HashMap<String, HashMap<String,String>>();
////		groundCompParamMap.put("Simulation", groundSimParams);
//		
//		
//		HashMap<String, String> wallColParams = new HashMap<String, String>();
//		wallColParams.put("halfExtentX", "10.0");
//		wallColParams.put("halfExtentY", "300.0");
//		wallColParams.put("radius", "25.0");
//		
//		wallCompParamMap.put("AABoxCollisionDetection", wallColParams);
////		compParamMap.put("CircleCollisionDetection", colParams);
//		
//		HashMap<String, String> wallColReactParams = new HashMap<String, String>();
//		wallColReactParams.put("impassableFromTop", "1");
//		wallColReactParams.put("impassableFromSide", "1");
//		
//		wallCompParamMap.put("CollisionReaction", wallColParams);
//		entity = new EntityTemplate("Wall", null, wallCompParamMap);
//		wall = entity.createEntity(300, 400, 0);
//		
//		
//		// platform
//		
//		HashMap<String, HashMap<String, String> > platCompParamMap = new HashMap<String, HashMap<String,String>>();
////		groundCompParamMap.put("Simulation", groundSimParams);
//		
//		
//		HashMap<String, String> platColParams = new HashMap<String, String>();
//		platColParams.put("halfExtentX", "80.0");
//		platColParams.put("halfExtentY", "10.0");
//		platColParams.put("radius", "25.0");
//		
//		platCompParamMap.put("AABoxCollisionDetection", platColParams);
////		compParamMap.put("CircleCollisionDetection", colParams);
//		
//		HashMap<String, String> platColReactParams = new HashMap<String, String>();
//		platColReactParams.put("impassableFromTop", "1");
//		platColReactParams.put("impassableFromSide", "0");
//		
//		platCompParamMap.put("CollisionReaction", platColReactParams);
//		entity = new EntityTemplate("Platform", null, platCompParamMap);
//		platform = entity.createEntity(200, 200, 0);
//		
//		
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		drawEntity(g, entity1);
		drawEntity(g, entity2);
		drawEntity(g, ground);
		drawEntity(g, wall);
		drawEntity(g, platform);
		drawEntity(g, diag);
		if(simComp!=null) {
			g.drawString("is walled: "+simComp.isWalled(), 10, 60);
			g.drawString("is active: "+simComp.isActive(), 10, 80);
			g.drawString("is grounded: "+simComp.isGrounded(), 10, 175);
			g.drawString("vx: "+simComp.getVelocityX(), 10, 100);
			g.drawString("ax: "+simComp.getAccelerationX(), 10, 120);
			g.drawString("vy: "+simComp.getVelocityY(), 10, 140);
			g.drawString("ay: "+simComp.getAccelerationY(), 10, 160);
		}
		g.drawString("Diag Orientation: "+diag.getOrientation(),600,60);
		
		SpriteManager.getInstance().render();
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		
		try {
			EntityManager.getInstance().setOfflineMode(true);
			Level.getInstance().start();
			
			entityTemplateManager = EntityTemplateManager.getInstance();
			EntityManager.getInstance().deleteAllEntities();
			
			
			entityTemplateManager.loadEntityTemplates("src/Physics/testbed/SimulationTestBed.ent");
			
			EntityTemplate entityTemplate = EntityTemplateManager.getInstance().getEntityTemplate("TestBedControllable");
			entity1 = entityTemplate.createEntity(100, 100, 0);
			
			
			entityTemplate = EntityTemplateManager.getInstance().getEntityTemplate("TestBedEnemy");
			entity2 = entityTemplate.createEntity(200, 250, 0);
			
//			entityTemplate = entityTemplateManager.getEntityTemplate("TestBedGround");
//			ground = entityTemplate.createEntity(300, 300, 0);
			
			entityTemplate = entityTemplateManager.getEntityTemplate("TestBedTile");
			ground = entityTemplate.createEntity(100, 240, 0);
			entityTemplate.createEntity(107, 240, 0);
			entityTemplate.createEntity(114, 240, 0);
			entityTemplate.createEntity( 93, 240, 0);
			entityTemplate.createEntity( 86, 240, 0);
			
			
			entityTemplate = entityTemplateManager.getEntityTemplate("TestBedWall");
			wall = entityTemplate.createEntity(300, 300, 0);
			
			platform = entityTemplateManager.getEntityTemplate("TestBedPlatform").createEntity(200, 200, 0);
			
			diag = entityTemplateManager.getEntityTemplate("TestBedDiag").createEntity(500, 150, 45);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SimulationComponentManager.getInstance().setGravity(98.1f);
	}

	//debug draw for entities
	public void drawEntity(Graphics g, Entity e) {
		g.drawString(""+e.getID(), e.getPosX()-4, e.getPosY()-8);
		CollisionDetectionComponent colComp = (CollisionDetectionComponent) e.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		if(colComp!=null) {
			g.setColor(Color.magenta);
			if(colComp instanceof AABoxCollisionDetectionComponent) {
				AABoxCollisionDetectionComponent box = (AABoxCollisionDetectionComponent) colComp;
				g.drawRect(e.getPosX() - box.getHalfExtentX(), e.getPosY() - box.getHalfExtentY(), 2*box.getHalfExtentX(), 2*box.getHalfExtentY());
			}
			if(colComp instanceof CircleCollisionDetectionComponent) {
				CircleCollisionDetectionComponent circle = (CircleCollisionDetectionComponent) colComp;
				g.drawOval(e.getPosX()- circle.getRadius(), e.getPosY() - circle.getRadius(), 2*circle.getRadius(), 2*circle.getRadius(),32);
//				g.drawOval(e.getPosX(), e.getPosY(), 2*circle.getRadius(), 2*circle.getRadius(),32);
			}
			if(colComp instanceof OOBoxCollisionDetectionComponent) {
				OOBoxCollisionDetectionComponent box = (OOBoxCollisionDetectionComponent) colComp;
				float angle = (float)Math.toRadians(e.getOrientation());
				float cosAngle = (float)Math.cos(angle);
				float sinAngle = (float)Math.sin(angle);
				float[] vertices = new float[]{
						
					cosAngle*(- box.getHalfExtentX()) - sinAngle*(- box.getHalfExtentY()),
					sinAngle*(- box.getHalfExtentX()) + cosAngle*(- box.getHalfExtentY()),
					
					cosAngle*(box.getHalfExtentX()) - sinAngle*(-box.getHalfExtentY()),
					sinAngle*(box.getHalfExtentX()) + cosAngle*(- box.getHalfExtentY()),
					
					cosAngle*(box.getHalfExtentX()) - sinAngle*(box.getHalfExtentY()),
					sinAngle*(box.getHalfExtentX()) + cosAngle*(box.getHalfExtentY()),
					
					cosAngle*(-box.getHalfExtentX()) - sinAngle*(box.getHalfExtentY()),
					sinAngle*(-box.getHalfExtentX()) + cosAngle*(box.getHalfExtentY())
				};
				GL11.glColor4f(1, 0, 1, 1);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glBegin(GL11.GL_LINE_LOOP);
				for (int i = 0; i < 8; i+=2) {
					GL11.glVertex2f(e.getPosX()+vertices[i], e.getPosY()+vertices[i+1]);
				}
				GL11.glEnd();
				GL11.glColor4f(1, 1, 1, 1);
				GL11.glEnable(GL11.GL_BLEND);
			}
			g.setColor(Color.red);
			float[] dim = colComp.getDimensions();
			g.drawRect(e.getPosX() - dim[0], e.getPosY() - dim[1], 2*dim[0], 2*dim[1]);
			
			g.setColor(Color.white);
		}
		SimulationComponent simComp = (SimulationComponent) e.getComponent(SimulationComponent.COMPONENT_TYPE);
		if(simComp!=null) {
			simComp.draw(g);
		}
		StaticSpriteComponent sprComp = (StaticSpriteComponent) e.getComponent(StaticSpriteComponent.COMPONENT_TYPE);
		if(sprComp!=null) {
			sprComp.draw(0,0);
		}
		
		
	}
	
	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		SimulationComponent simComp = (SimulationComponent) entity1.getComponent(SimulationComponent.COMPONENT_TYPE);
		Input inp = arg0.getInput();
		float factor = 1.0f; 
		if(inp.isKeyDown(Input.KEY_LSHIFT))
			factor = 0.1f;
		if(simComp!=null) {
			float forcePower = 150.f; 
			
			
			if(inp.isKeyDown(Input.KEY_A)) {
				simComp.addForce(-factor*forcePower, 0);
	//			simComp.setVelocityX(-10);
			}
			if(inp.isKeyDown(Input.KEY_D)) {
				simComp.addForce(+forcePower*factor, 0);
	//			simComp.setVelocityX(10);
			}
			if(inp.isKeyDown(Input.KEY_W)) {
				simComp.addForce(0, -forcePower*factor);
	//			simComp.setVelocityY(-10);
			}
			if(inp.isKeyDown(Input.KEY_S)) {
				simComp.addForce(0, forcePower*factor);
	//			simComp.setVelocityY(10);
			}
		}
		
		if(inp.isKeyDown(Input.KEY_E)) {
			diag.setOrientation(diag.getOrientation()+1*factor);
		}
		if(inp.isKeyDown(Input.KEY_Q)) {
			diag.setOrientation(diag.getOrientation()-1*factor);
		}
		
//		CollisionDetectionComponentManager.getInstance().detectCollisionsAndNotifyEntities();
		SimulationComponentManager.getInstance().simulate(arg1/1000.f);
		FadeInComponent ficmp = (FadeInComponent)entity1.getComponent(FadeInComponent.COMPONENT_TYPE);
		if(ficmp!=null) {
			ficmp.tick(arg1/1000.f);
		}
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SimulationTest());
		app.setDisplayMode(800, 600, false);
		app.start();
	}
}
