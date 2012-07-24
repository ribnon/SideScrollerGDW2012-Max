package gdw.control;

import gdw.entityCore.Message;

public class RunMessage extends Message {
	private boolean forward;

	public RunMessage(boolean forward) {
		this.forward = forward;
	}
}
