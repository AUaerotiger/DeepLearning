package com.gdms.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtil {

	private static void drawImage(int[][] array, BufferedImage img, int startX, int startY) {

		int width = array.length;
		int height = array[0].length;

		int a = 255;

		for(int y = 0; y < height; y++){
			int picY = y+startY;
			for(int x = 0; x < width; x++){
				int picX = x + startX;
				int r = array[x][y]; //red
				int g = array[x][y]; //green
				int b = array[x][y]; //blue

				int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel

				img.setRGB(picX, picY, p);
			}
		}

	}

	private static void drawImage(int[][] array, BufferedImage img, int startX, int startY, boolean redBorder) {

		int width = array.length;
		int height = array[0].length;
		int a = 255;
		
		for(int y = 0; y < height; y++){
			int picY = y+startY;
			for(int x = 0; x < width; x++){
				int picX = x + startX;
				int r = array[x][y]; //red
				int g = array[x][y]; //green
				int b = array[x][y]; //blue

				int p = (a<<24) | (r<<16) | (g<<8) | b; //pixel

				img.setRGB(picX, picY, p);
			}
		}

		int borderR;
		int borderG;
		int borderB;		
		if (redBorder) {
			borderR = 255;
			borderG = 0;
			borderB = 0;
		} else {
			borderR = 0;
			borderG = 255;
			borderB = 0;
		}

		int borderP = (a<<24) | (borderR<<16) | (borderG<<8) | borderB; //pixel

		//draw left border
		for (int i=0; i<height; i++) {
			int picX = startX;
			int picY = i + startY;
			img.setRGB(picX, picY, borderP);
		}

		//draw right border
		for (int i=0; i<height; i++) {
			int picX = startX+width-1;
			int picY = i + startY;
			img.setRGB(picX, picY, borderP);
		}

		//draw top border
		for (int i=0; i<width; i++) {
			int picX = startX+i;
			int picY = startY;
			img.setRGB(picX, picY, borderP);
		}

		//draw bottom border
		for (int i=0; i<width; i++) {
			int picX = startX+i;
			int picY = startY+height-1;
			img.setRGB(picX, picY, borderP);
		}

		

	}

	public static void writeImage(int[][][] array, String fileName, int picsPerRow) {

		//image dimension
		int width = array[0].length;
		int height = array[0][0].length;

		int numPics = array.length;

		int numRows = numPics / picsPerRow;

		int pixelsWide = picsPerRow * width;
		int pixelsHigh = numRows * height;

		//create buffered image object img
		BufferedImage img = new BufferedImage(pixelsWide, pixelsHigh, BufferedImage.TYPE_INT_ARGB);

		int startX = 0;
		int startY = 0;
		for (int i=0; i<numPics; i++) {

			drawImage(array[i], img, startX*width, startY*height);

			startX++;
			if (startX >= picsPerRow) {
				startX = 0;
				startY++;
			}

		}

		File f = null;
		try{
			f = new File(fileName);
			ImageIO.write(img, "png", f);
		}catch(IOException e){
			System.out.println("Error: " + e);
		}

	}

	public static void writeImage(int[][][] array, String fileName, int picsPerRow, int[] expected, 
			int[] actual, String[] classNames) {

		//image dimension
		int width = array[0].length;
		int height = array[0][0].length;

		int numPics = array.length;

		int numRows = numPics / picsPerRow;

		int pixelsWide = picsPerRow * width;
		int pixelsHigh = numRows * height;

		//create buffered image object img
		BufferedImage img = new BufferedImage(pixelsWide, pixelsHigh, BufferedImage.TYPE_INT_ARGB);

		int startX = 0;
		int startY = 0;
		for (int i=0; i<numPics; i++) {
			
			if (expected[i] == actual[i]) {
				drawImage(array[i], img, startX*width, startY*height, false);
			} else {
				drawImage(array[i], img, startX*width, startY*height, true);
			}

			startX++;
			if (startX >= picsPerRow) {
				startX = 0;
				startY++;
			}

		}

		File f = null;
		try{
			f = new File(fileName);
			ImageIO.write(img, "png", f);
		}catch(IOException e){
			System.out.println("Error: " + e);
		}

	}

}
