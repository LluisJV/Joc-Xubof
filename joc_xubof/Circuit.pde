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

  void dibuixar() {
    poligone.dibuixar();
    poligoni.dibuixar();
  }

  boolean interior(PVector posicio) {
    boolean estaDintre = false;
    if (poligoni.interior(posicio) == false && poligone.interior(posicio)) {
      estaDintre = true;
      return estaDintre;
    }
    return estaDintre;
  }

  boolean interior(float x, float y) {
    
    return interior(new PVector(x, y));
  }
}
