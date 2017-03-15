package com.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;

public class FileUtils {
	public String createFile(int function) {
		String fileName = "";
		switch (function) {
		case 1:
			fileName = "Rastrigin";
			break;
		case 2:
			fileName = "Griewangk";
			break;
		case 3:
			fileName = "Rosenbrock";
			break;
		case 4:
			fileName = "Camel";
			break;
		}
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
		fileName = fileName + timestamp + ".txt";
		try {
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			File file = new File(url.getPath() + fileName);
			file.getParentFile().mkdirs();
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	public void updateFile(String fileName, String text) {
		try {
			URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
			File file = new File(url.getPath() + fileName);
			FileWriter writer = new FileWriter(file.getPath(), true);
			BufferedWriter bw = new BufferedWriter(writer);
			bw.write(text);
			bw.newLine();
			bw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
