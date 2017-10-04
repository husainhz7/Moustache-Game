package Entities;

import mbGame.Input;
import states.GameState;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class PlayerControl extends AbstractControl {
	Vector3f walkDirection = new Vector3f();
	Vector3f camDir = new Vector3f();
	Vector3f camLeft = new Vector3f();
	float height=  2;
	
	@Override
	protected void controlUpdate(float tpf) {
		camDir.set(new Vector3f(GameState.app.getCamera().getDirection().x,0,GameState.app.getCamera().getDirection().z));
        camLeft.set(GameState.app.getCamera().getLeft());
        walkDirection.set(0, 0, 0);
        if (Input.left) {
            walkDirection.addLocal(camLeft);
        }
        if (Input.right) {
            walkDirection.addLocal(camLeft.negate());
        }
        if (Input.up) {
            walkDirection.addLocal(camDir);
        }
        if (Input.down) {
            walkDirection.addLocal(camDir.negate());
        }
        if (Input.duck) {
            height = 1;
        }else{
        	height = 2;
        }
        spatial.getControl(BetterCharacterControl.class).setWalkDirection(walkDirection.mult(6));
        GameState.app.getCamera().setLocation(spatial.getLocalTranslation().add(0, height, 0));
	}

	@Override
	protected void controlRender(RenderManager arg0, ViewPort arg1) {
		// TODO Auto-generated method stub
		
	}
}
