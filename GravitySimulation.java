import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/** An applet that displays a simple animation */
public class GravitySimulation extends Applet implements Runnable {
  int x = 250, y = 300, r = 15; // Position and radius of the circle
  int x1 = 350, y1= 300, r1 = 5;
  double m = 100, m1 = 50;
  double angX, angY, velX, velY, velX1, velY1 = 10, a, a1, f;
  

  Thread animator; // The thread that performs the animation

  volatile boolean pleaseStop; // A flag to ask the thread to stop

  /** This method simply draws the circle at its current position */
  public void paint(Graphics g) {
	setSize(600, 600);
	g.setColor(Color.red);
    g.fillOval(x - r, y - r, r * 2, r * 2);
    g.fillOval(x1 - r, y1 - r, r1 * 2, r1 * 2);
  }

  /**
   * This method moves (and bounces) the circle and then requests a redraw.
   * The animator thread calls this method periodically.
   */
  public void animate() {
    // Ask the browser to call our paint() method to draw the circle
    // at its new position.
	  
	angY = (y-y1)/Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
	angX = (x-x1)/Math.sqrt((x1-x)*(x1-x)+(y1-y)*(y1-y));
	//f = m1 * m/((x1-x)*(x1-x)+(y1-y)*(y1-y));
	a = 0;   //f/m;
	a1 = 10; //f/m1;
	
	velX += a*angX;
	velY += a*angY;
	x += velX;
    y +=velY;
    
    velX1 += a1*angX;
    velY1 += a1*angY;
	x1 += velX1;
    y1 +=velY1;
    repaint();
  }

  /**
   * This method is from the Runnable interface. It is the body of the thread
   * that performs the animation. The thread itself is created and started in
   * the start() method.
   */
  public void run() {
    while (!pleaseStop) { // Loop until we're asked to stop
      animate(); // Update and request redraw
      try {
        Thread.sleep(100);
      } // Wait 100 milliseconds
      catch (InterruptedException e) {
      } // Ignore interruptions
    }
  }

  /** Start animating when the browser starts the applet */
  public void start() {
    animator = new Thread(this); // Create a thread
    pleaseStop = false; // Don't ask it to stop now
    animator.start(); // Start the thread.
    // The thread that called start now returns to its caller.
    // Meanwhile, the new animator thread has called the run() method
  }

  /** Stop animating when the browser stops the applet */
  public void stop() {
    // Set the flag that causes the run() method to end
    pleaseStop = true;
  }
}

           