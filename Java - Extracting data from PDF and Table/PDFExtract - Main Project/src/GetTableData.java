import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class GetTableData {
	public ArrayList<File> getFiles(String path)
	{
		File folder = new File(path);
		File[] files = folder.listFiles();
		ArrayList<File> actual_files = new ArrayList<File>();
        for(int i=0;i<files.length;i++)
        {
        	if(files[i].isDirectory())
        	{
        		ArrayList<File> dir_files;
        		dir_files = getFiles(files[i].getAbsolutePath());
        		for(File x : dir_files)
        		{
        			actual_files.add(x);
        		}
        	}
        	else
        	{
        		actual_files.add(files[i]);
        	}
        }
        return actual_files;
	}
	public static void main(String args[])
	{
		ArrayList<File> files = new GetTableData().getFiles("C:\\Users\\Chiranjeev\\Desktop\\PDFBoxProject\\Table Data - PDF"); // Path where the text files containing folder name and page numbers with tables of each pdf are stored   
		for(File x : files)
		{
			String string = null;
			try {
				BufferedReader br = new BufferedReader(new FileReader(x));
				String st;
				String folder_name = br.readLine();
				System.out.println(folder_name);
				new File("C:/Users/Chiranjeev/Desktop/PDFBoxProject/Table Data - Text Files/"+folder_name).mkdir();
				PDFParser pdfParser = new PDFParser(new RandomAccessFile(new File("C:/Users/Chiranjeev/Desktop/PDFBoxProject/New ESG Reports/"+folder_name+".pdf"), "r")); //Path where the Actual PDF's are present
	            pdfParser.parse();
	            PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
	            PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
				  while ((st = br.readLine()) != null) 
				  {
					  System.out.println(st);
					  pdfTextStripper.setStartPage(Integer.valueOf(st));
			            pdfTextStripper.setEndPage(Integer.valueOf(st));
			            string = pdfTextStripper.getText(pdDocument);
			            FileWriter fw = new FileWriter("C:/Users/Chiranjeev/Desktop/PDFBoxProject/Table Data - Text Files/"+folder_name+"/Page"+st+".txt"); // Path where we want to get the output files (with table data)
			            fw.write(string);
			            fw.close(); 
				  }
				  br.close();
				  pdDocument.close();
				  System.out.println("Success");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
