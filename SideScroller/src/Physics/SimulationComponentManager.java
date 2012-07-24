package Physics;

import java.util.ArrayList;

public class SimulationComponentManager {
	
	private static SimulationComponentManager manager = null;
	
	private ArrayList<SimulationComponent> simulationList;
	
	private float lastDeltaTime;
	
	private SimulationComponentManager() {
		
		simulationList = new ArrayList<SimulationComponent>();
		lastDeltaTime = 0.0f;
		manager = this;
	}
	
	public static SimulationComponentManager get() {
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
	
	public void simulate(float deltaTime) {
		lastDeltaTime = deltaTime;
		
		for(SimulationComponent sim : simulationList) {
			if(sim.isActive())
				sim.simulate(deltaTime);
		}
		
	}
	
}
