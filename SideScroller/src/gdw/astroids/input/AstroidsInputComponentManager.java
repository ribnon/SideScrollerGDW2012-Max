package gdw.astroids.input;

import org.newdawn.slick.Input;

import gdw.control.PlayerInputComponentManager;

public class AstroidsInputComponentManager {
	
	private static AstroidsInputComponentManager instance = null;
	private AstroidsInputComponent inputComponent = null;
	
	public static AstroidsInputComponentManager getInstance() {
		if (instance == null) {
			instance = new AstroidsInputComponentManager();
		}
		return instance;
	}
	
	private AstroidsInputComponentManager() {
		
	}
	
	public void setInput(AstroidsInputComponent cmp) {
		inputComponent = cmp;
	}
	
	public void proceedInput(Input input) {
		inputComponent.handleInput(input);
	}
}
