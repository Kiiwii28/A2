package FlowSkeleton;

import java.awt.image.*; //includes bufferedImage?
import java.awt.Color;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class Water2{

    // using this instead of array of atomic integers so lss overhead (lose code simplicity)
    private static AtomicIntegerArray wLevel;
    BufferedImage wImg;
    int dim; //dimension x*y)
    volatile boolean bFirstRun;
    int dimY;
    int dimX;
   // ArrayList<Integer> blue = new ArrayList<Integer>();
    ArrayList<Integer> edges = new ArrayList<Integer>();
    ArrayList<Integer> check = new ArrayList<Integer>();
   // ArrayList<Integer> clear = new ArrayList<Integer>();

    public Water2(int xx, int yy){
        dimY = yy;
        dimX = xx;
       dim = xx*yy;
       wLevel = new AtomicIntegerArray(dim);
       bFirstRun = true;
       setEdges();




    }

    public void init(){
        for (int i=0;i<dim;i++){
            wLevel.set(i,0);
        }

    }
    public void drop(int x, int y,int depth,int span){
        //check for not going over edges
        System.out.println("adding water to point x =" + x + " and y = " + y);
        int linearPos = ((x*dimY)+y);

        for(int i = -(span); i < (span+1); i++){
            int line = linearPos + (i*dimY); //row is not an index
            for(int j = -(span);j<(span+1);j++){
                boolean NotFirstRow = line >= dimY;
                boolean NotLastRow = line < ((dimX-1)*(dimY));
                boolean NotFirstCol = !((line+j)%dimY==0);
                boolean NotLastCol = !((line+j)%dimY==dimY-1);
                if(NotFirstRow & NotLastRow & NotFirstCol & NotLastCol);
                wLevel.getAndAdd(line+j,depth);
                check.add(line+j);
            }
        }
    }


    int dim(){
        return dim;
    }

    // get x-dimensions (number of columns)
    int getDimX(){
        return dimX;
    }

    // get y-dimensions (number of rows)
    int getDimY(){
        return dimY;
    }

    public void setEdges(){
        //add first row
        for(int i= 0; i<dimY;i++){
           edges.add(i);
        }
        //add last row
        for (int i= ((dimX-1)*dimY); i<dimY*dimX;i++){
            edges.add(i);
        }
        //add first and last column
        for(int i = 0; i < dimY*dimX;i++){
            if(  (i%dimY)==0 | (i%dimY)==(dimY-1)    ){
                edges.add(i);
            }
        }

    }


    public void clearEdges(){
        for(int i=0;i<edges.size();i++) {
            wLevel.set(edges.get(i), 0); //edges is list of indices, not actual values
        }
    }

    /**
     *
     * @param x
     * @param y
     */
    public synchronized void move(int x,int y,Terrain land){


        if (getWaterLevel(x,y) > 0) {
           // System.out.println("inside move: x = " + x + ", y = " + y);
//        System.out.println("water value is: " + wLevel[x][y]);
//        System.out.println("terrain value is: " + land.getHeight(x,y));
//        System.out.println("surface value is: " + (land.getHeight(x,y)+ wLevel[x][y]));
            float surfaceMin = (float)(getWaterLevel(x,y)*0.01) + land.getHeight(x,y);
            float surface = 0;

            int minX = x;
            int minY = y;

            //find minimum
            //check not edge because edges would be zero
            for(int i = -1; i<2;i++) {
                for (int j = -1; j < 2; j++) {
                    surface = land.getHeight(x + i, y + j) + (float)(getWaterLevel(x + i, y + j)*0.01);
                    if (surface < surfaceMin) {
                        //System.out.println("new min is " + surface );
                        surfaceMin = surface;
                        minX = x + i;
                        minY = y + j;
                    }
                }
            }
                if ((minX != x) & (minY != y)){
                    //System.out.println("transfering to min at X = " + minX + " y is " + minY);
                    //System.out.println("min value =" + surfaceMin);
                    setWaterLevel(x,y,getWaterLevel(x,y) - 1);
                    check.add((x*dimY)+y); //add that x,y as a linear index to array of cells that need to be checked/updated
                    setWaterLevel(minX,minY,getWaterLevel(minX,minY)+1);
                   check.add((minX*dimY)+minY);
                }

            }
    }





    public int getWaterLevel(int x,int y){ //eg. given 5,6 with y-dimension = 11
        //(5*11)+6
        //get right row, then add to get to correct column
        return wLevel.get((x*dimY)+y);
    }

    public void setWaterLevel(int x,int y,int set){
        wLevel.set((x*dimY)+y,set);
    }

    public int getWaterLevel(int pos){
          return  wLevel.get(pos);
    }
    public BufferedImage getImage() {
        return wImg;
    }

    synchronized void deriveImage(){

       int height = dimY;
       int width = dimX;

//        int height = dimX; //rows
//        int width = dimY; //columns
        Color transparent = new Color(0,0,0,0);

        if (bFirstRun){
            wImg = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);
            for(int i = 0; i<dimX;i++){
                for(int j = 0; j<dimY;j++){
                    wImg.setRGB(i,j,transparent.getRGB());
                }
            }
            bFirstRun = false;
        }
        else{ //just updates
            for (int i = 0; i<check.size();i++){
                int x = check.get(i)/dimY;
                int y = check.get(i)%dimY;

                if (wLevel.get(check.get(i))>0){
                    wImg.setRGB(x,y,Color.BLUE.getRGB());
                    }
                    else{
                    wImg.setRGB(x,y,transparent.getRGB());
                    }
            }
            check.clear();
            for (int i = 0;i<edges.size();i++){ //edge is list of indices of wLevel's edges
                int x =  edges.get(i)/dimY; //convert to 2D co-ords
                int y =  edges.get(i)%dimX;
                wImg.setRGB(x,y,transparent.getRGB());
            }
        }
    }


}