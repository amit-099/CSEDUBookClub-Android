package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import adapter.ReceivedBookListAdapter;

public class RequestFragment extends Fragment {
    private ArrayList<BookObj> books;

    //List Adapter Init
    private ListView bookListView;
    private ReceivedBookListAdapter bookListAdapter;
    private List<String> new_user = new ArrayList<>();
    private FirebaseAuth mAuth;
    private String from;

    public RequestFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_requests, container, false);

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

                HashMap<String, Object> all_received_req = (HashMap<String, Object>) dataSnapshot.getValue();
                assert all_received_req != null;
                System.out.println("ALLLLLLLLLLLAAAAAAAAAAAAAALLLLLLLLLLLLLLLLLLAAAAAAAAAAAAAAAAAAAAALLLLLLLLLLLLL                ");
                //System.out.println(all_received_req);

                if(all_received_req.containsKey("receiverequest")) {
                    collectReqData(all_received_req);
                    System.out.println("mmmmmmmmmmmmmmmmmmmmmmmmmm " + books);


                    //List Adapter
                    bookListAdapter = new ReceivedBookListAdapter(books, from, getContext());

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

    private void collectReqData(HashMap<String, Object> users) {

        System.out.println(users);


        HashMap<String, Object> req_book = (HashMap<String, Object>) users.get("receiverequest");

        System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
        System.out.println(req_book);

        //iterate through each user, ignoring their UID
        for (HashMap.Entry<String, Object> entry : req_book.entrySet()){
            BookObj aBook = new BookObj();

            //Get user map
            HashMap<String, Object> singleBook = (HashMap<String, Object>) entry.getValue();
            System.out.println("oooooooooooooooooooooooooooooooooooooooooooo");
            System.out.println(singleBook);
            HashMap singleBook1 = new HashMap();
//            for (Object ent : singleBook.entrySet()){
//                if(!ent.equals("from")) {
//                    singleBook1 = (HashMap) entry.getValue();
//                }
//            }


            for (HashMap.Entry<String, Object> entry2 : singleBook.entrySet()) {
                System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
                System.out.println(entry2.getValue() + "        " + entry2.getKey());
                if (!entry2.getKey().equals("from")) {
                    singleBook1 = (HashMap) entry2.getValue();
                }
                else
                {
                    from = (String) entry2.getValue();
                }
            }






            System.out.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
            System.out.println(singleBook1);

            //Get phone field and append to list
            aBook.setName((String) singleBook1.get("name"));
            aBook.setAvailability((String) singleBook1.get("availability"));
            aBook.setCategory((String) singleBook1.get("category"));
            aBook.setOwner((String) singleBook1.get("owner"));
            aBook.setWriter((String) singleBook1.get("writer"));
            aBook.setBook_id((String) singleBook1.get("bookid"));

            //phoneNumbers.add((Long) singleUser.get("phone"));
            System.out.println("book      " + aBook.toString());
            books.add(aBook);
        }
        //System.out.println("qqqqqq   " + new_user.size());

        System.out.println("bookssssssss   " + books.toString());
    }

}
