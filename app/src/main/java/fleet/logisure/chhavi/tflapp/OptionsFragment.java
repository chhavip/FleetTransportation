package fleet.logisure.chhavi.tflapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gc.materialdesign.views.ButtonRectangle;

/**
 * Created by chhavi on 29/8/15.
 */
public class OptionsFragment extends Fragment {


private ButtonRectangle callButton;
    private ButtonRectangle pickupButton;
  //  @Override
  //  protected void onCreate(Bundle savedInstanceState) {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


   // super.onCreate(savedInstanceState);
       View v = inflater.inflate(R.layout.options_layout,null);// setContentView(R.layout.options_layout);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        callButton = (ButtonRectangle)v.findViewById(R.id.call_button);
        pickupButton = (ButtonRectangle)v.findViewById(R.id.pickup_button);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                phoneIntent.setData(Uri.parse("tel:91-987-337-1087"));
                startActivity(phoneIntent);
            }
        });

        pickupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickupFragment newFragment = new PickupFragment();
                Bundle args = new Bundle();
                //       args.putInt(ArticleFragment.ARG_POSITION, position);
                //   newFragment.setArguments(args);

                FragmentTransaction transaction = getFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.frame_container, newFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();
            }
        });
            return v;
    }
}
