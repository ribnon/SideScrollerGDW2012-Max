package gdw.entityCore;

import java.util.HashMap;

public abstract class ComponentTemplate {
	private HashMap<String, String> params;
	
	protected ComponentTemplate(HashMap<String, String> params){
		this.params=params;
	}
	
	public abstract Component createComponent();
	
	public String getStringParam(String name){
		return getStringParam(name, "");
	}
	public int getIntegerParam(String name){
		return getIntegerParam(name, 0);
	}
	public float getFloatParam(String name){
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
	public boolean isThingOnly(){
		return false;
	}
}
