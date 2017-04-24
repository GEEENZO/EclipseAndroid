package jp.venus.vtlwhtsy01801;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.content.SharedPreferences;
import android.util.Log;

public class ZipAllState  {
	
	SharedPreferences pref;

	public void zipArchiveFiles(String zipPath, String outputDir)
	{

	    try {
	        ZipFile zipFile = new ZipFile(zipPath);
	        Enumeration<? extends ZipEntry> enumeration = zipFile.entries();
	        while(enumeration.hasMoreElements())
	        {
	            ZipEntry zipEntry = enumeration.nextElement();
	            String outputPath = outputDir +"/"+ zipEntry.getName();
	            zipArchiveFile(zipPath, zipEntry.getName(), outputPath);
	        }
	        zipFile.close();
	        
	    }
	    catch(Exception ex)
	    {
	       
	        Log.d( "Err", "" + ex);
	    }
	}

	public void zipArchiveFile(String zipPath, String file, String outFile)
	{
	    try
	    {
	        ZipFile zipFile = new ZipFile(zipPath);
	        ZipEntry entry = zipFile.getEntry(file);
	        InputStream inputStream = zipFile.getInputStream(entry);
	        OutputStream outputStream = new FileOutputStream(outFile);
	  
	        byte[] buffer = new byte[ 1024 * 4 ];
	 
	        int r = 0;
	        while( -1 != ( r = inputStream.read(buffer)) )
	        {
	            outputStream.write( buffer, 0, r );
	        }
	        outputStream.close();
	        inputStream.close();
	        zipFile.close();
	    }
	    catch( Exception ex )
	    {
	        Log.d( "Err", "" + ex);
	    }
	}
	
	//
	public void zipSPunisher(File datafilePath, String dcf, String tpc){
		//Log.v("Launcher", "zipSPunisher");
		//String zipPath = getFilesDir().toString();
		//File dir = new File(Environment.getExternalStorageDirectory() + "/files/gm_main.jpg");//"/storage/sdcard0/files"
        if (!datafilePath.exists()) {
        	datafilePath.mkdir();
        }    
        String PLAINFILE = "secret/plain.dat";
    	String ENCRYPTFILE = "dip.zip";
    	String DECRYPTFILE = dcf;
    	String errcode = "p1el";
    	String THUMBALPHEBETFILE = tpc+errcode;
    	String PASW = "9ldiasdkKHediAd";
    	//dk3W01Kas9xIlvmD
		try {
			Key key = new SecretKeySpec(THUMBALPHEBETFILE.getBytes(), "AES");
			FileInputStream fis = new FileInputStream(datafilePath);
			byte[] iv = new byte[16];
			fis.read(iv);
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec ivspec = new IvParameterSpec(iv);
			cipher.init(Cipher.DECRYPT_MODE, key, ivspec);
			CipherInputStream cis = new CipherInputStream(fis, cipher);
			
			//FileInputStream fis2 = null;
			FileOutputStream fos = null;
			CipherOutputStream cos = null;
			
			//fis2 = new FileInputStream(ENCRYPTFILE);
			fos = new FileOutputStream(DECRYPTFILE);
			cos = new CipherOutputStream(fos, cipher);
 
			byte[] buf = new byte[1024*4];
			int i;
			while( (i = fis.read(buf)) != -1 ){
				cos.write(buf, 0, i);
			}
			cos.flush();
			cos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}