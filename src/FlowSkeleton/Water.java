/**Corresponding 2D array working alongside the terrain grid
 Txy + Wxy = Sxy (surface at xy which is what is compared)

 **/
package FlowSkeleton;

import java.awt.image.*; //includes bufferedImage?
import java.awt.Color;

public class Water{


    float [][] wLevel; // regular grid of water level values
    int dimx, dimy; // data dimensions
    BufferedImage wImg;

    public Water(int xx, int yy){
        dimx = xx;
        dimy = yy;




    }

    public void init(){
        wLevel = new float[dimx][dimy];
        for(int x = 0; x<dimx;x++){

            for(int y = 0; y<dimy; y++){
                wLevel[x][y] = 0;
            }
        }

        for(int x = 300; x<14;x++){
            for(int y = 300; y<14; y++){
                wLevel[x][y] = 1;
            }
        }

    }
    public void drop(int x, int y,int depth,int span){
       // synchronized (droplock)
        //check for not going over edges
        System.out.println("adding water to point x =" + x + " and y = " + y);


        for(int i = (-span); i<span;i++){
            for(int j = (-span); j<span;j++){
              //  System.out.println(wLevel[x+i][y+j] + " before");
                System.out.println("[x+i]= " + (x+i) + "  [y+j]= " + (y+j));
                if (((x+i) > -1 & (y+j) > -1) & ((x+i) < dimx & (y+j) < dimy)){
                    wLevel[x+i][y+j] = wLevel[x+i][y+j] + (float) (0.01f*depth) ;
                    //System.out.println(wLevel[x+i][y+j] + " after");
                }

            }
        }
    }


    int dim(){
        return dimx*dimy;
    }

    // get x-dimensions (number of columns)
    int getDimX(){
        return dimx;
    }

    // get y-dimensions (number of rows)
    int getDimY(){
        return dimy;
    }

    public void clearEdges(){
        //iterate through first row
        for (int j = 0;j<dimy;j++){
            wLevel[0][j] = 0;
            System.out.println("cleared: x = 0" + " y = " + j);
        }

        //iterate through last row
        for (int j = 0;j<dimy;j++){
            wLevel[dimx-1][j] = 0;
            System.out.println("cleared: x = "+ j + " y = " + (dimy-1));
            //wLevel[i][dimy] = 0;
        }

        //loop through all rows and set first and last column to zero
        for (int i = 1;i<dimx-1;i++){
            wLevel[i][0] = 0;
            System.out.println("cleared: x = "+ i + " y = 0");
            wLevel[i][dimy-1] = 0;
            System.out.println("cleared: x = "+i + " y = "+ (dimy-1));
            //wLevel[i][dimy] = 0;
        }
    }

    /**
     *
     * @param x
     * @param y
     */
    public void move(int x,int y,Terrain land){

//        System.out.println("inside move: x = " + x + ", y = " + y);
//        System.out.println("water value is: " + wLevel[x][y]);
//        System.out.println("terrain value is: " + land.getHeight(x,y));
//        System.out.println("surface value is: " + (land.getHeight(x,y)+ wLevel[x][y]));
        float surfaceMin = wLevel[x][y] + land.getHeight(x,y);
        float surface = 0;

        int minX = x;
        int minY = y;

        //find minimum
        //check not edge because edges would be zero
        if (wLevel[x][y] > 0) {
            for(int i = -1; i<2;i++) {
                for (int j = -1; j < 2; j++) {
                    surface = land.getHeight(x+i,y+j) + wLevel[x+i][y+j];
                    if (surface<surfaceMin){
                        //System.out.println("new min is " + surface );
                        surfaceMin = surface;
                        minX = x+i;
                        minY = y+j;
                }
            }
          if ((minX != x) & (minY != y)){
              System.out.println("transfering to min at X = " + minX + " y is " + minY);
              System.out.println("min value =" + surfaceMin);
              wLevel[x][y] = wLevel[x][y] - 0.01f;
              wLevel[minX][minY] = wLevel[minX][minY] + 0.01f;
          }

        }
//        for(int i = -1; i<2;i++){
//            for(int j = -1; j<2;j++){
//                if (wLevel[x+i][y+j] != null){
//                    if(wLevel[x+i][y+j] > 0){
//
//                    }
//                }
                //  System.out.println(wLevel[x+i][y+j] + " before");
//                System.out.println("[x+i]= " + (x+i) + "  [y+j]= " + (y+j));
//                if (((x+i) > -1 & (y+j) > -1) & ((x+i) < dimx & (y+j) < dimy)){
//                    if(wLevel[x+i][y+j] != null & )
//                    wLevel[x+i][y+j] = wLevel[x+i][y+j] + (float) (0.01f*depth) ;
                    //System.out.println(wLevel[x+i][y+j] + " after");
                }

            }



    public float getWaterLevel(int x,int y){
        return wLevel[x][y];
    }

    public BufferedImage getImage() {
        return wImg;
    }

    void deriveImage(){
        Color transparent = new Color(0,0,0,0);
        int height = dimy;
        int width = dimx;
        wImg = new BufferedImage(dimx, dimy, BufferedImage.TYPE_INT_ARGB);
        for(int i = 0; i<width;i++){
            for(int j = 0; j<height;j++){
                if (wLevel[i][j] > 0){
                    wImg.setRGB(i,j,Color.BLUE.getRGB());
                }
                else{
                    wImg.setRGB(i,j,transparent.getRGB());
                    }
                }
            }
        }


    }

    /**
     * find min and flow/transfer water
     * change water image overlay
     * eg.
     * ** //COMPARE SURFACE LEVELS
            float fMin = selected;
           for(int i = iRow-1;i<2;i++){
                  for(int j = iCol-1;i<2;i++){
                      if compare[i][j]<fMin.value(){
                          fMin.value = compare[i][j];
                         // pos[] = [i,k]; ????????????????
                      }

                  }
           }
           if (fMin != selected){
               fMin.waterlevel = fMin.waterlevel + 1;
               selected.waterlevel = selected.waterlevel - 1;


         }
            **/