package com.androidhive.imagefromurl;

import java.io.InputStream;
import java.io.OutputStream;
 
/**
 * Utility classes to automatically load images from a URL.
 * Source: http://www.androidhive.info/2012/07/android-loading-image-from-url-http/
 */
public class Utils {
    public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}