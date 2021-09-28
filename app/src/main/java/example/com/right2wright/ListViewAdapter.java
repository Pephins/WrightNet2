package example.com.right2wright;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class ListViewAdapter extends BaseAdapter {

    //variables
    Context mContext;
    LayoutInflater inflater;
    List<Model> modellist;
    ArrayList<Model> arrayList;

    //constructor

    public ListViewAdapter(Context context, List<Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder {
        TextView mTitleTv, mDescTv;
        ImageView mIconIv;
    }


    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int position) {
        return modellist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row, null);

            //locate the views in row.xml
            holder.mTitleTv = view.findViewById(R.id.mainTitle);
            holder.mDescTv = view.findViewById(R.id.mainDesc);
            holder.mIconIv = view.findViewById(R.id.mainIcon);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(position).getTitle());
        holder.mDescTv.setText(modellist.get(position).getDesc());
        //set the result in imageView
        holder.mIconIv.setImageResource(modellist.get(position).getIcon());

        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String s = modellist.get(position).getTitle();
                SharedPreferences.Editor pref = mContext.getSharedPreferences("Right2Wrightsharedpref", MODE_PRIVATE).edit();
                pref.putString("job", s);
                pref.apply();

                //Where to??
                if (modellist.get(position).getTitle().equals("Carpenter")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Carpenter");
                    intent.putExtra("contentTv", "This is a Carpenter Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Joiner")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Joiner");
                    intent.putExtra("contentTv", "This is a Joiner Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Plumber")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Plumber");
                    intent.putExtra("contentTv", "This is a Plumber Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Dstv")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Dstv");
                    intent.putExtra("contentTv", "This is a Dstv Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Electrician")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Electrician");
                    intent.putExtra("contentTv", "This is an Electrician Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Electronic Technician")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Electronic Technician");
                    intent.putExtra("contentTv", "This is an Electronic Technician Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Glazier")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Glazier");
                    intent.putExtra("contentTv", "This is a Glazier Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Locksmith")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Locksmith");
                    intent.putExtra("contentTv", "This is a Locksmith Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Plasterer")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Plasterer");
                    intent.putExtra("contentTv", "This is a Plasterer Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Shutterhand")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Shutterhand");
                    intent.putExtra("contentTv", "This is a Shutterhand Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Sound Technician")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Sound Technician");
                    intent.putExtra("contentTv", "This is a Sound Technician Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Cctv")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Cctv");
                    intent.putExtra("contentTv", "This is a Cctv Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Steel Fixer")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Steel Fixer");
                    intent.putExtra("contentTv", "This is a Steel Fixer Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Tiler")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Tiler");
                    intent.putExtra("contentTv", "This is a Tiler Description...");
                    mContext.startActivity(intent);
                }
                if (modellist.get(position).getTitle().equals("Welder")) {
                    Intent intent = new Intent(mContext, Artisans.class);
                    intent.putExtra("actionBarTitle", "Welder");
                    intent.putExtra("contentTv", "This is a Welder Description...");
                    mContext.startActivity(intent);
                }

            }
        });


        return view;
    }

    //filter
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length() == 0) {
            modellist.addAll(arrayList);
        } else {
            for (Model model : arrayList) {
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    modellist.add(model);
                }
            }
        }
        notifyDataSetChanged();
    }
}
