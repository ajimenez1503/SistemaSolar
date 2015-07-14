/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.media.j3d.Canvas3D;
import javax.swing.JDialog;

class Visualization extends JDialog {

    private Ventana controlWindow;

    public Visualization(Ventana parent, boolean modal, Canvas3D canvas) {
        super(parent, modal);
        controlWindow = parent;
        setLocation(500,100);
        Container contentPane = this.getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(canvas, BorderLayout.CENTER);
        pack();
    }
}
