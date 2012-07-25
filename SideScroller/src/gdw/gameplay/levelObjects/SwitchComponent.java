package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;

public class SwitchComponent extends Component {

	private boolean hitTrigger;

	private boolean pullActive;

	public static final int COMPONENT_TYPE = 16;

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

}
