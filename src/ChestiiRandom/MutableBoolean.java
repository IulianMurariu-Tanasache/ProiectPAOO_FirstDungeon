package ChestiiRandom;

/*! \class MutableBoolean
    \brief Aveam nevoie e un bool care sa-l modific intr-o functie.

    Boolean e imutable. Mi-am facut singur clasa mutable.
 */
public class MutableBoolean {
    public boolean val;

    public MutableBoolean()
    {
        val = false;
    }

    public MutableBoolean(boolean v)
    {
        val = v;
    }
}
