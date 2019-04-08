package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.searchify.R;
import com.example.searchify.UserObj;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.HomeRecyclerAdapter;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    List<String> goalName, goalDes, goalStatus;
    List<Integer> goalImages, goalDuration, goalStreak;
    private TextView goalHeaderText;


    private ArrayList<UserObj> new_user = new ArrayList<>();


    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

//        recyclerView = view.findViewById(R.id.goal_recycler_view);
//        layoutManager = new LinearLayoutManager(getContext());
//        recyclerView.setLayoutManager(layoutManager);

//        adapter = new HomeRecyclerAdapter(goalName, goalDes, goalImages, goalDuration, goalStreak, goalStatus);
//        recyclerView.setAdapter(adapter);


        DatabaseReference user_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").child("UID");

        user_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> all_user = (Map<String, Object>) dataSnapshot.getValue();
                assert all_user != null;
                collectUserData(all_user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //getUserData(user_ref);




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

    private void collectUserData(Map<String, Object> users) {

        UserObj aUser = new UserObj();
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map
            Map singleUser = (Map) entry.getValue();

            //Get phone field and append to list
            aUser.setName((String) singleUser.get("name"));
            aUser.setUser_name((String) singleUser.get("username"));
            //phoneNumbers.add((Long) singleUser.get("phone"));
            System.out.println("PPPPPPP      " + aUser.toString());
            new_user.add(aUser);
        }
        System.out.println("qqqqqq   " + new_user.size());

        //System.out.println(phoneNumbers.toString());
    }

}
