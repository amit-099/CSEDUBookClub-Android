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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.BookListAdapter;

public class BookFragment extends Fragment {
    private ArrayList<BookObj> books;

    //List Adapter Init
    private ListView bookListView;
    private BookListAdapter bookListAdapter;
    private List<String> new_user = new ArrayList<>();

    public BookFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        books = new ArrayList<>();

        bookListView = view.findViewById(R.id.all_book_list_view);


        System.out.println("Entering booooooooookssssssssss");

        DatabaseReference public_book_ref = FirebaseDatabase.getInstance().getReference().child("Books");

        public_book_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.hasChild("Books"))
//                {
//                    Map<String, Object> all_public_books = (Map<String, Object>) dataSnapshot.getValue();
//                    assert all_public_books != null;
//                    collectBookData(all_public_books);
//
//                    //List Adapter
//                    bookListAdapter = new BookListAdapter(books, getContext());
//
//                    bookListView.setAdapter(bookListAdapter);
//                    //bookListView.setClickable(true);
//
//                    bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
//                            Object obj = bookListView.getAdapter().getItem(itemNumber);
//
//                        }
//                    });
//                }

                Map<String, Object> all_public_books = (Map<String, Object>) dataSnapshot.getValue();
                assert all_public_books != null;
                collectBookData(all_public_books);

                //List Adapter
                bookListAdapter = new BookListAdapter(books, getContext());

                bookListView.setAdapter(bookListAdapter);
                //bookListView.setClickable(true);

                bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int itemNumber, long l) {
                        Object obj = bookListView.getAdapter().getItem(itemNumber);

                    }
                });


//                for(int i = 0; i < books.size(); i++) {
//                    System.out.println("iiiiii    " + books.get(i).getName());
//                }
//                System.out.println(books.size());
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

    private void collectBookData(Map<String, Object> users) {


        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){
            BookObj aBook = new BookObj();

            //Get user map
            Map singleBook = (Map) entry.getValue();

            //Get phone field and append to list
            aBook.setBook_id((String) singleBook.get("bookid"));
            aBook.setName((String) singleBook.get("name"));
            aBook.setAvailability((String) singleBook.get("availability"));
            aBook.setCategory((String) singleBook.get("category"));
            aBook.setOwner((String) singleBook.get("owner"));
            aBook.setWriter((String) singleBook.get("writer"));

            //phoneNumbers.add((Long) singleUser.get("phone"));
            System.out.println("book      " + aBook.toString());
            books.add(aBook);
        }
        //System.out.println("qqqqqq   " + new_user.size());

        System.out.println("bookssssssss   " + books.toString());
    }

}
