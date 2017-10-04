package mbGame;

import java.util.ArrayList;

import com.jme3.ui.Picture;

public class Splash {
	ArrayList<Picture> pics = new ArrayList<Picture>();
	ArrayList<Float> times = new ArrayList<Float>();
	Game game;
	int indexNow = 0;
	float time = 10;
	
	public Splash(Game g){
		game = g;
	}
	
	public void addPic(String path, float timeTaken){
		Picture pic = new Picture(path, false);
		pic.setImage(game.getAssetManager(), path, false);
		pic.setPosition(0, 0);
		pic.setWidth(Game.settings.getWidth());
		pic.setHeight(Game.settings.getHeight());
		pics.add(pic);
		times.add(timeTaken);
		
	}
	
	public void picAttach(int index){
		game.getGuiNode().attachChild(pics.get(index));
		time = times.get(index);
		indexNow = index;
	}
	
	public void update(float tpf){
		time -= tpf;
		if(time <= 0){
			++indexNow;
			if(indexNow < pics.size()){
				game.getGuiNode().detachChild(pics.get(indexNow - 1));				
				game.getGuiNode().attachChild(pics.get(indexNow));
				time = times.get(indexNow);
			}else{
				game.getGuiNode().detachChildNamed("Pictures/startPic2.png");
				pics.clear();
				times.clear();
				Game.start = false;
			}
		}
	}
}
