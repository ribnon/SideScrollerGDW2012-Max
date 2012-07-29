package gdw.entityCore;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.newdawn.slick.tiled.TiledMap;

public class EntityManager {
	//Singleton-Stuff:
	private static EntityManager instance = null;
	public static EntityManager getInstance(){
		if(instance==null){
			instance = new EntityManager();
		}
		return instance;
	}
	private EntityManager(){
	}
	
	private HashMap<Integer, Entity> entities = new HashMap<Integer, Entity>();
	private int nextID = 1;
	private boolean offlineMode=false;
	
	private boolean inTick = false;
	private HashMap<Integer, Entity> deferredRegistrations = new HashMap<Integer, Entity>();
	
	public boolean isOfflineMode() {
		return offlineMode;
	}
	public void setOfflineMode(boolean offlineMode) {
		this.offlineMode = offlineMode;
	}
	public int getNextID(){
		return nextID++;
	}
	Entity createEntity(int id,float posX, float posY, float orientation, EntityTemplate template){
		Entity ent = new Entity(id, template);
		ent.setPos(posX, posY);
		ent.setOrientation(orientation);
		if(inTick){
			deferredRegistrations.put(id, ent);
		}
		else{
			entities.put(id, ent);
		}
		return ent;
	}
	public Entity getEntity(int id){
		if(deferredRegistrations.containsKey(id)){
			return deferredRegistrations.get(id);
		}
		else if(entities.containsKey(id)){
			return entities.get(id);
		}
		else{
			return null;
		}
	}
	public void loadEntities(String fileName) throws IOException{
		BufferedReader rdr = new BufferedReader(new FileReader(fileName));
		String line=null;
		while((line=rdr.readLine())!=null){
			if(line.length()==0) continue;
			//ByteOrderMark entfernen:
			if(line.charAt(0)==65279){
				line = line.substring(1);
			}
			line = line.trim();
			if(line.length()==0) continue;
			if(line.charAt(0)=='#') continue;
			if(line.startsWith("Entity")){
				String[] tokens = line.split(" ");
				String templateName=null;
				String posXStr=null;
				String posYStr=null;
				String orientationStr=null;
				String entityName=null;
				if(tokens.length == 5){
					templateName=tokens[1];
					posXStr=tokens[2];
					posYStr=tokens[3];
					orientationStr=tokens[4];
				}
				else if(tokens.length == 7){
					templateName=tokens[1];
					posXStr=tokens[2];
					posYStr=tokens[3];
					orientationStr=tokens[4];
					if(tokens[5].equalsIgnoreCase("as")){
						entityName=tokens[6];
					}
				}
				else continue;
				EntityTemplate template = EntityTemplateManager.getInstance().getEntityTemplate(templateName);
				if(template==null) continue;
				try {
					float posX = Float.parseFloat(posXStr);
					float posY = Float.parseFloat(posYStr);
					float orientation = Float.parseFloat(orientationStr);
					Entity ent = template.createEntity(posX, posY, orientation);
					if(entityName!=null){
						NamedEntityReference.setEntityID(entityName, ent.getID());
					}
				} catch (NumberFormatException e) {
					continue;
				}
			}
			else if(line.startsWith("Include")){
				if(line.length()<9) continue;
				if(line.charAt(7)!=' ') continue;
				String includeFileNameStr=line.substring(8);
				if(includeFileNameStr.length()==0) continue;
				try{
					loadEntities(includeFileNameStr);
				}
				catch(IOException e){
					System.err.println("IOException beim Lesen eines Includefiles: " + e.getMessage());
					e.printStackTrace();
				}
			}
			else continue;
		}
	}
	public void loadEntitiesFromLevel(){
		TiledMap map = Level.getInstance().getMap();
		int collisionLayerIndex = map.getLayerIndex("Collision");
		EntityTemplate colBoxTemplate = EntityTemplateManager.getInstance().getEntityTemplate(" CollisionTile <internal> ");
		for(int x=0;x<map.getWidth();++x){
			for(int y=0;y<map.getHeight();++y){
				if(map.getTileId(x, y, collisionLayerIndex)==1){
					float xCoord = Level.getInstance().getTileWidth()*x+Level.getInstance().getTileWidth()*0.5f;
					float yCoord = Level.getInstance().getTileHeight()*y+Level.getInstance().getTileHeight()*0.5f;
					
					colBoxTemplate.createEntity(xCoord, yCoord, 0);
				}
			}
		}
		String mapTemplatesPrefix = "FromMap_";
		int objectGroups = map.getObjectGroupCount();
		for(int og=0;og<objectGroups;++og){
			int groupObjects = map.getObjectCount(og);
			for(int go=0;go<groupObjects;++go){
				String objectName = map.getObjectName(og, go);
				String templateName = mapTemplatesPrefix+objectName;
				String orientationStr = map.getObjectProperty(og, go, "Entity.orientation", "0");
				float orientation=0.0f;
				try {
					orientation = Float.parseFloat(orientationStr);
				} catch (NumberFormatException e) {}
				EntityTemplate entityTemplate = EntityTemplateManager.getInstance().getEntityTemplate(templateName);
				Entity ent = entityTemplate.createEntity(
						map.getObjectX(og, go)+map.getObjectWidth(og, go)*0.5f,
						map.getObjectY(og, go)+map.getObjectHeight(og, go)*0.5f, orientation);
				NamedEntityReference.setEntityID(objectName, ent.getID());
			}
		}
	}
	/*
	 *
	 * .       _,..wWWw--./+'.            _      ,.                          .
  ..wwWWWWWWWWW;ooo;++++.        .ll'  ,.++;
   `'"">wW;oOOOOOO;:++\++.      .lll .l"+++'   ,..
     ,wwOOOOOOOO,,,++++\+++.    lll',ll'++;  ,++;'
    ,oOOOOOOOO,,,,+++++`'++ll. ;lll ll:+++' ;+++'
   ;OOOOOOOOO,,,'++++++++++lll ;lll ll:++:'.+++'
   OOOO;OOO",,"/;++++,+,++++ll`:llllll++++'+++
  OOOO;OO",,'++'+++;###;"-++llX llll`;+++++++'  ,.    .,      _
;O;'oOOO ,'+++\,-:  ###++++llX :l.;;;,--++."-+++++ w":---wWWWWWww-._
;'  /O'"'"++++++' :;";#'+++lllXX,llll;++.+++++++++W,"WWWWWWWWww;""""'`
   ."     `"+++++'.'"''`;'ll;xXXwllll++;--.++++;wWW;xXXXXXXXXXx"Ww.
           .+++++++++++';xXXXXX;Wll"+-"++,'---"-.x""`"lllllllxXXxWWw.
           "---'++++++-;XXXXXXwWWl"++++,"---++++",,,,,,,,,,;lllXXXxWW,
             `'""""',+xXXXXX;wWW'+++++++++;;;";;;;;;;;oOo,,,,,llXXX;WW`
                   ,+xXXXXXwWW"++.++++-.;;+++<'   `"WWWww;Oo,,,llXXX"Ww
                   +xXXX"wwW"+++++'"--'"'  )+++     `WWW"WwOO,,lllXXXww
                  ,x++++;"+++++++++++`., )  )+++     )W; ,WOO,,lllX:"Ww
                  :++++++++++++++++++++W'"-:++++    .W'  WWOO,,lllX; `w
                  .++++++++++++++++.+++"ww :+++'   ,"   ,WWOO,,lllX;  ;
           ;ll--.-"`.;++++++++++++++.+++;+.;++(         :WWOO,,lllXx
          ,'lllllllll,++++;+++++++++;"++++++++++++-.    :WWOO,,lllXx
          ;llll;;;"';'++++;'"""'''`` `lll;;:+++++++++.  WWOOO,,lllX'
         ,lllll,    ;+++++;            `"lllll.++++++++ WWwO,,,llX;
         lllllll,  ,++++++;               llllll+++++++.:WWw',,llx
        ,llllllll, ;++++++;               :llllll+++++++."WW;,,llx
        ;lllllllllV+++++++;               :lllllll+++++++.`w' `.lx.
        `lllllllll'+++++++;               :lllllll++++++++  `\  `,X\
         "llllll;++++++++;                ;llllll'+++++++++   `-  \X;
          "llll'+++++++++;               ;lllllll"+++++++++        `)
           `-'`+++++++++;'              ,llllllll++++++++++
             +++++++++++;              ,llllllll'++++++++++
.           '++++++++++"               `""""""""'+++++++++"           .
	 */
	public void tick(float deltaTime){
		inTick=true;
		for(Entity ent: entities.values()){
			ent.tick(deltaTime);
		}
		inTick=false;
		if(!deferredRegistrations.isEmpty()){
			entities.putAll(deferredRegistrations);
			deferredRegistrations=new HashMap<Integer, Entity>();
		}
	}
	
	
	
	/**
	 * Nuke the site from Orbit.
	 * 
	 *          
     ..-^~~~^-..
   .~           ~.
  (;:           :;)
   (:           :)
     ':._   _.:'
         | |
       (=====)
         | |
         | |
         | |
      ((/   \))
	 */
	public void deleteAllEntities(){
		ArrayList<Entity> toDelete = new ArrayList<Entity>();
		for(Entity ent: entities.values()){
			toDelete.add(ent);
		}
		
		for(Entity ent: toDelete){
			ent.destroy();
		}
	}
	
	public void cleanUpEntities(){
		ArrayList<Entity> toDelete = new ArrayList<Entity>();
		for(Entity ent: entities.values()){
			if(ent.getDestroyFlag()){
				toDelete.add(ent);
			}
		}
		for(Entity ent: toDelete){
			ent.destroy();
		}
	}
	
	void unregisterEntity(Entity entity){
		entities.remove(entity.getID());
	}
}
