package com.gdms.util;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ByteUtil {
	
	public static byte[] readBytes(String fileName) {

		Path path = Paths.get(fileName);

		byte[] bytes;
		try {
			bytes = Files.readAllBytes(path);
		} catch (IOException e) {
			e.printStackTrace();
			return new byte[0];
		}

		return bytes;

	}

	public static int[] convertLabelBytes(byte[] bytes) {

		int length = bytes.length/2;
		int[] array = new int[length];
		byte[] fourBytes = new byte[4];
		for (int i=0; i<length; i++) {
			int index = 2*i;
			fourBytes[0] = 0;
			fourBytes[1] = 0;
			fourBytes[3] = bytes[index];
			fourBytes[2] = bytes[index+1];
			array[i] = ByteBuffer.wrap(fourBytes).getInt();
		}

		return array;

	}

	public static int[][][] convertImageBytes(byte[] bytes, int width, int height) {

		int totalPixels = bytes.length/2;
		int pixelsPerPic = width*height;
		int numPics = totalPixels / pixelsPerPic;
		int[][][] array = new int[numPics][width][height];
		byte[] fourBytes = new byte[4];
		int index = 0;
		for (int i=0; i<numPics; i++) {
			for (int j=0; j<width; j++) {
				for (int k=0; k<height; k++) {

					fourBytes[0] = 0;
					fourBytes[1] = 0;
					fourBytes[3] = bytes[index];
					fourBytes[2] = bytes[index+1];
					index = index+2;

					array[i][k][j] = ByteBuffer.wrap(fourBytes).getInt();
				}
			}
		}

		return array;

	}
	
	public static double[][] convertResultBytes(byte[] bytes, int numClasses) {
		
		int numResults = bytes.length/(8*numClasses);
		
		byte[] eightBytes = new byte[8];
		double[][] results = new double[numResults][numClasses];
		int index = 0;
		for (int i=0; i<numResults; i++) {
			
			for (int j=0; j<numClasses; j++) {
				
				eightBytes[7] = bytes[index];
				eightBytes[6] = bytes[index+1];
				eightBytes[5] = bytes[index+2];
				eightBytes[4] = bytes[index+3];
				eightBytes[3] = bytes[index+4];
				eightBytes[2] = bytes[index+5];
				eightBytes[1] = bytes[index+6];
				eightBytes[0] = bytes[index+7];
				
				results[i][j] = ByteBuffer.wrap(eightBytes).getDouble();
				
				index += 8;
				
			}
			
		}

		return results;
		
	}

}
