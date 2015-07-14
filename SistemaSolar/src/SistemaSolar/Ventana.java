/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import javax.media.j3d.Canvas3D;
import javax.swing.JFrame;

public class Ventana extends JFrame {
  //private TheUniverse universe;

  public Ventana(Canvas3D canvas) {
    super();
    setLocation (920, 100);
    Visualization visualization = new Visualization (this, false, canvas);
    visualization.setVisible(true);
    pack();
  }
}
