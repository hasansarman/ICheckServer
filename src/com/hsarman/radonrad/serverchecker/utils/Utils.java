package com.hsarman.radonrad.serverchecker.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {


	public static void COPY_File (String source2,String dest2) throws IOException {
		File source=new File(source2);
		File dest=new File(dest2);
	    InputStream is = null;
	    OutputStream os = null;
	    try {
	        is = new FileInputStream(source);
	        os = new FileOutputStream(dest);
	        byte[] buffer = new byte[1024];
	        int length;
	        while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
	    } finally {
	        is.close();
	        os.close();
	    }
	}

	public static String BLOCKER() {
		// TODO Auto-generated method stub
		
		
		return "BLOCKED";
		
	}
	
	public static String units = "BKMGTPEZY";

    public static long human_readable_parse(String arg0) {
        int spaceNdx = arg0.indexOf(" ");

        double ret = Double.parseDouble(arg0.substring(0, spaceNdx));

        String unitString = arg0.substring(spaceNdx+1);


        int unitChar = unitString.charAt(0);

        int power = units.indexOf(unitChar);

        boolean isSi = unitString.indexOf('i')!=-1;

        int factor = 1024;
        if (isSi) 
        {
            factor = 1000;
        }
        return new Double(ret * Math.pow(factor, power)).longValue();
    }

}
