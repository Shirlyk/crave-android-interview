package vodiolabs.androidinterview.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.List;

import vodiolabs.androidinterview.R;
import vodiolabs.androidinterview.utils.ImageLoader;

/**
 * Created by idan on 1/13/15.
 */
public class MainListAdapter extends BaseAdapter {

    private List<List<String>> mItems;
    private Context mContext;

    public MainListAdapter(Context ctx, List<List<String>> items) {
        mItems = items;
        mContext = ctx;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public List<String> getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_scroll_view, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mLayout = (LinearLayout) convertView.findViewById(R.id.view_scroll_view_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        for (String str : mItems.get(position)) {
            ImageView imageView = new ImageView(parent.getContext());
            new ImageLoader(imageView, null).execute(str);
            viewHolder.mLayout.addView(imageView);
        }

        return convertView;
    }

    private static class ViewHolder {
        LinearLayout mLayout;
    }
}
