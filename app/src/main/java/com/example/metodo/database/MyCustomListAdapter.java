package com.example.metodo.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.metodo.Utility.Personaldetails;
import com.example.metodo.databinding.CustomListAdapterBinding;

import java.util.ArrayList;
import java.util.List;

public class MyCustomListAdapter extends BaseAdapter {
    List<Personaldetails> personaldetails = new  ArrayList<>();
    Context context;
    public MyCustomListAdapter(List<Personaldetails> personaldetails,Context context) {
    this.context=context;
    this.personaldetails=personaldetails;
    }
    @Override
    public int getCount() {
        return personaldetails.size();
    }

    @Override
    public Object getItem(int position) {
        return personaldetails.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        CustomListAdapterBinding binding =CustomListAdapterBinding.inflate(LayoutInflater.from(context),parent,false);
        view =binding.getRoot();
        binding.title.setText(personaldetails.get(position).getTitle());
        binding.content.setText(personaldetails.get(position).getContent());
//        binding.date.setText(personaldetails.get(position).getDate());
        return view ;
    }
}
