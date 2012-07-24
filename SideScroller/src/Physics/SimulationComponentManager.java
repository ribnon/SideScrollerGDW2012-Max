package Physics;

import java.util.ArrayList;

public class SimulationComponentManager {
	
	private static SimulationComponentManager manager = null;
	
	private ArrayList<SimulationComponent> simulationList;
	
	private SimulationComponentManager() {
		
		simulationList = new ArrayList<SimulationComponent>();
		
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
		
	}
	
}
