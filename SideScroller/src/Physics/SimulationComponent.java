package Physics;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import collisionDetection.AABoxCollisionDetectionComponent;
import collisionDetection.CollisionDetectionComponent;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Entity;

public class SimulationComponent extends Component {

	private static final float TOLERANCE = 0.01f;

	public static final int COMPONENT_TYPE = 2;

	private float velocityX;
	private float velocityY;
	private float accelerationX;
	private float accelerationY;
	
	// Mass of Entity, if 0 - object is unmoveable (Infinite mass)
	private float mass;
	private float friction;
	private float externalForceX;
	private float externalForceY;
	private boolean active;
	
	// TODO: test code
	private boolean grounded;
	private Entity ground;

	public boolean isGrounded() {
		return grounded;
	}


	public void setGrounded(boolean grounded) {
		this.grounded = grounded;
	}

	// Debug draw method
	public void draw(Graphics g) {
		Entity owner = this.getOwner();
		
		g.setColor(Color.green);
		g.drawLine(owner.getPosX(), owner.getPosY(), owner.getPosX()+velocityX, owner.getPosY()+velocityY);
		g.setColor(Color.red);
		g.drawLine(owner.getPosX(), owner.getPosY(), owner.getPosX()+accelerationX, owner.getPosY()+accelerationY);
		g.setColor(Color.white);
	}
	
	
	public SimulationComponent(ComponentTemplate template) {
		super(template);
		// copy blueprint values
		SimulationComponentTemplate tmp = null;
		try {
			tmp = (SimulationComponentTemplate) template;
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
		if (tmp == null) {
			System.out
					.println("Creating SimulationComponent from NonSimulationTemplate");
			return;
		}
		velocityX = tmp.getVelocityX();
		velocityY = tmp.getVelocityY();

		accelerationX = tmp.getAccelerationX();
		accelerationY = tmp.getAccelerationY();

		mass = tmp.getMass();

		friction = tmp.getFriction();

		externalForceX = tmp.getExternalForceX();
		externalForceY = tmp.getExternalForceY();

		// default value
		active = true;
		
		SimulationComponentManager mng = SimulationComponentManager.getInstance();
		mng.addSimulationComponent(this);
	}

	protected void destroy() {
		SimulationComponentManager manager = SimulationComponentManager.getInstance();
		manager.removeSimulationComponent(this);
	}

	public void addForce(float x, float y) {
		this.externalForceX += x;
		this.externalForceY += y;
		this.active = true;
	}

	public float getVelocityX() {
		return velocityX;
	}

	public float getVelocityY() {
		return velocityY;
	}

	public void setVelocity(float x, float y) {
		this.velocityX = x;
		this.velocityY = y;
		this.active = true;
	}

	public float getAccelerationX() {
		return accelerationX;
	}

	public void setAccelerationX(float accelerationX) {
		this.accelerationX = accelerationX;
		this.active = true;
	}

	public float getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(float accelerationY) {
		this.accelerationY = accelerationY;
		this.active = true;
	}

	public float getMass() {
		return mass;
	}

	public void setMass(float mass) {
		this.mass = mass;
	}

	public float getFriction() {
		return friction;
	}

	public void setFriction(float friction) {
		this.friction = friction;
	}

	public float getExternalForceX() {
		return externalForceX;
	}

	public void setForce(float x, float y) {
		this.externalForceX = x;
		this.externalForceY = y;
		this.active = true;
	}

	public float getExternalForceY() {
		return externalForceY;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setVelocityX(float velocityX) {
		this.velocityX = velocityX;
		this.active = true;
	}

	public void setVelocityY(float velocityY) {
		this.velocityY = velocityY;
		this.active = true;
	}

	public void setExternalForceX(float externalForceX) {
		this.externalForceX = externalForceX;
		this.active = true;
	}

	public void setExternalForceY(float externalForceY) {
		this.externalForceY = externalForceY;
		this.active = true;
	}

	public void resetForce() {
		this.externalForceX = 0.f;
		this.externalForceY = 0.f;
	}

	public void simulate(float deltaTime) {
		if (mass <= 0.0f) {// Unmoveable object
			resetForce();
			
			
			return;
		}
		
		if(grounded = isOnB(ground)) {
			addForce(0, -SimulationComponentManager.getInstance().getGravity()*mass);
		}
		
		float forceX = this.externalForceX - (this.friction * this.velocityX)*deltaTime;
		float forceY = this.externalForceY - (this.friction * this.velocityY)*deltaTime;
		
//		float forceX = this.externalForceX;
//		float forceY = this.externalForceY;
		
		
		
		this.accelerationX = forceX / this.mass;
		this.accelerationY = forceY / this.mass;
		
		boolean shouldSleep = false;
		
		boolean accelerationNulled = false;
		if (accelerationNulled=(Math.abs(accelerationX) < TOLERANCE)) {
			accelerationX = 0.0f;
		}
		if (accelerationNulled&=(Math.abs(accelerationY) < TOLERANCE)) {
			accelerationY = 0.0f;
		}
		
		resetForce();

		float newVelocityX = this.velocityX + this.accelerationX * deltaTime;
		newVelocityX -= this.friction * newVelocityX * deltaTime;
		float newVelocityY = this.velocityY + this.accelerationY * deltaTime;
		newVelocityY -= this.friction * newVelocityY * deltaTime;

		boolean veloXNulled = false;
		
		
		if (veloXNulled=(Math.abs(velocityX) < TOLERANCE)) {
			velocityX = 0.0f;
		}
		if (Math.abs(velocityY) < TOLERANCE) {
			velocityY = 0.0f;
			
			shouldSleep = veloXNulled&&accelerationNulled;
		}
		this.velocityX = newVelocityX;
		this.velocityY = newVelocityY;
		
		
		float posX = this.getOwner().getPosX() + velocityX*deltaTime;
		float posY = this.getOwner().getPosY() + velocityY*deltaTime;
		
		this.getOwner().setPos(posX, posY);
		
		if(shouldSleep) 
			active = false;
	}

	public boolean isOnB(Entity B) {
		if(B == null)
			return false;
		CollisionDetectionComponent colCompA =(CollisionDetectionComponent) this.getOwner().getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		CollisionDetectionComponent colCompB =(CollisionDetectionComponent) B.getComponent(CollisionDetectionComponent.COMPONENT_TYPE);
		if(colCompA!=null && colCompB!=null) {
			if(colCompB instanceof AABoxCollisionDetectionComponent
			&& colCompA instanceof AABoxCollisionDetectionComponent) {
				AABoxCollisionDetectionComponent AAcolCompB = (AABoxCollisionDetectionComponent) colCompB;
				AABoxCollisionDetectionComponent AAcolCompA = (AABoxCollisionDetectionComponent) colCompA;
				float x = B.getPosY() - AAcolCompB.getHalfExtentY() - this.getOwner().getPosY() - AAcolCompA.getHalfExtentY();
//				System.out.println(x+"");
				if(Math.abs(this.getOwner().getPosX() - B.getPosX()) < AAcolCompB.getHalfExtentX()
				&&	Math.abs(x) < 0.1f) {
					ground = B;
					return true;
				}
			}
		}
		ground=null;
		return false;
	}
	
	
	@Override
	public int getComponentTypeID() {
		return SimulationComponent.COMPONENT_TYPE;
	}
}
