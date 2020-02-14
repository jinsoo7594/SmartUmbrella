package org.eclipse.paho.android.service.sample;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by song on 2017. 8. 2..
 */

public class reviewsadapter extends ArrayAdapter<reviews> {

    private ArrayList<reviews> reviewList;
    private LayoutInflater vi;
    private int Resource;
    private ViewHolder holder;
    // Button soww;
    View view;
    //ArrayList<Reviews> cp=0;
    int gen=0;
    reviewsadapter(Context context, int resource, ArrayList<reviews> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        this.reviewList = objects;

    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        View v = convertView;

        // if view is null then set below views


        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);

            //binding holder variables to xml views

            //holder.tvtitle = (TextView) v.findViewById(R.id.title);
            holder.tvcomment = (TextView) v.findViewById(R.id.comment);


            holder.rbrating1 = (TextView) v.findViewById(R.id.stars1);
            holder.rbrating2 = (TextView) v.findViewById(R.id.stars2);
            holder.rbrating3 = (TextView) v.findViewById(R.id.stars3);

            holder.skyname1 = (TextView) v.findViewById(R.id.sky1);
            holder.skyname2 = (TextView) v.findViewById(R.id.sky2);
            holder.skyname3 = (TextView) v.findViewById(R.id.sky3);

            holder.tempname1 = (TextView) v.findViewById(R.id.temp1);
            holder.tempname2 = (TextView) v.findViewById(R.id.temp2);
            holder.tempname3 = (TextView) v.findViewById(R.id.temp3);


            holder.sun1 = (ImageView) v.findViewById(R.id.weatherIconImageView1) ;
            holder.sun2 = (ImageView) v.findViewById(R.id.weatherIconImageView2) ;
            holder.sun3 = (ImageView) v.findViewById(R.id.weatherIconImageView3) ;


            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        //setting data to the holder vars from "Reviews" getters

        //holder.tvtitle.setText(reviewList.get(position).getTitle());
        holder.tvcomment.setText("현재위치 : "+reviewList.get(position).getComment());


        holder.rbrating1.setText("NOW");
        //reviewList.get(position).getStars1()
        holder.rbrating2.setText("1HOUR LATER");
        holder.rbrating3.setText("2HOURS LATER");
        holder.skyname1.setText(reviewList.get(position).getSky1());
        holder.skyname2.setText(reviewList.get(position).getSky2());
        holder.skyname3.setText(reviewList.get(position).getSky3());
        holder.tempname1.setText(reviewList.get(position).getTemp1()+"℃");
        holder.tempname2.setText(reviewList.get(position).getTemp2()+"℃");
        holder.tempname3.setText(reviewList.get(position).getTemp3()+"℃");



        String s1 = reviewList.get(position).getStars1();
        String s2 = reviewList.get(position).getStars2();
        String s3 = reviewList.get(position).getStars3();

        switch (change(s1)) {
            case "1":
                holder.sun1.setImageResource(R.drawable.icon_1);
                break;
            case "2":
                holder.sun1.setImageResource(R.drawable.icon_2);
                break;
            case "3":
                holder.sun1.setImageResource(R.drawable.icon_3);
                break;
            case "4":
                holder.sun1.setImageResource(R.drawable.icon_4);
                break;
            case "5":
                holder.sun1.setImageResource(R.drawable.icon_5);
                break;
            case "6":
                holder.sun1.setImageResource(R.drawable.icon_6);
                break;
            case "7":
                holder.sun1.setImageResource(R.drawable.icon_7);
                break;
            case "8":
                holder.sun1.setImageResource(R.drawable.icon_8);
                break;
            case "9":
                holder.sun1.setImageResource(R.drawable.icon_9);
                break;
            case "10":
                holder.sun1.setImageResource(R.drawable.icon_10);
                break;
            case "11":
                holder.sun1.setImageResource(R.drawable.icon_11);
                break;
            case "12":
                holder.sun1.setImageResource(R.drawable.icon_12);
                break;
            case "13":
                holder.sun1.setImageResource(R.drawable.icon_13);
                break;
            case "14":
                holder.sun1.setImageResource(R.drawable.icon_14);
                break;
            case "na":
                holder.sun1.setImageResource(R.drawable.icon_na);
                break;

        }

