class Poligon {
  ArrayList<PVector> vertexs = new ArrayList<PVector>();
  ArrayList<Segment> segments = new ArrayList<Segment>();    
  color c = color(#216400); //Color del polígon
  PShape s;

  PVector[] arrayRecovery;

  Poligon() {
  }  

  void calcularSegments() {
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

  void actualitzarSegments() {
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
  void afegirVertex(float x, float y, float z) {
    vertexs.add(new PVector(x, y, z));
    calcularSegments();
  }


  void dibuixar() {
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

  boolean interior(float x, float y) {
    return interior(new PVector(x, y));
  }

  /**
   Determina si un punt P és interior o exterior al polígon
   */
  boolean interior(PVector P) {
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

  boolean ccw(PVector A, PVector B, PVector C) {
    return (C.y-A.y) * (B.x-A.x) < (B.y-A.y) * (C.x-A.x);
  }

  boolean segmentsEsTallen(PVector A, PVector B, PVector C, PVector D) {
    return  ccw(A, C, D) != ccw(B, C, D) && ccw(A, B, C) != ccw(A, B, D);
  }

  boolean segmentsEsTallen(Segment S1, Segment S2) {
    return segmentsEsTallen(S1.start, S1.end, S2.start, S2.end);
  }

  PVector[] getArrayVertex() {
    PVector[] vertexsArray = new PVector[vertexs.size()];
    for (int i = 0; i < vertexs.size(); i++) {
      vertexsArray[i] = vertexs.get(i).copy();
    }
    return vertexsArray;
  }

  void saveJSON() {
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

  void loadJSONinterior() {
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
  void loadJSONexterior() {
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
