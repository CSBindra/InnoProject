import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PDFPageExtractor {

	public static void main(String[] args) throws IOException{
		File folder = new File("C:\\Users\\Chiranjeev\\Desktop\\PDFBoxProject\\New ESG Reports"); //Path where the Actual PDF's are present
		 
        String[] files = folder.list();
 
        for (String file : files)
        {
        	File document = new File("C:\\Users\\Chiranjeev\\Desktop\\PDFBoxProject\\New ESG Reports\\"+file);
    		PDDocument doc = PDDocument.load(document);
    		PDFTextStripper pdfStripper = new PDFTextStripper();
    		System.out.println(file);
    		String folder_name = file.split("\\.")[0];
    		new File("C:\\Users\\Chiranjeev\\Jupiter Notebook Programs\\ESG Project\\PageExtractedData\\"+folder_name).mkdir(); // Path from where Jupyter notebook picks data to identify tables

    		try
    		{
    			FileWriter fw;
    			for(int i=1;i<doc.getNumberOfPages();i++)
    			{	if(i < 10)
    				{
    					fw = new FileWriter("C:\\Users\\Chiranjeev\\Jupiter Notebook Programs\\ESG Project\\PageExtractedData\\"+folder_name+"\\"+folder_name.toLowerCase()+"_0"+i+".txt");
    				}
    				else
    				{
    					fw = new FileWriter("C:\\Users\\Chiranjeev\\Jupiter Notebook Programs\\ESG Project\\PageExtractedData\\"+folder_name+"\\"+folder_name.toLowerCase()+"_"+i+".txt");
    				}
    				pdfStripper.setStartPage(i);
    				pdfStripper.setEndPage(i);
    				
    				String text = pdfStripper.getText(doc);
    				fw.write(text);
    				fw.close();
    			}
    		}
    		catch(Exception e)
    		{
    			System.out.println(e);
    		}
    		System.out.println("Success");
    		doc.close();
       }
	}

}
