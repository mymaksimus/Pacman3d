package de.skysoldier.pacman3d.map;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLCaps.AGLDrawMode;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLMeshData;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLModelLoader;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLTexture;
import de.skysoldier.pacman3d.map.MapTile.TileType;

public class MapVisualization {
	
	private Map map;
	
	public MapVisualization(Map map){
		this.map = map;
	}
	
	public AGLMeshData getWallMeshData(){
		ArrayList<Float> data = new ArrayList<>();
		MapTile mapTiles[][] = map.getMapTiles();
		float wallWidth = map.getScale() * 0.5f, wallHeight = 5;
		for(MapTile tileRow[] : mapTiles){
			for(MapTile tile : tileRow){
				if(tile.getType() == TileType.WALL){
					Direction directions[] = Direction.values();
					for(Direction d : directions){
						MapTile neighbourTile = map.getNeighbourMapTile(tile, d);
						if(neighbourTile != null && neighbourTile.getType() == TileType.WALL){
							addWall(data, tile.getPosition(), wallWidth, wallHeight, d);
						}
					}
				}
			}
		}
		float dataArray[] = new float[data.size()];
		for(int i = 0; i < data.size(); i++){
			dataArray[i] = data.get(i);
		}
		return new AGLMeshData(dataArray, AGLDrawMode.TRIANGLES);
	}
	
	private void addWall(ArrayList<Float> data, Vector3f position, float wallWidth, float wallHeight, Direction direction){
		float xInDirection = position.x + wallWidth * direction.getVector().x;
		float zInDirection = position.z + wallWidth * direction.getVector().z;
		Vector3f p1 = new Vector3f(xInDirection, wallHeight, zInDirection);
		Vector3f p2 = new Vector3f(xInDirection, 0, zInDirection);
		Vector3f p3 = new Vector3f(position.x, wallHeight, position.z);
		Vector3f p4 = new Vector3f(position.x, 0, position.z);
		addDataRow(data, p1, new Vector2f(0, 0));
		addDataRow(data, p2, new Vector2f(0, 1));
		addDataRow(data, p3, new Vector2f(1, 0));
		addDataRow(data, p2, new Vector2f(0, 1));
		addDataRow(data, p3, new Vector2f(1, 0));
		addDataRow(data, p4, new Vector2f(1, 1));
	}
	
	private void addDataRow(ArrayList<Float> data, Vector3f point, Vector2f vt){
		data.add(point.x);
		data.add(point.y);
		data.add(point.z);
		data.add(vt.x);
		data.add(vt.y);
	}
	
	public ArrayList<AGLRenderObject> createWalls(AGLGlslAttribute shaderAttributes[]){
		MapTile mapTiles[][] = map.getMapTiles();
		float wallData[] = AGLModelLoader.loadObj(new AGLResource("wall.obj"), new Vector3f(map.getScale() * 0.5f, 3.0f, map.getScale() * 0.5f))[0];
		AGLTexture wallTexture = new AGLTexture(new AGLResource("wall.png"));
		AGLAsset wallAsset = new AGLAsset(new AGLMesh(new AGLMeshData(wallData, AGLDrawMode.TRIANGLES), shaderAttributes), wallTexture);
		ArrayList<AGLRenderObject> walls = new ArrayList<>();
		for(MapTile tilesz[] : mapTiles){
			for(MapTile tilex : tilesz){
				if(tilex.getType() == TileType.WALL){
					AGLRenderObject wall = new AGLRenderObject(wallAsset);
					wall.translateToGlobal(tilex.getGridx() * map.getScale(), 0, tilex.getGridz() * map.getScale());
//					wall.scaleTo(map.getScale(), 1, map.getScale());
					walls.add(wall);
				}
			}
		}
		return walls;
	}
}
