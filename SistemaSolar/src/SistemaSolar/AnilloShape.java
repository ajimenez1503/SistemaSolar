/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Stripifier;
import java.util.ArrayList;
import javax.media.j3d.Appearance;
import javax.media.j3d.GeometryArray;
import javax.media.j3d.Shape3D;
import javax.media.j3d.TexCoordGeneration;
import javax.media.j3d.Transform3D;
import javax.vecmath.Point3f;
import javax.vecmath.Vector4f;

public class AnilloShape extends Shape3D {

    public AnilloShape(float radioExterior, float radioInterior, int res, Appearance ap) {

        double angle = (Math.PI * 2.0f) / res;
        int[] contornosPorPoligono = {2};
        int[] verticesPorContorno = {res, res};
        ArrayList<Point3f> v = new ArrayList<Point3f>();
        Transform3D t1 = new Transform3D();
        Transform3D t2 = new Transform3D();
        t1.rotY(angle);
        Point3f p = new Point3f(radioExterior, 0, 0);
        v.add(new Point3f(p));
        for (int i = 1; i < res; i++) {
            t1.transform(p);
            v.add(new Point3f(p));
        }
        t2.rotY(-angle);
        p.set(radioInterior, 0, 0);
        v.add(new Point3f(p));
        for (int j = 1; j < res; j++) {
            t2.transform(p);
            v.add(new Point3f(p));
        }
        Point3f[] aa = new Point3f[res + res];
        GeometryInfo gi = new GeometryInfo(GeometryInfo.POLYGON_ARRAY);
        gi.setCoordinates(v.toArray(aa));
        gi.setContourCounts(contornosPorPoligono);
        gi.setStripCounts(verticesPorContorno);
        //Se generan las normales Deben generarse antes que las cadenas de t r i ángulos
        NormalGenerator normGen = new NormalGenerator(Math.toRadians(30));
        //normGen.generateNormals(gi);
        normGen.generateNormals(gi);
        //Se generan las cadenas de t r i ángulos
        Stripifier cadenas = new Stripifier();
        cadenas.stripify(gi);
        // Se obt iene l a geometr í a
        GeometryArray geometria = gi.getGeometryArray();
        //darle coordenadas de texutra
        TexCoordGeneration textCoorder = new TexCoordGeneration(TexCoordGeneration.OBJECT_LINEAR, TexCoordGeneration.TEXTURE_COORDINATE_2, new Vector4f(1.0f, 0.0f, 0.0f, 0.0f), new Vector4f(0.0f, 0.0f, 1.0f, 0.0f));
        ap.setTexCoordGeneration(textCoorder);
        this.setGeometry(geometria);
        this.setAppearance(ap);
    }
}
