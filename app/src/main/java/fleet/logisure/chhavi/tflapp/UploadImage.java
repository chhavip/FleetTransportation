/*
package fleet.logisure.chhavi.tflapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

*/
/**
 * Created by pruthvi on 13/7/15.
 *//*

public class UploadImage extends AsyncTask<Void, Void, Void> {

    Context context;
    ProgressDialog pd;
    String imgUplaodUrl = "http://tflapp.logisureindia.com/api/ImageUpload";
//    String imgUplaodUrl = "http://standapi.delivision.in/api/ImageUpload";
    String responseFromServer;
    int responseCode;
    int noOfFiles;
    private int i;

    UploadImage(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage("Uploading images please wait.. ");
        pd.setProgress(0);
        pd.setMax(100);
        pd.setIndeterminate(false);
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        pd.dismiss();
    }

    private HttpClient createHttpClient() {
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

    @Override
    protected Void doInBackground(Void... params) {
        try {
            File toBeFolder = new File(Environment.getExternalStorageDirectory(), "Logisure/toBeUploaded");
            File uploaded = new File(Environment.getExternalStorageDirectory(), "Logisure/uploaded");

            if (toBeFolder.exists()) {
                File[] files = toBeFolder.listFiles();
                noOfFiles = files.length;

                Activity activity = (Activity) context;
                if(noOfFiles == 0){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, "There are no Images to upload ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                i = 0;
                if (files != null) {
                    for (File child : files) {
                        try {
                            HttpClient httpclient = createHttpClient();
                            HttpPost httppost = new HttpPost(imgUplaodUrl);
//                            httppost.addHeader("Authorization", "Basic bG9naXN1cmU6c3RhbmRhcGk=");
                            MultipartEntity entity = new MultipartEntity(
                                    HttpMultipartMode.BROWSER_COMPATIBLE);

                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                            Bitmap bitmap = BitmapFactory.decodeFile(child.getAbsolutePath(), options);

                            byte[] arr = changeImageSize(bitmap);
                            Log.d("PhotoName", child.getName());
                            entity.addPart("files", new ByteArrayBody(arr, child.getName()));
                            httppost.setEntity(entity);
                            final HttpResponse response = httpclient.execute(httppost);
                            responseFromServer = EntityUtils.toString(response.getEntity());
                            responseCode = response.getStatusLine().getStatusCode();
                            Log.d("Response Line", response.getStatusLine().getReasonPhrase());
                            Log.d("TEST", "doLogin Response:" + responseFromServer);

                            if (!(responseCode == HttpStatus.SC_OK)) {
                                activity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, response.getStatusLine().getReasonPhrase(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                                break;
                            } else {
                                moveFile(toBeFolder.getAbsolutePath(), uploaded.getAbsolutePath(), child.getName());
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.setProgress((i * 100) / noOfFiles);
                            }
                        });
                        i = i + 1;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] changeImageSize(Bitmap thumbnail) {
        // thumbnail = Bitmap.createScaledBitmap(thumbnail, 400, 600, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] ba = stream.toByteArray();
        Log.e("Length of Byte Array:-", "" + (int) ba.length);
        Log.e("image==", Base64.encodeToString(ba, 0));
        return ba;
    }

    private void moveFile(String inputPath, String outputPath , String inputFile) {

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
