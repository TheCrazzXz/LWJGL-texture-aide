import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class Main 
{
    boolean running = false; //variable qui détermine si le programme est en cours d'execution

    Renders renders;

    public Main()
    {   
        DisplayManager.createDisplay();
        renders = new Renders();
        gamePrepare();
    }

    public void start()
    {
        running = true; //demarrer
        loop(); // aller à la boucle principale
    }
    public void stop()
    {
        running = false; //fermer
    }
    public void exit()
    {
        renders.loader.cleanUp();
        //renderer.shader.cleanUp();

        DisplayManager.closeDisplay(); //detruire la fenêtre
        System.exit(0); //arrêter le processus
    }
    public void gamePrepare()
    {
    	
    }
    public void loop()
    {
        //Boucle principale :
        while(running)
        {
            if(DisplayManager.isClosed_Window()) stop();
            
            render();
            renders.camera.move();
            update();
        }
        exit();
    }
    public void update()
    {		
        DisplayManager.updateDisplay();
    }
    public void render()
    {
        //renderer.prepare();
        renders.render();
    }


    public static void main(String[] args)
    {
        //System.setProperty("org.lwjgl.librarypath", new File("native_win").getAbsolutePath());
        Main main = new Main();
        
        main.start();
    }
}