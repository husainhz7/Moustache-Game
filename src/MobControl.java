

import states.GameState;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class MobControl extends AbstractControl {
	
	Vector3f front = new Vector3f();
	Vector3f left = new Vector3f();
	Vector3f walkDirection = new Vector3f();
	
	@Override
	protected void controlUpdate(float tpf) {
		walkDirection = GameState.player.player.getLocalTranslation().subtract(spatial.getLocalTranslation());
		spatial.getControl(BetterCharacterControl.class).setWalkDirection(walkDirection.mult(0.5f));
	}
	
	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub
		
	}
}
