/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3f;

public class Planeta extends Astro {

    private TransformGroup hijo;//sera donde se enganche los hijos (satelites y anillos)

    public Planeta(float radio, String text, float rOrbita, int segR, int segT) {
        super(radio, text, rOrbita, segR, segT);
    }

    @Override
    protected void createTransladar() {
        objTrans2 = new TransformGroup();
        objTrans2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans1.addChild(objTrans2);

        // Se crea la matriz de Translacion
        Transform3D t = new Transform3D();
        t.setTranslation(new Vector3f(radioOrbita, 0, 0));
        objTrans2.setTransform(t);
        hijo = new TransformGroup();
        objTrans2.addChild(hijo);
    }

    public void AddAnillo(Anillo anillo) {
        hijo.addChild(anillo);//lo enganchamos de la transformacion de transladar
    }

    public void AddSatelite(Satelite satelite) {
        hijo.addChild(satelite);//lo enganchamos de la transformacion de transladar
    }
}
