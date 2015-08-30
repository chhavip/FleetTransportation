/*
package fleet.logisure.chhavi.tflapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Signature extends AppCompatActivity {

    Context mContext = Signature.this;
    Button clear, submit;
    OrderItem orderItem;
    GestureOverlayView gestureOverlayView;
    TextView message;
    ProgressDialog progressDialog;
    String res;
    String signatureuname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signature);

        TextView header = (TextView) findViewById(R.id.headerTextView);
        HelperClass.setTypeface(mContext, header);

        clear = (Button) findViewById(R.id.clear);
        submit = (Button) findViewById(R.id.submit);
        gestureOverlayView = (GestureOverlayView) findViewById(R.id.gestureOverlayView1);
        message = (TextView) findViewById(R.id.text);

        orderItem = (OrderItem) getIntent().getSerializableExtra("orderItem");

        if (orderItem.temporaryStatus.equals("RAtP")) {
            message.setText("I confirm that the driver has picked up " + orderItem.temporaryQty + " items");
        } else {
            message.setText("I confirm that the driver has delivered " + orderItem.temporaryQty + " items");
        }

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gestureOverlayView.cancelClearAnimation();
                gestureOverlayView.clear(true);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                gestureOverlayView.setDrawingCacheEnabled(true);
                if (gestureOverlayView.getGesture() != null) {
                    Bitmap bmap = Bitmap.createBitmap(gestureOverlayView.getDrawingCache());
                    byte[] barr = changeImageSize(bmap);
                    signatureuname = generateUniqueImageName();
                    orderItem.signatureName = signatureuname;

                    File folder = new File(Environment.getExternalStorageDirectory(), "Logisure/toBeUploaded");
                    folder.mkdirs();

                    saveBitmap(bmap, folder.getAbsolutePath(), signatureuname);

                    if (HelperClass.isInternetConnected(mContext)) {
                        new uploadImageOnServerWithName(barr, signatureuname)
                                .execute();
                    } else {
                        Toast.makeText(mContext, "Internet is not connected", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(mContext, TakePhoto.class).putExtra("orderItem", orderItem));
                    }
                } else {
                    Toast.makeText(mContext, "Please enter a signature", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void saveBitmap(Bitmap bmp, String storepath, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(storepath + "/" + fileName);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public byte[] changeImageSize(Bitmap thumbnail) {
        thumbnail = Bitmap.createScaledBitmap(thumbnail, 400, 600, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] ba = stream.toByteArray();
        return ba;
    }

    public String generateUniqueImageName() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy");
        String format = s.format(new Date());
        String nameType;
        switch (orderItem.temporaryStatus) {
            case "RAtP":
                nameType = "PSignature_";
                break;
            default:
                nameType = "DSignature_";
        }
        String str = String.valueOf(nameType + format + "_"
                + c.get(Calendar.HOUR) + "_" + c.get(Calendar.MINUTE) + "_"
                + c.get(Calendar.SECOND) + "_" + c.get(Calendar.MINUTE) + "_"
                + c.get(Calendar.MILLISECOND));
        Log.e("String=", "" + str);
        return str + ".png";

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_signature, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class uploadImageOnServerWithName extends AsyncTask<Void, Void, Void> {
        final byte[] a;
        final String signatureuname;
        String res = null;
        String responseLine;

        uploadImageOnServerWithName(byte[] a, String uname) {
            this.a = a;
            this.signatureuname = uname;

        }

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Signature.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("Please Wait..");
            progressDialog.setIndeterminate(true);
            progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.animation));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            String responseFromServer = null;
            int statusCode;
            try {
                HttpClient httpclient = HelperClass.createHttpClient();
                HttpPost httpPost = new HttpPost(getResources().getString(R.string.image_upload_url));
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                entity.addPart("files", new ByteArrayBody(a, signatureuname));
                httpPost.setEntity(entity);
                HttpResponse response = httpclient.execute(httpPost);
                responseFromServer = EntityUtils.toString(response.getEntity());
                statusCode = response.getStatusLine().getStatusCode();
                responseLine = response.getStatusLine().getReasonPhrase();
                Log.d("TEST", "Upload Response:" + responseFromServer);

                if (statusCode == HttpStatus.SC_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Uploaded Signature ", Toast.LENGTH_SHORT).show();
                        }
                    });
                    File toBeFolder = new File(Environment.getExternalStorageDirectory(), "Logisure/toBeUploaded");
                    File uploaded = new File(Environment.getExternalStorageDirectory(), "Logisure/uploaded");
                    HelperClass.moveFile(toBeFolder.getAbsolutePath(), uploaded.getAbsolutePath(), signatureuname);

                    res = responseFromServer;
                } else {
                    Log.e("Upload error", responseLine) ;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Not uploaded image ", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            startActivity(new Intent(mContext, TakePhoto.class).putExtra("orderItem", orderItem));

        }
    }
}
*/
