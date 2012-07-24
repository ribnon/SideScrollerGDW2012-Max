package gdw.network.messageType;

import java.nio.ByteBuffer;

import gdw.network.NetMessageType;
import gdw.utils.DefaultCharSet;

public class EntitySpawnNetMessage extends NetMessageType
{
	public final String templateName;
	public final int id;
	public final float posX;
	public final float posY;
	public final float orientation;
	
	public EntitySpawnNetMessage(String templateName, int id, float posX,
			float posY, float orientation)
	{
		this.templateName = templateName;
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.orientation = orientation;
	}
	
	public static void fillInByteBuffer(EntitySpawnNetMessage item,ByteBuffer buf)
	{
		buf.put(NetMessageType.EntitySpawnMessageType);
		
		byte[] stringArr = item.templateName.getBytes(DefaultCharSet.getDefaultCharset());
		buf.put((byte) stringArr.length);
		buf.put(stringArr);
		buf.putInt(item.id);
		buf.putFloat(item.posX);
		buf.putFloat(item.posY);
		buf.putFloat(item.orientation);	
				
	}

	public static EntitySpawnNetMessage getFromByteBuffer(ByteBuffer buf)
	{
	
		byte[] stringArr = new byte[buf.get()];
		buf.get(stringArr);
		String templateName = new String(stringArr, DefaultCharSet.getDefaultCharset());
		int id = buf.getInt();
		float posX = buf.getFloat();
		float posY = buf.getFloat();
		float ori = buf.getFloat();
		return new EntitySpawnNetMessage(templateName, id, posX, posY, ori);
	}
}
