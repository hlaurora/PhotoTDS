package premium;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dominio.Usuario;

public class Servicios {
	
	public static void crearPdf(Usuario u) {
		FileOutputStream archivo;
		try {
			String rutaBase = System.getProperty("user.dir");

		    // Construir la ruta completa hacia el archivo
		    String rutaCompleta = rutaBase + File.separator + "src" + 
		    			File.separator + "premium" + File.separator + "Seguidores.pdf";
	        archivo = new FileOutputStream(rutaCompleta);

			Document document = new Document();
	        PdfWriter.getInstance(document, archivo);
            document.open();
             
            PdfPTable table = new PdfPTable(3);          
            table.addCell("Nombre");
            table.addCell("Email");
            table.addCell("Presentacion");
            
            for (Usuario w : u.getSeguidores()) {
            	table.addCell(w.getNombre());
            	table.addCell(w.getEmail());
            	table.addCell(w.getTextoPresentacion());
            }
                          
            // Agregamos la tabla al documento            
            document.add(table);
            document.close();
	     	
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void crearExcel(Usuario u) {
		try   
		{  
		String rutaBase = System.getProperty("user.dir");
	    String rutaCompleta = rutaBase + File.separator + "src" + 
	    			File.separator + "premium" + File.separator + "Seguidores.xls";
		
		
		HSSFWorkbook workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet("Seguidores de " +  u.getNombreUsuario());   
		HSSFRow rowhead = sheet.createRow((short)0);  
		rowhead.createCell(0).setCellValue("Nombre");  
		rowhead.createCell(1).setCellValue("Email");  
		rowhead.createCell(2).setCellValue("Presentacion");  
		
		for (int i = 1; i < u.getSeguidores().size()+1; i++) {
			Usuario s = u.getSeguidores().get(i-1);
			HSSFRow row = sheet.createRow((short)i);  
			row.createCell(0).setCellValue(s.getNombre());  
			row.createCell(1).setCellValue(s.getEmail());  
			row.createCell(2).setCellValue(s.getTextoPresentacion());  
		}
		
		FileOutputStream fileOut = new FileOutputStream(rutaCompleta);  
		workbook.write(fileOut);  
		fileOut.close();  
		workbook.close();  
		System.out.println("Excel creado.");  
		}   
		catch (Exception e)   
		{  
		e.printStackTrace();  
		}  
	}
	

}
