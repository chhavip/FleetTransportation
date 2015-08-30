package fleet.logisure.chhavi.tflapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by chhavi on 28/8/15.
 */
public class DieselSummaryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // getActivity().getActionBar().setTitle("Diesel");
        ((MaterialNavigationDrawer)this.getActivity()).getToolbar().setTitle("Diesel Summary");
        return super.onCreateView(inflater, container, savedInstanceState);

    }
}
