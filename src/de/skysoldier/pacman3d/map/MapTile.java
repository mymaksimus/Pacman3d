package de.skysoldier.pacman3d.map;

import org.lwjgl.util.vector.Vector3f;

public class MapTile {
	
	public enum TileType {
		
		VOID, WALL, PATH;
		
		static TileType value(int i){
			return TileType.values()[i];
		}
	}
	
	private Vector3f position;
	private int gridx, gridz;
	private TileType type;
	private Coin coin;
	
	public MapTile(int gridx, int gridz, Vector3f position, TileType type){
		this.gridx = gridx;
		this.gridz = gridz;
		this.position = position;
		this.type = type;
		if(type == TileType.PATH){
			this.coin = new Coin();
			coin.translateToGlobal(getPosition());
		}
	}
	
	public Vector3f getPosition(){
		return position;
	}
	
	public TileType getType(){
		return type;
	}
	
	public String toString(){
		return "[Tile:" + type + "] " + position;
	}

	public Coin getCoin(){
		return coin;
	}
	
	public int getGridx(){
		return gridx;
	}

	public int getGridz(){
		return gridz;
	}
}