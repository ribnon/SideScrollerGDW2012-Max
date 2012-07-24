package gdw.network.messageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

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
	
	
	public static EntityBusNetMessage getFromByteBuffer(ByteBuffer buf)
	{
		int entityID = buf.getInt();
		byte [] arr = new byte[buf.get()];
		buf.get(arr);
		ObjectInputStream inStream = null;
		EntityBusNetMessage result = null;
		try 
		{
			inStream = new ObjectInputStream(new ByteArrayInputStream(arr));
			result = new EntityBusNetMessage(entityID, (Message)inStream.readObject());
		} catch (IOException | ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		return result;
	}


	public static void fillInByteBuffer(EntityBusNetMessage msg,ByteBuffer buf)
	{
		byte [] arr;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream os = null;
		try {
			os = new ObjectOutputStream(baos);
		    os.writeObject(msg.busMessage);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    arr = baos.toByteArray();
	    buf.put(NetMessageType.EntityBusMessageType);
	    buf.putInt(msg.entityID);
	    buf.put((byte)arr.length);
	    buf.put(arr);
	}
}
