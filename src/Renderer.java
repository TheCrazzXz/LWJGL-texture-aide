import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;


public class Renderer {
	
	private static final float fov = 70;
	private static final float zNear = 0.1f;
	private static final float zFar = 1000;
	
	private Matrix4f projectionMatrix;
	
	public Renderer(StaticShader shader)
	{
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void prepare() 
	{
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		GL11.glClearColor(0, 1, 1, 1);
	}

	public void render(Entity entity, StaticShader shader) 
	{
		TexturedModel model = entity.getModel();
		RawModel rawModel = model.getRawModel();
        
        //GL20.glUseProgram(shader.programID);

		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
		shader.loadTransformationMatrix(transformationMatrix);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
		//GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        //GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, rawModel.getVertexCount());
		GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}
	
	private void createProjectionMatrix()
	{
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
		float x_scale = y_scale / aspectRatio;
		float frustum_length = zFar - zNear;

		projectionMatrix = new Matrix4f();
		projectionMatrix.m00 = x_scale;
		projectionMatrix.m11 = y_scale;
		projectionMatrix.m22 = -((zFar + zNear) / frustum_length);
		projectionMatrix.m23 = -1;
		projectionMatrix.m32 = -((2 * zNear * zFar) / frustum_length);
		projectionMatrix.m33 = 0;
	}
}



/*-------------------------------------------------------------------------------------------------------------------------------------*/

/*
import java.io.File;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GL20.*;

import org.lwjgl.util.glu.*;

import org.lwjgl.util.vector.*;

public class Renderer 
{
    private static final float fov = 70;
    private static final float zNear = 0.1f;
    private static final float zFar = 1000;

    private Matrix4f projectionMatrix;

    public Renderer(StaticShader shader)
    {
        createProjectionMatrix();
        //shader.start();
        shader.loadTransformationMatrix(projectionMatrix);
        shader.loadProjectionMatrix(projectionMatrix);
        //shader.loadTransformationMatrix(transformationMatrix);
        //shader.stop();
    }

    public void prepare()
    {
        //GL11.glClearColor(0, 0, 0, 1); //Couleur arrière plan
        //GL11.glClear(GL11.GL_COLOR_BUFFER_BIT); //Clear l'ecran

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0, 0.3f, 0.0f, 1);
    }
    public void render(Entity entity, StaticShader shader)
    {
        //shader.start();
        TexturedModel model = entity.getModel();
        RawModel rawModel = model.getRawModel();
        GL30.glBindVertexArray(rawModel.getVaoID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        Matrix4f transformationMatrix = Maths.createTransformationMatrix(entity.getPosition(), entity.getRotX(), entity.getRotY(), entity.getRotZ(), entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawModel.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        //shader.stop();
    }
    private void createProjectionMatrix()
    {
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        //float aspectRatio = (float)Display.getDisplayMode().getWidth() / (float) Display.getDisplayMode().getHeight();
        float yScale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float xScale = yScale / aspectRatio;
        float frustum_length = zFar - zNear;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = xScale;
        projectionMatrix.m11 = yScale;
        projectionMatrix.m22 = ((zFar + zNear) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * zNear * zFar) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}*/
