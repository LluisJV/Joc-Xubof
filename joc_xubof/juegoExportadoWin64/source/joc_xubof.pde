//LIBRERIES

//llibreries sound
import ddf.minim.*;

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


float fr = 0.1;
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





void setup() {
  size(800, 800, P3D);
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

  circuit.poligone.c = color(#55391E);
  circuit.poligoni.c = color(#175802);
}
void draw() {
  background(#175802);
  //thread("transicions");
  transicions(); //El que empram per canviar de pestanya
  joc(); //Valors i altres coses del propi joc
}

void transicions() {

  if (booleanJoc == false && booleanMenu == true) {

    minim = new Minim(this);

    menu.imatge();

    menu.rollover(100, 600, "xubof", "futarli manxada");
  } else {

    arbre.pintar();
  }
}

void pintar() {
  for (int i = 0; i < arbre.arrayRecovery.length; i++) { //afegim figura interior
    shapeMode(CENTER);
    shape(arbre.copa_arbre, arbre.arrayRecovery[i].x, arbre.arrayRecovery[i].y);
    shape(arbre.tronc_arbre, arbre.arrayRecovery[i].x, arbre.arrayRecovery[i].y);
  }
}

void joc() {

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
      fr = 0.037;
      if (v < -1) {
        v++;
      }
    } else {
      fr = 0.02;
    }
  }
}


//solucio pitjar dues tecles a la vegada
void keyPressed() {
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

void keyReleased() {
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


void cylinder(float bottom, float top, float h, int sides)
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
