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

  color c = color(0, 255, 0); //Color del cotxe.

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


    cotxe.scale(0.6);
    rodes.scale(0.6);

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

  void calcularPoligon() {

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
  
  void no_arrancar() {
    no_arranca_xubof.play();
  }

  void arrancar() {
    arranca_xubof.play();
    arrancar = true; 
    no_arrancar = false;
  }
  void dibuixar() {
    //poligon.c = c;
    //poligon.dibuixar();
  }

  void actualitzar() {
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

  void girar() {
    if (leftPitjat) {
      pushMatrix();
      translate(20, 20);
      angle = angle - 2;
      rotate(radians(angle));
      cotxe.rotate(radians(-2), 0.0, 0.0, 1.0);
      rodes.rotate(radians(-2), 0.0, 0.0, 1.0);

      popMatrix();
    }
    if (rightPitjat) {
      pushMatrix();     
      translate(20, 20);
      angle = angle + 2;
      rotate(radians(angle));
      cotxe.rotate(radians(2), 0.0, 0.0, 1.0);
      rodes.rotate(radians(2), 0.0, 0.0, 1.0);

      popMatrix();
    }
    println("angle: ", angle);
  }


  void gasFreno() {
    if (upPitjat) {
      a = -0.08;
    } else if (downPitjat) {
      a = +0.09;
    } else {
      if (v > 0.01) {
        a = -0.02;
      } else if (v < -0.01) {
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
