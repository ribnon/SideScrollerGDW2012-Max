package gdw.network;

import java.nio.ByteBuffer;


public abstract class NetMessageType 
{
	public static final byte DeadReckoningMessageType = 0;
	public static final byte EntityBusMessageType = 1;
	public static final byte EntityMessageType = 2;
	
	public abstract void fillInByteBuffer(ByteBuffer buf);
}
