package gdw.network.messageType;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;

import gdw.network.NetMessageType;

public class EntitySpawnNetMessage extends NetMessageType
{
	private final boolean spawn; 
	private final String entiyName;
	
	//templatemanager hole template
	//von Componenttemplate createEntity(int id(server vergibt, [hochzähö], posXY,Orination))
	
	
	private EntitySpawnNetMessage(ByteBuffer buf)
	{
		buf.put(NetMessageType.)
	}

	@Override
	public void fillInByteBuffer(ByteBuffer buf)
	{
		
		
	}
	
	public static EntitySpawnNetMessage getFromByteBuffer(ByteBuffer buf)
	{
		byte [] arr = new byte[buf.get()];
		buf.get(arr);
		ObjectInputStream inStream = new ObjectInputStream(new ByteArrayInputStream(arr));
		return new EntityBusNetMessage((Message)inStream.readObject());
	}
}
