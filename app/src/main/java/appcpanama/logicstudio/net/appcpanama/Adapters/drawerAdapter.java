package appcpanama.logicstudio.net.appcpanama.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import appcpanama.logicstudio.net.appcpanama.HomeScreen;
import appcpanama.logicstudio.net.appcpanama.R;


/**
 * Created by LogicStudio on 21/10/16.
 */

public class drawerAdapter extends RecyclerView.Adapter<drawerAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[];
    private String mNavSubTitles[];
    private int mIcons[];

    static HomeScreen homeScreen;


    public static class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        TextView titleItem;
        TextView subTitleItem;
        ImageView itemImage;

        ImageView backHeader;


        public ViewHolder(View itemView, int ViewType) {
            super(itemView);

            if (ViewType == TYPE_ITEM) {
                titleItem = (TextView) itemView.findViewById(R.id.item_drawer_title);
                subTitleItem = (TextView) itemView.findViewById(R.id.item_drawer_subtitle);
                itemImage = (ImageView) itemView.findViewById(R.id.item_drawer_image);
                Holderid = 1;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        homeScreen.clickDrawer(getLayoutPosition());
                    }
                });
            } else {


                backHeader = (ImageView) itemView.findViewById(R.id.header_drawer_backbtn);
                Holderid = 0;


                backHeader.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        homeScreen.onBackPressed();
                    }
                });
            }
        }


    }

    public drawerAdapter(HomeScreen homeScreen) {

        mNavTitles = new String[]{"Reporta", "Aprende", "Contáctanos", "Participa"};

        mNavSubTitles = new String[]{"Un animal en peligro",
                "Sobre cómo cuidar a los animales",
                "Para hablar con personal de rescate",
                "De nuestras actividades"};

        mIcons = new int[]{android.R.drawable.ic_dialog_map,
                android.R.drawable.ic_dialog_map,
                android.R.drawable.ic_dialog_map,
                android.R.drawable.ic_dialog_map};

        this.homeScreen = homeScreen;

    }


    @Override
    public drawerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drawer, parent, false);

            ViewHolder vhItem = new ViewHolder(v, viewType);

            return vhItem;

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_drawer, parent, false);

            ViewHolder vhHeader = new ViewHolder(v, viewType);

            return vhHeader;


        }
        return null;

    }

    @Override
    public void onBindViewHolder(drawerAdapter.ViewHolder holder, int position) {
        if (holder.Holderid == 1) {

            holder.titleItem.setText(mNavTitles[position - 1]);
            holder.subTitleItem.setText(mNavSubTitles[position - 1]);
            holder.itemImage.setImageResource(mIcons[position - 1]);
        } else {
        }
    }

    @Override
    public int getItemCount() {
        return mNavTitles.length + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

}