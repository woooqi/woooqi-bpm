package com.titan.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

public class FileUtils {
	
	
	public static byte[] File2byte(File file) {
		byte[] buffer = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] b = new byte[1024];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}

	public static void byte2File(byte[] buf, String filePath, String fileName) {
		BufferedOutputStream bos = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			File dir = new File(filePath);
			if (!dir.exists() && dir.isDirectory()) {
				dir.mkdirs();
			}
			file = new File(filePath + File.separator + fileName);
			fos = new FileOutputStream(file);
			bos = new BufferedOutputStream(fos);
			bos.write(buf);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static InputStream String2InputStream(String str) {
		ByteArrayInputStream stream = new ByteArrayInputStream(str.getBytes());
		return stream;
	}

	public static String inputStream2String(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}
	
	@SuppressWarnings("resource")
	public static String File2String(File file) {
		InputStream inputStream = null;
		StringBuffer buffer = new StringBuffer();
		try {
			inputStream = new FileInputStream(file);
			BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
			String line = "";
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}

	public static void string2File(String str, File file) {
		ByteArrayInputStream ins = new ByteArrayInputStream(str.getBytes());
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	

	
	public static void inputstream2File(InputStream ins, File file) {
		OutputStream os = null;
		try {
			os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
	
	public static InputStream file2InputStream(File file){
		String str = File2String(file);
		InputStream inputStream = String2InputStream(str);
		return inputStream;
	}
	
	public static byte[] inputStream2Byte(InputStream inputStream){
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			int len = 0;
			byte[] buffer = new byte[8192];
			while((len = inputStream.read(buffer,0,8192))!=-1){
				os.write(buffer,0,len);
			}
			bytes = os.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				inputStream.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bytes;
	}
	
	public static InputStream byte2InputStream(byte[] bytes){
		InputStream ins = new ByteArrayInputStream(bytes);
		return ins;
	}

	public static Document readFileAsDocument(File file) {
		SAXReader reader = new SAXReader();
		Document doc = null;
		try {
			doc = reader.read(readFileAsStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}
	
	public static InputStream readFileAsStream(File file) {
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (Exception e) {
			IOUtils.closeQuietly(in);
			e.printStackTrace();
		}
		return in;
	}

}
