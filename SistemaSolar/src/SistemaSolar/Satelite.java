/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class Satelite extends Astro {

    //vista
    private View viewPersp;

    public Satelite(float radio, String text, float radioOrbita, int segR, int segT) {
        super(radio, text, radioOrbita, segR, segT);
    }

    public Satelite(float radio, String text, float radioOrbita, int segR, int segT, boolean vista) {
        super(radio, text, radioOrbita, segR, segT);
        if (vista) {
            crearCamara();
        }
    }

    @Override
    protected void createTransladar() {
        objTrans2 = new TransformGroup();
        objTrans2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans1.addChild(objTrans2);
        Transform3D t = new Transform3D();
        t.setTranslation(new Vector3f(radioOrbita, 0, 0));
        objTrans2.setTransform(t);
    }

    public void crearCamara() {
        // Vista en perspectiva
        // TransformGroup para posicionar y orientarlavista
        Transform3D transformPersp = new Transform3D();
        transformPersp.lookAt(new Point3d(0, 0, 0), new Point3d(1, 0, 1), new Vector3d(0, 1, 0));
        transformPersp.invert();
        TransformGroup tgPersp = new TransformGroup(transformPersp);
        ViewPlatform vpPersp = new ViewPlatform();
        tgPersp.addChild(vpPersp);
        // Definición de la vista en perspectiva
        viewPersp = new View();
        viewPersp.setPhysicalBody(new PhysicalBody());
        viewPersp.setPhysicalEnvironment(new PhysicalEnvironment());
        viewPersp.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        viewPersp.setFieldOfView(Math.toRadians(90));
        viewPersp.setFrontClipDistance(0.1);
        viewPersp.setBackClipDistance(5000);

        objTrans3.addChild(tgPersp);
        viewPersp.attachViewPlatform(vpPersp);
    }

    public void fijarcamara(Canvas3D c) {
        viewPersp.addCanvas3D(c);
    }

    public void quitarcamara() {
        viewPersp.removeAllCanvas3Ds();
    }

}
