package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchify.BookObj;
import com.example.searchify.R;
import com.example.searchify.UserObj;

import java.util.List;

public class ReceivedBookListAdapter extends BaseAdapter {
    public List<BookObj> bookObjList;
    Context context;

    public ReceivedBookListAdapter(List<BookObj> bookObjList, Context

            context) {
        this.context = context;
        this.bookObjList = bookObjList ;
    }

    @Override
    public int getCount() {
        return bookObjList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;
        BookObj book = bookObjList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.received_book_list_adapter_layout, parent,

                    false);
        } else {
            v = convertView;
        }

        Typeface mTfRegular = Typeface.createFromAsset(v.getContext().getAssets(),"OpenSans-Regular.ttf");

        ImageView bookImageView = v.findViewById(R.id.book_img);
        TextView senderNameText = v.findViewById(R.id.req_sender_name);
        TextView bookNameText = v.findViewById(R.id.book_name);
        TextView authorNameText = v.findViewById(R.id.author_name);
        Button reqButton = v.findViewById(R.id.allow_req_btn);
        Button readButton = v.findViewById(R.id.reject_req_btn);

        if(book.getAvailability()=="no")
        {
            reqButton.setClickable(false);
            reqButton.setText("Not Available");
        }

        reqButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //send a request to book owner
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //current user e book read list e thakle unread banate hobe else read
            }
        });

        //Book Image
        bookImageView.setImageResource(R.drawable.library );
        bookImageView.setVisibility(View.VISIBLE);

        //Book name
        bookNameText.setTypeface(mTfRegular);
        bookNameText.setText(book.getName());
        bookNameText.setVisibility(View.VISIBLE);

        //author name
        authorNameText.setTypeface(mTfRegular);
        authorNameText.setText(book.getWriter());
        authorNameText.setVisibility(View.VISIBLE);


        return v;
    }
}