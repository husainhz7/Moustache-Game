package Entities;

import states.GameState;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Player {
	
	public BetterCharacterControl physics;
	public Node player;
	
	public Player(){
		player = new Node("player");
		physics = new BetterCharacterControl(1,2,2);
		
//		physics.setJumpSpeed(10);
		physics.setJumpForce(new Vector3f(0, 10, 0));
//		physics.setFallSpeed(20);
		physics.setGravity(new Vector3f(0, 20, 0));
//		physics.setGravity(20);
	    player.addControl(physics);
	    player.addControl(new PlayerControl());
	    physics.warp(new Vector3f(0, 4, 0));
	    GameState.app.getRootNode().attachChild(player);
	    GameState.bullet.getPhysicsSpace().add(physics);
	}
	
	public void remove(){
		GameState.bullet.getPhysicsSpace().remove(physics);
		GameState.app.getRootNode().detachChild(player);
	}
}
