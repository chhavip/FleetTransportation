package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import Models.Client;
import fleet.logisure.chhavi.tflapp.R;

/**
 * Created by chhavi on 30/8/15.
 */
public class ClientListAdapter extends ArrayAdapter<Client>{
     Context context;
    LayoutInflater inflater;
    public ClientListAdapter(Context context, int resource, List<Client> objects) {
        super(context, resource, objects);
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    public  class ClientViewHolder {
        protected TextView ClientName;
        protected TextView type;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ClientViewHolder clientViewHolder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.client_list_item, null);
            clientViewHolder = new ClientViewHolder();
            clientViewHolder.ClientName = (TextView) convertView.findViewById(R.id.client_name);
            clientViewHolder.type = (TextView) convertView.findViewById(R.id.type_text);
            convertView.setTag(clientViewHolder);
        }else
            clientViewHolder = (ClientViewHolder) convertView.getTag();

        Client client = getItem(position);
        clientViewHolder.ClientName.setText(client.getClientName());
        if(client.getOrderSegment().equals("RC")){
            clientViewHolder.type.setText("Registered Client");
        }else{
            clientViewHolder.type.setText("Spot Booking");

        }

        return convertView;
       // return super.getView(position, convertView, parent);
    }
}
