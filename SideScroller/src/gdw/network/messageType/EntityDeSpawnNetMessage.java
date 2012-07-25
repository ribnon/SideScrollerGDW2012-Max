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
	
	public static void fillInByteBuffer(LinkedList<EntityDeSpawnNetMessage> list, ByteBuffer buf)
	{
		buf.put(NetMessageType.EntityDespawnMessageType);
		ByteBuffer helper = ByteBuffer.allocate(buf.remaining()-Byte.SIZE);
		byte counter = 0;
		while(!list.isEmpty())
		{
			try
			{
				EntityDeSpawnNetMessage msg = list.peek();
				buf.putInt(msg.entityID);
			}catch (IndexOutOfBoundsException e)
			{
				break;
			}
			list.remove();
			counter++;
			
		}
		buf.put(counter);
		helper.flip();
		buf.put(helper);
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
