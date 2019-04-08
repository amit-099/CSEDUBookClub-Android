package fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.searchify.R;

import java.util.ArrayList;
import java.util.List;

import adapter.HomeRecyclerAdapter;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    List<String> goalName, goalDes, goalStatus;
    List<Integer> goalImages, goalDuration, goalStreak;
    private TextView goalHeaderText;


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        goalHeaderText = view.findViewById(R.id.goal_header);

        goalName = new ArrayList<>();
        goalDes = new ArrayList<>();
        goalDuration = new ArrayList<>();
        goalImages = new ArrayList<>();
        goalStreak = new ArrayList<>();
        goalStatus = new ArrayList<>();



        recyclerView = view.findViewById(R.id.goal_recycler_view);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new HomeRecyclerAdapter(goalName, goalDes, goalImages, goalDuration, goalStreak, goalStatus);
        recyclerView.setAdapter(adapter);

        return view;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
