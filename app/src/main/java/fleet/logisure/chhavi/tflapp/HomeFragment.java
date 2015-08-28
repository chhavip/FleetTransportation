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
public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.home_layout,null);
        if(!AppPreferences.isLoggedIn(getActivity()))
        ((MaterialNavigationDrawer)this.getActivity()).setFragment(new AttendanceFragment(), "Take Attendance");
       // else


        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
