package gdw.physics;

import java.util.ArrayList;

public class SimulationComponentManager {
	
	private static SimulationComponentManager manager = null;
	
	private ArrayList<SimulationComponent> simulationList;
	
	private float lastDeltaTime;
	
	private float gravity;
	
	private SimulationComponentManager() {
		
		simulationList = new ArrayList<SimulationComponent>();
		lastDeltaTime = 0.0f;
		gravity = 0.0f;
		manager = this;
	}
	
	public static SimulationComponentManager getInstance() {
		if(manager==null) {
			manager = new SimulationComponentManager();
		}
		return manager;
	}
	
	public static void destroy() {
		manager.simulationList.clear();
		manager = null;
	}
	
	public void addSimulationComponent(SimulationComponent comp) {
		simulationList.add(comp);
	}
	
	public void removeSimulationComponent(SimulationComponent comp) {
		simulationList.remove(comp);
	}
	
	public float getDeltaTime() {
		return lastDeltaTime;
	}
	
	public void setGravity(float g) {
		gravity = g;
	}
	
	public float getGravity() {
		return gravity;
	}
	
	public void simulate(float deltaTime) {
		lastDeltaTime = deltaTime;
		
		for(SimulationComponent sim : simulationList) {
			if(sim.isActive()) {
//				if(!sim.isGrounded())
				sim.addForce(0, gravity*sim.getMass());
				sim.simulate(lastDeltaTime);
			}
		}
		
	}
	
}
