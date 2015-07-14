/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.loaders.IncorrectFormatException;
import com.sun.j3d.loaders.ParsingErrorException;
import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import java.io.FileNotFoundException;
import javax.media.j3d.Alpha;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.RotPosPathInterpolator;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;

public class Nave extends BranchGroup {

    //vista
    private View viewPersp;

    private TransformGroup objTrans1, rec;

    public Nave(float s, String m) {
        setScale(s);
        recorrido();
        ObjectFile archivo = new ObjectFile(ObjectFile.RESIZE | ObjectFile.STRIPIFY | ObjectFile.TRIANGULATE);
        try {
            Scene modelo = archivo.load(m);
            rec.addChild(modelo.getSceneGroup());
        } catch (FileNotFoundException | ParsingErrorException | IncorrectFormatException e) {
            System.err.println(e);
            System.exit(1);
        }
        crearCamara();
    }

    private void setScale(float s) {
        objTrans1 = new TransformGroup();
        Transform3D t = new Transform3D();
        t.setScale(s);
        objTrans1.setTransform(t);
        this.addChild(objTrans1);
    }

    public void recorrido() {
        rec = new TransformGroup();
        rec.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans1.addChild(rec);
        //transformacion
        Transform3D t = new Transform3D();

        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        Alpha value = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, 5000, 0, 0, 0, 0, 0);
        //Alpha value = new Alpha(-1, 5000);    

        //posiciones
        float[] alphas = {0.0f, 0.25f, 0.5f, 0.75f, 1.0f};
        Point3f[] positions = {new Point3f(-10.0f, 5f, 0.0f), new Point3f(0.0f, 3f, 10.0f), new Point3f(10.0f, 3f, 0.0f), new Point3f(0f, 3f, -10.0f), new Point3f(-10f, 3f, 0.0f)};

        //rotaciones
        Quat4f[] rotations = new Quat4f[5];
        for (int i = 0; i < 5; i++) {
            rotations[i] = new Quat4f();
        }
        //cambiar rotacion en funcion de las posiciones
        rotations[0].set(new AxisAngle4f(0.0f, 0.0f, 1.0f, (float) Math.toRadians(60)));
        rotations[1].set(new AxisAngle4f(1.0f, 0.0f, 0.0f, (float) Math.toRadians(60)));
        rotations[2].set(new AxisAngle4f(0.0f, 1.0f, 0.0f, (float) Math.toRadians(60)));
        rotations[3].set(new AxisAngle4f(1.0f, 0.0f, 1.0f, (float) Math.toRadians(60)));
        rotations[4].set(new AxisAngle4f(0.0f, 0.0f, 1.0f, (float) Math.toRadians(60)));

        RotPosPathInterpolator anim = new RotPosPathInterpolator(value, rec, t, alphas, rotations, positions);

        anim.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 10000000.0));
        anim.setEnable(true);
        // Se cuelga del grupo de transformación y este se devuelve
        rec.addChild(anim);
    }

    public void crearCamara() {
        // Vista en perspectiva
        // TransformGroup para posicionar y orientarlavista
        Transform3D transformPersp = new Transform3D();
        transformPersp.lookAt(new Point3d(0, 0, 0), new Point3d(5, -10, 5), new Vector3d(0, 1, 0));
        transformPersp.invert();
        TransformGroup tgPersp = new TransformGroup(transformPersp);
        ViewPlatform vpPersp = new ViewPlatform();
        tgPersp.addChild(vpPersp);
        // Definición de la vista en perspectiva
        viewPersp = new View();
        viewPersp.setPhysicalBody(new PhysicalBody());
        viewPersp.setPhysicalEnvironment(new PhysicalEnvironment());
        viewPersp.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        viewPersp.setFieldOfView(Math.toRadians(45));
        viewPersp.setFrontClipDistance(0.1);
        viewPersp.setBackClipDistance(5000);

        rec.addChild(tgPersp);
        viewPersp.attachViewPlatform(vpPersp);
    }

    public void fijarcamara(Canvas3D c) {
        viewPersp.addCanvas3D(c);
    }

    public void quitarcamara() {
        viewPersp.removeAllCanvas3Ds();
    }
}
