package mbGame;

import states.GameState;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;

public class Game extends SimpleApplication   {
	
	Splash startSplash;
	public static boolean start = false;
	public static AppSettings settings = new AppSettings(true);
	public GameState game;
	
	public static void main(String[] args) {
		settings.setResolution(1280, 600);
		settings.setTitle("Moustach Game");
		Game app = new Game();
		app.setSettings(settings);
		app.setShowSettings(false);
		app.start();
	}
	
	@Override
	public void simpleInitApp() {
	    setDisplayStatView(false);
	    cam.setFrustumPerspective(80, settings.getWidth()/settings.getHeight()	, 0.1f, 100f);
	    guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
	    //startSplash();
	    game = new GameState(guiFont);
	    stateManager.attach(game);
	}
	
	public void startSplash(){
		startSplash = new Splash(this);
		startSplash.addPic("Pictures/startPic1.png", 10);
		startSplash.addPic("Pictures/startPic2.png", 10);
		startSplash.picAttach(0);
	}
	
	@Override
	public void simpleUpdate(float tpf) {
        
        //startSplash.update(tpf);
	}
}