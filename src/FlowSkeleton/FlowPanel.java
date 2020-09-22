package FlowSkeleton;

import java.awt.Graphics;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FlowPanel extends JPanel implements Runnable {
	static Terrain land;
	//static Water flood;
	static Water2 flood;
	static volatile boolean bPause;
	volatile boolean bRunning;
	static volatile boolean bReset = false;
	AtomicBoolean booleant1 = new AtomicBoolean(true);
	AtomicBoolean booleant2 = new AtomicBoolean(true);
	AtomicBoolean booleant3 = new AtomicBoolean(true);
	AtomicBoolean booleant4 = new AtomicBoolean(true);
	private static long startTime;
	
	//final public AtomicInteger ThreadCount = new AtomicInteger();


	//Water flood;
	FlowPanel(Terrain terrain,Water2 water) {
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
	private static void tick(){
		startTime = System.currentTimeMillis();
	}

	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime);
	}


	public void run(){
		AtomicBoolean ab = new AtomicBoolean(true);
		int partLength = land.permute.size()/4;
	System.out.println("0 + " + partLength + " " + 2*partLength + " " + 3*partLength + " " + 4*partLength + ". land permute size is " + land.permute.size());

		Thread testing1 = new Thread(new FlowSkeleton.Task(0, partLength, "T1", booleant1));
		Thread testing2 = new Thread(new FlowSkeleton.Task(partLength, 2*partLength, "T2", booleant2));
		Thread testing3 = new Thread(new FlowSkeleton.Task(2*partLength, 3*partLength, "T3", booleant3));
		Thread testing4 = new Thread(new FlowSkeleton.Task(3*partLength, land.permute.size(), "T4", booleant4));

		testing1.start();
		testing2.start();
		testing3.start();
		testing4.start();
		int iteration = 0;
		long startT;
		while(bRunning){
			startT = System.currentTimeMillis();
			tick();
			if(!bPause){
				//System.out.println("while loop");
				flood.clearEdges(); //if all waiting after a pause, will resume them
				if (booleant1.get()==false && booleant2.get()==false && booleant3.get()==false && booleant4.get()==false && ((System.currentTimeMillis()-startT)>10)){
					iteration++;
					System.out.println(tock() + " threads done");
					System.out.println("full 2d grid iteration " + iteration + " and trying to give head start");
					startT = System.currentTimeMillis();
					tick();
					repaint();
					//System.out.println(tock() + " repaint done");
					BoolReset(true);
				}

				}else{
				BoolReset(false);
				try{
					Thread.sleep(1000);
				} catch (InterruptedException e){
					e.printStackTrace();
				}

			}
		}

	}

	public synchronized void BoolReset(boolean b){
		booleant1.set(b);
		booleant2.set(b);
		booleant3.set(b);
		booleant4.set(b);
	}
}
		class Task implements Runnable {
			int[] iteration;
			int size;
			String name;
			//ThreadSafeCounter taskComplete;
//			AtomicBoolean bGo;
			AtomicBoolean bT;
			int lo;
			int hi;
//			Terrain land;
//			Water flood;

			//public Task(int[] part, int psize, String word, AtomicBoolean booleant, AtomicBoolean ab) {
			public Task(int pLo, int pHi, String word, AtomicBoolean booleant) {
				//iteration = new int[psize];
				//size = psize;
				//iteration = part;
				lo = pLo;
				hi = pHi;
				name = word;
				bT = booleant;
				System.out.println("hi = " + hi + ". lo = "+lo);
				System.out.println("hi - lo = " + (hi - lo) );


			}

			public void run() {
				iterate();
			}

			//took out synchronized
			private void iterate() {

				while (true) { //constant loop, may put pause controls in
					if (bT.get() == true) { //loop through entire loop

						int local = 0;
						for (int i = lo; i < hi; i++) {
							if (FlowPanel.bPause == true) { //so doesn' just check after entire iteration
								//System.out.println("reset = true inside thread");
								break;
							}
							int[] loc = new int[2];
							//System.out.println("printing i: " + i + ", local counter = " + local + "from thread " + name);
							local++;
							loc = FlowPanel.land.getPermute(i, loc);
							//System.out.println(loc[0] + " is first index of loc = x, and y is "+ loc[1]);
							FlowPanel.flood.move(loc[0], loc[1], FlowPanel.land);
						}
						//System.out.println("printing i: " + i + "from thread " + name);
						bT.set(false);
						//break;

					}
//					else {
//						System.out.println(name + "waiting");
//					}
					/**	//BELOW CODE WAS TAKEN OU TO TRY SPEED UP
					} else { //now its boolean is false and it is waiting
						try {
							//System.out.println(name + "waiting");
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							System.out.println("insomniacs can't sleep");
							e.printStackTrace();
						}
					}**/

				}
			}
		}