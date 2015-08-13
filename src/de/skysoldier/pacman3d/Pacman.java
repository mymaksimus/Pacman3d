package de.skysoldier.pacman3d;

import org.lwjgl.input.Keyboard;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLAsset;
import de.skysoldier.pacman3d.map.Direction;
import de.skysoldier.pacman3d.map.Map;
import de.skysoldier.pacman3d.map.MapTile;

public class Pacman extends Character {

	public Pacman(Map map, AGLAsset pacmanAsset){
		super(map, pacmanAsset);
	}
	
	public void updateDirection(float eyeAngle){
		Direction moveDirection = null;
		if(Keyboard.isKeyDown(Keyboard.KEY_S)) moveDirection = Direction.POSITIVE_Z;
		if(Keyboard.isKeyDown(Keyboard.KEY_W)) moveDirection = Direction.NEGATIVE_Z;
		if(Keyboard.isKeyDown(Keyboard.KEY_A)) moveDirection = Direction.NEGATIVE_X;
		if(Keyboard.isKeyDown(Keyboard.KEY_D)) moveDirection = Direction.POSITIVE_X;
		if(moveDirection != null){
			int relativeAngle = (((int) (eyeAngle + 45f)) / 90) * 90;
			float newAngle = Pacman3d.clampAngle(moveDirection.getAngle() - (360.0f - relativeAngle));
			moveDirection = Direction.parseDirection(newAngle);
			setCurrentMoveDirection(moveDirection);
//			System.out.println(Direction.parseDirection(moveDirection.getAngle()));
//			System.out.println(moveDirection.getAngle() + ", " + eyeAngle + ", " + relativeAngle);
		}
	}
	
	public void update(){
		super.update();
	}

	public void reachedTile(MapTile tile){
		tile.getCoin().setRemoveRequested(true);
	}
}