package gdw.control;

import gdw.entityCore.Message;

public class RunMessage extends Message {
	private final boolean forward;

	public RunMessage(boolean forward) {
		this.forward = forward;
	}

	public boolean isForward() {
		return forward;
	}
}
