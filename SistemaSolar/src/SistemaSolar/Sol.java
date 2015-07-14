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
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.PointLight;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;

public class Sol extends BranchGroup {

    public Sol(float radio, String text) {
        generarModelo(radio, text);
        generarLuz();
    }

    private void generarLuz() {
        Color3f light1Color = new Color3f(1f, 1f, 1f);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 5000000f);
        Point3f lightPosition = new Point3f(0f, 0f, 0f);
        Point3f lightAttenuation = new Point3f(1f, 0f, 0f);
        PointLight light = new PointLight(light1Color, lightPosition, lightAttenuation);
        light.setInfluencingBounds(bounds);
        this.addChild(light);
    }

    private void generarModelo(float radio, String text) {

        // Set up the texture map
        TextureLoader loader = new TextureLoader(text, "RGB", new Container());
        Texture texture = loader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

        // Set up colors
        Color3f black = new Color3f(0.1f, 0.1f, 0.1f);
        Color3f white = new Color3f(0.75f, 0.75f, 0.75f);

        // Set up the texture attributes
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);
        ap.setMaterial(new Material(black, white, white, black, 0.0f));
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        this.addChild(new Sphere(radio, primflags, ap));
    }

    public void addPlaneta(Planeta p) {
        this.addChild(p);//lo enganchamos de la transformacion de transladar
    }
}
