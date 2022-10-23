import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class joc_xubof extends PApplet {

//LIBRERIES

//llibreries sound


Minim minim; //objectes sound
AudioPlayer relentixubof, gasxubof, musicafons, musicamenu;
AudioPlayer arranca_xubof, no_arranca_xubof, pitu_xubof;

//CLASES


Poligon poligoni; //poligons
Poligon poligone;

Circuit circuit; //circuit

Cotxe cotxe; //cotxe

Menu menu; //menu

decoracions arbre;


//VARIABLES


float fr = 0.1f;
float v; //Velocitat del cotxe. Sempre es mou per endavant.

boolean upPitjat = false;
boolean downPitjat = false;
boolean leftPitjat = false;
boolean rightPitjat = false;
boolean spacePitjat = false;
boolean no_arrancar = true;
boolean arrancar = false;

//BOOLEANS MENU I JOC

boolean booleanMenu = true;
boolean booleanJoc = false;





public void setup() {
  
  //objectes
  cotxe = new Cotxe(200, 300);
  circuit = new Circuit();
  menu = new Menu();

  arbre = new decoracions();


  //sonido
  minim = new Minim(this); //nou objecte "minim"
  relentixubof = minim.loadFile("sound/relentixubof.mp3");
  gasxubof = minim.loadFile("sound/gasxubof.mp3");//nom arciu mp3, ha de estar a sa meteixa carpeta
  musicamenu = minim.loadFile("sound/musicamenu.mp3");
  musicafons = minim.loadFile("sound/musicafons.mp3");
  pitu_xubof = minim.loadFile("sound/pitu_xubof.mp3");
  arranca_xubof = minim.loadFile("sound/arranca_xubof.mp3");
  no_arranca_xubof = minim.loadFile("sound/no_arranca_xubof.mp3");

  //carregar 3d
  //arbre.carregar();

  circuit.poligone.c = color(0xff55391E);
  circuit.poligoni.c = color(0xff175802);
}
public void draw() {
  background(0xff175802);
  //thread("transicions");
  transicions(); //El que empram per canviar de pestanya
  joc(); //Valors i altres coses del propi joc
}

public void transicions() {

  if (booleanJoc == false && booleanMenu == true) {

    minim = new Minim(this);

    menu.imatge();

    menu.rollover(100, 600, "xubof", "futarli manxada");
  } else {

    arbre.pintar();
  }
}

public void pintar() {
  for (int i = 0; i < arbre.arrayRecovery.length; i++) { //afegim figura interior
    shapeMode(CENTER);
    shape(arbre.copa_arbre, arbre.arrayRecovery[i].x, arbre.arrayRecovery[i].y);
    shape(arbre.tronc_arbre, arbre.arrayRecovery[i].x, arbre.arrayRecovery[i].y);
  }
}

public void joc() {

  //sonido
  //relenti sempre ences
  if (booleanJoc == true && booleanMenu == false) {
    musicamenu.pause();  //Aturam la música del menu
    musicafons.play();
    lights();


    if (booleanJoc == true && booleanMenu == false) {
      musicamenu.pause();  //Aturam la música del menu


      cotxe.no_arrancar();

      if (arrancar == true && no_arrancar == false) {


        if (upPitjat || leftPitjat ||rightPitjat) {
          relentixubof.pause();
          gasxubof.play();
        } else {
          gasxubof.pause();
          relentixubof.play();
          pitu_xubof.pause();
        }
        if (spacePitjat) {
          pitu_xubof.play();
        } else {
          pitu_xubof.pause();
        }
      }
    }


    //utilitzacio de funcions externes

    circuit.dibuixar();
    cotxe.actualitzar();
    cotxe.dibuixar();
    cotxe.gasFreno();
    cotxe.girar();

    //pintar arbres




    //PVector[]  vertexs = cotxe.poligon.getArrayVertex();
    PVector p1 = cotxe.poligon.vertexs.get(0);
    PVector p2 = cotxe.poligon.vertexs.get(1);
    PVector p3 = cotxe.poligon.vertexs.get(2);
    PVector p4 = cotxe.poligon.vertexs.get(3);


    if (circuit.interior(p1) == false || circuit.interior(p2) == false ||  circuit.interior(p3) == false || circuit.interior(p4) == false) {
      fr = 0.037f;
      if (v < -1) {
        v++;
      }
    } else {
      fr = 0.02f;
    }
  }
}


//solucio pitjar dues tecles a la vegada
public void keyPressed() {
  if (key == 'w') {
    upPitjat = true;
    cotxe.arrancar();
    arrancar = true; 
    no_arrancar = false;
  } else if (key == 's') {
    downPitjat = true;
  } else if (key == 'a') {
    leftPitjat = true;
  } else if (key == 'd') {
    rightPitjat = true;
  } else if (key == ' ') {
    spacePitjat = true;
  }
}

public void keyReleased() {
  if (key == 'w') {
    upPitjat = false;
  } else if (key == 's') {
    downPitjat = false;
  } else if (key == 'a') {
    leftPitjat = false;
  } else if (key == 'd') {
    rightPitjat = false;
  } else if (key == ' ') {
    spacePitjat = false;
  }
}


public void cylinder(float bottom, float top, float h, int sides)
{
  pushMatrix();

  translate(0, h/2, 0);

  float angle;

  float[] x = new float[sides+1];

  float[] z = new float[sides+1];

  float[] x2 = new float[sides+1];
  float[] z2 = new float[sides+1];


  for (int i=0; i < x.length; i++) {
    angle = TWO_PI / (sides) * i;

    x[i] = sin(angle) * bottom;
    z[i] = cos(angle) * bottom;
  }

  for (int i=0; i < x.length; i++) {
    angle = TWO_PI / (sides) * i;
    x2[i] = sin(angle) * top;
    z2[i] = cos(angle) * top;
  }

  beginShape(TRIANGLE_FAN);
  vertex(0, -h/2, 0);

  for (int i=0; i < x.length; i++) {
    vertex(x[i], -h/2, z[i]);
  }

  endShape();

  beginShape(QUAD_STRIP); 

  for (int i=0; i < x.length; i++) {
    vertex(x[i], -h/2, z[i]);
    vertex(x2[i], h/2, z2[i]);
  }

  endShape();

  //draw the top of the cylinder
  beginShape(TRIANGLE_FAN); 

  vertex(0, h/2, 0);

  for (int i=0; i < x.length; i++) {
    vertex(x2[i], h/2, z2[i]);
  }

  endShape();

  popMatrix();
}
class Circuit {
  Poligon poligone;
  Poligon poligoni;
  
  Circuit() {
    poligoni = new Poligon();
    poligone = new Poligon();
    poligoni.c = color(255);
    poligoni.loadJSONinterior();
    poligone.loadJSONexterior();
  }

  public void dibuixar() {
    poligone.dibuixar();
    poligoni.dibuixar();
  }

  public boolean interior(PVector posicio) {
    boolean estaDintre = false;
    if (poligoni.interior(posicio) == false && poligone.interior(posicio)) {
      estaDintre = true;
      return estaDintre;
    }
    return estaDintre;
  }

  public boolean interior(float x, float y) {
    
    return interior(new PVector(x, y));
  }
}
class Cotxe {
  //ATRIBUTS
  PShape cotxe, rodes;
  PImage textura_cotxe, textura_rodes;

  float w = 25; //Amplada
  float h = 15; //Altura

  float camx, camy, camz;


  float a; //Acceleració del cotxe 

  PVector pos; //Posició del cotxe, centre.

  float angle = 90; //Angle en què està girat en graus.
  //Positiu --> SENTIT HORARI
  //Negatiu --> SENTIT ANTIHORARI

  Poligon poligon; //Forma del cotxe. Conté els vèrtexs del cotxe
  //poligon.vertexs; //Array amb els vèrtexs

  int c = color(0, 255, 0); //Color del cotxe.

  //CONSTRUCTOR
  Cotxe(float cotx_x, float cotx_y) {
    pos = new PVector(cotx_x, cotx_y);
    //flaotsicio.x --> Per accedir a la coordenada x
    //posicio.y --> Per accedir a la coordenada y

    a = 0; //Al principi està aturat, no accelera
    v = 0; //Al principi no es mou.

    cotxe = loadShape("3d/coscotxe.obj");
    rodes = loadShape("3d/rodescotxe.obj");

    textura_cotxe = loadImage("texture/vermell.png");
    textura_rodes = loadImage("texture/gris.jpg");


    cotxe.scale(0.6f);
    rodes.scale(0.6f);

    cotxe.setTexture(textura_cotxe);
    rodes.setTexture(textura_rodes);


    //cotxe
    pushMatrix();
    cotxe.rotateX(radians(90));
    cotxe.translate(-6, 14, 2);
    fill(255, 0, 0);
    popMatrix();

    cotxe.beginShape();
    //texture(textura_cotxe);
    cotxe.endContour();

    //rodes
    pushMatrix();
    rodes.rotateX(radians(90));
    rodes.translate(-6, 14, 2);
    fill(0, 255, 0);
    popMatrix();

    rodes.beginShape();
    texture(textura_rodes);
    rodes.endContour();
  }

  public void calcularPoligon() {

    //Vectors que uneixen el centre del cotxe amb els vèrtexs
    PVector p01 = new PVector(-w/2, -h/2);
    PVector p02 = new PVector(w/2, -h/2);
    PVector p03 = new PVector(w/2, h/2);
    PVector p04 = new PVector(-w/2, h/2);

    //Rotam els vectors l'angle de gir
    p01.rotate(radians(angle));
    p02.rotate(radians(angle));
    p03.rotate(radians(angle));
    p04.rotate(radians(angle));    

    //La posició de cada vèrtex és el centre més el vector abans calculat.
    PVector p1 = PVector.add(pos, p01);
    PVector p2 = PVector.add(pos, p02);
    PVector p3 = PVector.add(pos, p03);
    PVector p4 = PVector.add(pos, p04);

    //Recalculam el polígon
    poligon = new Poligon();
    poligon.afegirVertex(p1.x, p1.y, 1);
    poligon.afegirVertex(p2.x, p2.y, 1);
    poligon.afegirVertex(p3.x, p3.y, 1);
    poligon.afegirVertex(p4.x, p4.y, 1);
  }

  //MÈTODES
  
  public void no_arrancar() {
    no_arranca_xubof.play();
  }

  public void arrancar() {
    arranca_xubof.play();
    arrancar = true; 
    no_arrancar = false;
  }
  public void dibuixar() {
    //poligon.c = c;
    //poligon.dibuixar();
  }

  public void actualitzar() {
    calcularPoligon();




    pos.x = constrain(pos.x+v*cos(radians(angle)), 10, 790);
    pos.y = constrain(pos.y+v*sin(radians(angle)), 10, 790);
    pos.z = 0;

    println("possicio x: ", pos.x);
    println("possicio y: ", pos.y);
    println("possicio z: ", pos.z);


    //cotxe.translate(pos.x,pos.y);



    //rodes

    //shapeMode(CENTER);
    shape(cotxe, pos.x, pos.y);
    shape(rodes, pos.x, pos.y);



    //camara
    float girar_cam = 0;
    if (mousePressed) {

      girar_cam = mouseX-width/2;
    }

    camx = pos.x+2*PI*20*cos(radians(angle+girar_cam));
    camy = pos.y+2*PI*20*sin(radians(angle+girar_cam));

    beginCamera();
    camera(camx, camy, 100, poligon.vertexs.get(0).x, poligon.vertexs.get(0).y, 0, 0, 0, -1);
    endCamera();


    calcularPoligon();
  }

  public void girar() {
    if (leftPitjat) {
      pushMatrix();
      translate(20, 20);
      angle = angle - 2;
      rotate(radians(angle));
      cotxe.rotate(radians(-2), 0.0f, 0.0f, 1.0f);
      rodes.rotate(radians(-2), 0.0f, 0.0f, 1.0f);

      popMatrix();
    }
    if (rightPitjat) {
      pushMatrix();     
      translate(20, 20);
      angle = angle + 2;
      rotate(radians(angle));
      cotxe.rotate(radians(2), 0.0f, 0.0f, 1.0f);
      rodes.rotate(radians(2), 0.0f, 0.0f, 1.0f);

      popMatrix();
    }
    println("angle: ", angle);
  }


  public void gasFreno() {
    if (upPitjat) {
      a = -0.08f;
    } else if (downPitjat) {
      a = +0.09f;
    } else {
      if (v > 0.01f) {
        a = -0.02f;
      } else if (v < -0.01f) {
        a = fr;
      } else {
        a  = 0; //Estic aturat
      }
    }
    v = v+a+fr;
    v = constrain(v, -8, 0);

    println("fregament: ", fr);
    println("velocitat: ", v);
    println("acceleracio: ", a);
  }
}
class Menu {

  //ATRIBUTS

  boolean rolloverVolum;

  float w = 200; 
  float h = 100;

  PFont font;

  PImage img;

  Menu menu;

  //CONSTRUCTOR

  Menu() {
    
    font = loadFont("Stencil-48.vlw");
    textFont(font);
    
  }
  
  //MÈTODES

  public void imatge() {

    musicamenu.play();

    img = loadImage("fonduxubof.png");  //Imatge menu (Ara d´en Boff)

    image (img, 0, 0);
    
    textSize(100);
    fill(255);
    text("joc de xubof", 400, 100);
    
  }

  public void rollover(float x, float y, String str1, String str2) {




    fill(255); //Color rectangle rollover Joc

    //rectangle "Start Game"
    rect(x, y, w, h);
    fill (0);

    textSize(22);
    textAlign(CENTER, CENTER);
    textFont(font, 20);
    text(str1, x + 50, y, 100, 100);


    if (mouseX > x && mouseY > y && mouseX < x + w && mouseY < y + h) {

      fill(0, 0, 255);
      rect(x - 5, y - 5, w + 10, h + 10); //Teoricament efecte blau al voltant rectangle
      rect(x, y, w, h);
      fill(255);
      text(str2, x + 50, y, 100, 100);


      if (mousePressed) {

        booleanMenu = false;
        booleanJoc = true;
      }
    }
  }
}
class Poligon {
  ArrayList<PVector> vertexs = new ArrayList<PVector>();
  ArrayList<Segment> segments = new ArrayList<Segment>();    
  int c = color(0xff216400); //Color del polígon
  PShape s;

  PVector[] arrayRecovery;

  Poligon() {
  }  

  public void calcularSegments() {
    segments.clear();
    if (vertexs.size() >= 2) {
      for (int i = 0; i < vertexs.size(); i++) {
        PVector vertexActual, vertexSeguent;        
        vertexActual = vertexs.get(i);
        //Si és el darrer vèrtex el vèrtex següent és el primer
        if (i != vertexs.size() - 1) {          
          vertexSeguent = vertexs.get(i+1);
        } else {                 
          vertexSeguent = vertexs.get(0);
        }       
        segments.add(new Segment(vertexActual, vertexSeguent));
        //println(vertexs);
      }
    }
  }

  /**
   Actualitza els segments després d'afegir un vèrtex
   */

  public void actualitzarSegments() {
    PVector penultimVertex, primerVertex, darrerVertex;

    if (vertexs.size() >= 1) {
      penultimVertex = vertexs.get(vertexs.size() - 1);
      darrerVertex = vertexs.get(vertexs.size());
      segments.add(new Segment(penultimVertex, darrerVertex));
      if (vertexs.size() >= 2) {        
        primerVertex = vertexs.get(0);
        //Modificam el segment que tanca la figura
        segments.remove(0);
        segments.add(0, new Segment(darrerVertex, primerVertex));
      }
    }
  }

  /**
   Afegeix un vèrtex al polígon
   */
  public void afegirVertex(float x, float y, float z) {
    vertexs.add(new PVector(x, y, z));
    calcularSegments();
  }


  public void dibuixar() {
    s = createShape();
    s.beginShape();
    s.fill(c);
    for (PVector vertex : vertexs) {
      s.vertex(vertex.x, vertex.y, vertex.z);
    }
    s.endShape(CLOSE);
    shapeMode(CORNER);
    shape(s, 0, 0);
  }

  public boolean interior(float x, float y) {
    return interior(new PVector(x, y));
  }

  /**
   Determina si un punt P és interior o exterior al polígon
   */
  public boolean interior(PVector P) {
    int tallsEsquerrePunt = 0;

    //El punt és interior si el número d'interseccions amb el polígon a l'esquerre del punt és impar.
    PVector Q = new PVector(0, P.y);
    Segment horitzontalEsquerreP = new Segment(P, Q);

    //Miram el número de segments que talla    

    for (Segment segment : segments) {
      if (segmentsEsTallen(horitzontalEsquerreP, segment)) {
        tallsEsquerrePunt++;
      }
    }

    return tallsEsquerrePunt % 2 == 1;
  }

  public boolean ccw(PVector A, PVector B, PVector C) {
    return (C.y-A.y) * (B.x-A.x) < (B.y-A.y) * (C.x-A.x);
  }

  public boolean segmentsEsTallen(PVector A, PVector B, PVector C, PVector D) {
    return  ccw(A, C, D) != ccw(B, C, D) && ccw(A, B, C) != ccw(A, B, D);
  }

  public boolean segmentsEsTallen(Segment S1, Segment S2) {
    return segmentsEsTallen(S1.start, S1.end, S2.start, S2.end);
  }

  public PVector[] getArrayVertex() {
    PVector[] vertexsArray = new PVector[vertexs.size()];
    for (int i = 0; i < vertexs.size(); i++) {
      vertexsArray[i] = vertexs.get(i).copy();
    }
    return vertexsArray;
  }

  public void saveJSON() {
    PVector[] arrayVertexs = getArrayVertex();
    JSONArray jsonArray;
    jsonArray = new JSONArray();
    for (int i = 0; i < arrayVertexs.length; i++) {
      JSONObject json = new JSONObject();
      json.setFloat("x", arrayVertexs[i].x);
      json.setFloat("y", arrayVertexs[i].y);
      jsonArray.setJSONObject(i, json);
    }
    saveJSONArray(jsonArray, "data/interior.json");
  }

  public void loadJSONinterior() {
    JSONArray interior;
    interior = loadJSONArray("data/circuit/interior.json");
    arrayRecovery = new PVector[interior.size()];
    for (int i = 0; i < interior.size(); i++) { //afegim figura interior
      JSONObject json = interior.getJSONObject(i);
      arrayRecovery[i] = new PVector();
      arrayRecovery[i].x = json.getFloat("x");
      arrayRecovery[i].y = json.getFloat("y");

      afegirVertex(arrayRecovery[i].x, arrayRecovery[i].y, 1);
      //pujarfigura(arrayRecovery[i].x, arrayRecovery[i].y);
    }
  }
  public void loadJSONexterior() {
    PVector[] arrayRecovery1;
    JSONArray exterior;
    exterior = loadJSONArray("data/circuit/exterior.json");
    arrayRecovery1 = new PVector[exterior.size()];

    for (int i = 0; i < exterior.size(); i++) { //afegim figura exterior
      JSONObject json = exterior.getJSONObject(i);
      arrayRecovery1[i] = new PVector();
      arrayRecovery1[i].x = json.getFloat("x");
      arrayRecovery1[i].y = json.getFloat("y");

      afegirVertex(arrayRecovery1[i].x, arrayRecovery1[i].y, 0);
    }
  }


  class Segment {
    PVector start, end;
    Segment(PVector _P, PVector _Q) {
      start = _P.copy();
      end = _Q.copy();
    }
    Segment(float px, float py, float qx, float qy) {
      start = new PVector(px, py);
      end = new PVector(qx, qy);
    }
  }
}


class decoracions {
  PShape tronc_arbre, copa_arbre;
  PImage texture_tronc_arbre, texture_copa_arbre;
  PVector[] arrayRecovery;


  decoracions() {
    carregar();
    posicions_arbes();
  }

  public void carregar() {

    //model 3d
    tronc_arbre = loadShape("3d/tronc_arbre.obj");
    copa_arbre = loadShape("3d/arbre_simple.obj");



    tronc_arbre.rotateX(radians(90));
    //copa_arbre.rotateX(radians(90));
    copa_arbre.translate(3, 3, 5);

    tronc_arbre.scale(2);
    copa_arbre.scale(2);


    //texture
    texture_tronc_arbre = loadImage("texture/marron.jpg");
    texture_copa_arbre = loadImage("texture/herba.jpg");

    tronc_arbre.setTexture(texture_tronc_arbre);
    copa_arbre.setTexture(texture_copa_arbre);


    copa_arbre.scale(2);
  }

  public void posicions_arbes() {


    JSONArray array_arbres;

    array_arbres = loadJSONArray("data/circuit/array_arbres_boffets.json");
    arrayRecovery = new PVector[array_arbres.size()];
    for (int i = 0; i < array_arbres.size(); i++) { //afegim figura interior
      JSONObject json = array_arbres.getJSONObject(i);
      arrayRecovery[i] = new PVector();
      arrayRecovery[i].x = json.getFloat("x");
      arrayRecovery[i].y = json.getFloat("y");
    }
  }
  public void pintar() {
    for (int i = 0; i < arrayRecovery.length; i++) { //afegim figura interior
      arbres_circuit(arrayRecovery[i].x, arrayRecovery[i].y);
      
    }
  }
}
public void arbres_circuit(float arbr_x, float arbr_y) {
  
  lights();

  pushMatrix();
  rotateY(PI);
  rotateX(PI/2);
  translate(-arbr_y, -40, -arbr_x);

  noStroke();
  fill(135, 75, 22);
  cylinder(1, 5, 40, 4);

  fill(0, 200, 0);
  cylinder(1, 10, 30, 7);
  stroke(1);
  popMatrix();
}
  public void settings() {  size(800, 800, P3D); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "joc_xubof" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
