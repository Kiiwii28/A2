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

		while(bRunning){
			if(!bPause){
				System.out.println("running, not paused");
				try{
					Thread.sleep(2000);
				}catch (InterruptedException e){
					e.printStackTrace();
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