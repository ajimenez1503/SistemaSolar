/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import java.awt.AWTEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;

import javax.media.j3d.WakeupOnAWTEvent;

public class Pick extends Behavior {

    private WakeupOnAWTEvent condition;
    private PickCanvas pickCanvas;

    public Pick(Canvas3D aCanvas, BranchGroup bg) {
        condition = new WakeupOnAWTEvent(MouseEvent.MOUSE_CLICKED);
        pickCanvas = new PickCanvas(aCanvas, bg);
        setEnable(true);
    }

    @Override
    public void initialize() {
        setEnable(true);
        wakeupOn(condition);
    }

    @Override
    public void processStimulus(Enumeration cond) {
        WakeupOnAWTEvent c = (WakeupOnAWTEvent) cond.nextElement();
        AWTEvent[] e = c.getAWTEvent();
        MouseEvent mouse = (MouseEvent) e[0];
        pickCanvas.setShapeLocation(mouse);
        PickResult result = pickCanvas.pickClosest();
        if (result != null) {
            BranchGroup as = (BranchGroup) result.getNode(PickResult.BRANCH_GROUP);
            if (as instanceof Astro) {
                ((Astro) as).switchRotacion();//parar la rotacion
            }
        }
        setEnable(true);
        wakeupOn(condition);
    }

}
