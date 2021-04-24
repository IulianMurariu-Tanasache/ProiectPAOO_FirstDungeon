package GameObject;

import java.awt.*;

/*! \class GameObject
 *  \brief Clasa abstracta care va reprezenta toate obiectele jocului.
 *
 *  Are 3 functii abstracte care trebuiesc implementate in carele care o mostenesc: tick(), render(), getBounds().
 */
public abstract class GameObject {

    protected int x,y;              //!< Pozitia obiectului pe ecran.
    protected float scale;            //!< Scara de reprezentare.
    protected int speedX, speedY;   //!< Viteza obiectului.
    protected ID id;                //!< Id/Tag-ul obiectului.

    /*! \fn public GameObject(int x, int y, ID i)
        \brief Constructor cu scale default egal cu 1.

        \param x Pozitia pe x.
        \param y Pozitia pe y.
        \param i ID/Tag-ul obiectului. Vezi Enum ID.
     */
    public GameObject(int x, int y, ID i)
    {
        this.x = x;
        this.y = y;
        this.scale = 1f;
        speedX = 0;
        speedY = 0;
        id = i;
    }

    /*! \fn public GameObject(int x, int y, ID i)
       \brief Constructor cu scale diferit.

       \param x Pozitia pe x.
       \param y Pozitia pe y.
       \param scale Scara de reprezentare a obiectului.Pentru marire/micsorare.
       \param i ID/Tag-ul obiectului. Vezi Enum ID.
    */
    public GameObject(int x, int y, float scale, ID i)
    {
        this.x = x;
        this.y = y;
        this.scale = scale;
        speedX = 0;
        speedY = 0;
        id = i;
    }

    /*! \fn public abstract void tick()
        \brief Functie de update. Trebuie implementata de orice obiect.
     */
    public abstract void tick();

    /*! \fn public abstract void render(Graphics g, double elapsed)
        \brief Functie de render. Trebuie implementata de orice obiect.

        \param g Comopnenta de grafica. Necesara pentru functia de desenare.
        \param elapsed timpul trecut intre 2 render-uri consecutive. Necesar pentru limitarea animatiilor, unde e cazul.
     */
    public abstract void render(Graphics g, double elapsed);

    /*! \fn public abstract void mapCollision(Room r)
        \brief Functia de coliziune cu harta. Trebuie implementata de orice obiect.

        \param r Camera curenta. Necesara pentru a extrage tile-urile inconjuratoare.
     */
    public abstract void mapCollision();

    /*! \fn public abstract Rectangle getBounds()
        \brief Functie care returneaza dreptunghiul de coliziune. Trebuie implementata de orice obiect.
     */
    public abstract Rectangle getBounds();

    /*! \fn public int getX()
       \brief Getter X.
    */
    public int getX() {
        return x;
    }

    /*! \fn public void setX(int x)
        \brief Setter X.
        \param x Pozitia pe X.
     */
    public void setX(int x) {
        this.x = x;
    }

    /*! \fn public int getY()
      \brief Getter Y.
   */
    public int getY() {
        return y;
    }

    /*! \fn public void setY(int y)
        \brief Setter Y.
        \param x Pozitia pe Y.
     */
    public void setY(int y) {
        this.y = y;
    }

    /*! \fn public int getSpeedX()
      \brief Getter viteza pe X.
   */
    public float getSpeedX() {
        return speedX;
    }

    /*! \fn public void setSpeedX(int speedX)
        \brief Setter viteza pe X
        \param x Viteza pe X
     */
    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    /*! \fn public int getSpeedY()
      \brief Getter viteza pe Y.
   */
    public float getSpeedY() {
        return speedY;
    }

    /*! \fn public void setSpeedY(int speedY)
       \brief Setter viteza pe Y.
       \param x Viteza pe Y.
    */
    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public float getScale() {
        return scale;
    }
}
