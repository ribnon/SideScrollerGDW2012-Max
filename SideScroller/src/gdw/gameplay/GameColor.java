package gdw.gameplay;

import org.newdawn.slick.Color;

public class GameColor {

	private boolean red;
	private boolean yellow;
	private boolean blue;

	private static final Color RED = new Color(255, 0, 0);
	private static final Color YELLOW = new Color(255, 255, 0);
	private static final Color BLUE = new Color(0, 0, 255);
	private static final Color ORANGE = new Color(255, 166, 0);
	private static final Color PINK = new Color(166, 0, 255);
	private static final Color GREEN = new Color(0, 255, 0);
	private static final Color WHITE = new Color(255, 255, 255);
	private static final Color BLACK = new Color(0, 0, 0);

	public GameColor(boolean r, boolean g, boolean b) 
	{
		red = r;
		yellow = g;
		blue = b;
	}

	public GameColor() 
	{
		this(false, false, false);
	}

	public boolean getRed() 
	{
		return red;
	}

	public boolean getBlue() 
	{
		return blue;
	}

	public boolean getYellow()
	{
		return yellow;
	}

	public void mix(GameColor gc) 
	{
		this.red = this.red || gc.getRed();
		this.yellow = this.yellow || gc.getYellow();
		this.blue = this.blue || gc.getBlue();
	}

	public Color convertToGraphicsColor() 
	{
		if (!red && !yellow && !blue)
			return WHITE;
		else if (red && !yellow && !blue)
			return RED;
		else if (!red && yellow && !blue)
			return YELLOW;
		else if (red && yellow && !blue)
			return ORANGE;
		else if (!red && !yellow && blue)
			return BLUE;
		else if (red && !yellow && blue)
			return PINK;
		else if (!red && yellow && blue)
			return GREEN;
		else
			return BLACK;

	}
}
