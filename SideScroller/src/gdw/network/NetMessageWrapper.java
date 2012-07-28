package gdw.network;

import java.nio.ByteBuffer;

public class NetMessageWrapper
{
	public final boolean reliable;
	
	public final ByteBuffer msg;

	public NetMessageWrapper(boolean reliable, ByteBuffer msg)
	{
		super();
		this.reliable = reliable;
		
		msg.position(msg.capacity());
		msg.flip();
		this.msg = msg;
	}
}
