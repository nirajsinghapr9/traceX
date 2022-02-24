package com.example.tracexassesment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<Data> list = new ArrayList<>();
    private Context context;
    private MainAdapter.ClickListener clickListener;

    public MainAdapter(Context context) {
        this.context = context;
    }

    public void setOnClickListener(MainAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setList(List<Data> data) {
        this.list = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MainAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recylerview, parent, false);
        return new MainAdapter.MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.MyViewHolder holder, int position) {
        Data data = list.get(position);
        holder.icon.setText(list.get(position).getName().substring(0, 1));
        Random mRandom = new Random();
        int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.icon.getBackground()).setColor(color);
        holder.card.setOnClickListener(view -> clickListener.onClickViewOrder(position, data));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.icon)
        TextView icon;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.name)
        TextView name;
        @SuppressLint("NonConstantResourceId")
        @BindView(R.id.card)
        CardView card;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface ClickListener {
        void onClickViewOrder(int position, Data data);

    }
}
