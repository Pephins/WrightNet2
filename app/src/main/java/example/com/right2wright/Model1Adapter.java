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

public class Model1Adapter extends BaseAdapter {

    Context mContext;
    LayoutInflater inflater;
    List<Model1> modellist;
    ArrayList<Model1> arrayList;

    //constructor

    public Model1Adapter (Context context, List<Model1> model1List){
        mContext = context;
       // modellist = new ArrayList<>();
        this.modellist = model1List;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model1>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder{
        TextView mTitleTv, mDescTv;
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
            view = inflater.inflate(R.layout.row2,null);

            //locate the views in row.xml
            holder.mTitleTv = view.findViewById(R.id.mainTitle1);
            holder.mDescTv = view.findViewById(R.id.mainDesc1);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        //set the results into textviews
        holder.mTitleTv.setText(modellist.get(position).getTitle());
        holder.mDescTv.setText(modellist.get(position).getDesc());
        //set the result in imageView

        //listview item clicks
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //code later
                String s = modellist.get(position).getTitle();
                SharedPreferences.Editor pref = mContext.getSharedPreferences("Right2Wrightsharedpref", MODE_PRIVATE).edit();
                pref.putString("username", s);
                pref.apply();
                Intent intent = new Intent(mContext, Profile2.class);
                mContext.startActivity(intent);

            }
        });


        return view;
    }

}