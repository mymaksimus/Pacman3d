package de.skysoldier.pacman3d;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector3f;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGL3dApplication;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGL3dCamera;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLCaps.AGLDisplayCap;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLDisplay;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLProjection;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderController;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLRenderObject;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLShaderProgram;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLTexture;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLView;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLViewPart;
import de.skysoldier.pacman3d.map.Map;
import de.skysoldier.pacman3d.map.Map.MapTileIterator;
import de.skysoldier.pacman3d.map.MapBuilder;
import de.skysoldier.pacman3d.map.MapTile;
import de.skysoldier.pacman3d.map.MapUtils;
import de.skysoldier.pacman3d.util.Logger;

public class Pacman3d extends AGL3dApplication {
	
	private Pacman pacman;
	private ArrayList<Ghost> ghosts;
	
	public Pacman3d(){
		build();
	}
	
	public AGL3dCamera buildCamera(){
		return new AGL3dCamera(new AGLProjection.PerspectiveProjection(45, 0.1f, 1000.0f));
	}
	
	public AGLDisplay buildDisplay(){
		return new AGLDisplay(AGLDisplayCap.FULLSCREEN);
	}
	
	public void build(){
		AGLGlslAttribute pacmanShaderAttributes[] = new AGLGlslAttribute[]{
				AGLGlslAttribute.createAttributeVec3("vIn"),
				AGLGlslAttribute.createAttributeVec2("texCoord")
		};
		
		AssetBuilderEnum values[] = AssetBuilderEnum.values();
		for(AssetBuilderEnum e : values){
			e.build(pacmanShaderAttributes);
		}
		
		Map map = MapUtils.readMap(new AGLResource("test2.map"), 10);
		
		AGLShaderProgram pacmanShader = new AGLShaderProgram(new AGLResource("pacman.shader"), "###", pacmanShaderAttributes);
		AGLView gameView = new AGLView(getCamera());
		AGLViewPart pacmanPart = new AGLViewPart(pacmanShader);
		
		map.iterateMapTiles(new MapTileIterator(){
			public void next(MapTile tile){
				if(tile.getCoin() != null) pacmanPart.addRenderObjects(tile.getCoin());
			}
		});
		
		AGLTexture pacmanTexture = new AGLTexture(new AGLResource("pacman.png"));
		AGLAsset pacmanAsset = new AGLAsset(new AGLMesh(MeshDataPreset.PACMAN.getData(), pacmanShaderAttributes), pacmanTexture);
		pacman = new Pacman(map, pacmanAsset);
		pacmanPart.addRenderObjects(pacman);
		
		ghosts = new ArrayList<>();
		AGLTexture ghostTexture = new AGLTexture(new AGLResource("ghost.png"));
		AGLAsset ghostAsset = new AGLAsset(new AGLMesh(MeshDataPreset.GHOST.getData(), pacmanShaderAttributes), ghostTexture);
		ghosts.add(new Ghost(map, ghostAsset));
		pacmanPart.addRenderObjects(ghosts.get(0));
		
//		ArrayList<AGLRenderObject> walls = map.getMapVisualization().createWalls(pacmanShaderAttributes);
//		for(AGLRenderObject o : walls){
//			pacmanPart.addRenderObjects(o);
//		}
		
		AGLTexture wallTexture = new AGLTexture(new AGLResource("wall.png"));
		AGLAsset wallAsset = new AGLAsset(new AGLMesh(map.getMapVisualization().getWallMeshData(), pacmanShaderAttributes), wallTexture);
		AGLRenderObject wall = new AGLRenderObject(wallAsset);
		pacmanPart.addRenderObjects(wall);
		
//		AGLTexture groundTexture = new AGLTexture(new AGLResource("ground.png"));
//		AGLRenderObject ground = new AGLRenderObject(new AGLAsset(new AGLMesh(MeshDataPreset.GROUND.getData(), pacmanShaderAttributes), groundTexture));
//		ground.translateGlobal(0, -15, 0);
//		pacmanPart.addRenderObjects(ground);
		
//		AGLTexture mapPointTexture = new AGLTexture(new AGLResource("mappoint.png"));
//		AGLRenderObject mapPoints = new AGLRenderObject(new AGLAsset(new AGLMesh(map.getRasterMeshData(), pacmanShaderAttributes), mapPointTexture));
//		mapPoints.translateGlobal(0, -14, 0);
//		pacmanPart.addRenderObjects(mapPoints);
		
		gameView.addViewPart(pacmanPart);
		
		getCamera().lookAt(eye, at, up);
		
		GL11.glPointSize(10);
		Mouse.setGrabbed(true);
		AGLRenderController.bindViews(gameView);
		AGLRenderController.init(true, true);
		runGameLoop(10);
	}
	
	private Vector3f eye = new Vector3f(0, 20, -100), at = new Vector3f(0, 0, 0), up = new Vector3f(0, 1, 0);	
//	private float speed = 30f;
	private float eyeAngle;
	public void run(){
		eyeAngle += Mouse.getDX() * 0.1f;
		eyeAngle = clampAngle(eyeAngle);
		at = pacman.getPosition();
		eye.x = (float) (pacman.getPosition().x + Math.sin(Math.toRadians(eyeAngle)) * 30f);
		eye.z = (float) (pacman.getPosition().z + Math.cos(Math.toRadians(eyeAngle)) * 30f);
		getCamera().lookAt(eye, at, up);
		
		pacman.updateDirection(eyeAngle);
		if(pacman.getPosition().equals(ghosts.get(0).getPosition())){
//			Systesm.out.println("dead.");
		}
	}
	
	public static float clampAngle(float angle){
		if(angle > 360.0f) return angle - 360.0f;
		if(angle < 0) return 360.0f + angle;
		return angle;
	}

	public static void main(String[] args){
//		MapPoint m1 = new MapPoint(new Vector3f(0, 0, 0));
//		MapPoint m2 = new MapPoint(new Vector3f(0, 0, 50));
//		MapPoint m3 = new MapPoint(new Vector3f(50, 0, 50));
//		m1.connectWith(m2, true);
//		m2.connectWith(m3, true);
//		
//		new MapBuilder();
		
		Logger.setEnabled(false);
		new Pacman3d();
	}
}