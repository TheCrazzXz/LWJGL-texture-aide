import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

public class DisplayManager
{
    public static int width = 1280; //Largeur (x) fenêtre
    public static int height = 720; //Hauteur (y) fenêtre
    public static String title = "Titre"; //Titre de la fenêtre

    private static int FPS_CAP = 60; //FPS

    public static void createDisplay()
    {
        ContextAttribs attribs = new ContextAttribs(3, 2)
        .withForwardCompatible(true)
        .withProfileCore(true);

        try 
        {
            Display.setDisplayMode(new DisplayMode(width, height));
            Display.setTitle(title);
            Display.create(new PixelFormat(), attribs);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        GL11.glViewport(0, 0, width, height);
        
    }
    public static void updateDisplay()
    {
        Display.sync(FPS_CAP);
        Display.update();
    }
    public static void closeDisplay()
    {
        Display.destroy();
    }
    public static boolean isClosed_Window()
    {
        return Display.isCloseRequested();
    }
}