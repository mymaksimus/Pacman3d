package de.skysoldier.pacman3d.map;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderObject;
import de.skysoldier.pacman3d.AssetBuilderEnum;

public class Coin extends AGLRenderObject {

	private float rotStart;
	
	public Coin(){
		super(AssetBuilderEnum.COIN.getAsset());
		rotStart = (float) Math.toRadians(Math.random() * 90);
	}
	
	public void update(){
		float rot = (float) (90 * Math.sin(AGLRenderController.getTicksInSeconds()));
		rotateToY((float) Math.toRadians(rot) + rotStart); 
		super.update();
	}
}