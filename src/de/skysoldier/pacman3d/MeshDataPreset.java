package de.skysoldier.pacman3d;

import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLMeshData;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLModelLoader;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLCaps.AGLDrawMode;

public enum MeshDataPreset {
	
	GROUND(new float[]{-100, 0,-100, 0, 0, 100, 0,-100, 3, 0,-100, 0, 100, 0, 3, 100, 0, 100, 3, 3}, AGLDrawMode.TRIANGLE_STRIP),
	PACMAN(new AGLResource("pacman.obj"), new Vector3f(2.0f, 2.0f, 2.0f), AGLDrawMode.TRIANGLES),
	GHOST(new AGLResource("ghost.obj"), new Vector3f(2.0f, 2.0f, 2.0f), AGLDrawMode.TRIANGLES),
	COIN(new AGLResource("coin.obj"), new Vector3f(1.5f, 1.5f, 1.5f), AGLDrawMode.TRIANGLES);
	
	private AGLMeshData meshData;
	
	MeshDataPreset(float data[], AGLDrawMode drawMode){
		meshData = new AGLMeshData(data, drawMode);
	}
	
	MeshDataPreset(AGLResource objResource, AGLDrawMode drawMode){
		this(objResource, new Vector3f(1.0f, 1.0f, 1.0f), drawMode);
	}
	
	MeshDataPreset(AGLResource objResource, Vector3f scale,  AGLDrawMode drawMode){
		this(AGLModelLoader.loadObj(objResource, scale)[0], drawMode);
	}
	
	public AGLMeshData getData(){
		return meshData;
	}
}