        switch (change(s2)) {
            case "1":
                holder.sun2.setImageResource(R.drawable.icon_1);
                break;
            case "2":
                holder.sun2.setImageResource(R.drawable.icon_2);
                break;
            case "3":
                holder.sun2.setImageResource(R.drawable.icon_3);
                break;
            case "4":
                holder.sun2.setImageResource(R.drawable.icon_4);
                break;
            case "5":
                holder.sun2.setImageResource(R.drawable.icon_5);
                break;
            case "6":
                holder.sun2.setImageResource(R.drawable.icon_6);
                break;
            case "7":
                holder.sun2.setImageResource(R.drawable.icon_7);
                break;
            case "8":
                holder.sun2.setImageResource(R.drawable.icon_8);
                break;
            case "9":
                holder.sun2.setImageResource(R.drawable.icon_9);
                break;
            case "10":
                holder.sun2.setImageResource(R.drawable.icon_10);
                break;
            case "11":
                holder.sun2.setImageResource(R.drawable.icon_11);
                break;
            case "12":
                holder.sun2.setImageResource(R.drawable.icon_12);
                break;
            case "13":
                holder.sun2.setImageResource(R.drawable.icon_13);
                break;
            case "14":
                holder.sun2.setImageResource(R.drawable.icon_14);
                break;
            case "na":
                holder.sun2.setImageResource(R.drawable.icon_na);
                break;

        }
        switch (change(s3)) {
            case "1":
                holder.sun3.setImageResource(R.drawable.icon_1);
                break;
            case "2":
                holder.sun3.setImageResource(R.drawable.icon_2);
                break;
            case "3":
                holder.sun3.setImageResource(R.drawable.icon_3);
                break;
            case "4":
                holder.sun3.setImageResource(R.drawable.icon_4);
                break;
            case "5":
                holder.sun3.setImageResource(R.drawable.icon_5);
                break;
            case "6":
                holder.sun3.setImageResource(R.drawable.icon_6);
                break;
            case "7":
                holder.sun3.setImageResource(R.drawable.icon_7);
                break;
            case "8":
                holder.sun3.setImageResource(R.drawable.icon_8);
                break;
            case "9":
                holder.sun3.setImageResource(R.drawable.icon_9);
                break;
            case "10":
                holder.sun3.setImageResource(R.drawable.icon_10);
                break;
            case "11":
                holder.sun3.setImageResource(R.drawable.icon_11);
                break;
            case "12":
                holder.sun3.setImageResource(R.drawable.icon_12);
                break;
            case "13":
                holder.sun3.setImageResource(R.drawable.icon_13);
                break;
            case "14":
                holder.sun3.setImageResource(R.drawable.icon_14);
                break;
            case "na":
                holder.sun3.setImageResource(R.drawable.icon_na);
                break;

        }




        //holder.rbrating.setRating(Integer.parseInt(reviewList.get(position).getStars()));
        return v;

    }




    private static class ViewHolder {

        TextView tvtitle;
        TextView tvcomment;
        TextView rbrating1;
        TextView rbrating2;
        TextView rbrating3;
        TextView skyname1;
        TextView skyname2;
        TextView skyname3;
        TextView tempname1;
        TextView tempname2;
        TextView tempname3;

        ImageView sun1;
        ImageView sun2;
        ImageView sun3;

    }

    public String change(String code){
        switch (code){
            case "SKY_V01" : return "1";
            case "SKY_V02" : return "2";
            case "SKY_V03" : return "3";
            case "SKY_V04" : return "4";
            case "SKY_V05" : return "5";
            case "SKY_V06" : return "6";
            case "SKY_V07" : return "7";
            case "SKY_V08" : return "8";
            case "SKY_V09" : return "9";
            case "SKY_V10" : return "10";
            case "SKY_V11" : return "11";
            case "SKY_V12" : return "12";
            case "SKY_V13" : return "13";
            case "SKY_V14" : return "14";
            default: return "na";


        }

    }

}
