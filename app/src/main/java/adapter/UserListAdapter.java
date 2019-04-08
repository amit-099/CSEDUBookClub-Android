package adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.searchify.R;
import com.example.searchify.UserObj;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    public List<UserObj> userObjList;
    Context context;

    public UserListAdapter(List<UserObj> mFoodList, Context

            context) {
        this.context = context;
        this.userObjList = mFoodList ;
    }

    @Override
    public int getCount() {
        return userObjList.size();
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
        UserObj foodList = userObjList.get(position);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.spinner_value_layout, parent,

                    false);
        } else {
            v = convertView;
        }

        Typeface mTfRegular = Typeface.createFromAsset(v.getContext().getAssets(),"OpenSans-Regular.ttf");

        ImageView foodImageView = v.findViewById(R.id.spinnerImages);
        TextView foodNameText = v.findViewById(R.id.spinnerTextView);
        foodNameText.setTypeface(mTfRegular);


        foodImageView.setImageResource(R.drawable.man);
        foodNameText.setText(foodList.getName());

        foodImageView.setVisibility(View.VISIBLE);
        foodNameText.setVisibility(View.VISIBLE);


        return v;
    }
}