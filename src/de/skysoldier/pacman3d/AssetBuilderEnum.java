package de.skysoldier.pacman3d;

import de.skysoldier.abstractgl2.mklmbversion.lib.AGLAsset;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLGlslAttribute;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLMesh;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLResource;
import de.skysoldier.abstractgl2.mklmbversion.lib.AGLTexture;

public enum AssetBuilderEnum {
	
	COIN(MeshDataPreset.COIN, new AGLTexture(new AGLResource("coin.png")));
	
	private MeshDataPreset preset;
	private AGLAsset asset;
	private AGLTexture texture;
	
	AssetBuilderEnum(MeshDataPreset preset, AGLTexture texture){
		this.preset = preset;
		this.texture = texture;
	}
	
	public void build(AGLGlslAttribute attributes[]){
		asset = new AGLAsset(new AGLMesh(preset.getData(), attributes), texture);
	}
	
	public AGLAsset getAsset(){
		return asset;
	}
}
