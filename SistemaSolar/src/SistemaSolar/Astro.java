/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import javax.media.j3d.Alpha;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;

public abstract class Astro extends BranchGroup {

    protected TransformGroup objTrans1, objTrans2, objTrans3;
    protected float radioOrbita;
    private boolean rotando;

    private RotationInterpolator rotacion;

    public Astro(float radio, String text, float rOrbita, int segR, int segT) {
        this.setCapability(Node.ENABLE_PICK_REPORTING);
        radioOrbita = rOrbita;
        generarTransformacion(segR, segT);
        generarModelo(radio, text);
        rotando = true;
    }

    public void switchRotacion() {
        rotando = !rotando;
        rotacion.setEnable(rotando);
    }

    //el modelo se añadie a objTrans para que se le apliquen las transformaciones
    private void generarModelo(float radio, String text) {

        // Set up the texture map
        TextureLoader loader = new TextureLoader(text, "RGB", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
                // Set up the texture attributes

        // Set up colors
        Color3f black = new Color3f(0.2f, 0.2f, 0.2f);
        Color3f white = new Color3f(0.75f, 0.75f, 0.75f);

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        ap.setMaterial(new Material(black, black, white, black, 0.0f));

        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        objTrans3.addChild(new Sphere(radio, primflags, ap));
    }

    private void createTranslacion(int segT) {
        objTrans1 = new TransformGroup();
        objTrans1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        this.addChild(objTrans1);
        // Se crea la matriz de Translacion
        Transform3D yAxis = new Transform3D();
        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        Alpha value = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, segT, 0, 0, 0, 0, 0);
        // Se crea el interpolador de rotación, las figuras iran rotando
        RotationInterpolator translacion = new RotationInterpolator(value, objTrans1, yAxis, 0.0f, (float) Math.PI * 2.0f);
        // Se le pone el entorno de activación y se activa
        translacion.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100000000.0));
        translacion.setEnable(true);
        // Se cuelga del grupo de transformación y este se devuelve
        objTrans1.addChild(translacion);
    }

    protected abstract void createTransladar();

    private void createRotacion(int segR) {
        objTrans3 = new TransformGroup();
        objTrans3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        objTrans2.addChild(objTrans3);
        // Se crea la matriz de Translacion
        Transform3D yAxis = new Transform3D();
        // Se crea un interpolador, un valor numérico que se ira modificando en tiempo de ejecución
        Alpha value = new Alpha(-1, Alpha.INCREASING_ENABLE, 0, 0, segR, 0, 0, 0, 0, 0);
        // Se crea el interpolador de rotación, las figuras iran rotando
        rotacion = new RotationInterpolator(value, objTrans3, yAxis, 0.0f, (float) Math.PI * 2.0f);
        // Se le pone el entorno de activación y se activa
        rotacion.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000000000.0));
        rotacion.setEnable(true);
        // Se cuelga del grupo de transformación y este se devuelve
        objTrans3.addChild(rotacion);
    }

    private void generarTransformacion(int segR, int segT) {
        createTranslacion(segT);
        createTransladar();
        createRotacion(segR);
    }
}
