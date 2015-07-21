package com.dasher.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.dasher.model.Complain;
import com.dasher.model.MarketCommodity;
import com.dasher.model.ShopDish;
import com.dasher.model.User;

public class FileUploadUtil {

	public static Map<String,String> uploadFile(HttpServletRequest request,String path){
		Map<String,String> dataMap=new HashMap<String,String>();
		String uploadFileName="";
		try{
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload upload = new ServletFileUpload(factory);
			//解决上传文件名的中文乱码
			upload.setHeaderEncoding("UTF-8"); 
			//3、判断提交上来的数据是否是上传表单的数据
//			if(!ServletFileUpload.isMultipartContent(request)){
//				//按照传统方式获取数据
//				return null;
//			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = upload.parseRequest(request);
			for(FileItem item : list){
				//如果fileitem中封装的是普通输入项的数据
				if(item.isFormField()){
					String name = item.getFieldName();
					//解决普通输入项的数据的中文乱码问题
					String value = item.getString("UTF-8");
					dataMap.put(name, value);
				}else{//如果fileitem中封装的是上传文件
					//得到上传的文件名称，
					String filename = item.getName();
					//System.out.println(filename);
					if(filename==null || filename.trim().equals("")){
						continue;
					}
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					filename = filename.substring(filename.lastIndexOf("\\")+1);
					UUID uuid=UUID.randomUUID();
					String str[]=uuid.toString().split("-");
					for(int i=0;i<str.length;i++)
					{
						uploadFileName=uploadFileName+str[i];
					}
					uploadFileName=uploadFileName+filename.substring(filename.lastIndexOf("."));
					//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
					String savePath = request.getSession().getServletContext().getRealPath(path);
					File file = new File(savePath);
					//判断上传文件的保存目录是否存在
					if (!file.exists()) {
						//创建目录
						file.mkdir();
					}
					//获取item中的上传文件的输入流
					InputStream in = item.getInputStream();
					String filePath=savePath + "\\" + uploadFileName;
					file = new File(filePath);
					//判断上传文件的保存目录是否存在
					if (!file.exists()) {
						file.createNewFile();
					}
					//创建一个文件输出流
					FileOutputStream out = new FileOutputStream(file);
					//创建一个缓冲区
					byte buffer[] = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len=in.read(buffer))>0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						out.write(buffer, 0, len);
					}
					//关闭输入流
					in.close();
					//关闭输出流
					out.close();
					//删除处理文件上传时生成的临时文件
					item.delete();
					
					dataMap.put("fileName", uploadFileName);
				}
			}
		}catch (Exception e) {
			return null;
		}
		return dataMap;
	}

	//解析xls文件
	public static List<ShopDish> readXls(HttpServletRequest request,String path) throws FileNotFoundException, IOException{

		List<ShopDish> list=new ArrayList<ShopDish>();

		String savePath = request.getSession().getServletContext().getRealPath(path);
		File file = new File(savePath);
		POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

		int rowstart = hssfSheet.getFirstRowNum();
		int rowEnd = hssfSheet.getLastRowNum();
		for(int i=rowstart+1;i<=rowEnd;i++)
		{
			HSSFRow row = hssfSheet.getRow(i);
			if(null == row) continue;
			//int cellStart = row.getFirstCellNum();
			//int cellEnd = row.getLastCellNum();

			ShopDish sd=new ShopDish();
			String na=row.getCell(0).getStringCellValue();
			sd.setName(na);
			String pr=row.getCell(1).getNumericCellValue()+"";
			String ty=row.getCell(2).getNumericCellValue()+"";
			
			sd.setPrice(Float.parseFloat(pr));
			sd.setTypeId(Integer.parseInt(ty.substring(0, ty.indexOf("."))));
			sd.setChilies(row.getCell(3).getStringCellValue());
			sd.setDescription(row.getCell(4).getStringCellValue());

			list.add(sd);
		}
		return list;
	}

	//解析xlsx文件
	public static List<ShopDish> readXlsx(HttpServletRequest request,String path) throws InvalidFormatException, IOException{
		try{
			List<ShopDish> list=new ArrayList<ShopDish>();

			String savePath = request.getSession().getServletContext().getRealPath(path);
			File file = new File(savePath);

			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

			int rowstart = xssfSheet.getFirstRowNum();
			int rowEnd = xssfSheet.getLastRowNum();
			for(int i=rowstart+1;i<=rowEnd;i++)
			{
				XSSFRow row = xssfSheet.getRow(i);
				if(null == row) continue;

				ShopDish sd=new ShopDish();
				String na=row.getCell(0).getStringCellValue();
				sd.setName(na);
				String pr=row.getCell(1).getNumericCellValue()+"";
				String ty=row.getCell(2).getNumericCellValue()+"";
				
				sd.setPrice(Float.parseFloat(pr));
				sd.setTypeId(Integer.parseInt(ty.substring(0, ty.indexOf("."))));
				sd.setChilies(row.getCell(3).getStringCellValue());
				sd.setDescription(row.getCell(4).getStringCellValue());

				list.add(sd);
			}
			return list;
		}catch(Exception e){
			return null;
		}
	}
	
	
	//解析xls文件
	public static List<MarketCommodity> readCommodityXls(HttpServletRequest request,String path) throws FileNotFoundException, IOException{

		List<MarketCommodity> list=new ArrayList<MarketCommodity>();
		String savePath = request.getSession().getServletContext().getRealPath(path);
		File file = new File(savePath);
		POIFSFileSystem poifsFileSystem = new POIFSFileSystem(new FileInputStream(file));
		HSSFWorkbook hssfWorkbook =  new HSSFWorkbook(poifsFileSystem);
		HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);

		int rowstart = hssfSheet.getFirstRowNum();
		int rowEnd = hssfSheet.getLastRowNum();
		for(int i=rowstart+1;i<=rowEnd;i++)
		{
			HSSFRow row = hssfSheet.getRow(i);
			if(null == row) continue;
			//int cellStart = row.getFirstCellNum();
			//int cellEnd = row.getLastCellNum();

			MarketCommodity mc=new MarketCommodity();
			mc.setName(row.getCell(0).getStringCellValue());
			String pr=row.getCell(1).getNumericCellValue()+"";
			String ty=row.getCell(2).getNumericCellValue()+"";
			mc.setPrice(Float.parseFloat(pr));
			mc.setTypeId(Integer.parseInt(ty.substring(0, ty.indexOf("."))));
			mc.setUnit(row.getCell(3).getStringCellValue());
			mc.setSubscribe(row.getCell(4).getStringCellValue());
			list.add(mc);
		}
		return list;
	}

	//解析xlsx文件
	public static List<MarketCommodity> readCommodityXlsx(HttpServletRequest request,String path) throws InvalidFormatException, IOException{

		List<MarketCommodity> list=new ArrayList<MarketCommodity>();

		String savePath = request.getSession().getServletContext().getRealPath(path);
		File file = new File(savePath);

		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);

		int rowstart = xssfSheet.getFirstRowNum();
		int rowEnd = xssfSheet.getLastRowNum();
		for(int i=rowstart+1;i<=rowEnd;i++)
		{
			XSSFRow row = xssfSheet.getRow(i);
			if(null == row) continue;

			MarketCommodity mc=new MarketCommodity();
			mc.setName(row.getCell(0).getStringCellValue());
			String pr=row.getCell(1).getNumericCellValue()+"";
			String ty=row.getCell(2).getNumericCellValue()+"";
			mc.setPrice(Float.parseFloat(pr));
			mc.setTypeId(Integer.parseInt(ty.substring(0, ty.indexOf("."))));
			mc.setUnit(row.getCell(3).getStringCellValue());
			mc.setSubscribe(row.getCell(4).getStringCellValue());
			list.add(mc);
		}
		return list;
	}
	
	public static void createExcel(HttpServletRequest request,String fileName,List<User> userList) throws IOException{
		//将用户的列表信息保存为文件
        String savePath = request.getSession().getServletContext().getRealPath("/upload/settle/user/"+fileName);
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if (!file.exists()) {
			file.createNewFile();
		}
		//创建一个文件输出流
		FileOutputStream outputStream = new FileOutputStream(file);
        // 创建一个workbook 对应一个excel应用文件  
        XSSFWorkbook workBook = new XSSFWorkbook(); 
        // 在workbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = workBook.createSheet("用户结算列表"); 
        ExcelCreateUtil exportUtil = new ExcelCreateUtil(workBook, sheet);  
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();  
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();  
        // 构建表头  
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;  
        String[] titles={"真实姓名","用户名","手机号","账户余额","结算账号类型","结算账号","结算时间","备注"};
        for (int i = 0; i < titles.length; i++)  
        {  
            cell = headRow.createCell(i);  
            cell.setCellStyle(headStyle);  
            cell.setCellValue(titles[i]);  
        }  
     // 构建表体数据  
        if (userList != null && userList.size() > 0)  
        {  
            for (int j = 0; j < userList.size(); j++)  
            {  
                XSSFRow bodyRow = sheet.createRow(j + 1);  
                User u = userList.get(j);  
  
                cell = bodyRow.createCell(0);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(u.getFirstName()+u.getLastName());  
  
                cell = bodyRow.createCell(1);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(u.getNickName());  
                
                cell = bodyRow.createCell(2);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(u.getMobilePhone());  
  
                cell = bodyRow.createCell(3);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(u.getBalance());  
                
                cell = bodyRow.createCell(4);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(u.getBankType()); 
                
                cell = bodyRow.createCell(5);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(u.getBankAccount()); 
                
                cell = bodyRow.createCell(6);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(DateUtil.getCurrentDateStr()); 
                
                cell = bodyRow.createCell(7);  
                cell.setCellStyle(bodyStyle);  
                cell.setCellValue(""); 
            }  
        }  
        try  
        {  
            workBook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        } 
	}

	public static void createRefundExcel(HttpServletRequest request,
			String fileName, Complain c, Complain com) throws IOException {
		//将用户的列表信息保存为文件
        String savePath = request.getSession().getServletContext().getRealPath("/upload/settle/user/"+fileName);
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if (!file.exists()) {
			file.createNewFile();
		}
		//创建一个文件输出流
		FileOutputStream outputStream = new FileOutputStream(file);
        // 创建一个workbook 对应一个excel应用文件  
        XSSFWorkbook workBook = new XSSFWorkbook(); 
        // 在workbook中添加一个sheet,对应Excel文件中的sheet  
        XSSFSheet sheet = workBook.createSheet("用户退款记录"); 
        ExcelCreateUtil exportUtil = new ExcelCreateUtil(workBook, sheet);  
        XSSFCellStyle headStyle = exportUtil.getHeadStyle();  
        XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();  
        // 构建表头  
        XSSFRow headRow = sheet.createRow(0);
        XSSFCell cell = null;  
        String[] titles={"编号","用户名","手机号","订单号","订单类型","订单金额","退款类型","退款结果","退款金额","退款时间"};
        for (int i = 0; i < titles.length; i++)  
        {  
            cell = headRow.createCell(i);  
            cell.setCellStyle(headStyle);  
            cell.setCellValue(titles[i]);  
        }  
     // 构建表体数据  
        XSSFRow bodyRow = sheet.createRow(1);  
        
        cell = bodyRow.createCell(0);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getComId());  

        cell = bodyRow.createCell(1);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getUserName());  

        cell = bodyRow.createCell(2);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getUserPhone());  
        
        cell = bodyRow.createCell(3);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getMid()); 
        
        cell = bodyRow.createCell(4);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getType()==1?"餐厅订单":"超市订单");
        
        cell = bodyRow.createCell(5);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getDishsMoney()+com.getCarriageMoney());
        
        cell = bodyRow.createCell(6);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(com.getComType()==1?"订单投诉退款":(com.getComType()==2?"订单取消退款":"订单延时退款"));
        
        cell = bodyRow.createCell(7);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(c.getComResult()==1?"同意":"驳回");
        
        cell = bodyRow.createCell(8);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(c.getReturnMoney());
        
        cell = bodyRow.createCell(9);  
        cell.setCellStyle(bodyStyle);  
        cell.setCellValue(c.getUpdateDate());
        try  
        {  
            workBook.write(outputStream);  
            outputStream.flush();  
            outputStream.close();  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        finally  
        {  
            try  
            {  
                outputStream.close();  
            }  
            catch (IOException e)  
            {  
                e.printStackTrace();  
            }  
        } 
		
	}
}
