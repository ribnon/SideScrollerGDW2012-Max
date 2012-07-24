package Physics;

import java.util.ArrayList;

public class SimulationComponentManager {
	
	private static SimulationComponentManager manager = null;
	
	private ArrayList<SimulationComponent> simulationList;
	
	private SimulationComponentManager() {
		
		simulationList = new ArrayList<SimulationComponent>();
		
		manager = this;
	}
	
	public static SimulationComponentManager initializeSimulationComponentManager() {
		if(manager==null) {
			manager = new SimulationComponentManager();
		}
		return manager;
	}
	public static SimulationComponentManager get() {
		return initializeSimulationComponentManager();
	}
	
	public void addSimulationComponent(SimulationComponent comp) {
		simulationList.add(comp);
	}
	
	public void removeSimulationComponent(SimulationComponent comp) {
		simulationList.remove(comp);
	}
	
	
	
	
}
