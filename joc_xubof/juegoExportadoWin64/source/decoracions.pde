

class decoracions {
  PShape tronc_arbre, copa_arbre;
  PImage texture_tronc_arbre, texture_copa_arbre;
  PVector[] arrayRecovery;


  decoracions() {
    carregar();
    posicions_arbes();
  }

  void carregar() {

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

  void posicions_arbes() {


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
  void pintar() {
    for (int i = 0; i < arrayRecovery.length; i++) { //afegim figura interior
      arbres_circuit(arrayRecovery[i].x, arrayRecovery[i].y);
      
    }
  }
}
void arbres_circuit(float arbr_x, float arbr_y) {
  
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
