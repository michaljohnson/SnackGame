import java.awt.Point;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Spielklasse des Spiels Snake.
 * <p>
 * Ziel dieses Spiels ist es alle Goldstuecke einzusammeln und
 * die Tuer zu erreichen, ohne sich selber zu beissen oder in
 * die Spielfeldbegrenzung reinzukriechen.
 */
public class SnakeSpiel {
  private Schlange schlange;
  private Tuer tuer;
  private Spielfeld spielfeld;
  private boolean spielLaeuft = true;
  private Scanner scanner;
  private List<Point> goldStuecke;


  public static void main(String[] args) {
    new SnakeSpiel(3).spielen();
  }

  public SnakeSpiel(int anzahlGoldStuecke){
    if (anzahlGoldStuecke <0){
      throw new IllegalArgumentException("Anzahl Goldstücke darf nicht negativ sein!");
    }
    scanner = new Scanner(System.in);
    spielInitialisieren(anzahlGoldStuecke);

  }

  public SnakeSpiel(){
    scanner = new Scanner(System.in);
    spielInitialisieren(0);
  }

  public void addGoldStuecke(){
    goldStuecke.add(spielfeld.erzeugeZufallspunktInnerhalb());
  }

  public void  setAnzahlGoldStuecke(int anzahlGoldStuecke){
    if (anzahlGoldStuecke <0){
      throw new IllegalArgumentException("Anzahl Goldstücke darf nicht negativ sein!");
    }
    for(int i= 0; i< anzahlGoldStuecke; i++){
      goldStuecke.add(spielfeld.erzeugeZufallspunktInnerhalb());
    }
  }


  /**
   * Startet das Spiel.
   */
  public void spielen() {
    while (spielLaeuft) {
      zeichneSpielfeld();
      ueberpruefeSpielstatus();
      fuehreSpielzugAus();
    }
    scanner.close();
  }

  private void spielInitialisieren(int anzahlGoldStuecke) {
    tuer = new Tuer(0, 5);
    spielfeld = new Spielfeld(40, 10);
    goldStuecke = new ArrayList<>();
    int i = 0;
    while(i < anzahlGoldStuecke){
      addGoldStuecke();
      i++;
    }
    schlange = new Schlange(30, 2);
  }

  private void zeichneSpielfeld() {
    char ausgabeZeichen;
    for (int y = 0; y < spielfeld.gibHoehe(); y++) {
      for (int x = 0; x < spielfeld.gibBreite(); x++) {
        Point punkt = new Point(x, y);
        ausgabeZeichen = '.';
        if (schlange.istAufPunkt(punkt)) {
          ausgabeZeichen = '@';
        } else if (istEinGoldstueckAufPunkt(punkt)) {
          ausgabeZeichen = '$';
        } else if (tuer.istAufPunkt(punkt)) {
          ausgabeZeichen = '#';
        }
        if (schlange.istKopfAufPunkt(punkt)) {
          ausgabeZeichen = 'S';
        }
        System.out.print(ausgabeZeichen);
      }
      System.out.println();
    }
  }

  private boolean istEinGoldstueckAufPunkt(Point punkt) {
    return goldStuecke.contains(punkt);
  }

  private void ueberpruefeSpielstatus() {
    if (istEinGoldstueckAufPunkt(schlange.gibPosition())) {
      goldStuecke.remove(schlange.gibPosition());
      schlange.wachsen();
      System.out.println("Goldstueck eingesammelt.");
    }
    if (istVerloren()) {
      System.out.println("Verloren!");
      spielLaeuft = false;
    }
    if (istGewonnen()) {
      System.out.println("Gewonnen!");
      spielLaeuft = false;
    }
  }

  private boolean istGewonnen() {
    return goldStuecke.size() == 0 &&
      tuer.istAufPunkt(schlange.gibPosition());
  }

  private boolean istVerloren() {
    return schlange.istKopfAufKoerper() ||
      !spielfeld.istPunktInSpielfeld(schlange.gibPosition());
  }

  private void fuehreSpielzugAus() {
    char eingabe = liesZeichenVonTastatur();
    schlange.bewege(eingabe);
  }

  private char liesZeichenVonTastatur() {
    char konsolenEingabe = scanner.next().charAt(0);
    return konsolenEingabe;
  }
}