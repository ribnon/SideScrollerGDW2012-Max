package gdw.entityCore;

import gdw.network.NetSubSystem;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class MyTiledMap extends TiledMap {

	public MyTiledMap(String ref) throws SlickException {
		super(ref,EntityManager.getInstance().isOfflineMode() || !NetSubSystem.getInstance().isServer());
	}

	public ArrayList<String> getObjectPropertyNames(int groupID, int objectID) {
		Enumeration<?> e = getObjectPropertyNamesInternal(groupID, objectID);
		ArrayList<String> res = new ArrayList<String>();
		if(e == null)
			return res;
		while(e.hasMoreElements()){
			Object o=e.nextElement();
			String s=(String)o;
			res.add(s);
		}
		return res;
	}
	
	private Enumeration<?> getObjectPropertyNamesInternal(int groupID, int objectID) {
		if (groupID >= 0 && groupID < objectGroups.size()) {
			ObjectGroup grp = (ObjectGroup) objectGroups.get(groupID);
			if (objectID >= 0 && objectID < grp.objects.size()) {
				GroupObject object = (GroupObject) grp.objects.get(objectID);

				if (object == null) {
					return null;
				}
				if (object.props == null) {
					return null;
				}

				return object.props.propertyNames();
			}
		}
		return null;
	}
}
