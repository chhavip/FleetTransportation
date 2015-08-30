/*

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TakePhoto extends AppCompatActivity {

    private final int CAMERA_PIC_REQUEST = 1;
    Context mContext = TakePhoto.this;
    ImageButton takeImage;
    Location gpsLocation;
    Double lat, lng;
    float acc;
    OrderItem orderItem;
    ProgressDialog pd;
    GetLocation loc;
    String uinameforpickup;
    String responseLine;
    private Bitmap thumbnail;
    private LocationManager locationManager;
    double latitude,longitude,accuracy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);


        TextView header = (TextView) findViewById(R.id.headerTextView);
        HelperClass.setTypeface(mContext, header);

        takeImage = (ImageButton) findViewById(R.id.goodsImage);

        loc = new GetLocation(this);

        gpsLocation = loc.getLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();
            accuracy = gpsLocation.getAccuracy();

        } else if (gpsLocation == null) {
            Toast.makeText(this, "Please Check GPS", Toast.LENGTH_LONG)
                    .show();
        }

        orderItem = (OrderItem) getIntent().getSerializableExtra("orderItem");

        takeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                File folder = new File(Environment.getExternalStorageDirectory(), "Logisure/toBeUploaded");
                folder.mkdirs();

                uinameforpickup = generateUniquePickupImageName();

                File image = new File(folder, uinameforpickup);
                Uri uriSavedImage = Uri.fromFile(image);

                i.putExtra(MediaStore.EXTRA_OUTPUT, uriSavedImage);

                startActivityForResult(i, CAMERA_PIC_REQUEST);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC_REQUEST:
                if (resultCode == RESULT_OK) {

                    orderItem.photoName = uinameforpickup;

                    if (HelperClass.isInternetConnected(mContext)) {
                        new uploadImageOnServerWithName( uinameforpickup)
                                .execute();
                    } else {
                        Toast.makeText(mContext, "Internet not connected", Toast.LENGTH_SHORT).show();
                    }
                }
        }
    }

    @SuppressLint("SimpleDateFormat")
    public String generateUniquePickupImageName() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("ddMMyyyy");
        String format = s.format(new Date());
        String str = String.valueOf("PDImage_" + format + "_"
                + c.get(Calendar.HOUR) + "_" + c.get(Calendar.MINUTE) + "_"
                + c.get(Calendar.SECOND) + "_" + c.get(Calendar.MINUTE) + "_"
                + c.get(Calendar.MILLISECOND));
        return str + ".jpg";
    }

    public byte[] changeImageSize(Bitmap thumbnail) {
        thumbnail = Bitmap.createScaledBitmap(thumbnail, 400, 600, false);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] ba = stream.toByteArray();
        return ba;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_take_photo, menu);
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

    @Override
    public void onBackPressed() {

    }

    class uploadImageOnServerWithName extends AsyncTask<Void, Void, Void> {
        byte[] a;
        String uname, res;
        File toBeFolder, uploaded ;

        @SuppressWarnings("static-access")
        uploadImageOnServerWithName( String uname) {

            this.uname = uname;
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Uploading goods Image");
            pd.setIndeterminate(true);
            pd.setIndeterminateDrawable(getResources().getDrawable(R.drawable.animation));
            pd.setCancelable(false);
            pd.show();


        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            String responseFromServer = null;
            int status;
            try {

                toBeFolder = new File(Environment.getExternalStorageDirectory(), "Logisure/toBeUploaded");
                uploaded = new File(Environment.getExternalStorageDirectory(), "Logisure/uploaded");

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(toBeFolder.getAbsolutePath()+"/"+ uinameforpickup , options);

                a = changeImageSize(bitmap);
                HttpClient httpclient = HelperClass.createHttpClient();
                HttpPost httppost = new HttpPost(getResources().getString(R.string.image_upload_url));
                MultipartEntity entity = new MultipartEntity(
                        HttpMultipartMode.BROWSER_COMPATIBLE);
                entity.addPart("files", new ByteArrayBody(a, uname));
                httppost.setEntity(entity);
                HttpResponse response = httpclient.execute(httppost);
                responseFromServer = EntityUtils.toString(response.getEntity());
                responseLine = response.getStatusLine().getReasonPhrase();
                status = response.getStatusLine().getStatusCode();
                Log.d("TEST", "doLogin Response:" + responseFromServer);

                if (status == HttpStatus.SC_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Uploaded Image", Toast.LENGTH_SHORT).show();
                        }
                    });

                    HelperClass.moveFile(toBeFolder.getAbsolutePath(), uploaded.getAbsolutePath(), uinameforpickup);
                    res = responseFromServer;
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Not uploaded image", Toast.LENGTH_SHORT).show();
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
            pd.dismiss();
            if(orderItem.temporaryStatus.equals("RAtP") && orderItem.Cashcollectedfrom.equals("pickup")){
                startActivity(new Intent(mContext , CashCollection.class).putExtra("orderItem", orderItem));

//            }else if(orderItem.temporaryStatus.equals("RAtD") && orderItem.Cashcollectedfrom.equals("delivery")){
//                startActivity(new Intent(mContext , CashCollection.class).putExtra("orderItem", orderItem));

            }else {
                new uploadDataOnServer().execute();
            }
        }
    }

    class uploadDataOnServer extends AsyncTask<Void, Void, Void> {
        String res;

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(mContext);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setMessage("Uploading current data");
            pd.setIndeterminate(true);
            pd.setIndeterminateDrawable(getResources().getDrawable(R.drawable.animation));
            pd.setCancelable(false);
            pd.show();

            gpsLocation = loc.getLocation(LocationManager.GPS_PROVIDER);
            if (gpsLocation != null) {
                latitude = gpsLocation.getLatitude();
                longitude = gpsLocation.getLongitude();
                accuracy = gpsLocation.getAccuracy();

            } else {
                Toast.makeText(mContext , "Please Check GPS", Toast.LENGTH_LONG)
                        .show();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            // TODO Auto-generated method stub
            String responseFromServer;
            int status;
            try {
                HttpClient httpclient = HelperClass.createHttpClient();

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("OrderId", "" + orderItem.orderId));
                nameValuePairs.add(new BasicNameValuePair("Quantity", "" + orderItem.temporaryQty));
                nameValuePairs.add(new BasicNameValuePair("SignatureUrl", orderItem.signatureName));
                nameValuePairs.add(new BasicNameValuePair("PhotoUrl", orderItem.photoName));
                nameValuePairs.add(new BasicNameValuePair("mobileLat", "" + latitude));
                nameValuePairs.add(new BasicNameValuePair("mobileLng", "" + longitude));
                nameValuePairs.add(new BasicNameValuePair("amount", "" + orderItem.temporaryAmount));

                Log.d("Pairs", "" + nameValuePairs);
                HttpPost httppost = null;
                if (orderItem.temporaryStatus.equals("RAtP")) {
                    httppost = new HttpPost(getResources().getString(R.string.pickup_url));
                } else if (orderItem.temporaryStatus.equals("RAtD")) {
                    httppost = new HttpPost(getResources().getString(R.string.delivery_url));
                }
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                httppost.addHeader("Authorization", "Basic bG9naXN1cmU6aG9tZWFwaQ==");
                HttpResponse response = httpclient.execute(httppost);
                responseFromServer = EntityUtils.toString(response.getEntity());
                status = response.getStatusLine().getStatusCode();
                Log.d("ResponseLine", response.getStatusLine().getReasonPhrase());
                Log.d("TEST", "doLogin Response: Here" + responseFromServer);


                if (status == HttpStatus.SC_OK) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext, "Uploaded Data", Toast.LENGTH_SHORT).show();
                        }
                    });
                    res = responseFromServer;
                } else {
                    res = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pd.dismiss();

            startActivity(new Intent(mContext, OrderList.class).putExtra("vehicle", orderItem.vehicle));

        }
    }

}
*/
