package gdw.control;

import java.util.ArrayList;

import org.newdawn.slick.Input;

public class PlayerInputComponentManager {
	private static PlayerInputComponentManager instance = null;
	private static ArrayList<PlayerInputComponent> playerInpComponents;

	public static PlayerInputComponentManager getInstance() {
		if (instance == null) {
			instance = new PlayerInputComponentManager();
		}
		return instance;
	}

	private PlayerInputComponentManager() {
		playerInpComponents = new ArrayList<PlayerInputComponent>();
	}

	public void registerPlayerInputComponent(PlayerInputComponent comp) {
		playerInpComponents.add(comp);
	}

	public boolean deregisterPlayerInputComponent(PlayerInputComponent comp) {
		return playerInpComponents.remove(comp);
	}

	public void sendInputToPlayerInputComponents(Input input) {
		for (PlayerInputComponent currentcomponent : playerInpComponents) {
			currentcomponent.processingInput(input);
		}
	}
}
