package gdw.astroids.input;

import org.newdawn.slick.Input;

import gdw.astroids.components.EngineComponent;
import gdw.control.messageType.RunMessage;
import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;
import gdw.physics.SimulationComponent;
import gdw.physics.SimulationComponentManager;

public class AstroidsInputComponent extends Component {

	int leftKey, rightKey, upKey, downKey;
	int shootKey;

	public AstroidsInputComponent(ComponentTemplate template) {
		super(template);
		AstroidsInputComponentTemplate t = (AstroidsInputComponentTemplate) template;
		leftKey = t.leftKey;
		rightKey = t.rightKey;
		upKey = t.upKey;
		downKey = t.downKey;
		shootKey = t.shootKey;
		
		
		//register at manager
		AstroidsInputComponentManager.getInstance().setInput(this);
	}

	//same as PlayerInput
	public static final int COMPONENT_TYPE = 3;
	
	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}

	
	public void onMessage(Message msg) {
	}


	public void handleInput(Input input) {
		Component comp = this.getOwner().getComponent(SimulationComponent.COMPONENT_TYPE);
		if(comp==null)
			return;
		SimulationComponent simComp = (SimulationComponent) comp;
		
		comp = this.getOwner().getComponent(EngineComponent.COMPONENT_TYPE);
		if(comp==null)
			return;
		EngineComponent engComp = (EngineComponent)comp;
		
		if(input.isKeyDown(downKey)) {
			float angle = (float)Math.toRadians(this.getOwner().getOrientation());
//			System.out.println(angle+"");
			float cosAngle = (float)Math.cos(angle);
			float sinAngle = (float)Math.sin(angle);
//			System.out.println("move down");
			simComp.addForce(-sinAngle*(engComp.getBreakPower()), cosAngle*(engComp.getBreakPower()));
		}
		if(input.isKeyDown(leftKey)) {
//			System.out.println("move left");
//			simComp.addForce(-engComp.getPower(), 0.0f);
			getOwner().setOrientation(getOwner().getOrientation()-engComp.getRotation()*SimulationComponentManager.getInstance().getDeltaTime());
		}
		if(input.isKeyDown(rightKey)) {
//			System.out.println("move right");
//			simComp.addForce(engComp.getPower(), 0.0f);
			getOwner().setOrientation(getOwner().getOrientation()+engComp.getRotation()*SimulationComponentManager.getInstance().getDeltaTime());
		}
		if(input.isKeyDown(upKey)) {
			float angle = (float)Math.toRadians(this.getOwner().getOrientation());
//			System.out.println(angle+"");
			float cosAngle = (float)Math.cos(angle);
			float sinAngle = (float)Math.sin(angle);
			
//			System.out.println("move up");
			simComp.addForce(-sinAngle*(-engComp.getPower()), cosAngle*(-engComp.getPower()));
//			simComp.addForce(engComp.getPower()*(float)Math.cos(angle), -engComp.getPower()*(float)Math.sin(angle));
		}
		if(input.isKeyDown(shootKey)) {
//			System.out.println("shoot");
		}
	}
}