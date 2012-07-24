import java.nio.ByteBuffer;

import gdwNet.client.BasicClient;
import gdwNet.client.ServerInfo;

import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

public class JoinButton extends MouseOverArea {
	private ServerInfo serverInfo;
	private boolean wasDown = false;
	private int count = 0;
	
	
	public JoinButton(int posX, int posY, int width, int height, Image img,
			GUIContext gc, ServerInfo si) {
		super(gc, img, posX, posY, width, height);
		serverInfo = si;
	}

	public void mouseReleased(int button, int mx, int my) {
		if (button == 0  && !wasDown) {
			count++;
			ByteBuffer b = ByteBuffer.allocate(1);
			b.clear();
			System.out.println("Button "+button+" gedückt," + count);
			// BasicClient.connectToServer(serverInfo, b);
			wasDown = true;
			return;
		}
		else
		{
			if(button == 0 && wasDown)
			{
				count++;
				wasDown = false;
				System.out.println("flanke "+ count);
			}
		}
		
	}

}
