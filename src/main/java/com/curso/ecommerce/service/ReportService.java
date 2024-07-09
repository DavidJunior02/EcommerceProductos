package com.curso.ecommerce.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFCreationHelper;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curso.ecommerce.model.DetalleOrden;
import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Producto;
import com.curso.ecommerce.repository.IDetalleOrdenRepository;
import com.curso.ecommerce.repository.IOrdenRepository;
import com.curso.ecommerce.repository.IProductoRepository;

@Service
public class ReportService {

	@Autowired
	private IDetalleOrdenRepository detalleordenrepo;
	
	@Autowired
	private IOrdenRepository ordenrepo;
	
	@Autowired
	private IProductoRepository prodrepo;



	public void generateExcel(Integer id,HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<DetalleOrden> list=detalleordenrepo.findByOrdenId(id);
		
		HSSFWorkbook hbook=new HSSFWorkbook();
		HSSFSheet hsheet=hbook.createSheet("DetalleOrden Info");
		HSSFRow hrow=hsheet.createRow(0);
		
	hrow.createCell(0).setCellValue("Nro. Producto");
	hrow.createCell(1).setCellValue("Nombre");
	hrow.createCell(2).setCellValue("Cantidad");
	hrow.createCell(3).setCellValue("Precio");
	hrow.createCell(4).setCellValue("Total");
	
	int datarowindex=1;
 for(DetalleOrden detalle: list) {
	 
	 HSSFRow datarow=hsheet.createRow(datarowindex);
     datarow.createCell(0).setCellValue(detalle.getId());
     datarow.createCell(1).setCellValue(detalle.getNombre());
     datarow.createCell(2).setCellValue(detalle.getCantidad());
     datarow.createCell(3).setCellValue(detalle.getPrecio());
     datarow.createCell(4).setCellValue(detalle.getTotal());
     
     datarowindex++;
 }
 ServletOutputStream ops=response.getOutputStream();
 hbook.write(ops);
 hbook.close();
 ops.close();
}





	public void generateExcel2(HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		List<Orden> list2=ordenrepo.findAll();
		
		HSSFWorkbook hbook=new HSSFWorkbook();
		HSSFSheet hsheet=hbook.createSheet("Orden Info");
		HSSFRow hrow=hsheet.createRow(0);
		
	hrow.createCell(0).setCellValue("Nro. Orden");
	hrow.createCell(1).setCellValue("Codigo Orden");
	hrow.createCell(2).setCellValue("Fecha de Creacion");
	hrow.createCell(3).setCellValue("Total");
	HSSFCreationHelper creationHelper = hbook.getCreationHelper();
	HSSFCellStyle dateCellStyle = hbook.createCellStyle();
	dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd/MM/yyyy"));

	int datarowindex=1;
 for(Orden ord: list2) {
	 
	 HSSFRow datarow=hsheet.createRow(datarowindex);
     datarow.createCell(0).setCellValue(ord.getId());
     datarow.createCell(1).setCellValue(ord.getNumero());
     HSSFCell dateCell = datarow.createCell(2);
     dateCell.setCellValue(ord.getFechaCreacion());
     dateCell.setCellStyle(dateCellStyle);
     
     
     datarow.createCell(3).setCellValue(ord.getTotal());
     
     datarowindex++;
 }
 ServletOutputStream ops=response.getOutputStream();
 hbook.write(ops);
 hbook.close();
 ops.close();
		
		
	}
	

	public void generateExcel3(HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		List<Producto> list3=prodrepo.findAll();
		
		HSSFWorkbook hbook=new HSSFWorkbook();
		HSSFSheet hsheet=hbook.createSheet("Productos Info");
		HSSFRow hrow=hsheet.createRow(0);
		
	hrow.createCell(0).setCellValue("Nro. Producto");
	hrow.createCell(1).setCellValue("Nombre");
	hrow.createCell(2).setCellValue("Descripcion ");
	hrow.createCell(3).setCellValue("Inventario");
	hrow.createCell(4).setCellValue("Precio");
	
	int datarowindex=1;
 for(Producto pro: list3) {
	 
	 HSSFRow datarow=hsheet.createRow(datarowindex);
     datarow.createCell(0).setCellValue(pro.getId());
     datarow.createCell(1).setCellValue(pro.getNombre());
     datarow.createCell(2).setCellValue(pro.getDescripcion());
     datarow.createCell(3).setCellValue(pro.getCantidad());
     datarow.createCell(4).setCellValue(pro.getPrecio());
     
     datarowindex++;
 }
 ServletOutputStream ops=response.getOutputStream();
 hbook.write(ops);
 hbook.close();
 ops.close();
}


	
	}
	

