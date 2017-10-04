package mbGame;

import java.util.ArrayList;

import states.GameState;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;

public class Dialog {

	ArrayList<String> text = new ArrayList<String>();
	//float time;
	int index = 0;
	public BitmapText dialog;
	float time;
	ArrayList<Float> times = new ArrayList<Float>();
	
	public Dialog(String txt, float timeTaken, BitmapFont font){
		text.add(txt);
		times.add(timeTaken);
		dialog = new BitmapText(font, false);
		dialog.setSize(font.getCharSet().getRenderedSize());
		dialog.setText(text.get(index));
		dialog.setLocalTranslation(500, dialog.getLineHeight(), -1);
        GameState.app.getGuiNode().attachChild(dialog);
        time = times.get(index);
	}
	
	public void addDialog(String d,float timeTaken){
		text.add(d);
		times.add(timeTaken);
	}
	
	public void update(float tpf){
		time -= tpf;
		if(time <= 0){
			++index;
			if(index < text.size()){
				dialog.setText(text.get(index));
				time = times.get(index);
			}else{
				GameState.app.getGuiNode().detachChild(dialog);
			}
		}
	}
}
