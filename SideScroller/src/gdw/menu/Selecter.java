package gdw.menu;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public abstract class Selecter
{
	private static Image arrowLeft, arrowRight;
	private static Image invalidIDImage;
	private static boolean isInitialized = false;

	private int currentHatID = -1;
	private int disabledHatID = -1;
	private Image baseImage = null;
	private ArrayList<Image> hatImages = new ArrayList<Image>();
	private String name = "";
	private int lineHeight = 16;
	private boolean modifiable = false;
	private int leftArrowStartX, leftArrowStartY, leftArrowSizeX,
			leftArrowSizeY;
	private int rightArrowStartX, rightArrowStartY, rightArrowSizeX,
			rightArrowSizeY;

	public Selecter()
	{
		try
		{
			baseImage = new Image("assets/spritesheets/singleImages/teddy.png");
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
		hatImages.add(img);
	}

	public void draw(GameContainer container, Graphics graphics, int offsetX,
			int offsetY, float scale)
	{
		lineHeight = graphics.getFont().getLineHeight();
		int textPadding = lineHeight / 8;

		Image drawImage = invalidIDImage;
		if (baseImage != null)
			drawImage = baseImage;

		if (modifiable)
		{
			leftArrowStartX = offsetX;
			leftArrowStartY = (int) (offsetY + drawImage.getHeight() * scale
					/ 2);
			leftArrowSizeX = (int) (arrowLeft.getWidth() * scale);
			leftArrowSizeY = (int) (arrowLeft.getHeight() * scale);
			arrowLeft.draw(leftArrowStartX, leftArrowStartY, scale);

			rightArrowStartX = (int) (offsetX + arrowLeft.getWidth() * scale + drawImage
					.getWidth() * scale);
			rightArrowStartY = (int) (offsetY + drawImage.getHeight() * scale
					/ 2);
			rightArrowSizeX = (int) (arrowRight.getWidth() * scale);
			rightArrowSizeY = (int) (arrowRight.getHeight() * scale);
			arrowRight.draw(rightArrowStartX, rightArrowStartY, scale);
		}
		drawImage.draw(offsetX + arrowLeft.getWidth() * scale, offsetY, scale);
		if (currentHatID >= 0 && currentHatID < hatImages.size())
			hatImages.get(currentHatID).draw(
					offsetX + arrowLeft.getWidth() * scale, offsetY, scale);

		int nameWidth = graphics.getFont().getWidth(name);
		graphics.drawString(name, offsetX + getWidth() * scale / 2 - nameWidth
				/ 2, offsetY + drawImage.getHeight() * scale + textPadding);
	}

	public int getWidth()
	{
		return arrowLeft.getWidth() + baseImage.getWidth()
				+ arrowRight.getWidth();
	}

	public int getHeight()
	{
		return baseImage.getHeight() + lineHeight + lineHeight / 8;
	}

	public void setHatImageIndex(int index)
	{
		currentHatID = index;
		notifyPeerOfHatChange(currentHatID);
	}

	public int getImageIndex()
	{
		return currentHatID;
	}

	public void setModifiable(boolean m)
	{
		modifiable = m;
	}

	public void handleClick(int button, int x, int y, int clickCount)
	{
		if (!modifiable)
			return;

		if (x > leftArrowStartX && y > leftArrowStartY
				&& x < leftArrowStartX + leftArrowSizeX
				&& y < leftArrowStartY + leftArrowSizeY)
			changeToPreviousHat();
		if (x > rightArrowStartX && y > rightArrowStartY
				&& x < rightArrowStartX + rightArrowSizeX
				&& y < rightArrowStartY + rightArrowSizeY)
			changeToNextHat();
	}
	private void changeToPreviousHat()
	{
		--currentHatID;
		if (hatImages.size() > 0)
		{
			currentHatID %= hatImages.size();
			if (currentHatID < 0)
				currentHatID = hatImages.size() + currentHatID;
			if (currentHatID == disabledHatID && hatImages.size() > 2)
				changeToPreviousHat();
		}
		notifyPeerOfHatChange(currentHatID);
	}
	private void changeToNextHat()
	{
		++currentHatID;
		if (hatImages.size() > 0)
		{
			currentHatID %= hatImages.size();
			if (currentHatID == disabledHatID && hatImages.size() > 2)
				changeToNextHat();
		}
		notifyPeerOfHatChange(currentHatID);
	}

	public void setBaseImage(Image img)
	{
		baseImage = img;
	}
	
	public void disableHatID(int id)
	{
		disabledHatID = id;
	}
	
	public abstract void notifyPeerOfHatChange(int newHatID);
}
