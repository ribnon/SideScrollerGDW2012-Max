package gdw.control;

import gdw.network.NetSubSystem;

import java.util.LinkedList;

import org.newdawn.slick.Input;

public class PlayerInputComponentManager {
	private static PlayerInputComponentManager instance = null;
	private static LinkedList<PlayerInputComponent> playerInpComponents;

	public static PlayerInputComponentManager getInstance() {
		if (instance == null) {
			instance = new PlayerInputComponentManager();
		}
		return instance;
	}

	private PlayerInputComponentManager() {
		playerInpComponents = new LinkedList<PlayerInputComponent>();
	}

	public void registerPlayerInputComponent(PlayerInputComponent comp) {
		playerInpComponents.add(comp);
	}

	public boolean deregisterPlayerInputComponent(PlayerInputComponent comp) {
		return playerInpComponents.remove(comp);
	}

	public void sendInputToPlayerInputComponents(Input input) {
		for (PlayerInputComponent currentcomponent : playerInpComponents) {
			if (currentcomponent.getPlayerID() == NetSubSystem.getInstance()
					.getPlayerID())
				currentcomponent.processingInput(input);
		}
	}
}
