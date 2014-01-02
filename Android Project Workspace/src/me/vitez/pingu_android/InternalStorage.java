package me.vitez.pingu_android;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;



public class InternalStorage extends Activity {
	
	private final String FILENAME = "internalData.dat";
	
	public void write(String s) throws IOException {
		FileOutputStream fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
		fos.write(s.getBytes());
		fos.close();
	}
	
	public String read() throws IOException {
		FileInputStream fis = openFileInput(FILENAME);
		StringBuffer fileContent = new StringBuffer("");

		byte[] buffer = new byte[1024];

		while (fis.read(buffer) != -1) {
		    fileContent.append(new String(buffer));
		}
		
		return fileContent.toString();
	}
}
