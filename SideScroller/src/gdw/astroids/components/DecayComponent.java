package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class DecayComponent extends Component {

	private String decayIn;
	private float averagePieces;
	private float[] spawnRange;
	
	
	public float[] getSpawnRange() {
		return spawnRange;
	}

	public void setSpawnRange(float[] spawnRange) {
		this.spawnRange = spawnRange;
	}

	public float getAveragePieces() {
		return averagePieces;
	}

	public void setAveragePieces(float averagePieces) {
		this.averagePieces = averagePieces;
	}

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
		averagePieces = t.averagePieces;
		spawnRange = new float[] {
				t.spawnRange[0],
				t.spawnRange[1]
		};
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
