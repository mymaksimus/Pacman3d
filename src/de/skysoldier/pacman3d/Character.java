package de.skysoldier.pacman3d;

import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGL;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderObject;
import de.skysoldier.pacman3d.map.Direction;
import de.skysoldier.pacman3d.map.Map;
import de.skysoldier.pacman3d.map.MapTile;
import de.skysoldier.pacman3d.map.MapTile.TileType;

public abstract class Character extends AGLRenderObject implements CharacterListener {
	
	private Map map;
	private Direction currentMoveDirection, requestedMoveDirection;
	private long timeSinceLastTile;
	private MapTile currentMapTile;
	private boolean isOnTile = true;
	
	public Character(Map map, AGLAsset asset){
		super(asset);
		this.map = map;
		this.currentMapTile = map.getSpawnTile();
		translateToGlobal(currentMapTile.getPosition());
	}
	
	public void setCurrentMoveDirection(Direction direction){
		if(isOnTile){
			if(direction != currentMoveDirection){
				if(direction == null){
					currentMoveDirection = null;
				}
				else {
					MapTile neighbour = map.getNeighbourMapTile(currentMapTile, direction);
					if(neighbour != null && neighbour.getType() == TileType.PATH){
						this.currentMoveDirection = direction;
						rotateToY((float) Math.toRadians(currentMoveDirection.getAngle()));
						timeSinceLastTile = AGL.getTime();
					}
					else {
						currentMoveDirection = null;
					}
				}
			}
			else if(map.getNeighbourMapTile(currentMapTile, direction).getType() != TileType.PATH){
				currentMoveDirection = null;
			}
		}
		else {
			setRequestedMoveDirection(direction);
		}
	}
	
	public void setRequestedMoveDirection(Direction direction){
		requestedMoveDirection = direction;
	}
	
	public void update(){
		if(currentMoveDirection != null){
			float state = 5f * (AGL.getTime() - timeSinceLastTile) / 1e9f;
			isOnTile = false;
			boolean reachedTile = false;
			if(state >= 1.0f){
				state = 1.0f;
				reachedTile = true;
			}
			translateToGlobal(Vector3f.add(currentMapTile.getPosition(), (Vector3f) currentMoveDirection.getVector().scale(state * map.getScale()), new Vector3f()));
			if(reachedTile){
//				System.out.println("---");
//				System.out.println(currentMapTile);
				currentMapTile = map.getNeighbourMapTile(currentMapTile, currentMoveDirection);
//				System.out.println(currentMapTile);
				reachedTile(currentMapTile);
				isOnTile = true;
				setCurrentMoveDirection(requestedMoveDirection);
				timeSinceLastTile = AGL.getTime();
			}
		}
		super.update();
	}
}