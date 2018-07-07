package BitmapReader;

import java.awt.*;
import java.io.*;

public class BitmapReader {

    InputStream test;
    private final int height;
    private final int width;
    private final int colorDepth;
    private final int colorsUsed;
    private final int bfOffBit;
    private byte[] b = new byte[54];
    private int[] colortable;
    private int[][] pixeltable;

    public BitmapReader(InputStream test) throws Exception{
        this.test = test;
        int value;
        int counter;
        for(int i = 0; i < b.length; i++) {
            try {
                value = test.read();
                b[i] = (byte) value;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        bfOffBit = (b[13]<<24)&0xff000000|
                (b[12]<<16)&0x00ff0000|
                (b[11]<<8)&0x0000ff00|
                (b[10]<<0)&0x000000ff;

        width = (b[21]<<24)&0xff000000|
                (b[20]<<16)&0x00ff0000|
                (b[19]<<8)&0x0000ff00|
                (b[18]<<0)&0x000000ff;

        height = (b[25]<<24)&0xff000000|
                (b[24]<<16)&0x00ff0000|
                (b[23]<<8)&0x0000ff00|
                (b[22]<<0)&0x000000ff;

        colorDepth = (b[29]<<8)&0xff00|
                (b[28]<<0)&0x00ff;

        colorsUsed = (b[49]<<24)&0xff000000|
                (b[48]<<16)&0x00ff0000|
                (b[47]<<8)&0x0000ff00|
                (b[46]<<0)&0x000000ff;
        if(colorDepth == 1) {
            pixeltable = new int[height * 8][width * 8];
        } else {
            pixeltable = new int[height][width];
        }

        System.out.println(bfOffBit);
        System.out.println(colorsUsed);
        System.out.println(colorDepth);
        if(colorDepth <= 8) {
            if(colorsUsed == 0) {
                colortable = new int[(int)Math.pow(2,colorDepth)];
            } else {
                //System.out.println("THIS IS COLORUSED " + colorsUsed);
                colortable = new int[colorsUsed];
            }
            for(int i = 0; i < colortable.length; i++) {
                try {

                    colortable[i] = (byte) test.read() << 0 & 0x0000ff | (byte) test.read() << 8 & 0x00ff00 |
                            (byte) test.read() << 16 & 0xff0000;
                    //System.out.println(colortable[i]);
                    //System.out.println("COLOR " + colortable[i]);
                    test.skip(1);

                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            for(int tmp = 0; tmp < bfOffBit-b.length-colortable.length*4; tmp++) {
                try {
                    test.read();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(int row = 0; row < height; row++) {
                counter = 0;
                int offset = 0;
                int multiply = 0;
                int zähler = 128;
                int speicher = 0;
                for (int col = 0; col < width; col++) {
                    try {
                        if (colorsUsed != 0 && colorDepth == 1) {
                            if (counter == 0) {

                                speicher = test.read();
                                counter = 7;
                                zähler = 128;
                            }
                        counter--;
                        pixeltable[row][col] = colortable[(int) Math.floor(speicher / zähler)];
                        speicher = speicher % zähler;
                        zähler = zähler / 2;
                        multiply++;
                        }
                        else {

                            offset++;
                        speicher = test.read();

                            if(speicher != -1) {
                            pixeltable[row][col] = colortable[speicher];
                        }
                        multiply++;
                    }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } if(colorsUsed == 1 && multiply % 32 != 0) {
                    try {
                        int zwischen = 32 - (multiply % 32);
                        test.skip(zwischen);
                    } catch (IOException e ) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        if(multiply % 4 != 0) {
                            int zwischen = 4 - (multiply % 4);
                            test.skip(zwischen);
                        }
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } else if(colorDepth == 24) {
            pixeltable = new int[getImageHeight()][getImageWidth()];
            byte[] f = new byte[3];
                for(int row = 0; row < height; row++) {
                    counter = 0;
                    for(int col = 0; col < width; col++) {
                        try {
                            test.read(f);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        counter += 3;
                        value = f[0] << 0 & 0x0000ff | f[1] << 8 & 0x00ff00 |
                                f[2] << 16 & 0xff0000;
                        pixeltable[row][col] = value;
                    }
                    if(counter % 4 != 0) {
                        int zwischen = 4 - (counter % 4);
                        try {
                            test.skip(zwischen);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

    public int getImageHeight() {
        return height;
    }

    public int getImageWidth() {
        return width;
    }

    public int getPixel(int col, int row) {
        return pixeltable[height-row-1][col];
    }

    public int getColorDepth() {
        return colorDepth;
    }

    public int getColorsUsed() {
        return colorsUsed;
    }


}
