package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.searchify.BookObj;
import com.example.searchify.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import adapter.ReceivedBookListAdapter;

public class AnalysisFragment extends Fragment {
    private ArrayList<BookObj> books;

    //List Adapter Init
    private ListView bookListView;
    private ReceivedBookListAdapter bookListAdapter;
    private List<String> new_user = new ArrayList<>();
    private FirebaseAuth mAuth;

    public AnalysisFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_analysis, container, false);

        books = new ArrayList<>();

        bookListView = view.findViewById(R.id.received_req_book_list_view);

        mAuth = FirebaseAuth.getInstance();


        System.out.println("Entering analysissssssssssssssssssss");

        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        DatabaseReference receive_req_ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Owners").
                child("UID").child(user_id);

        receive_req_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> all_received_req = (Map<String, Object>) dataSnapshot.getValue();
                assert all_received_req != null;
                System.out.println("ALLLLLLLLLLLAAAAAAAAAAAAAALLLLLLLLLLLLLLLLLLAAAAAAAAAAAAAAAAAAAAALLLLLLLLLLLLL                ");
                System.out.println(all_received_req);

                if(all_received_req.containsKey("receiverequest")) {
                    collectReqData(all_received_req);
                    System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmm " + books);


                    //List Adapter
                    bookListAdapter = new ReceivedBookListAdapter(books, getContext());

                    bookListView.setAdapter(bookListAdapter);
                    //bookListView.setClickable(true);

                    bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                            Object obj = bookListView.getAdapter().getItem(itemNumber);
//

                        }
                    });
                }
                else
                {
                    System.out.println("no booooooooooooks fffffffffound");
                }

//
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    private void collectReqData(Map<String, Object> users) {


        Map<String, Object> req_book = (Map<String, Object>) users.get("receiverequest");

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : req_book.entrySet()){
            BookObj aBook = new BookObj();

            //Get user map
            Map singleBook = (Map) entry.getValue();

            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            System.out.println(singleBook);

            //Get phone field and append to list
            aBook.setName((String) singleBook.get("name"));
            aBook.setAvailability((String) singleBook.get("availability"));
            aBook.setCategory((String) singleBook.get("category"));
            aBook.setOwner((String) singleBook.get("owner"));
            aBook.setWriter((String) singleBook.get("writer"));
            aBook.setBook_id((String) singleBook.get("bookid"));

            //phoneNumbers.add((Long) singleUser.get("phone"));
            System.out.println("book      " + aBook.toString());
            books.add(aBook);
        }
        //System.out.println("qqqqqq   " + new_user.size());

        System.out.println("bookssssssss   " + books.toString());
    }

}
