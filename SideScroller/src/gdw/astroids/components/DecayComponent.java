package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class DecayComponent extends Component {

	private String decayIn;
	
	public String getDecayIn() {
		return decayIn;
	}

	public void setDecayIn(String decayIn) {
		this.decayIn = decayIn;
	}

	public DecayComponent(ComponentTemplate template) {
		super(template);
		DecayComponentTemplate t = (DecayComponentTemplate)template;
		
		decayIn = t.decayIn;
	}
	public static final int COMPONENT_TYPE = 1010;
	@Override
	public int getComponentTypeID() {
		// TODO Auto-generated method stub
		return COMPONENT_TYPE;
	}
	
	@Override
	public void onMessage(Message msg) {
		super.onMessage(msg);
	}

}
