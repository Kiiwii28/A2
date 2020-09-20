package FlowSkeleton;

import java.awt.Graphics;
import javax.swing.JPanel;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class FlowPanel extends JPanel implements Runnable {
	static Terrain land;
	static Water flood;
	volatile boolean bPause;
	volatile boolean bRunning;
	AtomicBoolean booleant1 = new AtomicBoolean(true);
	AtomicBoolean booleant2 = new AtomicBoolean(true);
	AtomicBoolean booleant3 = new AtomicBoolean(true);
	AtomicBoolean booleant4 = new AtomicBoolean(true);
	
	//final public AtomicInteger ThreadCount = new AtomicInteger();


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

	public void run() {
		//ThreadSafeCounter tsc = new ThreadSafeCounter();
		AtomicBoolean ab = new AtomicBoolean(true);
		//equals zero
		System.out.print("running!");
//		int[] test = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
//		int length = test.length;//should equal 17
//		int partLength = length / 4;


/**
		AtomicBoolean booleant1 = new AtomicBoolean(true);
		AtomicBoolean booleant2 = new AtomicBoolean(true);
		AtomicBoolean booleant3 = new AtomicBoolean(true);
		AtomicBoolean booleant4 = new AtomicBoolean(true);
**/
		//System.out.println("part length = " + partLength + ". full is = " + length);

//		int[] new1 = Arrays.copyOfRange(test, 0, partLength);
//		int[] new2 = Arrays.copyOfRange(test, partLength, 2 * partLength);
//		int[] new3 = Arrays.copyOfRange(test, 2 * partLength, 3 * partLength);
//		int[] new4 = Arrays.copyOfRange(test, 3 * partLength, length);

		//for now do without permuted arrays
//some parameters are unnecessary
		//don't need .length, don't need tsc, might not need ab
//		Thread testing1 = new Thread(new Task(new1, new1.length, "T1", booleant1, ab));
//		Thread testing2 = new Thread(new Task(new2, new2.length, "T2", booleant2, ab));
//		Thread testing3 = new Thread(new Task(new3, new3.length, "T3", booleant3, ab ));
//		Thread testing4 = new Thread(new Task(new4, new4.length, "T4", booleant4, ab));

		int partLength = land.permute.size()/4;


		Thread testing1 = new Thread(new Task(0, partLength-1, "T1", booleant1));
		Thread testing2 = new Thread(new Task(partLength, 2*partLength-1, "T2", booleant2));
		Thread testing3 = new Thread(new Task(2*partLength, 3*partLength-1, "T3", booleant3));
		Thread testing4 = new Thread(new Task(3*partLength, land.permute.size(), "T4", booleant4));

		testing1.start();
		testing2.start();
		testing3.start();
		testing4.start();

		int iteration = 0 ;
		while (bRunning) {
			//ab.set(true);
			//JLABEL SOON iteration++;
			flood.clearEdges();
			//may be unnecessary

			//System.out.println(tsc.get() + " " + ab.get() + " at iteration: i = " + iteration);
//			while (tsc.get() < 4){
//				continue;
//			}//break out when equals four
			if (booleant1.get()==false && booleant2.get()==false && booleant3.get()==false && booleant4.get()==false){

				//tsc.set(0);
				//ab.set(true);
				//tsc.set(0);
				try {
					iteration++;
					System.out.println("full 2d grid iteration " + iteration + " and trying to give head start");
					repaint();
					Thread.sleep(0);
					//may need to put these in synchronized method?

//					booleant1.set(true);
//					booleant2.set(true);
//					booleant3.set(true);
//					booleant4.set(true);

					BoolReset();
					//ab.set(true);
					//Thread.sleep(2000); //necessary?

				} catch (InterruptedException e) {
					System.out.println("insomniacs can't sleep");
					e.printStackTrace();
				}

			}


		}
		System.out.println("end of while loop running");
	}
	public synchronized void BoolReset(){
		booleant1.set(true);
		booleant2.set(true);
		booleant3.set(true);
		booleant4.set(true);
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
				//taskComplete = tsc;
//				bGo = ab;
				bT = booleant;
//				land = pLand;
//				flood = pFlood;

			}

			public void run() {
				iterate();
			}

			//took out synchronized
			private void iterate() {
//				System.out.println("helloooO!!");
//				System.out.println("we are inside thread: " + name);
				//System.out.println("size is : " + size + " length is " + iteration.length + " " + name);
//
//				System.out.println("low is : " + lo + " high is " + hi + " at : " + name);

//				System.out.println(name + " " + iteration[0]);
//				System.out.println(name + " " + iteration[size - 1]);

				while (true) { //constant loop, may put pause controls in
					if (bT.get() == true) { //loop through entire loop

//						for (int i = 0; i < size; i++) {
//							System.out.println("printing i: " + i + "from thread " + name);
//						}//finish loop now indicate done

						int local = 0;
						for (int i = lo; i < hi; i++) {
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

//					else {
//						System.out.println(name + "waiting");
//					}
						//BELOW CODE WAS TAKEN OU TO TRY SPEED UP
					} else { //now its boolean is false and it is waiting
						try {
							//System.out.println(name + "waiting");
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							System.out.println("insomniacs can't sleep");
							e.printStackTrace();
						}
					}

				}
			}
		}




//					for (int i = 0; i < size; i++){
//							if(bT.get()==true) { //if that thread CAN run, run then set to false
//								System.out.println("printing i: " + i + "from thread " + name + " equals: " + iteration[i]);
//								bT.set(false);
//								break;
//							}
//							else{
//								try {
//									System.out.println(name + "waiting");
//									Thread.sleep(2000);
//								} catch (InterruptedException e) {
//									System.out.println("insomniacs can't sleep");
//									e.printStackTrace();
//								}
//							}
//						}
//					}

//				for (int i = 0; i < size; i++) {
//					while (true){
//						if(bT.get()==true) {
//						System.out.println("printing i: " + i + "from thread " + name + " equals: " + iteration[i]);
//						bT.set(false);
//						break;
//						}
//						else{
//							try {
//								System.out.println(name + "waiting");
//								Thread.sleep(2000);
//							} catch (InterruptedException e) {
//								System.out.println("insomniacs can't sleep");
//								e.printStackTrace();
//							}
//						}
//					}

