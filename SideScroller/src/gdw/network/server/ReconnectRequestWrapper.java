package gdw.network.server;


public class ReconnectRequestWrapper
{
	public final ConnectionInfo info;
	public final long timestamp;

	public ReconnectRequestWrapper(ConnectionInfo info)
	{
		this.info = info;
		this.timestamp = System.currentTimeMillis();
	}
	
}
