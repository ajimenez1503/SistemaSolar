/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import java.awt.AWTEvent;
import java.awt.event.KeyEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.WakeupOnAWTEvent;

public class Vistas extends Behavior {

    // Objeto referenciado
    private Escena esc;

    //eventos
    private WakeupOnAWTEvent condicionRespuesta = new WakeupOnAWTEvent(KeyEvent.KEY_PRESSED);

    public Vistas(Escena esc1) {
        esc=esc1;
    }

    @Override
    public void initialize() {
        wakeupOn(condicionRespuesta);
        setEnable(true);
    }

    @Override
    public void processStimulus(Enumeration criterios) {
        //Se extrae de los criterios la tecla pulsada
        WakeupOnAWTEvent unCriterio = (WakeupOnAWTEvent) criterios.nextElement();
        AWTEvent[] eventos = unCriterio.getAWTEvent();
        KeyEvent tecla = (KeyEvent) eventos[0];
        switch (tecla.getKeyCode()) {
            case KeyEvent.VK_LEFT: // Cursor izquierdo
                //fijo camara nave
                esc.cambiarVistaNave();
                break;
            case KeyEvent.VK_RIGHT: // Cursor derecho
                //fijo la camara en la luna
                esc.cambiarVistaLuna();
                break;
            case KeyEvent.VK_UP: // Cursor arriba
                //fijar camara por defecto
                esc.cambiarVistaDefault();
                break;
            default:
                break;
        }
        setEnable(true);
        wakeupOn(condicionRespuesta);
    }
}
