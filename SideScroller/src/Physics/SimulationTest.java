package Physics;

import java.util.HashMap;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class SimulationTest extends BasicGame {

	SimulationComponentTemplate temp;
	SimulationComponent comp;
	
	float posX, posY;
	
	public SimulationTest() {
		super("SimTest");
		// TODO Auto-generated constructor stub
		
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("mass", "1.0");
		params.put("friction","0.0");
		
		
		
		temp = new SimulationComponentTemplate(params);
		
		comp = new SimulationComponent(temp);
	}

	@Override
	public void render(GameContainer arg0, Graphics g) throws SlickException {
		// TODO Auto-generated method stub
		g.drawOval(posX, posY, 10, 10, 32);
		g.drawString(comp.isActive()+"", 10, 80);
		g.drawString(comp.getVelocityX()+"", 10, 100);
		g.drawString(comp.getVelocityY()+"", 10, 140);
	}

	@Override
	public void init(GameContainer arg0) throws SlickException {
		// TODO Auto-generated method stub
		comp.addForce(10, 0);
	}

	@Override
	public void update(GameContainer arg0, int arg1) throws SlickException {
		// TODO Auto-generated method stub
		final float forcePower = 10.f;
		Input inp = arg0.getInput();
		if(inp.isKeyDown(Input.KEY_A)) {
			comp.addForce(-forcePower, 0);
		}
		if(inp.isKeyDown(Input.KEY_D)) {
			comp.addForce(+forcePower, 0);
		}
		if(inp.isKeyDown(Input.KEY_W)) {
			comp.addForce(0, -forcePower);
		}
		if(inp.isKeyDown(Input.KEY_S)) {
			comp.addForce(0, forcePower);
		}
		
		comp.simulate(16/1000.f);
		posX += comp.getVelocityX() * 16/1000.f;
		posY += comp.getVelocityY() * 16/1000.f;
		
	}
	
	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new SimulationTest());
		app.start();
	}
}
