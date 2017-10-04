package mbGame;

import states.GameState;
import states.Scene;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;

public class CollisionListener implements PhysicsCollisionListener {

	@Override
	public void collision(PhysicsCollisionEvent event) {
		if(event.getNodeA().getName().equals("player") || event.getNodeB().getName().equals("player")){
			if(event.getNodeA().getName().equals("warp") || event.getNodeB().getName().equals("warp")){
				System.out.println("Next level Man!");
				changeScenes(GameState.level2,GameState.intro);
			}
		}
		
		if(event.getNodeA().getName().equals("shootFrustrum") || event.getNodeB().getName().equals("shootFrustrum")){
			System.out.println(event.getNodeA().getName() + " "+ event.getNodeB().getName());
		}
	}
	
	public void changeScenes(Scene next, Scene last){
		last.remove();
		next.load();
	}

}
