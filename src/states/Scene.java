package states;

import java.util.ArrayList;

import macepear.entities.Mob;
import Entities.Car;
import Entities.Trigger;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Scene {
	public Spatial sceneModel;
	public RigidBodyControl landscape;
	String path,collPath;
	ArrayList<Mob> mobs = new ArrayList<Mob>();
	ArrayList<Trigger> warps = new ArrayList<Trigger>();
	public static Node pickUpAble = new Node();
	boolean loaded = false;
	
	public Scene(String path, String collPath) {
		this.path = path;
		this.collPath = collPath;
	}
	
	public void load(){
		GameState.bullet.getPhysicsSpace().destroy();
		GameState.bullet.getPhysicsSpace().create();
		sceneModel = GameState.app.getAssetManager().loadModel(path);
		GameState.app.getRootNode().attachChild(sceneModel);
		Spatial sceneColl = GameState.app.getAssetManager().loadModel(collPath);
		CollisionShape sceneShape = CollisionShapeFactory.createMeshShape((Node) sceneColl);
		landscape = new RigidBodyControl(sceneShape,0);
		GameState.bullet.getPhysicsSpace().add(landscape);
		sceneModel.addControl(landscape);
		
		for(Spatial mesh:((Node)sceneModel).getChildren()){
	    	if(mesh.getName().equals("enemy")){
			Mob enemy = new Mob(mesh); 
	    		mobs.add(enemy);
	    	}
	    	if(mesh.getName().equals("warp")){
	    		Trigger warp = new Trigger((Node)mesh);
	    		warps.add(warp);
	    	}
	    	if(mesh.getName().equals("pick")){
	    		pickUpAble.attachChild(mesh);
	    	}
	    	if(mesh.getName().equals("car")){
	    		Car car = new Car();
	    		pickUpAble.attachChild(Car.vehicle);
	    	}
	    	if(mesh.getName().equals("Dlight")){
	    		DirectionalLight dl = new DirectionalLight();
	            dl.setColor(ColorRGBA.White);
	            dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
	            GameState.app.getRootNode().addLight(dl);
	    	}
	    	if(mesh.getName().equals("Plight")){
	    		PointLight pl = new PointLight();
	            pl.setPosition(mesh.getLocalTranslation());
	            GameState.app.getRootNode().addLight(pl);
	    	}
	    }
		GameState.app.getRootNode().attachChild(pickUpAble);
		loaded = true;
	}
	
	public void remove(){
		for(Mob mob:mobs){
			mob.remove();
		}
		for(Trigger warp:warps){
			warp.remove();	
		}
		GameState.app.getRootNode().detachChild(pickUpAble);
		GameState.bullet.getPhysicsSpace().remove(landscape);
		GameState.app.getRootNode().detachChild(sceneModel);
	}
}