package gdw.network.messageType;

import java.nio.ByteBuffer;
import java.util.LinkedList;

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
	
	public static void fillInByteBuffer(LinkedList<EntitySpawnNetMessage> list,ByteBuffer buf)
	{		
		buf.put(NetMessageType.EntitySpawnMessageType);
		//was noch platz gelassen hat und anzahl
		ByteBuffer helper = ByteBuffer.allocate(buf.remaining()-Byte.SIZE);
		byte counter = 0;
		while(!list.isEmpty())
		{
			try
			{
				EntitySpawnNetMessage item = list.peek();
				
				byte[] stringArr = item.templateName.getBytes(DefaultCharSet.getDefaultCharset());
				helper.put((byte) stringArr.length);
				helper.put(stringArr);
				helper.putInt(item.id);
				helper.putFloat(item.posX);
				helper.putFloat(item.posY);
				helper.putFloat(item.orientation);	
				counter++;
				list.remove();
			}catch (IndexOutOfBoundsException e)
			{
				break;
			}
		}
		buf.put(counter);
		helper.flip();
		buf.put(helper);				
	}

	public static EntitySpawnNetMessage[] getFromByteBuffer(ByteBuffer buf)
	{
		EntitySpawnNetMessage[] result = new EntitySpawnNetMessage[buf.get()];
		for(int i=0;i<result.length;++i)
		{
			byte[] stringArr = new byte[buf.get()];
			buf.get(stringArr);
			String templateName = new String(stringArr, DefaultCharSet.getDefaultCharset());
			int id = buf.getInt();
			float posX = buf.getFloat();
			float posY = buf.getFloat();
			float orientation = buf.getFloat();
			result[i] = new EntitySpawnNetMessage(templateName, id, posX, posY, orientation);
		}
				
		return result;
	}
}
