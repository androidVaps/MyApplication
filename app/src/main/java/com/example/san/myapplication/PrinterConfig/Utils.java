package com.example.san.myapplication.PrinterConfig;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;


public class Utils {

	/**
	 * 获取当前系统的语言环境
	 * 
	 * @param context
	 * @return boolean
	 */
	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		if (language.endsWith("zh"))
			return true;
		else
			return false;
	}

	/**
	 * 获取Assets子文件夹下的文件数据流数组InputStream[]
	 * 
	 * @param context
	 * @return InputStream[]
	 */
	@SuppressWarnings("unused")
	private static InputStream[] getAssetsImgaes(String imgPath, Context context) {
		String[] list = null;
		InputStream[] arryStream = null;
		try {
			list = context.getResources().getAssets().list(imgPath);
			arryStream = new InputStream[3];
			for (int i = 0; i < list.length; i++) {
				InputStream is = context.getResources().getAssets()
						.open(imgPath + File.separator + list[i]);
				arryStream[i] = is;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arryStream;
	}

	/*
	 * 未转换为十六进制字节的字符串
	 * 
	 * @param paramString
	 * 
	 * @return byte[]
	 */
	public static byte[] hexStr2Bytesnoenter(String paramString) {
		String[] paramStr = paramString.split(" ");
		byte[] arrayOfByte = new byte[paramStr.length];

		for (int j = 0; j < paramStr.length; j++) {
			arrayOfByte[j] = Integer.decode("0x" + paramStr[j]).byteValue();
		}
		return arrayOfByte;
	}

	/**
	 * 统计指定字符串中某个符号出现的次数
	 * 
	 * @param str
	 * @return int
	 */
	public static int Count(String strData, String str) {
		int iBmpNum = 0;
		for (int i = 0; i < strData.length(); i++) {
			String getS = strData.substring(i, i + 1);
			if (getS.equals(str)) {
				iBmpNum++;
			}
		}
		//System.out.println(str + "出现了:" + iBmpNum + "次");
		return iBmpNum;
	}
	
	/**
	 * 字符串转换为16进制
	 * 
	 * @param strPart
	 * @return
	 */
	public static String stringTo16Hex(String strPart) {
		if (strPart == "")
			return "";
		try {
			byte[] b = strPart.getBytes("gbk"); // 数组指定编码格式，解决中英文乱码
			String str = "";
			for (int i = 0; i < b.length; i++) {
				Integer I = new Integer(b[i]);
				String strTmp = I.toHexString(b[i]);
				if (strTmp.length() > 2)
					strTmp = strTmp.substring(strTmp.length() - 2) + " ";
				else
					strTmp = strTmp.substring(0, strTmp.length()) + " ";
				str = str + strTmp;
			}
			return str.toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * @Title:intToHexString
	 * @Description:10进制数字转成16进制
	 * @param a 转化数据
	 * @param len 占用字节数
	 * @return String
	 */
	public static String intToHexString(int a, int len) {
		len <<= 1;
		String hexString = Integer.toHexString(a);
		int b = len - hexString.length();
		if (b > 0) {
			for (int i = 0; i < b; i++) {
				hexString = "0" + hexString;
			}
		}
		return hexString;
	}
	// ------------------20161216 Add-----------------------
	/**
	 * 获取SD卡路径
	 * 
	 * @return String
	 */
	private static String getSDCardPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取SDCard图片路径
	 * @param fileName
	 * @return
	 */
	public static String getBitmapPath(String fileName) {
		String imgPath = getSDCardPath() + "DCIM" + File.separator + "BMP"
				+ File.separator + fileName;
		return imgPath;
	}

	/**
	 * BitmapOption 位图选项
	 * 
	 * @param inSampleSize
	 * @return
	 */
	private static Options getBitmapOption(int inSampleSize) {
		System.gc();
		Options options = new Options();
		options.inPurgeable = true;
		options.inSampleSize = inSampleSize;
		options.inPreferredConfig = Config.ARGB_4444; // T4 二维码图片效果最佳
		return options;
	}

	/**
	 * 获取Bitmap数据
	 * 
	 * @param imgPath
	 * @return
	 */
	public static Bitmap getBitmapData(String imgPath) {
		Bitmap bm = BitmapFactory.decodeFile(imgPath, getBitmapOption(1)); // 将图片的长和宽缩小味原来的1/2
		return bm;
	}
}
