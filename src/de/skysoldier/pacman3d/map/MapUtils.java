package de.skysoldier.pacman3d.map;

import java.util.ArrayList;

import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLResource;
import de.skysoldier.pacman3d.map.MapTile.TileType;

public class MapUtils {
	
	public static Map readMap(AGLResource mapResource, float scale){
		ArrayList<String> mapLines = mapResource.toLineListResource();
		ArrayList<MapTile[]> mapTiles = new ArrayList<>();
		int gridz = 0;
		int spawnData[] = new int[2];
		for(int i = 0; i < mapLines.size(); i++){
			String line = mapLines.get(i);
			if(line.startsWith("SPAWN")){
				String rawSpawnData[] = line.split(" ")[1].split(",");
				spawnData[0] = Integer.parseInt(rawSpawnData[0]);
				spawnData[1] = Integer.parseInt(rawSpawnData[1]);
			}
			else {
				char mapLineChars[] = line.toCharArray();
				MapTile[] mapTileLine = new MapTile[mapLineChars.length];
				for(int j = 0; j < mapTileLine.length; j++){
					Vector3f position = new Vector3f(j * scale, 0, gridz * scale);
					mapTileLine[j] = new MapTile(j, gridz, position, TileType.value(Character.getNumericValue(mapLineChars[j])));
				}
				mapTiles.add(mapTileLine);
				gridz++;
			}
		}
		return new Map(mapTiles.toArray(new MapTile[mapTiles.size()][]), spawnData, scale);
	}
}
