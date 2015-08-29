package fleet.logisure.chhavi.tflapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by chhavi on 28/8/15.
 */
public class AttendanceFragment extends Fragment {
    private ImageButton takePhoto;
    private TextView attendanceText;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  return super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.attendance_fragment_layout,null);

        takePhoto = (ImageButton)v.findViewById(R.id.take_photo);
        attendanceText = (TextView)v.findViewById(R.id.attendance_text);
        if(!AppPreferences.isLoggedIn(getActivity())) {
            attendanceText.setText("Attendace - IN");
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppPreferences.setLoggedInAsTrue(getActivity());
                    attendanceText.setText("Attendace - OUT");
               //     ((MaterialNavigationDrawer) getActivity()).setFragment(new HomeFragment(), "Home");
                }
            });
        }else{

            attendanceText.setText("Attendace - OUT");
            takePhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppPreferences.setLoggedInAsFalse(getActivity());
                    attendanceText.setText("Attendace - IN");
              //      ((MaterialNavigationDrawer) getActivity()).setFragment(new HomeFragment(), "Home");
                }
            });

        }
        return  v;
    }
}
