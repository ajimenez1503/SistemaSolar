/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.utils.image.TextureLoader;
import java.awt.Container;
import javax.media.j3d.Appearance;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

public class Anillo extends BranchGroup {


    public Anillo(float ExteriorRadio, float InteriorRadio, String text) {
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

        AnilloShape abajo = new AnilloShape(ExteriorRadio, InteriorRadio, 20, ap);
        AnilloShape arriba = new AnilloShape(ExteriorRadio, InteriorRadio, 80, ap);

        TransformGroup objTrans1 = new TransformGroup();
        Transform3D t1 = new Transform3D();
        t1.rotX(Math.PI);
        objTrans1.setTransform(t1);
        
        //creamos una separacion entre ambos para que no este uno sobre otro. 
        TransformGroup objTrans2 = new TransformGroup();
        Transform3D t2 = new Transform3D();
        t2.setTranslation(new Vector3f(0f, 1f, 0f));
        objTrans2.setTransform(t2);
 
        this.addChild(objTrans1);
        objTrans1.addChild(objTrans2);
        objTrans2.addChild(abajo);
        
        this.addChild(arriba);
    }
}
