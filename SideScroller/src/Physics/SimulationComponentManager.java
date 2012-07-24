package Physics;

import java.util.ArrayList;

public class SimulationComponentManager {
	
	private static SimulationComponentManager manager = null;
	
	private ArrayList<SimulationComponent> simulationList;
	
	private SimulationComponentManager() {
		
		simulationList = new ArrayList<SimulationComponent>();
		
		manager = this;
	}
	
	public SimulationComponentManager initializeSimulationComponentManager() {
		if(manager==null) {
			manager = new SimulationComponentManager();
		}
		return manager;
	}
	
	
	
}
