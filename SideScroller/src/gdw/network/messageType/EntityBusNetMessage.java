package gdw.network.messageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.LinkedList;

import gdw.entityCore.Message;
import gdw.network.NetMessageType;

public class EntityBusNetMessage extends NetMessageType
{
	public final int entityID;
	public final Message busMessage;
	
	public EntityBusNetMessage(int entityID, Message msg)
	{
		this.entityID = entityID;
		this.busMessage = msg;
	}
	
	
	public static EntityBusNetMessage[] getFromByteBuffer(ByteBuffer buf)
	{
		EntityBusNetMessage[] result = new EntityBusNetMessage[buf.get()];
		for(int i =0;i<result.length;++i)
		{
			int entityID = buf.getInt();
			byte [] arr = new byte[buf.get()];
			buf.get(arr);
			ObjectInputStream inStream = null;
			try 
			{
				inStream = new ObjectInputStream(new ByteArrayInputStream(arr));
				result[i] = new EntityBusNetMessage(entityID, (Message)inStream.readObject());
			} catch (IOException | ClassNotFoundException e) 
			{
				e.printStackTrace();
				return null;
			}
		}	
		return result;
	}


	public static void fillInByteBuffer(LinkedList<EntityBusNetMessage> list,ByteBuffer buf)
	{
		buf.put(NetMessageType.EntityBusMessageType);
		ByteBuffer helper = ByteBuffer.allocate(buf.remaining()-Byte.SIZE);
		byte counter = 0;
		while(!list.isEmpty())
		{
			try
			{
				EntityBusNetMessage msg = list.peek();
				byte [] arr;
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    ObjectOutputStream os = null;
				try 
				{
					os = new ObjectOutputStream(baos);
				    os.writeObject(msg.busMessage);
				} catch (IOException e) {
					e.printStackTrace();
				}
				arr = baos.toByteArray();
				buf.putInt(msg.entityID);
				buf.put((byte)arr.length);
				buf.put(arr);
			}catch (IndexOutOfBoundsException e)
			{
				break;
			}
			//hochzählen durchgrang erfolgreich
			counter++;
			list.remove();
		}
		buf.put(counter);
		helper.flip();
		buf.put(helper);
	}
}
