package FlowSkeleton;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Arrays;

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

//	public void run(){
//		while(bRunning){
//			System.out.println("running");
//			if(!bPause){
//				System.out.println("running,not paused.");
//				for(int i = 0;i<30;i++){
//					System.out.println("forloop-testing at " + i);
//					if((bPause) || (bRunning == false )){
//						System.out.println("breaking");
//						break;
//					}
//				}
//
//			}
//			else {
//				System.out.println("running, paused.");
//			}
//			if(bRunning==false){
//				break;
//			}
//			System.out.println("end of run");
//			System.out.println("bRunning is " + bRunning);
//		}
//	}

	/**
	public void run(){
      System.out.println("x is = " + land.getDimX() + ". y is = " + land.getDimY());
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
	}**/

	public void run(){
		System.out.print("running!");
		int[] test = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
		int length = test.length;//should equal 17
		int partLength = length/4;

		System.out.println("part length = " + partLength + ". full is = "+length);

		//inclusive lower bound, exlusive upperbound
//		int[] new1 = Arrays.copyOfRange(test,0,partLength-1);
//		int[] new2 = Arrays.copyOfRange(test,partLength,2*partLength-1);
//		int[] new3 = Arrays.copyOfRange(test,2*partLength,3*partLength-1);
//		int[] new4 = Arrays.copyOfRange(test,3*partLength,length-1);

		int[] new1 = Arrays.copyOfRange(test,0,partLength);
		int[] new2 = Arrays.copyOfRange(test,partLength,2*partLength);
		int[] new3 = Arrays.copyOfRange(test,2*partLength,3*partLength);
		int[] new4 = Arrays.copyOfRange(test,3*partLength,length);

		Thread testing1 = new Thread(new Task(new1,new1.length,"T1"));
		Thread testing2 = new Thread(new Task(new2,new2.length,"T2"));
		Thread testing3 = new Thread(new Task(new3,new3.length,"T3"));
		Thread testing4 = new Thread(new Task(new4,new4.length,"T4"));

		testing1.start();
		testing2.start();
		testing3.start();
		testing4.start();
		try{

					System.out.println("trying");
					Thread.sleep(3000);
					notifyAll();

				}catch (InterruptedException e){
					e.printStackTrace();
				}
	}

}
class Task implements Runnable{
	int[] iteration;
	int size;
	String name;

	public Task(int[] part,int psize,String word) {
		iteration = new int[psize];
		size = psize;
		iteration = part;
		name = word;
	}

	public void run(){
			System.out.println("helloooO!!");
		System.out.println("we are inside thread: " + name);
		System.out.println("size is : " + size + " length is " + iteration.length + " " + name);
			System.out.println(name + " " + iteration[0]);
			System.out.println(name + " " + iteration[size-1]);

			synchronized (bLock){
				bLock.wait();
			}
			for(int i = 0;i<size;i++){
				System.out.println("printing i: " + i + "from thread " + name + " equals: " + iteration[i]);
				try{
					wait();
				}catch (InterruptedException e){
					e.printStackTrace();
				}


			}


		}

	//need synchronization

	}
/**
	for(int i = 0;i<land.dim();i++){
		int[] loc = new int[2];
		loc = land.getPermute(i,loc);
		//System.out.println(loc[0] + " is first index of loc = x, and y is "+ loc[1]);
		flood.move(loc[0],loc[1],land);
 **/