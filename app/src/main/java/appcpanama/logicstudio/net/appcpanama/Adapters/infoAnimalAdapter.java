package appcpanama.logicstudio.net.appcpanama.Adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import java.util.List;

import appcpanama.logicstudio.net.appcpanama.JavaBeans.infoAnimalBeans;
import appcpanama.logicstudio.net.appcpanama.R;

public class infoAnimalAdapter extends RecyclerView.Adapter<infoAnimalAdapter.ViewHolder> {

    private List<infoAnimalBeans> items;

    public infoAnimalAdapter(List<infoAnimalBeans> items) {
        this.items = items;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView icono;

        public ViewHolder(View v) {
            super(v);

            icono = (ImageView) v.findViewById(R.id.item_infoanimal_img);
            title = (TextView)v.findViewById(R.id.item_infoanimal_name);
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

        }
    }
}


