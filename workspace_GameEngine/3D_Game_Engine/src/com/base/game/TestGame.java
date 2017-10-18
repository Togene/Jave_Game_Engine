package com.base.game;

import com.base.engine.core.Game;
import com.base.engine.core.GameObject;
import com.base.engine.core.Texture;
import com.base.engine.core.Time;
import com.base.engine.core.Transform;
import com.base.engine.core.Vector2f;
import com.base.engine.core.Vector3f;
import com.base.engine.rendering.Attenuation;
import com.base.engine.rendering.BaseLight;
import com.base.engine.rendering.Camera;
import com.base.engine.rendering.Material;
import com.base.engine.rendering.Mesh;
import com.base.engine.rendering.PhongShader;
import com.base.engine.rendering.PointLight;
import com.base.engine.rendering.RenderUtil;
import com.base.engine.rendering.Shader;
import com.base.engine.rendering.SpotLight;
import com.base.engine.rendering.Vertex;
import com.base.engine.rendering.Window;

public class TestGame implements Game 
{
	private Camera camera;
	
	private GameObject root;
	
	public void init()
	{
		root = new GameObject();
		camera = new Camera();
		
		
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;
		
		
		Vertex[] vertices = new Vertex[]
				{
						new Vertex(new Vector3f(-fieldWidth, 0.0f, -fieldDepth),  new Vector2f(0.0f, 0.0f)),
						new Vertex(new Vector3f(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2f(0.0f, 1.0f)),
						new Vertex(new Vector3f(fieldWidth * 3, 0.0f, -fieldDepth),   new Vector2f(1.0f, 0.0f)),
						new Vertex(new Vector3f(fieldWidth * 3, 0.0f, fieldDepth* 3),       new Vector2f(1.0f, 1.0f)),
				};
		int[] indices = new int[] {0, 1, 2,
								   2, 1, 3
		};
		
		Material material = new Material(new Texture("test.png"), new Vector3f(1.0f, 1.0f, 1.0f), 1, 8);
		Mesh mesh = new Mesh(vertices, indices, true);
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);
		root.addComponent(meshRenderer);
		Transform.setProjection(70f, Window.getWidth(), Window.GetHeight(), 0.1f, 1000);
		Transform.setCamera(camera);
		
	}
	
	public void input()
	{
		camera.input();
		root.input();
	}
	
	float temp = 0.0f;
	
	public void update()
	{
		root.update();
		root.getTransform().setTranslation(0, -1, 5);
	}
	
	
	public void render()
	{
		root.render();
	}
}