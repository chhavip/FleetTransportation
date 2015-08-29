package fleet.logisure.chhavi.tflapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by chhavi on 28/8/15.
 */
public class HomeFragment extends Fragment {
private Button activityButton;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.home_layout,null);
        if(!AppPreferences.isLoggedIn(getActivity()))
        ((MaterialNavigationDrawer)this.getActivity()).setFragment(new AttendanceFragment(), "Take Attendance");
       // else

        activityButton = (Button)v.findViewById(R.id.activity_button);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(),OptionsFragment.class);
                startActivity(i);
            }
        });
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
