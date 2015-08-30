package fleet.logisure.chhavi.tflapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by chhavi on 29/8/15.
 */
public class PickupFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.pickup_layout,null);
        return v;
    }
}
