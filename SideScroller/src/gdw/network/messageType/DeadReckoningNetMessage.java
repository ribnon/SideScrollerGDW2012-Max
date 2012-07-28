package gdw.network.messageType;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.util.LinkedList;

import gdw.network.NetMessageType;
import gdw.network.server.GDWServerLogger;

public class DeadReckoningNetMessage extends NetMessageType
{
	public final int entityID;
	public final int sequenceID;
	public final float posX;
	public final float posY;
	public final float velocityX;
	public final float velocityY;
	public final float roundTipTime;
	
	//messagecode, highlevelcode
	public final static int ROUNDTIP_WRITE_POSITION = Byte.SIZE+Byte.SIZE+Byte.SIZE;
	

	public DeadReckoningNetMessage(int id, int seqID, float posX, float posY, float veloX, float veloY, float roundTip)
	{
		this.entityID = id;
		this.sequenceID = seqID;
		this.posX = posX;
		this.posY = posY;
		this.velocityX = veloX;
		this.velocityY = veloY;
		this.roundTipTime = roundTip;
	}
	
	private DeadReckoningNetMessage(ByteBuffer buf,float roundTip)
	{
		this.entityID = buf.getInt();
		this.sequenceID = buf.getInt();
		this.posX = buf.getFloat();
		this.posY = buf.getFloat();
		this.velocityX = buf.getFloat();
		this.velocityY = buf.getFloat();
		this.roundTipTime = roundTip;
		
		GDWServerLogger.logMSG("posx:"+this.posX+" posY: "+this.posY);
	}
	
	public static DeadReckoningNetMessage[] getFromByteBuffer(ByteBuffer buf)
	{
		int length = buf.get();
		GDWServerLogger.logMSG("deadReck anzahl:"+length);
		DeadReckoningNetMessage [] arr = new DeadReckoningNetMessage[length];
		float roundTip = buf.getFloat();
		for(int i=0;i<length;++i)
		{
			arr[i] = new DeadReckoningNetMessage(buf,roundTip);
		}
		return arr;
	}
	
	/**
	 * Im sry...                                               
                                                                                            
                                                  
                `````````.`                       
               .`````.``````.                     
              ````````````````` ,                 
             .````````````````.,``                
            .```````````.``````.``,               
            ```````````````.``.````               
            ``````````....`.```````.              
           .````````.``````.```````,              
           .```````.@@+`````@,`````,              
           .```````# #@`````@+ .+```              
           .``````.: `@+```,@@;,.:`.              
           .``````:`#@@@```:@@#; @;.              
           .`````` `,@@+````++., #``              
           .``````. .;;:````;::` .;.              
            ``````@ `;;``.```:`  ,`.              
            .`````.`  ``.````````.`.              
            .`````.``````````````.``              
             ``````````````..```.`..              
             .````.````````````.``..              
              ````` `.````````.````.              
              `````   ...```..```.``              
               ````.  .``````.`````               
               ````..```````````.`.               
                .```..````````````.               
                ..``.```````.``.`.                
                 ```.```````.`.```                
                 `.`.```````.````.                
              `. ..`.````````.```                 
             .` .`.`.``````````.`                 
             .. .```.```````.```.                 
             .````...`````````.``                 
              ````.`..````````.`                  
               ...`...```,````.` ``               
                ```...```,``````  `               
                ``....```,``.```. .               
                .`...````,``.`.```.               
                 ....````,```..``.                
                 `...````,````..                  
                 ....````,````..                  
                 ....````.````..                  
           .`````....````.`````.                  
          .``  `.....````.`````.                  
          `      ...`````.`````.                  
          .      ...`````.`````.                  
            `    ...`````.`````.                  
                   .`````.``````                  
                   `.````..```..                  
                                                                                                                                                                                                                    
          
	*/
	
	public static void fillInByteBuffer(LinkedList<DeadReckoningNetMessage> list,ByteBuffer buf)
	{
		buf.put(NetMessageType.DeadReckoningMessageType);
		ByteBuffer helper = ByteBuffer.allocate(buf.remaining()-(Byte.SIZE+Float.SIZE));
		byte counter = 0;
		while(!list.isEmpty())
		{
			DeadReckoningNetMessage msg = list.peek();
			GDWServerLogger.logMSG("in byteBuffer wird geschrieben posX: "+msg.posX+" "+msg.posY);
			try
			{
				helper.putInt(msg.entityID);
				helper.putInt(msg.sequenceID);
				helper.putFloat(msg.posX);
				helper.putFloat(msg.posY);
				helper.putFloat(msg.velocityX);
				helper.putFloat(msg.velocityY);
			}catch(IndexOutOfBoundsException e)
			{
				break;
			}catch (BufferOverflowException e)
			{
				break;
			}
			counter++;
			list.remove();
		}
		buf.put(counter);
		buf.putFloat(0.0f);//placeHolder
		helper.flip();
		buf.put(helper);
	}
}
