package fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.searchify.BookObj;
import com.example.searchify.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class BookFragment extends Fragment {
    private ArrayList<BookObj> books;

    public BookFragment() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        books = new ArrayList<>();
        System.out.println("Entering booooooooookssssssssss");

        DatabaseReference public_book_ref = FirebaseDatabase.getInstance().getReference().child("Books");

        public_book_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Object> all_public_books = (Map<String, Object>) dataSnapshot.getValue();
                assert all_public_books != null;
                collectBookData(all_public_books);

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
