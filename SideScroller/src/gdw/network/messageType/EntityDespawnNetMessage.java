package gdw.network.messageType;


import java.nio.ByteBuffer;
import java.util.LinkedList;

import gdw.network.NetMessageType;

public class EntityDespawnNetMessage extends NetMessageType
{
	public int entityID;
	
	public EntityDespawnNetMessage(int id)
	{
		this.entityID = id;
	}
	
	public static void fillInByteBuffer(LinkedList<EntityDespawnNetMessage> list, ByteBuffer buf)
	{
		buf.put(NetMessageType.EntityDespawnMessageType);
		buf.put((byte)list.size());
		for(EntityDespawnNetMessage msg : list)
		{
			buf.putInt(msg.entityID);
		}
	}

	
	public static EntityDespawnNetMessage[] getFromByteBuffer(ByteBuffer buf)
	{
		EntityDespawnNetMessage [] result = new EntityDespawnNetMessage[buf.get()];
		for(int i=0;i<result.length;++i)
		{
			result [i] = new EntityDespawnNetMessage(buf.getInt());
		}
		return result;
	}
}
