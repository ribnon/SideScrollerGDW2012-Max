package gdw.network.messageType;


import java.nio.ByteBuffer;
import java.util.LinkedList;

import gdw.network.NetMessageType;

public class EntityDeSpawnNetMessage extends NetMessageType
{
	public int entityID;
	
	public EntityDeSpawnNetMessage(int id)
	{
		this.entityID = id;
	}
	
	public static void fillInByteBuffer(LinkedList<EntityDeSpawnNetMessage> list, ByteBuffer buf, int maxAmount)
	{
		buf.put(NetMessageType.EntityDespawnMessageType);
		if(list.size() > maxAmount)
		{
			buf.put((byte)maxAmount);
		}else
		{
			buf.put((byte)list.size());
		}
		for(int i=0;i<maxAmount;++i)
		{
			EntityDeSpawnNetMessage msg = list.poll();
			if(msg == null)
			{
				return;
			}
			buf.putInt(msg.entityID);
		}
	}

	
	public static EntityDeSpawnNetMessage[] getFromByteBuffer(ByteBuffer buf)
	{
		EntityDeSpawnNetMessage [] result = new EntityDeSpawnNetMessage[buf.get()];
		for(int i=0;i<result.length;++i)
		{
			result [i] = new EntityDeSpawnNetMessage(buf.getInt());
		}
		return result;
	}
}
