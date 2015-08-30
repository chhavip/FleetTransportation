package fleet.logisure.chhavi.tflapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import java.util.List;

import Adapters.ClientListAdapter;
import Models.Client;
import Models.ClientResponse;
import Utility.GsonRequest;
import it.neokree.materialnavigationdrawer.MaterialNavigationDrawer;

/**
 * Created by chhavi on 28/8/15.
 */
public class HomeFragment extends Fragment {
private Button activityButton;
    private ListView clientsList;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //    return super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.home_layout,null);
        ((MaterialNavigationDrawer)this.getActivity()).getToolbar().setTitle("Logisure");
        if(!AppPreferences.isLoggedIn(getActivity()))
        ((MaterialNavigationDrawer)this.getActivity()).setFragment(new AttendanceFragment(), "Take Attendance");
       // else

        clientsList = (ListView)v.findViewById(R.id.clients_list);
        context = (MaterialNavigationDrawer)this.getActivity();
        fetchClients();
      /*  activityButton = (Button)v.findViewById(R.id.activity_button);
        activityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsFragment newFragment = new OptionsFragment();
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
        });*/
        return v;
    }

    private void fetchClients(){


        String url = "http://tflapp_v10.logisureindia.com/getclients/clients?imei=353490069471015";
        RequestQueue queue = AppController.getInstance().getRequestQueue();


        GsonRequest<ClientResponse> myreq = new GsonRequest<ClientResponse>(Request.Method.GET,url,ClientResponse.class, createMyReqSuccessListener(),
                createMyReqErrorListener());

        queue.add(myreq);
    }

    private Response.Listener<ClientResponse> createMyReqSuccessListener() {
        return new Response.Listener<ClientResponse>() {
            @Override
            public void onResponse(ClientResponse response) {
                //    loadContent(response);
                List<Client> clients = response.getClients();
                Log.e("client", clients.get(0).getClientName());
                ClientListAdapter adapter = new ClientListAdapter(context,0,clients);
                clientsList.setAdapter(adapter);
                clientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                    }
                });

            }
        };
    }

    private Response.ErrorListener createMyReqErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    Log.e("error",error.getMessage());
            }
        };
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
