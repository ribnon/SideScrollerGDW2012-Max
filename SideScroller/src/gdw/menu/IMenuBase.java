package gdw.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public interface IMenuBase
{
	void draw(GameContainer container, Graphics graphics);
	
	void update(GameContainer container, int deltaTime);

	void mouseMoved(int oldx, int oldy, int newx, int newy);

	void mouseWheelMoved(int change);

	void keyReleased(int key, char c);

	void keyPressed(int key, char c);

	void mouseReleased(int button, int x, int y);

	void mousePressed(int button, int x, int y);

	void mouseClicked(int button, int x, int y, int clickCount);
}
