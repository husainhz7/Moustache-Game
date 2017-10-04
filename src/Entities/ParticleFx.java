package Entities;

import states.GameState;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh.Type;
import com.jme3.material.Material;

public class ParticleFx {
	ParticleEmitter emitter;
	Material material;
	float time;
	
	public ParticleFx(String name){
		emitter = new ParticleEmitter(name, Type.Triangle, 30);
		material = new Material(GameState.app.getAssetManager(), "Common/MatDefs/Misc/Particle.j3md");
		material.setTexture("Texture",GameState.app.getAssetManager().loadTexture("Effects/Explosion/flame.png"));
		emitter.setMaterial(material);
		emitter.setImagesY(2); 
		emitter.setImagesX(2);
		emitter.setStartSize(5);
		emitter.setSelectRandomImage(true);
		emitter.setLocalTranslation(0, 1, 0);
		GameState.app.getRootNode().attachChild(emitter);
	}
	public void emit(){		
		emitter.emitAllParticles();
	}
	
	

}
