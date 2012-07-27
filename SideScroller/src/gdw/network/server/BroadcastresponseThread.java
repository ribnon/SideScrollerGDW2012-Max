package gdw.network.server;

import gdw.network.NETCONSTANTS;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ProtocolFamily;
import java.net.StandardProtocolFamily;
import java.nio.channels.DatagramChannel;


public class BroadcastresponseThread extends Thread
{
	private final DatagramChannel socket;
	private final BasicServer ref;
	private boolean close;
	public final int boundedPort;

	public BroadcastresponseThread( BasicServer ref) throws IOException
	{
		this.socket = DatagramChannel.open();
		this.socket.bind(null);
		this.boundedPort = this.socket.socket().getLocalPort();
		this.socket.configureBlocking(true);
		this.ref = ref;
		this.close = false;

		this.socket.socket().setBroadcast(true);
		
		GDWServerLogger.logMSG("broacstsocket ist auf "+this.socket.getLocalAddress()+" gebunden");

		this.start();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run()
	{
		while (!close)
		{


			DatagramPacket packet = new DatagramPacket(new byte[0], 0);

			try
			{
				this.socket.socket().receive(packet);
				
				byte [] buf = this.ref.getBroadcastResponse().array();
				DatagramPacket responceDatagramPacket = new DatagramPacket(
						buf, buf.length, packet.getAddress(),
						packet.getPort());

				this.socket.socket().send(responceDatagramPacket);

			} catch (IOException e)
			{
				e.printStackTrace();
				this.close = true;

			}
			GDWServerLogger.logMSG("Broadcastresponce gesendet");
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getBoundedPort()
	{
		return this.socket.socket().getLocalPort();
	}
}
