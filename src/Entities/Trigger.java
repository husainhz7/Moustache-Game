package Entities;

import states.GameState;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

public class Trigger {
	
	public Node node;
	public GhostControl control;
	
	public Trigger(Node nd){
		node = nd;
		control = new GhostControl(new BoxCollisionShape(new Vector3f(1,1,1)));
		node.addControl(control);
		GameState.bullet.getPhysicsSpace().add(control);
	}
	
	public void remove(){
		GameState.bullet.getPhysicsSpace().remove(control);
	}

}
