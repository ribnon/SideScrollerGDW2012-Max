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
		return null;//TODO Implement
	}
	public int getIntegerParam(String name,int defaultValue){
		return 0;//TODO Implement
	}
	public float getFloatParam(String name,float defaultValue){
		return 0.0f;//TODO Implement
	}
	public boolean isThingOnly(){
		return false;
	}
}
