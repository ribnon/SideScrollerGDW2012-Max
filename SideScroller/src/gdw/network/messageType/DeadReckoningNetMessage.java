package gdw.network.messageType;

import java.nio.ByteBuffer;

import gdw.network.NetMessageType;

public class DeadReckoningNetMessage extends NetMessageType
{
	//TODO eventuell roundtripzeit mit eigenen nachrichten usw machen
	public final int entityID;
	public final int sequenceID;
	public final float posX;
	public final float posY;
	public final float velocityX;
	public final float velocityY;

	private DeadReckoningNetMessage(ByteBuffer buf)
	{
		this.entityID = buf.getInt();
		this.sequenceID = buf.getInt();
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
		buf.putInt(entityID);
		buf.putInt(sequenceID);
		buf.putFloat(posX);
		buf.putFloat(posY);
		buf.putFloat(velocityX);
		buf.putFloat(velocityY);
	}
}
