import java.awt.Point;
import java.util.Random;
import java.util.List;
import java.util.ArrayList;
public class Goldstueck {
    private int wert;
    private Spielfeld spielfeld;
    private int score;
    private Point point;
    private Random random;


    public Goldstueck(){
        point = spielfeld.erzeugeZufallspunktInnerhalb();
        wert = random.nextInt(5);

    }

    public void setWert(int wert){
        this.wert = wert;
    }

    public int getWert(){
        return wert;
    }

    public int getScore(){
        return score;
    }

    public void sumUpScore(){

    }




}
