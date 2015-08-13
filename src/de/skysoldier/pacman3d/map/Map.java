package de.skysoldier.pacman3d.map;

public class Map {
	
	private MapTile[][] mapTiles;
	private MapTile spawnTile;
	private float scale;
	private MapVisualization mapVisualization;
	
	public Map(MapTile mapTiles[][], int spawnData[], float scale){
		this.mapTiles = mapTiles;
		spawnTile = mapTiles[spawnData[0]][spawnData[1]];
		this.scale = scale;
		this.mapVisualization = new MapVisualization(this);
	}
	
	public MapTile getMapTile(int rasterx, int rasterz){
		try{
			return mapTiles[rasterz][rasterx];
		}
		catch(IndexOutOfBoundsException e){
			return null;
		}
	}
	
	protected MapTile[][] getMapTiles(){
		return mapTiles;
	}
	
	public MapTile getNeighbourMapTile(MapTile from, Direction direction){
		return getMapTile(from.getGridx() + (int) direction.getVector().x, from.getGridz() + (int) direction.getVector().z);
	}
	
	public MapTile getSpawnTile(){
		return spawnTile;
	}
	
	public float getScale(){
		return scale;
	}
	
	public MapVisualization getMapVisualization(){
		return mapVisualization;
	}
	
	
	//only test purposes...
	public static interface MapTileIterator {
		void next(MapTile tile);
	}
	
	public void iterateMapTiles(MapTileIterator iterator){
		for(MapTile row[] : mapTiles){
			for(MapTile mt : row){
				iterator.next(mt);
			}
		}
	}
	//test end
}