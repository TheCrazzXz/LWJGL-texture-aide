import org.lwjgl.util.vector.*;

public class StaticShader extends ShaderProgram{
	
	private static String VERTEX_FILE = "shaders/vertexShader.txt";
	private static String FRAGMENT_FILE = "shaders/fragmentShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;


	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() 
	{
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	@Override
	protected void getAllUniformLocations()
	{
		location_transformationMatrix = super.getUniformLocations("transformationMatrix");
		location_projectionMatrix = super.getUniformLocations("projectionMatrix");
		location_viewMatrix = super.getUniformLocations("viewMatrix");
	}
	public void loadTransformationMatrix(Matrix4f matrix)
	{
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadViewMatrix(Camera camera)
	{
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection)
	{
		super.loadMatrix(location_projectionMatrix, projection);
	}
}