package gdw.network.messageType;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import gdw.network.NetMessageType;

public class EntityBusNetMessage extends NetMessageType
{
	//an alle senden
	public final Message busMessage;
	public final int entityID;
	
	private EntityBusNetMessage(Message msg)
	{
		this.busMessage = msg;
	}
	
	
	public static EntityBusNetMessage getFromByteBuffer(ByteBuffer buf)
	{
		byte [] arr = new byte[buf.get()];
		buf.get(arr);
		ObjectInputStream inStream = new ObjectInputStream(new ByteArrayInputStream(arr));
		return new EntityBusNetMessage((Message)inStream.readObject());
	}

	@Override
	public void fillInByteBuffer(ByteBuffer buf)
	{
		byte [] arr;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(baos);
	    os.writeObject(this.busMessage);
	    
	    arr = baos.toByteArray();
	    buf.put(NetMessageType.EntityBusMessageType);
	    buf.put((byte)arr.length);
	    buf.put(arr);
	}
}
