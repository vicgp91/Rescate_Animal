package appcpanama.logicstudio.net.appcpanama.Adapters;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import appcpanama.logicstudio.net.appcpanama.AnimalDetails;
import appcpanama.logicstudio.net.appcpanama.InfoAnimal;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.infoAnimalBeans;
import appcpanama.logicstudio.net.appcpanama.R;
import appcpanama.logicstudio.net.appcpanama.ReportarActivity;

public class ListAnimalAdapter extends RecyclerView.Adapter<ListAnimalAdapter.ViewHolder> {

    private List<infoAnimalBeans> items;
    private static ClickListener clickListener;


    public ListAnimalAdapter(List<infoAnimalBeans> items){
        this.items = items;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        ImageView icono;

        public ViewHolder(View v) {
            super(v);
            icono = (ImageView) v.findViewById(R.id.item_infoanimal_img);
            title = (TextView)v.findViewById(R.id.item_infoanimal_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }


    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        } else
            return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_infoanimal, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (items != null) {

            viewHolder.title.setText(items.get(i).getNombre());
            viewHolder.icono.setImageResource(items.get(i).getImg());

        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        ListAnimalAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }



}


