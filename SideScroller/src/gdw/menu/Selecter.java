package gdw.menu;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Selecter
{
	private static Image arrowLeft, arrowRight;
	private static Image invalidIDImage;
	private static boolean isInitialized = false;

	private int currentSelectionID = -1;
	private ArrayList<Image> images = new ArrayList<Image>();
	private String name = "";
	private int lineHeight = 16;

	public Selecter()
	{
		try
		{
			if (!isInitialized)
			{
				arrowLeft = new Image("assets/menu/arrow_left_bucket.png");
				arrowRight = new Image("assets/menu/arrow_right_bucket.png");
				invalidIDImage = new Image("assets/enemy_brocoli_bad.png");
				isInitialized = true;
			}
		} catch (SlickException e)
		{
			e.printStackTrace();
		}
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void addImage(Image img)
	{
		images.add(img);
	}

	public void draw(GameContainer container, Graphics graphics, int offsetX,
			int offsetY, float scale)
	{
		lineHeight = graphics.getFont().getLineHeight();
		int textPadding = lineHeight / 8;

		Image drawImage = invalidIDImage;
		if (currentSelectionID >= 0 && currentSelectionID < images.size())
			drawImage = images.get(currentSelectionID);

		arrowLeft.draw(offsetX, offsetY + drawImage.getHeight()*scale / 2, scale);
		drawImage.draw(offsetX + arrowLeft.getWidth()*scale, offsetY, scale);
		arrowRight.draw(offsetX + arrowLeft.getWidth()*scale + drawImage.getWidth()*scale,
				offsetY + drawImage.getHeight()*scale / 2, scale);
		int nameWidth = graphics.getFont().getWidth(name);
		graphics.drawString(name, offsetX + getWidth()*scale / 2 - nameWidth / 2,
				offsetY + drawImage.getHeight()*scale + textPadding);
	}

	public int getWidth()
	{
		Image drawImage = invalidIDImage;
		if (currentSelectionID >= 0 && currentSelectionID < images.size())
			drawImage = images.get(currentSelectionID);
		return arrowLeft.getWidth() + drawImage.getWidth() + arrowRight.getWidth();
	}

	public int getHeight()
	{
		Image drawImage = invalidIDImage;
		if (currentSelectionID >= 0 && currentSelectionID < images.size())
			drawImage = images.get(currentSelectionID);
		return drawImage.getHeight() + lineHeight + lineHeight/8;
	}
}
