package gdw.entityCore;

import java.util.HashMap;

public class NamedEntityReference extends EntityReference {
	private static HashMap<String, Integer> keys;
	private String key;
	
	public NamedEntityReference(String key) {
		this.key = key;
	}
	@Override
	public int getID() {
		if(keys.containsKey(key)){
			return keys.get(key);
		}
		else return -1;
	}
	public static void setEntityID(String key, int id){
		keys.put(key, id);
	}
}
