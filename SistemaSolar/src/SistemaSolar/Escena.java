/* (C) Programmed by:
   Antonio Jimenez Martínez
   Andres Ortiz Corrales

Practica Sistemas graficos. Sistema Solar
Version:1
*/
package SistemaSolar;

import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.util.Random;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;



public class Escena {
        private BranchGroup root;
        private SimpleUniverse universe;
        private Satelite luna;
        private Nave n;
        private Canvas3D canvas;
        private View view;
        
        public Escena(){
            root = new BranchGroup();
            crearVistaPlanta();
            crearBackground();
            crearLuzAmbiental();
            crearVista();
            addnave();
            crearSistemaSolar();
            addPick();
            addVistas();
            compilar();
            fijarcamaras();
        }
        
        public void crearVistaPlanta(){

            //crear vista plana en otra venta
            // TransformGroup para posicionar y orientarlavista
            Transform3D transformPlanta = new Transform3D ( ) ;
            transformPlanta.lookAt (new Point3d (0,1000,0) , new Point3d (0,0,0) ,new Vector3d (0 ,0 ,-1) ) ;
            transformPlanta.invert( ) ;
            TransformGroup tgPlanta = new TransformGroup ( transformPlanta ) ;
            ViewPlatform vpPlanta = new ViewPlatform ( ) ;
            tgPlanta.addChild (vpPlanta) ;
            // Definición de la vista paralela
            View viewPlanta = new View ( ) ;
            viewPlanta.setPhysicalBody (new PhysicalBody ( ) ) ;
            viewPlanta.setPhysicalEnvironment (new PhysicalEnvironment ( ) ) ;
            viewPlanta.setProjectionPolicy(View.PARALLEL_PROJECTION) ;
            viewPlanta.setScreenScalePolicy (View.SCALE_EXPLICIT) ;
            viewPlanta.setScreenScale(0.0003);
            viewPlanta.setFrontClipDistance(0.01);
            viewPlanta.setBackClipDistance(2000);

            Canvas3D canvasPlana = new Canvas3D (SimpleUniverse.getPreferredConfiguration()) ;
            canvasPlana.setSize(800,450);

            viewPlanta.addCanvas3D (canvasPlana) ;
            viewPlanta.attachViewPlatform (vpPlanta) ;

            root.addChild(tgPlanta);
            Ventana ventana=new Ventana(canvasPlana);   
        }
           
        public void  crearBackground(){
             //branchgroup
            TheBackground bg= new TheBackground();
            root.addChild(bg);
        }
        
