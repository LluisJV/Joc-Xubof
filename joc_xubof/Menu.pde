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

  void imatge() {

    musicamenu.play();

    img = loadImage("fonduxubof.png");  //Imatge menu (Ara d´en Boff)

    image (img, 0, 0);
    
    textSize(100);
    fill(255);
    text("joc de xubof", 400, 100);
    
  }

  void rollover(float x, float y, String str1, String str2) {




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
