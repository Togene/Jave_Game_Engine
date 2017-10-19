package com.base.game;

import com.base.engine.core.GameComponent;
import com.base.engine.core.Transform;
import com.base.engine.rendering.BasicShader;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.Shader;

public class MeshRenderer implements GameComponent
{
	private Material material;
	private Mesh mesh;
	
	public MeshRenderer(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}
	@Override
	public void input(Transform transform, float delta) {}
	
	@Override
	public void update(Transform transform, float delta) {}
	
	
	@Override
	public void render(Transform transform, Shader shader)
	{
		//transform.getTransformation(), transform.getProjectedTransformation()
		shader.bind();
		shader.updateUniforms(transform, material);
		mesh.draw();		
	}

}
