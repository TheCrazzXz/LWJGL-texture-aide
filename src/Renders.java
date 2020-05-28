import org.lwjgl.opengl.*;
import org.lwjgl.opengl.GL11.*;
import org.lwjgl.opengl.GL30.*;
import org.lwjgl.opengl.GL20.*;

import org.lwjgl.util.glu.*;
import org.lwjgl.util.vector.*;


public class Renders 
{
    Loader3D loader = new Loader3D();
    StaticShader shader = new StaticShader();
    Renderer renderer = new Renderer(shader);
    
    Camera camera = new Camera();

    //Suite Exemple :
    RawModel model;
    TexturedModel staticModel;
    Entity entity;
    
    RawModel f_model;
    TexturedModel f_staticmodel;
    Entity block1;
    Entity block2;
    
    
    public Renders()
    {

        float[] eMat = {
            -0.5f, 0, -5, //position
            0, 0, 0,  //rotation
            0.01f        //taille
        };
        
        float[] f_eMat = {
                0, 0, -2, //position
                0, 0, 0,  //rotation
                0.1f        //taille
            };
        float[] f_eMat2 = {
                0, 1, -2, //position
                0, 0, 0,  //rotation
                0.1f        //taille
            };
        
        block1 = prepareBlockEntity(f_eMat, "Ressources/textures/texture.png");
        block2 = prepareBlockEntity(f_eMat2, "Ressources/textures/texture.png");

        model = LoaderOBJ.loadObjModel("Ressources/models/model1.obj", loader);
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("Ressources/textures/texture.png")));
        entity = new Entity(staticModel, new Vector3f(eMat[0], eMat[1], eMat[2]), eMat[3], eMat[4], eMat[5], eMat[6]);
        
    }
    public void prepare()
    {
        renderer.prepare();
    }

    public void render()
    {
        //Suite exemple :
        
        //entity.increasePosition(0.002f, 0, 0);
        entity.increaseRotation(0, 1, 0);
        
        block1.increaseRotation(1, 1, 1);
        
        block2.increaseRotation(1, 0, 1);
        
        block2.setScale(block2.getScale() + 0.002f);
        
        if(block2.getPosition() == )
        {
        	block2.increasePosition(0.002f, 0, 0);
        }
        		
        
        /*---------------*/
        prepare();
        shader.start();
        
        /*---------------*/

        updateCamera();
        //Fin exemple :
        renderer.render(entity, shader);
        renderer.render(block1, shader);
        renderer.render(block2, shader);
        //System.out.println("Postion réctangle" + " : " + "(" + entity.getPosition() + ", " + entity.getScale() + ")");
        /*---------------*/
        shader.stop();
        /*---------------*/
    }
    public Entity prepareEntity(float[] p_eMat, String TexturePath, float[] p_vertices, int[] p_indices, float[] p_textureCoords)
    {
    	RawModel e_model = loader.loadToVAO(p_vertices, p_textureCoords, p_indices);
    	TexturedModel e_staticmodel = new TexturedModel(e_model, new ModelTexture(loader.loadTexture(TexturePath)));
   
        return new Entity(e_staticmodel, new Vector3f(p_eMat[0], p_eMat[1], p_eMat[2]), 
        		p_eMat[3], p_eMat[4], p_eMat[5], p_eMat[6]);
    }
    
    public Entity prepareBlockEntity(float[] p_eMat, String TexturePath)
    {	
    	RawModel e_model = loader.loadToVAO(Block.e_vertices, Block.e_textureCoords, Block.e_indices);
    	TexturedModel e_staticmodel = new TexturedModel(e_model, new ModelTexture(loader.loadTexture(TexturePath)));
    	
    	return new Entity(e_staticmodel, new Vector3f(p_eMat[0], p_eMat[1], p_eMat[2]), 
        		p_eMat[3], p_eMat[4], p_eMat[5], p_eMat[6]);
    }
    
    public void updateCamera()
    {
    	shader.loadViewMatrix(camera);
    }
    public void move()
    {
    	camera.move();
    }
    /*
    public void cleanUpShader()
    {
        shader.cleanUp();
    }
    */
}