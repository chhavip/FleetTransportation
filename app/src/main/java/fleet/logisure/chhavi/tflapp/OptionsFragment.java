package fleet.logisure.chhavi.tflapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by chhavi on 29/8/15.
 */
public class OptionsFragment extends AppCompatActivity {


private ButtonRectangle callButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);

        callButton = (ButtonRectangle)findViewById(R.id.call_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:91-987-337-1087"));
                startActivity(phoneIntent);
            }
        });

    }
}
