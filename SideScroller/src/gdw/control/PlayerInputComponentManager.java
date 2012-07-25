package gdw.control;

import gdw.network.NetSubSystem;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Input;

public class PlayerInputComponentManager {
	private static PlayerInputComponentManager instance = null;
	private static LinkedList<PlayerInputComponent> playerInpComponents;
	private static HashMap<String, Integer> buttonValues;

	public static PlayerInputComponentManager getInstance() {
		if (instance == null) {
			instance = new PlayerInputComponentManager();
		}
		return instance;
	}

	private PlayerInputComponentManager() {
		playerInpComponents = new LinkedList<PlayerInputComponent>();
		buttonValues = new HashMap<String, Integer>();

		buttonValues.put("0", 11);
		buttonValues.put("1", 2);
		buttonValues.put("2", 3);
		buttonValues.put("3", 4);
		buttonValues.put("4", 5);
		buttonValues.put("5", 6);
		buttonValues.put("6", 7);
		buttonValues.put("7", 8);
		buttonValues.put("8", 9);
		buttonValues.put("9", 10);
		buttonValues.put("A", 30);
		buttonValues.put("ADD", 78);
		buttonValues.put("APOSTROPHE", 40);
		buttonValues.put("APPS", 221);
		buttonValues.put("AT", 145);
		buttonValues.put("AX", 150);
		buttonValues.put("B", 48);
		buttonValues.put("BACK", 14);
		buttonValues.put("BACKSLASH", 43);
		buttonValues.put("C", 46);
		buttonValues.put("CAPITAL", 58);
		buttonValues.put("CIRCUMFLEX", 144);
		buttonValues.put("COLON", 146);
		buttonValues.put("COMMA", 51);
		buttonValues.put("CONVERT", 121);
		buttonValues.put("D", 32);
		buttonValues.put("DECIMAL", 83);
		buttonValues.put("DELETE", 211);
		buttonValues.put("DIVIDE", 181);
		buttonValues.put("DOWN", 208);
		buttonValues.put("E", 18);
		buttonValues.put("END", 207);
		buttonValues.put("ENTER", 28);
		buttonValues.put("EQUALS", 13);
		buttonValues.put("ESCAPE", 1);
		buttonValues.put("F", 33);
		buttonValues.put("F1", 59);
		buttonValues.put("F10", 68);
		buttonValues.put("F11", 87);
		buttonValues.put("F12", 88);
		buttonValues.put("F13", 100);
		buttonValues.put("F14", 101);
		buttonValues.put("F15", 102);
		buttonValues.put("F2", 60);
		buttonValues.put("F3", 61);
		buttonValues.put("F4", 62);
		buttonValues.put("F5", 63);
		buttonValues.put("F6", 64);
		buttonValues.put("F7", 65);
		buttonValues.put("F8", 66);
		buttonValues.put("F9", 67);
		buttonValues.put("G", 34);
		buttonValues.put("GRAVE", 41);
		buttonValues.put("H", 35);
		buttonValues.put("HOME", 199);
		buttonValues.put("I", 23);
		buttonValues.put("INSERT", 210);
		buttonValues.put("J", 36);
		buttonValues.put("K", 37);
		buttonValues.put("KANA", 112);
		buttonValues.put("KANJI", 148);
		buttonValues.put("L", 38);
		buttonValues.put("LALT", 56);
		buttonValues.put("LBRACKET", 26);
		buttonValues.put("LCONTROL", 29);
		buttonValues.put("LEFT", 203);
		buttonValues.put("LMENU", 56);
		buttonValues.put("LSHIFT", 42);
		buttonValues.put("LWIN", 219);
		buttonValues.put("M", 50);
		buttonValues.put("MINUS", 12);
		buttonValues.put("MULTIPLY", 55);
		buttonValues.put("N", 49);
		buttonValues.put("NEXT", 209);
		buttonValues.put("NOCONVERT", 123);
		buttonValues.put("NUMLOCK", 69);
		buttonValues.put("NUMPAD0", 82);
		buttonValues.put("NUMPAD1", 79);
		buttonValues.put("NUMPAD2", 80);
		buttonValues.put("NUMPAD3", 81);
		buttonValues.put("NUMPAD4", 75);
		buttonValues.put("NUMPAD5", 76);
		buttonValues.put("NUMPAD6", 77);
		buttonValues.put("NUMPAD7", 71);
		buttonValues.put("NUMPAD8", 72);
		buttonValues.put("NUMPAD9", 73);
		buttonValues.put("NUMPADCOMMA", 179);
		buttonValues.put("NUMPADENTER", 156);
		buttonValues.put("NUMPADEQUALS", 141);
		buttonValues.put("O", 24);
		buttonValues.put("P", 25);
		buttonValues.put("PAUSE", 197);
		buttonValues.put("PERIOD", 52);
		buttonValues.put("POWER", 222);
		buttonValues.put("PRIOR", 201);
		buttonValues.put("Q", 16);
		buttonValues.put("R", 19);
		buttonValues.put("RALT", 184);
		buttonValues.put("RBRACKET", 27);
		buttonValues.put("RCONTROL", 157);
		buttonValues.put("RETURN", 28);
		buttonValues.put("RIGHT", 205);
		buttonValues.put("RMENU", 184);
		buttonValues.put("RSHIFT", 54);
		buttonValues.put("RWIN", 220);
		buttonValues.put("S", 31);
		buttonValues.put("SCROLL", 70);
		buttonValues.put("SEMICOLON", 39);
		buttonValues.put("SLASH", 53);
		buttonValues.put("SLEEP", 223);
		buttonValues.put("SPACE", 57);
		buttonValues.put("STOP", 149);
		buttonValues.put("SUBTRACT", 74);
		buttonValues.put("SYSRQ", 183);
		buttonValues.put("T", 20);
		buttonValues.put("TAB", 15);
		buttonValues.put("U", 22);
		buttonValues.put("UNDERLINE", 147);
		buttonValues.put("UNLABELED", 151);
		buttonValues.put("UP", 200);
		buttonValues.put("V", 47);
		buttonValues.put("W", 17);
		buttonValues.put("X", 45);
		buttonValues.put("Y", 21);
		buttonValues.put("YEN", 125);
		buttonValues.put("Z", 44);
		buttonValues.put("MOUSE_LEFT_BUTTON", 0);
		buttonValues.put("MOUSE_MIDDLE_BUTTON", 2);
		buttonValues.put("MOUSE_RIGHT_BUTTON", 1);
	}

	public void registerPlayerInputComponent(PlayerInputComponent comp) {
		playerInpComponents.add(comp);
	}

	public boolean deregisterPlayerInputComponent(PlayerInputComponent comp) {
		return playerInpComponents.remove(comp);
	}

	public static int getKeyValue(String keyname) {
		return buttonValues.get(keyname);
	}

	/**
	 * Sends the input to the PlayerInputComponent with the right ID
	 * 
	 * @param input
	 */
	public void sendInputToPlayerInputComponents(Input input) {
		for (PlayerInputComponent currentcomponent : playerInpComponents) {
			if (currentcomponent.getPlayerID() == NetSubSystem.getInstance()
					.getPlayerID())
				currentcomponent.processingInput(input);
		}
	}
}
