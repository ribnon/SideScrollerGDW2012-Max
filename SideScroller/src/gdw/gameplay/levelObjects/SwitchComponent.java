package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.EntityReference;

public class SwitchComponent extends Component {

	private EntityReference targetEntity;

	private SwitchType type;

	private enum SwitchType {
		Hit, Step, Pull
	}

	private boolean contactFlag, activeFlag;

	public static final int COMPONENT_TYPE = 16;

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}
}
