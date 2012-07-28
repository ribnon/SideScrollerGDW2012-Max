package gdw.control.messageType;

import gdw.entityCore.Message;

public class RunMessage extends InputMessage {
	private final boolean forward;

	public RunMessage(boolean forward) {
		this.forward = forward;
	}

	public boolean isForwardDirection() {
		return forward;
	}
}
