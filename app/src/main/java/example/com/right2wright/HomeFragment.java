package example.com.right2wright;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Toolbar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class HomeFragment extends Fragment{


    //private EditText text;
    ListView listView;
    ListViewAdapter adapter;
    String[] title;
    String[] description;
    int[] icon;
    ArrayList<Model> arrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button art_btn = getActivity().findViewById(
                R.id.artisan1);
        art_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity().getApplicationContext(), Register.class));
            }
        });


        Intent intent = getActivity().getIntent();
        SharedPrefManager.getInstance(getContext()).isLoggedIn();

        /////////////////////////////////////////////////////////////////////////////////////

        title = new String[]{"Carpenter", "Cctv", "Dstv", "Electrician", "Electronic Technician",
                "Glazier", "Joiner", "Locksmith", "Plasterer", "Plumber", "Shutterhand", "Sound Technician",
                "Steel Fixer", "Tiler", "Welder"};
        description = new String[]{"Skilled in making and repairing wooden objects and structures.", "Skilled in Cctv installation and repairing",
                "Skilled in Dstv installation and repairing","Skilled in repairing Electronic appliances",
                "Help design, develop, test, manufacture, install, and repair electrical and electronic equipment",
                "Skilled fitting glass into windows and doors.", "One who constructs the wooden components of a building, such as stairs, doors, and door and window frames.",
                "One who makes and repairs locks.", "Skilled in applying plaster to walls, ceilings, or other structures.",
                "Skilled in fitting and repairing the pipes, fittings, and other apparatus of water supply, sanitation, or heating systems.",
                " Skilled in concrete, shuttering, scaffolding, formwork, concrete placing and concrete curing Sound Tech Description",
                "Tradesman who positions and secures steel reinforcing bars, also known as rebar, and steel mesh used in reinforced concrete on construction projects.",
                "A skilled person who works in a recording studio or for a radio or television",
                "One who lays tiles.", "One who welds metal"};
        icon = new int[]{R.drawable.carpenteruse_fin, R.drawable.cctv_fin, R.drawable.dstvuse_fin, R.drawable.electrician_fin, R.drawable.electronic_technician_fin,
                R.drawable.glazier_fin, R.drawable.joiner_fin, R.drawable.locksmithuse_fin, R.drawable.plasterer_fin, R.drawable.plumber_fin,
                R.drawable.shutterhand_fin, R.drawable.sound_technician_fin, R.drawable.steelfixer_fin, R.drawable.tiler_fin, R.drawable.welder_fin};

        listView = getActivity().findViewById(R.id.listView);

        for (int i = 0; i < title.length; i++) {
            Model model = new Model(title[i], description[i], icon[i]);
            //bind all string in array
            arrayList.add(model);
        }

        //pass results in listViewAdapter class
        adapter = new ListViewAdapter(getActivity(), arrayList);

        //bind the adapter to the listview
        listView.setAdapter(adapter);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu,menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }else{
                    adapter.filter(s);
                }
                return true;
            }
        });
       // super.onCreateOptionsMenu(menu, inflater);
    }
}


    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();

        inflater.inflate(R.menu.menu_add_app, menu);
        MenuItem item = menu.findItem(R.id.action_search_menu_add_app);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Here is where we are going to implement the filter logic
                return true;
            }

        });
    }*/