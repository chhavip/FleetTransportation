/*
package fleet.logisure.chhavi.tflapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

*/
/**
 * Created by pruthvi on 29/6/15.
 *//*

public class    HelperClass {


    public static Typeface typeface;

    public static HttpClient createHttpClient() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setContentCharset(params,
                HTTP.DEFAULT_CONTENT_CHARSET);
        HttpProtocolParams.setUseExpectContinue(params, true);
        SchemeRegistry schReg = new SchemeRegistry();
        schReg.register(new Scheme("http", PlainSocketFactory
                .getSocketFactory(), 80));
        schReg.register(new Scheme("https",
                SSLSocketFactory.getSocketFactory(), 443));
        ClientConnectionManager conMgr = new ThreadSafeClientConnManager(
                params, schReg);
        return new DefaultHttpClient(conMgr, params);
    }


    public static void setTypeface(Context context, TextView tv) {

        typeface = Typeface.createFromAsset(context.getAssets(), "PLYMOUTH.TTF");
        tv.setTypeface(typeface);
    }


    public static byte[] changeImageSize(Bitmap thumbnail) {
        // thumbnail = Bitmap.createScaledBitmap(thumbnail, 400, 600, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] ba = stream.toByteArray();
        Log.v("Length of Byte Array:-", "" + (int) ba.length);
        Log.v("image in base64 ==", Base64.encodeToString(ba, 0));
        return ba;
    }

    public static boolean isInternetConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        if (info.getState() != NetworkInfo.State.CONNECTED) {
            return false;
        }
        return true;
    }

    public static void moveFile(String inputPath, String outputPath , String inputFile) {

        InputStream in = null;
        OutputStream out = null;
        try {

            //create output directory if it doesn't exist
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }


            in = new FileInputStream(inputPath + "/" + inputFile);
            out = new FileOutputStream(outputPath +"/" + inputFile);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file
            out.flush();
            out.close();
            out = null;

            // delete the original file
            new File(inputPath +"/"+ inputFile).delete();


        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

    }
}
*/
