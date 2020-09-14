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
    }
    public void drop(int x, int y,int rise){
       // synchronized (droplock)
        //check for not going over edges
        System.out.println("adding water to point x =" + x + " and y = " + y);
        for(int i = -1; i<2;i++){
            for(int j = -1; j<2;j++){
                System.out.println(wLevel[x+i][y+j] + " before");
                wLevel[x+i][y+j] = wLevel[x+i][y+j] + (float) (0.01f*rise) ;
                System.out.println(wLevel[x+i][y+j] + " after");
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

    // get greyscale image
    public BufferedImage getImage() {
        return wImg;
    }

    public float getWaterLevel(int x,int y){
        return wLevel[x][y];
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

}