package gdw.entityCore;

import gdw.astroids.components.DestroyableComponentTemplate;
import gdw.astroids.components.EngineComponentTemplate;
import gdw.astroids.components.random.RandomMovementComponentTemplate;
import gdw.astroids.components.random.RandomRotationComponentTemplate;
import gdw.astroids.input.AstroidsInputComponentTemplate;
import gdw.collisionDetection.AABoxCollisionDetectionComponentTemplate;
import gdw.collisionDetection.CircleCollisionDetectionComponentTemplate;
import gdw.collisionDetection.OOBoxCollisionDetectionComponentTemplate;
import gdw.collisionReaction.CollisionReactionComponentTemplate;
import gdw.control.PlayerInputComponentTemplate;
import gdw.gameplay.color.ColorSourceComponentTemplate;
import gdw.gameplay.color.ColorableComponentTemplate;
import gdw.gameplay.color.FadeInComponentTemplate;
import gdw.gameplay.enemy.EnemyBehaviorComponentTemplate;
import gdw.gameplay.enemy.EnemyDamageDealerComponentTemplate;
import gdw.gameplay.levelObjects.PathFollowComponentTemplate;
import gdw.gameplay.levelObjects.RotateBySwitchComponentTemplate;
import gdw.gameplay.levelObjects.SwitchComponentTemplate;
import gdw.gameplay.levelObjects.SwitchUserComponentTemplate;
import gdw.gameplay.player.DuckableComponentTemplate;
import gdw.gameplay.player.PlayerBehaviorComponentTemplate;
import gdw.gameplay.player.PlayerWeaponComponentTemplate;
import gdw.gameplay.progress.LevelGoalComponentTemplate;
import gdw.gameplay.progress.RainbowComponentTemplate;
import gdw.gameplay.progress.StartSpawnComponentTemplate;
import gdw.genericBehavior.AttachableComponentTemplate;
import gdw.genericBehavior.AttachmentComponentTemplate;
import gdw.genericBehavior.FollowComponentTemplate;
import gdw.genericBehavior.PivotRotationComponentTemplate;
import gdw.graphics.AnimatedSpriteComponent;
import gdw.graphics.AnimatedSpriteComponentTemplate;
import gdw.graphics.CameraComponent;
import gdw.graphics.CameraComponentTemplate;
import gdw.graphics.OverlayedAnimatedSpriteComponent;
import gdw.graphics.OverlayedAnimatedSpriteComponentTemplate;
import gdw.graphics.StaticSpriteComponent;
import gdw.graphics.StaticSpriteComponentTemplate;
import gdw.network.NetComponentTemplate;
import gdw.physics.SimulationComponentTemplate;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;




public class ComponentTemplateFactory {
	//Singleton-Stuff:
	private static ComponentTemplateFactory instance = null;
	public static ComponentTemplateFactory getInstance(){
		if(instance==null){
			instance = new ComponentTemplateFactory();
		}
		return instance;
	}
	private ComponentTemplateFactory(){
		componentTemplateClasses.put("StaticSprite", StaticSpriteComponentTemplate.class);
		componentTemplateClasses.put("AnimatedSprite", AnimatedSpriteComponentTemplate.class);
		componentTemplateClasses.put("OverlayedAnimatedSprite", OverlayedAnimatedSpriteComponentTemplate.class);
		componentTemplateClasses.put("Camera", CameraComponentTemplate.class);
		componentTemplateClasses.put("Simulation", SimulationComponentTemplate.class);
		componentTemplateClasses.put("PlayerInput", PlayerInputComponentTemplate.class);
		componentTemplateClasses.put("AABoxCollisionDetection", AABoxCollisionDetectionComponentTemplate.class);
		componentTemplateClasses.put("OOBoxCollisionDetection", OOBoxCollisionDetectionComponentTemplate.class);
		componentTemplateClasses.put("CircleCollisionDetection", CircleCollisionDetectionComponentTemplate.class);
		componentTemplateClasses.put("CollisionReaction", CollisionReactionComponentTemplate.class);
		componentTemplateClasses.put("Follow", FollowComponentTemplate.class);
		componentTemplateClasses.put("Attachment", AttachmentComponentTemplate.class);
		componentTemplateClasses.put("Attachable", AttachableComponentTemplate.class);
		componentTemplateClasses.put("Network", NetComponentTemplate.class);
		//TODO: Auskommentierung entfernen, wenn Komponenten geschrieben sind.
		componentTemplateClasses.put("PlayerWeapon", PlayerWeaponComponentTemplate.class);
		componentTemplateClasses.put("Duckable", DuckableComponentTemplate.class);
		componentTemplateClasses.put("PlayerBehavior", PlayerBehaviorComponentTemplate.class);
		componentTemplateClasses.put("EnemyBehavior", EnemyBehaviorComponentTemplate.class);
		componentTemplateClasses.put("EnemyDamageDealer", EnemyDamageDealerComponentTemplate.class);
		componentTemplateClasses.put("SwitchUser", SwitchUserComponentTemplate.class);
		componentTemplateClasses.put("Switch", SwitchComponentTemplate.class);
//		componentTemplateClasses.put("Door", DoorComponentTemplate.class);
		componentTemplateClasses.put("PathFollow", PathFollowComponentTemplate.class);
		componentTemplateClasses.put("Rainbow", RainbowComponentTemplate.class);
		componentTemplateClasses.put("StartSpawn", StartSpawnComponentTemplate.class);
		componentTemplateClasses.put("LevelGoal", LevelGoalComponentTemplate.class);
		componentTemplateClasses.put("ColorSource", ColorSourceComponentTemplate.class);
		componentTemplateClasses.put("Colorable", ColorableComponentTemplate.class);
		componentTemplateClasses.put("PivotRotation", PivotRotationComponentTemplate.class);
		componentTemplateClasses.put("RotateBySwitch", RotateBySwitchComponentTemplate.class);
		componentTemplateClasses.put("FadeIn", FadeInComponentTemplate.class);
		
		//astroid specific
		componentTemplateClasses.put("AstroidInput", AstroidsInputComponentTemplate.class);
		componentTemplateClasses.put("Engine", EngineComponentTemplate.class);
		componentTemplateClasses.put("Destroyable", DestroyableComponentTemplate.class);
		componentTemplateClasses.put("RandomMovement", RandomMovementComponentTemplate.class);
		componentTemplateClasses.put("RandomRotation", RandomRotationComponentTemplate.class);
	}

	private HashMap<String, Class<? extends ComponentTemplate>> componentTemplateClasses = new HashMap<String, Class<? extends ComponentTemplate>>();
	
	public ComponentTemplate createComponentTemplate(String name, HashMap<String, String> params){
		if(componentTemplateClasses.containsKey(name)){
			try {
				return componentTemplateClasses.get(name).getConstructor(HashMap.class).newInstance(params);
			}
			catch (InvocationTargetException e) {
				System.err.println("Warnung: " + e.getTargetException().getMessage());
				e.getTargetException().printStackTrace();
				return null;
			}
			catch (Exception e) {
				System.err.println("Warnung: " + e.getMessage());
				e.printStackTrace();
				return null;
			} 
		}
		else return null;
	}
	
	public boolean testIsGhostOnly(String compTemplateName){
		if(componentTemplateClasses.containsKey(compTemplateName)){
			try
			{
				return (Boolean)(componentTemplateClasses.get(compTemplateName).getMethod("isGhostOnly").invoke(null));
			} catch (Exception e)
			{
				return false;
			} 
		}
		else return false;
	}
}
