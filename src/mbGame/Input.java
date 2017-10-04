package mbGame;

import states.GameState;
import states.Scene;
import Entities.Car;

import com.jme3.bullet.control.GhostControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.Ray;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Input implements ActionListener, AnalogListener{

	Spatial shootShape; 
	GhostControl shootFrustrum;
	public static boolean left = false, right = false, up = false, down = false, duck = false;
	private final float accelerationForce = 1000.0f;
	private final float brakeForce = 100.0f;
	private float steeringValue = 0;
	private float accelerationValue = 0;
	public Input(){
		setUpKeys();
		shootShape = GameState.app.getAssetManager().loadModel("Models/shoot.blend");
		shootShape.setName("shootFrustrum");
	}
	
	/** We over-write some navigational key mappings here, so we can
	 * add physics-controlled walking and jumping: */
	private void setUpKeys() {
		GameState.app.getInputManager().addMapping("Left", new KeyTrigger(KeyInput.KEY_A));
		GameState.app.getInputManager().addMapping("Right", new KeyTrigger(KeyInput.KEY_D));
		GameState.app.getInputManager().addMapping("Up", new KeyTrigger(KeyInput.KEY_W));
		GameState.app.getInputManager().addMapping("Down", new KeyTrigger(KeyInput.KEY_S));
		GameState.app.getInputManager().addMapping("Jump", new KeyTrigger(KeyInput.KEY_SPACE));
		GameState.app.getInputManager().addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		GameState.app.getInputManager().addMapping("Duck", new KeyTrigger(KeyInput.KEY_LSHIFT));	
		GameState.app.getInputManager().addMapping("Pick", new KeyTrigger(KeyInput.KEY_E));
		GameState.app.getInputManager().addListener(this, "Left");
		GameState.app.getInputManager().addListener(this, "Right");
		GameState.app.getInputManager().addListener(this, "Up");
		GameState.app.getInputManager().addListener(this, "Down");
		GameState.app.getInputManager().addListener(this, "Jump");
		GameState.app.getInputManager().addListener(this, "Shoot");
		GameState.app.getInputManager().addListener(this, "Duck");
		GameState.app.getInputManager().addListener(this, "Pick");
	}
	@Override
	public void onAction(String binding, boolean isPressed, float tpf) {
		if(!Game.start){
			if(!Car.inCar){
				if (binding.equals("Left")) {
					left = isPressed;
				} else if (binding.equals("Right")) {
					right= isPressed;
				} else if (binding.equals("Up")) {
					up = isPressed;
				} else if (binding.equals("Down")) {
					down = isPressed;
				} else if (binding.equals("Jump")) {
					if (isPressed) { 
						GameState.player.physics.jump();
					}	
				} else if (binding.equals("Duck")){
					GameState.player.physics.setDucked(isPressed);
					duck = isPressed;
				}else if(binding.equals("Shoot")){
					if(isPressed){
						shootFrustrum = new GhostControl(CollisionShapeFactory.createMeshShape((Node)shootShape));
						shootShape.addControl(shootFrustrum);
						GameState.app.getRootNode().attachChild(shootShape);
						GameState.bullet.getPhysicsSpace().add(shootFrustrum);
					}else{
						GameState.app.getRootNode().detachChild(shootShape);
						GameState.bullet.getPhysicsSpace().remove(shootFrustrum);		
					}
				}else if(binding.equals("Pick")){
					CollisionResults results = new CollisionResults();
			        Ray ray = new Ray(GameState.app.getCamera().getLocation(), GameState.app.getCamera().getDirection());
			        Scene.pickUpAble.collideWith(ray, results);
			        for(CollisionResult result : results){
			        	if(result.getGeometry().getName().equals("car")){
			        		GameState.player.remove();
			        		Car.inCar = true;
			        	}
			        }
				}
			}else{
				 if (binding.equals("Left")) {
					 if (isPressed) {
						 steeringValue += .5f;
					 } else {
						 steeringValue += -.5f;
					 }
					 Car.control.steer(steeringValue);
				 } else if (binding.equals("Right")) {
					 if (isPressed) {
						 steeringValue += -.5f;
					 } else {
						 steeringValue += .5f;
					 }
					 Car.control.steer(steeringValue);
				 } else if (binding.equals("Up")) {
					 if (isPressed) {
						 accelerationValue += accelerationForce;
					 } else {
						 accelerationValue -= accelerationForce;
					 }
					 Car.control.accelerate(accelerationValue);
				 } else if (binding.equals("Down")) {
					 if (isPressed) {
						 Car.control.brake(brakeForce);
					 } else {
						 Car.control.brake(0f);
					 }
				 }
			}
		}
	}
	
	public void update(){
		shootShape.setLocalTranslation(GameState.app.getCamera().getLocation());
		shootShape.setLocalRotation(GameState.app.getCamera().getRotation());
	}

	@Override
	public void onAnalog(String name, float value, float tpf) {
		// TODO Auto-generated method stub
		
	}
	
}
