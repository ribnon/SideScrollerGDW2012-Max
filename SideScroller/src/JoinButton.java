import java.nio.ByteBuffer;

import javax.naming.ldap.BasicControl;

import gdwNet.client.BasicClient;
import gdwNet.client.ServerInfo;

import org.newdawn.slick.Image;
import org.newdawn.slick.gui.GUIContext;
import org.newdawn.slick.gui.MouseOverArea;

public class JoinButton extends MouseOverArea {
	private ServerInfo serverInfo;

	public JoinButton(int posX, int posY, int width, int height, Image img,
			GUIContext gc, ServerInfo si) {
		super(gc, img, posX, posY, width, height);
		serverInfo = si;
	}

	public void mouseReleased(int button, int mx, int my) {
		if(mx < getX() || mx > getX()+getWidth() || my < getY() || my > getY()+getHeight())
			return;
		ByteBuffer b = ByteBuffer.allocate(1);
		b.clear();
		System.out.println("Button " + button + " gedückt");
		BasicClient.connectToServer(serverInfo, b);
		return;

	}

}