        public void crearLuzAmbiental(){
            //luz ambiental
            BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 50000000f);
            AmbientLight ambientLight = new AmbientLight(true,new Color3f(0f,0f,0f));
            ambientLight.setInfluencingBounds(bounds);
            root.addChild(ambientLight);
        }
        
        public void addnave(){
            String m="models/naveEspacial/naveEspacial.obj";
            n = new Nave(50f,m);
            root.addChild(n);
        }
        
        public void crearVista(){
            universe = new SimpleUniverse();
            ViewingPlatform viewingPlatform = universe.getViewingPlatform();
            TransformGroup viewTransformGroup = viewingPlatform.getViewPlatformTransform();
            Transform3D viewTransform3D = new Transform3D();
            viewTransform3D.lookAt(new Point3d(1, 1, 400), new Point3d(0, 0, 0), new Vector3d(0, 1, 0));
            viewTransform3D.invert();
            viewTransformGroup.setTransform(viewTransform3D);

            view = universe.getViewer().getView();
            view.setFieldOfView(Math.toRadians(70));
            view.setBackClipDistance(50000.0);
            view.setPhysicalBody (new PhysicalBody ( ) ) ;
            view.setPhysicalEnvironment (new PhysicalEnvironment ( ) ) ;
            view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION) ;
            view.setFrontClipDistance(0.1);

            // El comportamiento, para mover la camara con el raton
            OrbitBehavior orbit = new OrbitBehavior(universe.getCanvas(), OrbitBehavior.REVERSE_ALL);
            orbit.setSchedulingBounds(new BoundingSphere(new Point3d(0.0f, 0.0f, 0.0f), 1000000.0f));
            orbit.setZoomFactor(30f);
            viewingPlatform.setViewPlatformBehavior(orbit);
            
            
            //get canvas
            canvas=universe.getCanvas();

            
        }
        
        public void fijarcamaras(){
            view.removeAllCanvas3Ds();
            luna.fijarcamara(canvas);
            luna.quitarcamara();
            n.fijarcamara(canvas);
            n.quitarcamara();
            view.addCanvas3D(canvas);
            
        } 
             
        public void crearSistemaSolar(){
            int max=110000,min=6000;
            Random ran=new Random();
            int segR=0,segT=0;
            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;
            String ap = "imgs/sun.jpg";
            Sol s = new Sol(40f, ap);
            ap = "imgs/mercurio.jpg";
            Planeta mercurio = new Planeta(2.4f, ap, 50f,segR,segT);
            s.addPlaneta(mercurio);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;
            ap = "imgs/venus.jpg";
            Planeta venus = new Planeta(6.1f, ap, 70f,segR,segT);
            s.addPlaneta(venus);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;
            ap = "imgs/tierra.jpg";
            Planeta tierra = new Planeta(6.3f, ap, 100f,5000,segT);
            s.addPlaneta(tierra);
                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/luna.jpg";
                luna = new Satelite(1f, ap, 8f,segR,segT,true);
                tierra.AddSatelite(luna);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;
            ap = "imgs/marte.jpg";
            Planeta marte = new Planeta(3.3f, ap, 130f,segR,segT);
            s.addPlaneta(marte);
                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/fobos.jpg";
                Satelite fobos = new Satelite(1f, ap, 5f,segR,segT);
                marte.AddSatelite(fobos);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/deimos.jpg";
                Satelite deimos = new Satelite(1f, ap, 8f,segR,segT);
                marte.AddSatelite(deimos);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;    
            ap = "imgs/jupiter.jpg";
            Planeta jupiter = new Planeta(16f, ap, 210f,segR,segT);
            s.addPlaneta(jupiter);
                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/io.jpg";
                Satelite io = new Satelite(1f, ap, 22f,segR,segT);
                jupiter.AddSatelite(io);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/europa.jpg";
                Satelite europa = new Satelite(1f, ap, 25f,segR,segT);
                jupiter.AddSatelite(europa);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/fobos.jpg";
                Satelite ganimedes = new Satelite(1f, ap, 28f,segR,segT);
                jupiter.AddSatelite(ganimedes);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/deimos.jpg";
                Satelite calisto = new Satelite(1f, ap, 31f,segR,segT);
                jupiter.AddSatelite(calisto);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;    
            ap = "imgs/saturno.jpg";
            Planeta saturno = new Planeta(14f, ap, 300f,segR,segT);
            s.addPlaneta(saturno);
                ap = "imgs/mercurio.jpg";
                Anillo a1= new Anillo(19f, 16f,ap);
                saturno.AddAnillo(a1);
                ap = "imgs/deimos.jpg";
                Anillo a2= new Anillo(22f, 26f,ap);
                saturno.AddAnillo(a2);

                ap = "imgs/marte.jpg";
                Anillo a3= new Anillo(30f, 34f,ap);
                saturno.AddAnillo(a3);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;   
            ap = "imgs/urano.jpg";
            Planeta urano = new Planeta(9f, ap, 400f,segR,segT);
            s.addPlaneta(urano);
                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/io.jpg";
                Satelite miranda = new Satelite(1f, ap, 12f,segR,segT);
                urano.AddSatelite(miranda);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;

                ap = "imgs/europa.jpg";
                Satelite ariel = new Satelite(1f, ap, 15f,segR,segT);
                urano.AddSatelite(ariel);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/fobos.jpg";
                Satelite umbriel = new Satelite(1f, ap, 19f,segR,segT);
                urano.AddSatelite(umbriel);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/deimos.jpg";
                Satelite titania = new Satelite(1f, ap, 25f,segR,segT);
                urano.AddSatelite(titania);

                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/luna.jpg";
                Satelite oberon = new Satelite(1f, ap, 30f,segR,segT);
                urano.AddSatelite(oberon);

            segR=ran.nextInt(max-min)+min;
            segT=ran.nextInt(max-min)+min;
            ap = "imgs/neptuno.jpg";
            Planeta neptuno = new Planeta(10f, ap, 460f,segR,segT);
            s.addPlaneta(neptuno);
                segR=ran.nextInt(max-min)+min;
                segT=ran.nextInt(max-min)+min;
                ap = "imgs/luna.jpg";
                Satelite triton = new Satelite(1f, ap, 12f,segR,segT);
                neptuno.AddSatelite(triton);

            root.addChild(s);
            
        }
       
        public void compilar(){
            // Se optimiza la escena y se cuelga del universo
            root.compile();
            universe.addBranchGraph(root);
        }
        
        public void addPick(){
            //pick
            Pick p= new Pick(canvas,root);
            p.setSchedulingBounds ( new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 50000000f) ) ;
            root.addChild(p);
        }

         public void  addVistas(){
            //teclado
            Vistas vistas= new Vistas(this);
            vistas.setSchedulingBounds ( new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 50000000f) ) ;
            root.addChild(vistas);
        }
       
        public void cambiarVistaNave(){
            view.removeAllCanvas3Ds();
            luna.quitarcamara();
            n.quitarcamara();
            n.fijarcamara(canvas);
        }
        
         public void cambiarVistaLuna(){
            view.removeAllCanvas3Ds();
            luna.quitarcamara();
            n.quitarcamara();
            luna.fijarcamara(canvas);
        }
         
         public void cambiarVistaDefault(){
            view.removeAllCanvas3Ds();
            luna.quitarcamara();
            n.quitarcamara();
            view.addCanvas3D(canvas);
        }
        

    public static void main(String[] args) {        
        Escena escena=new Escena();
    }

}
