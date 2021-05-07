package GUI;

public class GUIThread implements Runnable{

    /*
     *
     *  OUT OF USE!
     *
     */

    private boolean isRunning;
    private Thread UIThread;

    public void start()
    {
        isRunning = true;
        //GameState.getInstance();
        UIThread = new Thread(this);
        UIThread.start();
    }

    /*! @fn private void stop()
     *  @brief Metoda seteaza flagul de rualre pe false si asteapta ca game_thread-ul sa isi incheie executia
     */
    public void stop()
    {
        isRunning = false;
        //GameState.getInstance().clearUI();
        try{
            UIThread.join();
        }catch(InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(isRunning) {

        }
    }
}
