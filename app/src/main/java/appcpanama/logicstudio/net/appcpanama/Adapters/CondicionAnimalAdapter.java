package appcpanama.logicstudio.net.appcpanama.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import appcpanama.logicstudio.net.appcpanama.JavaBeans.CondicionAnimalBeans;
import appcpanama.logicstudio.net.appcpanama.JavaBeans.infoAnimalBeans;
import appcpanama.logicstudio.net.appcpanama.R;

public class CondicionAnimalAdapter extends RecyclerView.Adapter<CondicionAnimalAdapter.ViewHolder> {

    private List<CondicionAnimalBeans> itemsCondicion;
    private static ClickListener clickListener;


    public CondicionAnimalAdapter(List<CondicionAnimalBeans> itemsCondicion){
        this.itemsCondicion = itemsCondicion;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title;
        //ImageView icono;

        public ViewHolder(View v) {
            super(v);
            //icono = (ImageView) v.findViewById(R.id.item_infoanimal_img);
            title = (TextView)v.findViewById(R.id.item_condicionanimal_name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }


    @Override
    public int getItemCount() {
        if (itemsCondicion != null) {
            return itemsCondicion.size();
        } else
            return 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_condicion_animal, viewGroup, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        if (itemsCondicion != null) {

            viewHolder.title.setText(itemsCondicion.get(i).getNombre());
           // viewHolder.itemsCondicion.setImageResource(itemsCondicion.get(i).getImg());

        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        CondicionAnimalAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }



}


