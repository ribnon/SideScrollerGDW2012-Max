package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;

public class SwitchUserComponent extends Component {

	private boolean hitTrigger;

	private boolean pullActive;

	public static final int COMPONENT_TYPE = 15;

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

}
