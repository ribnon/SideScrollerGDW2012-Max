package gdw.network.messageType;

import java.nio.ByteBuffer;

import gdw.network.NetMessageType;

public class TimeSyncMessage extends NetMessageType
{
	public final long timeStamp;
	public final int clientID;
	
	public TimeSyncMessage(long timeStamp, int clientID)
	{
		this.timeStamp = timeStamp;
		this.clientID = clientID;
	}
	
	public static void fillInByteBuffer(TimeSyncMessage item,ByteBuffer buf, int playerID)
	{
		buf.put(NetMessageType.TimeSyncMessageType);
		buf.putLong(item.timeStamp);
		buf.putInt(playerID);
	}
	
	public static TimeSyncMessage getFromByteBuffer(ByteBuffer buf)
	{
		return new TimeSyncMessage(buf.getLong(), buf.getInt());
	}
}
