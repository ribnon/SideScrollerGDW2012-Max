package collisionDetection;

public class CollisionDetectionMessage extends Message
{
	private int idCandidate1;
	private int idCandidate2;
	
	public CollisionDetectionMessage(int idCandidate1, int idCandidate2)
	{
		super();
		
		this.idCandidate1 = idCandidate1;
		this.idCandidate2 = idCandidate2;
	}
	
	public int getIDCandidate1()
	{
		return this.idCandidate1;
	}
	
	public int getIDCandidate2()
	{
		return this.idCandidate2;
	}
}
