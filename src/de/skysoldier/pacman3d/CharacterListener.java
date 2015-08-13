package de.skysoldier.pacman3d;

import de.skysoldier.pacman3d.map.MapTile;

public interface CharacterListener {
	void reachedTile(MapTile tile);
}
