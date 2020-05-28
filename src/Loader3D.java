import java.io.File;
/*----------------TEXTURES----------------*/
import java.io.FileInputStream;
import org.newdawn.slick.opengl.*;
import org.newdawn.slick.*;
/*-----------------------------------------*/

import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL15.*;
import org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.util.glu.*;

import java.util.List;
import java.util.ArrayList;

public class Loader3D 
{
    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    public RawModel loadToVAO(float[] positions, float[] textureCoords, int[] indices)
    {
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String filePath)
    {
        Texture texture = null;
        try 
        {
            texture = TextureLoader.getTexture("PNG", new FileInputStream(filePath));
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);

        return textureID;
        
    }

    public void cleanUp()
    {
        for(int vao : vaos)
        {
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo : vbos)
        {
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture : textures)
        {
            GL11.glDeleteTextures(texture);
        }
    }

    private int createVAO()
    {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    private void storeDataInAttributeList(int attributeNumber, int coordonateSize, float[] data)
    {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber, coordonateSize, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }
    private void unbindVAO()
    {
        GL30.glBindVertexArray(0);
    }
    private void bindIndicesBuffer(int[] indices)
    {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }
    private IntBuffer storeDataInIntBuffer(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private FloatBuffer storeDataInFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}