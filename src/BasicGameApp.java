//Basic Game Application
//Version 2
// Basic Object, Image, Movement
// Astronaut moves to the right.
// Threaded

//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;


//*******************************************************************************
// Class Definition Section

public class BasicGameApp implements Runnable, KeyListener {

   //Variable Definition Section
   //Declare the variables used in the program 
   //You can set their initial values too
   
   //Sets the width and height of the program window
	final int WIDTH = 1000;
	final int HEIGHT = 700;

   //Declare the variables needed for the graphics
	public JFrame frame;
	public Canvas canvas;
   public JPanel panel;
   
	public BufferStrategy bufferStrategy;

	public Image rigPic;
	public Image mordPic;
	public Image gumPic;
	public Image darPic;
	public Image Background;


   //Declare the objects used in the program
   //These are things that are made up of more than one variable type

	public Astronaut rigby;
	public Astronaut mordecai;
	public Astronaut gumball;
	public Astronaut darwin;
	public int score;


   // Main method definition
   // This is the code that runs first and automatically
	public static void main(String[] args) {
		BasicGameApp ex = new BasicGameApp();   //creates a new instance of the game
		new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method  
	}


   // Constructor Method
   // This has the same name as the class
   // This section is the setup portion of the program
   // Initialize your variables and construct your program objects here.
	public BasicGameApp() {
      
      setUpGraphics();
       
      //variable and objects
      //create (construct) the objects needed for the game and load up
		Background = Toolkit.getDefaultToolkit().getImage("AT Bg.png");


		rigPic = Toolkit.getDefaultToolkit().getImage("Rigby.png");
		rigby = new Astronaut(10,200, 0, 0);

		mordPic = Toolkit.getDefaultToolkit().getImage("mordecai.png");
		mordecai = new Astronaut(10,300, 0, 1);

		gumPic = Toolkit.getDefaultToolkit().getImage("Gumball.png");
		gumball = new Astronaut(10, 100, 1, 0);

		darPic = Toolkit.getDefaultToolkit().getImage("Darwin.png");
		darwin = new Astronaut(10, 400, 1, 1);


	}// BasicGameApp()

   
//*******************************************************************************
//User Method Section
//
// put your code to do things here.

   // main thread
   // this is the code that plays the game after you set things up
	public void run() {

      //for the moment we will loop things forever.
		while (true) {

         moveThings();  //move all the game objects
			checkIntersections();
         render();  // paint the graphics
         pause(20); // sleep for 10 ms
		}
	}


	public void moveThings()
	{
      //calls the move( ) code in the objects
		rigby.move();
		mordecai.bounce();

		darwin.move();
		gumball.wrap();

	}
	public void checkIntersections(){
		if(rigby.rec.intersects(mordecai.rec)){
			//rigby.isAlive = true; this is to change the backrgound when they touch

			score += 1;
			System.out.println("The score is " + score);
		}
		if(rigby.rec.intersects(gumball.rec)){
			//rigby.isAlive = false;
			score += 1;
			System.out.println("The score is " + score);
		}
		if(mordecai.rec.intersects(gumball.rec)){
			//astro.isAlive = false;
			score += 1;
			System.out.println("The score is " + score);
		}
	}
	
   //Pauses or sleeps the computer for the amount specified in milliseconds
   public void pause(int time ){
   		//sleep
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {

			}
   }

   //Graphics setup method
   private void setUpGraphics() {
      frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.
   
      panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
      panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
      panel.setLayout(null);   //set the layout
   
      // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
      // and trap input events (Mouse and Keyboard events)
      canvas = new Canvas();  
      canvas.setBounds(0, 0, WIDTH, HEIGHT);
	  canvas.addKeyListener(this);
      canvas.setIgnoreRepaint(true);
   
      panel.add(canvas);  // adds the canvas to the panel.
   
      // frame operations
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
      frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
      frame.setResizable(false);   //makes it so the frame cannot be resized
      frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!
      
      // sets up things so the screen displays images nicely.
      canvas.createBufferStrategy(2);
      bufferStrategy = canvas.getBufferStrategy();
      canvas.requestFocus();
      System.out.println("DONE graphic setup");
   
   }


	//paints things on the screen using bufferStrategy
	private void render() {
		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
		g.clearRect(0, 0, WIDTH, HEIGHT);

      //draw the image of the astronaut
		g.drawImage(Background,0,0,WIDTH,HEIGHT, null);

		g.drawImage(rigPic, rigby.xpos, rigby.ypos, rigby.width, rigby.height, null);
		g.drawImage(mordPic, mordecai.xpos, mordecai.ypos, mordecai.width, mordecai.height, null);
		g.drawImage(gumPic, gumball.xpos, gumball.ypos, gumball	.width, gumball.height, null);
		g.drawImage(darPic, darwin.xpos, darwin.ypos, darwin.width, darwin.height, null);
		g.setColor(Color.white);
		g.fillRect(50, 40, 70, 15);
		g.setColor(Color.black);
		g.drawString("Score: " + score, 50, 50);

		g.dispose();

		bufferStrategy.show();
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {

		if(e.getKeyCode() == 40){
			rigby.dx = 0;
			rigby.dy = 5;
		}
		if(e.getKeyCode() == 38){
			rigby.dx = 0;
			rigby.dy = -5;
		}
		if(e.getKeyCode() == 39){
			rigby.dx = 5;
			rigby.dy = 0;
		}
		if(e.getKeyCode() == 37){
			rigby.dx = -5;
			rigby.dy = 0;
		}


	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 40){
			rigby.dx = 0;
			rigby.dy = 0;
		}
		if(e.getKeyCode() == 38){
			rigby.dx = 0;
			rigby.dy = 0;
		}
		if(e.getKeyCode() == 39){
			rigby.dx = 0;
			rigby.dy = 0;
		}
		if(e.getKeyCode() == 37){
			rigby.dx = 0;
			rigby.dy = 0;
		}

	}
}