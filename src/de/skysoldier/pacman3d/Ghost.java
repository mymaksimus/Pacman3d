package de.skysoldier.pacman3d;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderController;
import de.skysoldier.pacman3d.map.Map;
import de.skysoldier.pacman3d.map.MapTile;

public class Ghost extends Character {

	private MapTile currentAim;
	
	public Ghost(Map map, AGLAsset asset){
		super(map, asset);
	}
	
	public void update(){
		rotateY(AGLRenderController.getDeltaS() * (float) Math.toRadians(90));
		if(currentAim  != null){
			
		}
		super.update();
	}
	
	public void reachedTile(MapTile tile){
		//empty
	}
}
