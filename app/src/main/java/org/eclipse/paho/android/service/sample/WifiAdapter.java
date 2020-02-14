package org.eclipse.paho.android.service.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class WifiAdapter extends ArrayAdapter<WifiData> {
    Context context;
    int resource;
    List<WifiData> objects;
    LayoutInflater inflater;

    public WifiAdapter(Context context, int resource, List<WifiData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.objects = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // xml파일을 레이아웃 서비스를 가능하게 만들어주는 객체
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            convertView = inflater.inflate(resource, null); // resource가 레이아웃 서비스를 가능하게 만들어짐

            holder = new ViewHolder();

            // 한 항목을 나타내는 뷰의 각 컨트롤 객체를 참조
            holder.tvBSSID = (TextView) convertView.findViewById(R.id.tvBSSID);
            holder.tvSSID = (TextView)convertView.findViewById(R.id.tvSSID);

            convertView.setTag(holder);
            System.out.println("convertViews Null " + position);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
            System.out.println("convertViews Not Null " + position);
        }
        // 데이터를 읽어와서 각 뷰에 출력을 위한 셋팅
        WifiData wifiData = objects.get(position);

        holder.tvSSID.setText(wifiData.getSSID());
        holder.tvBSSID.setText(wifiData.getBSSID());

        return convertView;
    }

    class ViewHolder {
        TextView tvBSSID;
        TextView tvSSID;
    }
}