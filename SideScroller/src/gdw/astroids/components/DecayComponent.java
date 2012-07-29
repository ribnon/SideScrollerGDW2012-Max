package gdw.astroids.components;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;
import gdw.entityCore.Message;

public class DecayComponent extends Component {

	private String[] decayIn;
	private float[] averagePieces;
	private float[][] spawnRange;
	
	
	public float[][] getSpawnRange() {
		return spawnRange;
	}

	public void setSpawnRange(float[][] spawnRange) {
		this.spawnRange = spawnRange;
	}

	public float[] getAveragePieces() {
		return averagePieces;
	}

	public void setAveragePieces(float[] averagePieces) {
		this.averagePieces = averagePieces;
	}

	public String[] getDecayIn() {
		return decayIn;
	}

	public void setDecayIn(String[] decayIn) {
		this.decayIn = decayIn;
	}

	public DecayComponent(ComponentTemplate template) {
		super(template);
		DecayComponentTemplate t = (DecayComponentTemplate)template;
		
		decayIn = new String[t.decayIn.length];
		for(int i=0;i<t.decayIn.length;++i) {
			decayIn[i] = t.decayIn[i];
		}
		
		averagePieces = new float[t.averagePieces.length];
		for(int i=0;i<averagePieces.length;++i) {
			averagePieces[i] = t.averagePieces[i];
		}
		
		spawnRange = new float[t.spawnRange.length][2];
		for(int i=0;i<t.spawnRange.length;++i) {
			spawnRange[i] = new float[] {
					t.spawnRange[i][0],
					t.spawnRange[i][1]
			};
		}
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
