package gdw.network;

import java.nio.ByteBuffer;

public interface INetworkBridge
{
	public void pollNetInput();
	
	public ByteBuffer getMessageBuffer();
	
	public void sendMessage(ByteBuffer msg, boolean reliable);
}
