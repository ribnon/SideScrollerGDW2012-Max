package gdw.gameplay.levelObjects;

import gdw.entityCore.Component;
import gdw.entityCore.ComponentTemplate;

public class SwitchUserComponent extends Component {

	private boolean hitTrigger;

	private boolean pullActive;

	public static final int COMPONENT_TYPE = 15;

	public SwitchUserComponent(ComponentTemplate template, boolean hitTrigger) {
		this(template, hitTrigger, false);
	}

	public SwitchUserComponent(ComponentTemplate template, boolean hitTrigger,
			boolean pullActive) {
		super(template);
		this.hitTrigger = hitTrigger;
		this.pullActive = pullActive;
	}

	public boolean gethitTrigger() {
		return hitTrigger;
	}

	public void sethitTrigger(boolean hitTrigger) {
		this.hitTrigger = hitTrigger;
	}

	public boolean getpullActive() {
		return pullActive;
	}

	public void setpullActive(boolean pullActive) {
		this.pullActive = pullActive;
	}

	@Override
	public int getComponentTypeID() {
		return COMPONENT_TYPE;
	}

}
