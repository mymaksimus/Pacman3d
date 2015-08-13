package de.skysoldier.pacman3d.map;

import org.lwjgl.util.vector.Vector3f;

public enum Direction {
	
	POSITIVE_X(1, 0, 0, 270), 
	NEGATIVE_X(-1, 0, 0, 90),
	POSITIVE_Z(0, 0, 1, 180), 
	NEGATIVE_Z(0, 0, -1, 0);
	
	private Vector3f vector;
	private float angle;
	
	Direction(float x, float y, float z, float angle){
		this.vector = new Vector3f(x, y, z);
		this.angle = angle;
	}
	
	public Vector3f getVector(){
		return new Vector3f(vector);
	}
	
	public float getAngle(){
		return angle;
	}
	
	public static Direction parseDirection(float angle){
//		if(angle <= 45f || angle >= 315f) return Direction.NEGATIVE_Z;
		if(angle >= 135f && angle <= 225f) return Direction.POSITIVE_Z;
		if(angle >= 45f && angle <= 135f) return Direction.NEGATIVE_X;
		if(angle >= 225f && angle <= 315f) return Direction.POSITIVE_X;
		return Direction.NEGATIVE_Z;
	}
}