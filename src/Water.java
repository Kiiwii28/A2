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
        for(int x = 0; x<14;x++){

            for(int y = 0; y<14; y++){
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
                System.out.println(wLevel[x+i][y+j] + " before");
                wLevel[x+i][y+j] = wLevel[x+i][y+j] + (float) (0.01f*depth) ;
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