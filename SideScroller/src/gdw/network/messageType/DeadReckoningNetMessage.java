package gdw.network.messageType;

import java.nio.ByteBuffer;

import gdw.network.NetMessageType;

public class DeadReckoningNetMessage extends NetMessageType
{
	//TODO eventuell rounttipzeit mit eigenen nachrichten usw machen
	public final float posX;
	public final float posY;
	public final float velocityX;
	public final float velocityY;
	public final int entityID;
	
	
	
	private DeadReckoningNetMessage(ByteBuffer buf)
	{
		this.posX = buf.getFloat();
		this.posY = buf.getFloat();
		this.velocityX = buf.getFloat();
		this.velocityY = buf.getFloat();
	}
	
	public static DeadReckoningNetMessage getFromByteBuffer(ByteBuffer buf)
	{
		return new DeadReckoningNetMessage(buf);
	}
	
	public void fillInByteBuffer(ByteBuffer buf)
	{
		buf.put(NetMessageType.DeadReckoningMessageType);
		buf.putFloat(posX);
		buf.putFloat(posY);
		buf.putFloat(velocityX);
		buf.putFloat(velocityY);
	}
}
