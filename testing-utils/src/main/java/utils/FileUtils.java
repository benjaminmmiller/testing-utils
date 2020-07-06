package utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class FileUtils {
	
	
	public static File createDirectory(String path, boolean emptyDirectory) {
		File newDirectory = new File(path);
		if(newDirectory.exists()==false) {
			newDirectory.mkdirs();
		}
		if(emptyDirectory==true) {
			try {
				org.apache.commons.io.FileUtils.cleanDirectory(newDirectory);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return newDirectory;
	}
	
	public static void deleteDirectory(String path) {
		try {
			org.apache.commons.io.FileUtils.deleteDirectory(new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static File[] retrieveFilesInDirectory(String filePath) {
		File folder = new File(filePath);
		File[] listOfFiles = folder.listFiles();
		return listOfFiles;
	}
	
	public static String getProjectFilePath() {
		return System.getProperty("user.dir");
	}
	
	public static String generateDateForFilename() {
		String currentDate = new Date().toString();
		currentDate = currentDate.replaceAll(":", ".");
		System.out.println(currentDate);
		return currentDate;
	}
	
	public static void takeWdScreenshot(WebDriver wd) {
		File screenshot = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
		String screenshotDirectoryPath = FileUtils.getProjectFilePath()+"\\test-output\\screenshots";
		File screenshotFolder = FileUtils.createDirectory(screenshotDirectoryPath, false);
		
		try {
			org.apache.commons.io.FileUtils.copyFile(screenshot, new File(screenshotFolder.getAbsolutePath()+"\\"+generateDateForFilename()+".jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static List<File> sortFiles(List<File> files){
		Collections.sort(files, new Comparator<File>() {
		    public int compare(File f1, File f2) {
		        return extractInt(f1.getName()) - extractInt(f2.getName());
		    }

		    int extractInt(String s) {
		        String num = s.replaceAll("\\D", "");
		        // return 0 if no digits found
		        return num.isEmpty() ? 0 : Integer.parseInt(num);
		    }
		});
		return files;
	}
	
	public static File createAndWriteToTextFile(String fileDir, String fileName, String fileText) {
		File newFile = new File(fileDir+"\\"+fileName+".txt");
		try {
			if(newFile.exists()) {
				File oldFile = newFile;
				oldFile.delete();
				newFile.createNewFile();
			}
			else {
				newFile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(newFile);
			fileWriter.write(fileText);
			fileWriter.close();
			System.out.println("File has been created and written to: "+newFile.getAbsolutePath());
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return newFile;
	}
}
