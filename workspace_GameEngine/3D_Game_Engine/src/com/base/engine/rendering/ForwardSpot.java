package com.base.engine.rendering;

import com.base.engine.components.BaseLight;
import com.base.engine.components.PointLight;
import com.base.engine.components.SpotLight;
import com.base.engine.core.Matrix4f;
import com.base.engine.core.Transform;

public class ForwardSpot extends Shader
{
	
	private static final ForwardSpot instance = new ForwardSpot();
	
	public static ForwardSpot getInstance(){return instance;}
	
	public ForwardSpot()
	{
		super();
		
		AddVertexShaderFromFile("forward-spot.vs.glsl");	
		AddFragmentShaderFromFile("forward-spot.fs.glsl");
		
		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);
		
		compileShader();
		
		AddUniform("model");
		AddUniform("MVP");
		
		AddUniform("specularIntensity");
		AddUniform("specularPower");
		AddUniform("eyePos");
		
		AddUniform("spotLight.pointLight.base.color");
		AddUniform("spotLight.pointLight.base.intensity");
		AddUniform("spotLight.pointLight.atten.constant");
		AddUniform("spotLight.pointLight.atten.linear");
		AddUniform("spotLight.pointLight.atten.exponent");			
		AddUniform("spotLight.pointLight.position");
		AddUniform("spotLight.pointLight.range");
		AddUniform("spotLight.direction");
		AddUniform("spotLight.cutoff");
		
		
	}
	
	
	public void updateUniforms(Transform transform,  Material material, RenderingEngine renderingEngine)
	{
		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f projectedMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();;
		
		setUniform("model", worldMatrix);	
		setUniform("MVP", projectedMatrix);
		
		
		setUniformf("specularIntensity", material.getFloat("specularIntensity"));
		setUniformf("specularPower", material.getFloat("specularPower"));
		
		setUniform("eyePos", renderingEngine.getMainCamera().getTransform().getTransformedPos());
		
		setUniformSpotLight("spotLight", (SpotLight)renderingEngine.getActivelight());
	}
	
	public void setUniformBaseLight(String uniformName, BaseLight baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}
	
	public void setUniformPointLight(String uniformName, PointLight pointLight)
	{
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());		
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}
	
	public void setUniformSpotLight(String uniformName, SpotLight spotLight)
	{
		setUniformPointLight(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
