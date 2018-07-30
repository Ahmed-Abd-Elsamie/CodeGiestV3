package com.example.root.codegiest.user;

/**
 * Created by root on 26/07/18.
 */


import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.root.codegiest.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends ArrayAdapter<User> {

    private Activity context;
    private List<User> friends;


    public UsersAdapter(Activity context,List<User>friends) {
        super(context, R.layout.main_screen_adapter,friends);
        this.context = context;
        this.friends = friends;
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.main_screen_adapter,parent,false);
        TextView name = (TextView) view.findViewById(R.id.name_textView);
        CircleImageView img = (CircleImageView)view.findViewById(R.id.imageView);

        User itemUser = getItem(position);
        name.setText(itemUser.getName());

        Picasso.with(context).load(itemUser.getImg()).into(img);

        return view;
    }


}
