package gdw.entityCore;

import java.util.HashMap;

public abstract class ComponentTemplate {
	private HashMap<String, String> params;
	
	protected ComponentTemplate(HashMap<String, String> params){
		this.params=params;
	}
	
	public abstract Component createComponent();
	
	public String getStringParam(String name){
		System.err.println("Warnung " + name + " verwendet alten Param-Getter ohne Default-Wert. " +
			"Bitte neue Version mit Default-Werten benutzen.");
		return getStringParam(name, "");
	}
	public int getIntegerParam(String name){
		System.err.println("Warnung " + name + " verwendet alten Param-Getter ohne Default-Wert. " +
				"Bitte neue Version mit Default-Werten benutzen.");
		return getIntegerParam(name, 0);
	}
	public float getFloatParam(String name){
		System.err.println("Warnung " + name + " verwendet alten Param-Getter ohne Default-Wert. " +
				"Bitte neue Version mit Default-Werten benutzen.");
		return getFloatParam(name, 0.0f);
	}
	public String getStringParam(String name,String defaultValue){
		if(params.containsKey(name)){
			return params.get(name);
		}
		else{
			return defaultValue;
		}
	}
	public int getIntegerParam(String name,int defaultValue){
		if(params.containsKey(name)){
			try {
				return Integer.parseInt(params.get(name));
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		else{
			return defaultValue;
		}
	}
	public float getFloatParam(String name,float defaultValue){
		if(params.containsKey(name)){
			try {
				return Float.parseFloat(params.get(name));
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		}
		else{
			return defaultValue;
		}
	}
	public EntityReference getEntityReferenceParam(String name){
		if(params.containsKey(name)){
			String val = params.get(name).trim();
			if(val.charAt(0)=='@'){
				return new NamedEntityReference(val.substring(1));
			}
			else{
				try {
					return new StaticEntityReference(Integer.parseInt(val));
				} catch (NumberFormatException e) {
					return new StaticEntityReference(-1);
				}
			}
		}
		else{
			return new StaticEntityReference(-1);
		}
	}
	public boolean isThingOnly(){
		return false;
	}
}
