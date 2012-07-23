package control;

import java.awt.Component;

import org.newdawn.slick.Input;

public class PlayerInputComponent extends Component
{
	private int playerNumber;

	private int downKey, jumpKey, leftKey, rightKey, attackKey, specattackKey;

	private float jumpVelocity, runVelocity;

	private boolean wasDown, wasJump, wasLeft, wasRight, wasAttack,
			wasSpecAttack;

	public static int COMPONENT_TYPE = 3;

	public PlayerInputComponent(ComponentTemplate template, int downKey,
			int jumpKey, int leftKey, int rightKey, int attackKey,
			int specattackKey, int jumpVelocity, int runVelocity)
	{
		super.template = template;

		wasDown = false;
		wasJump = false;
		wasLeft = false;
		wasRight = false;
		wasAttack = false;
		wasSpecAttack = false;

		this.attackKey = attackKey;
		this.specattackKey = specattackKey;
		this.jumpKey = jumpKey;
		this.downKey = downKey;
		this.rightKey = rightKey;
		this.leftKey = leftKey;

		this.jumpVelocity = jumpVelocity;
		this.runVelocity = runVelocity;

		PlayerInputComponentManager.getInstance().registerPlayerInputComponent(
				this);
	}

	public processingInput(Input input)
	{

	}

	public void onMessage(Message msg)
	{

	}

	public int getComponentTypeID()
	{
		return COMPONENT_TYPE;
	}

	public void destroy()
	{
		PlayerInputComponentManager.getInstance()
				.deregisterPlayerInputComponent(this);
	}
}
