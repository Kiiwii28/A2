package FlowSkeleton;

import javax.swing.*;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Flow{
	static long startTime = 0;
	static int frameX;
	static int frameY;

	static FlowPanel fp;
	static Water2 flood; //might not be necessary could be handeled by waterpanel

	// start timer
	private static void tick(){
		startTime = System.currentTimeMillis();
	}
	
	// stop timer, return time elapsed in seconds
	private static float tock(){
		return (System.currentTimeMillis() - startTime) / 1000.0f; 
	}
	
	public static void setupGUI(int frameX,int frameY,Terrain landdata) {

		JLabel timeStepper = new JLabel("test");
		Dimension fsize = new Dimension(800, 800);
    	JFrame frame = new JFrame("Waterflow"); 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.getContentPane().setLayout(new BorderLayout());
    	
      	JPanel g = new JPanel();
        g.setLayout(new BoxLayout(g, BoxLayout.PAGE_AXIS)); 
   
		fp = new FlowPanel(landdata,flood);
		fp.setPreferredSize(new Dimension(frameX,frameY));
		g.add(fp); //frame > J panel > (flow panel + buttons and timesteps)

		// to do: add a MouseListener, buttons and ActionListeners on those buttons
	   	fp.addMouseListener(new MouseListener(){

	   		public void mouseClicked(MouseEvent e) {
				System.out.println("you clicked, didn't you?");
				int mousex = e.getX();
				int mousey = e.getY();
				flood.drop(mousex,mousey,10,6);
				//fp.test();
			}

	   		public void mouseExited(MouseEvent e){ }
			public void mouseEntered(MouseEvent e){ }
			public void mousePressed(MouseEvent e){ }
			public void mouseReleased(MouseEvent e){ }

		});
		JPanel b = new JPanel();
	    b.setLayout(new BoxLayout(b, BoxLayout.LINE_AXIS));

	    JButton kiera = new JButton("Kiera says hey");
	    kiera.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		System.out.println("hello, baby");
			}
		});

		JButton btReset = new JButton("Reset");
		btReset.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//flood.init();
				System.out.println("resettt BUTTON");

				flood.Clear();
				FlowPanel.bReset = true;
				fp.repaint();
				System.out.println("resettt");
			}
		});


		JButton bStart = new JButton("Start");
		bStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("start button pressed");

				fp.play();
				//fp.setbRunning(true);
			}
		});

		JButton bPause = new JButton("Pause");
		bPause.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println("pause button pressed");
				fp.pause();
			}
		});

		JButton endB = new JButton("End");;
		// add the listener to the jbutton to handle the "pressed" event
		endB.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// to do ask threads to stop
				fp.setbRunning(false);
				frame.dispose();
			}
		});


		/**
		 * add buttons to button panel
		 *
		 * then add button panel to main panel on JFrame
		 */
		b.add(endB);
		b.add(btReset);
		//b.add(kiera);
		b.add(bPause);
		b.add(bStart);
		g.add(b);
    	
		frame.setSize(frameX, frameY+50);	// a little extra space at the bottom for buttons
      	frame.setLocationRelativeTo(null);  // center window on screen
      	frame.add(g); //add contents to window
        frame.setContentPane(g);
        frame.setVisible(true);
        Thread fpt = new Thread(fp); //main single thread?
		fp.pause();
		fp.setbRunning(true);//testing
        fpt.start();
	}
	
		
	public static void main(String[] args) {
		Terrain landdata = new Terrain();

		// check that number of command line arguments is correct
		if(args.length != 1)
		{
			System.out.println("Incorrect number of command line arguments. Should have form: java -jar flow.java intputfilename");
			System.exit(0);
		}
				
		// landscape information from file supplied as argument
		// 
		landdata.readData(args[0]);
		
		frameX = landdata.getDimX();
		frameY = landdata.getDimY();
		SwingUtilities.invokeLater(()->setupGUI(frameX, frameY, landdata));

		// to do: initialise and start simulation
		flood = new Water2(landdata.getDimX(),landdata.getDimY());
		flood.init();

		float testLevel = flood.getWaterLevel(1,2);
		System.out.println(testLevel);
		float testland = landdata.height[0][0];
		System.out.println(testland);

	}
}
