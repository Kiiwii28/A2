package FlowSkeleton;

import java.awt.Graphics;
import javax.swing.JPanel;

public class FlowPanel extends JPanel implements Runnable {
	Terrain land;
	Water flood;
	volatile boolean bPause;
	volatile boolean bRunning;


	//Water flood;
	FlowPanel(Terrain terrain,Water water) {
		flood = water;
		land=terrain;
	}
		
	// responsible for painting the terrain and water
	// as images
	@Override

    protected void paintComponent(Graphics g) {

		int width = getWidth();
		int height = getHeight();
		  
		super.paintComponent(g);
		
		// draw the landscape in greyscale as an image
		if (land.getImage() != null){
			g.drawImage(land.getImage(), 0, 0, null);
		}
		//?paid overlay image graphic from water.getImage()
		flood.deriveImage();
		if(flood.getImage() != null){
			g.drawImage(flood.getImage(),0,0,null);
		}
		/**
		BufferedImage background = land.getImage();
		BufferedImage foreground = flood.getImage();
		BufferedImage overlayedImage = overLa
		**/
	}
	public void test(){
		System.out.println("in test before repaint");
		repaint();
		System.out.println("in test before repaint");
	}

	public void play(){
		bPause = false;
		System.out.println("set pause to false");
	}
	public void pause(){
		bPause = true;
	}

	public void setbRunning(boolean bRun){
		bRunning = bRun;

	}

	public void run(){
      //System.out.println("x is = " + land.getDimX() + ". y is = " + land.getDimY());
		while(bRunning){
			if(!bPause){
				flood.clearEdges();
				System.out.println("running, not paused");
				for(int i = 0;i<land.dim();i++){
					int[] loc = new int[2];
					loc = land.getPermute(i,loc);
					//System.out.println(loc[0] + " is first index of loc = x, and y is "+ loc[1]);
					flood.move(loc[0],loc[1],land);

//					try{ Thread.sleep(2000);
//						repaint();
//					}
//					catch (InterruptedException e){
//						e.printStackTrace(); }
//					flood.drop(loc[0],loc[1],1,1);
					repaint();
				}

			}
			else{
				System.out.println("running, BUT paused");
			}
		}

//		while(bRunning){
//			if(!bPause){
//				System.out.println("running, not paused");
//			}
//			else{
//				try{
//					System.out.println("we are paused");
//					System.out.println("hello");
//
//					Thread.sleep(2000);
//
//				}catch (InterruptedException e){
//					e.printStackTrace();
//				}
//			}
//		}


		// display loop here
		// to do: this should be controlled by the GUI
		// to allow stopping and starting
	    repaint();
	}
}