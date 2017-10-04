package states;

import mbGame.CollisionListener;
import mbGame.Dialog;
import mbGame.Input;
import Entities.Car;
import Entities.ParticleFx;
import Entities.Player;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.font.BitmapFont;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.CameraControl.ControlDirection;

public class GameState extends AbstractAppState{
	public static Scene intro;
	public static BulletAppState bullet;
	public static SimpleApplication app;
	public static Player player;
	Input input;
	Dialog introDialog;
	BitmapFont guiFont;
	AudioNode start;
	Spatial shootHud;
	public static Scene level2;
	CameraNode camNode;
	
	public GameState(BitmapFont font){
		guiFont = font;
	}
	
	@Override
	public void initialize(AppStateManager stateManager, Application app) {
		super.initialize(stateManager, app);
		GameState.app = (SimpleApplication)app;
		setUpPhysics();
		setUpLights();
		explosion();
		GameState.app.getFlyByCamera().setMoveSpeed(20);
		loadScene();
		input = new Input();
		introDialog = new Dialog("We're closed!", 30, guiFont);
	    introDialog.addDialog("Come back tommorow!", 10);
	    player = new Player();
	    shootHud = GameState.app.getAssetManager().loadModel("Models/shootHud.blend");
	    GameState.app.getRootNode().attachChild(shootHud);
	}	
	
	public void explosion(){
		ParticleFx xplosion = new ParticleFx("xplosion");
		xplosion.emit();
	}

	public void setUpPhysics() {
		bullet = new BulletAppState();
		app.getStateManager().attach(bullet);
		bullet.getPhysicsSpace().addCollisionListener(new CollisionListener());
		bullet.setDebugEnabled(true);
	}
	
	public void loadScene() {
		intro = new Scene("Textures/Intro.blend","Textures/IntroColl.blend");
		intro.load();
		level2 = new Scene("Textures/grass.blend", "Textures/grass.blend");
	}

	void setUpSound(){
		start = new AudioNode(app.getAssetManager(), "Sounds/introAmbient.ogg",false);
		start.setPositional(false);
		start.setLooping(false);
		start.setVolume(1);
		app.getRootNode().attachChild(start);
		start.play();
	}
	
	public void setUpLights(){
		app.getViewPort().setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
		
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(2));
        app.getRootNode().addLight(al);
        
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
	}

	@Override
	public void update(float tpf) {
		super.update(tpf);
		introDialog.update(tpf);
		input.update();
		shootHud.setLocalTranslation(app.getCamera().getLocation());
		shootHud.setLocalRotation(app.getCamera().getRotation());
		if(Car.inCar){
			app.getRootNode().detachChild(shootHud);
//			app.getFlyByCamera().setEnabled(false);
//			camNode = new CameraNode("Camera Node", app.getCamera());
//			camNode.setControlDir(ControlDirection.SpatialToCamera);
//			Car.vehicle.attachChild(camNode);
//			camNode.setLocalTranslation(new Vector3f(0, 5, -5));
//			camNode.lookAt(Car.vehicle.getLocalTranslation(), Vector3f.UNIT_Y);
		}
	}

	@Override
	public void render(RenderManager rm) {
		super.render(rm);
	}

	@Override
	public void cleanup() {
		player.remove();
		if(intro.loaded)
			intro.remove();
		if(level2.loaded)
			level2.remove();
		app.getRootNode().detachAllChildren();
		super.cleanup();
	}
}