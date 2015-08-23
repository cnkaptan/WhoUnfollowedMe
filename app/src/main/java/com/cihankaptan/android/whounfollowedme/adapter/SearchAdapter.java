package com.cihankaptan.android.whounfollowedme.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.view.FontTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by cnkaptan on 06/08/15.
 */
public class SearchAdapter extends BaseAdapter implements Filterable {

    private static final String TAG = SearchAdapter.class.getSimpleName();
    private List<User> contents;
    private String str = null;
    private ArrayList<User> filterContents;
    private ContentFilter contentFilter = new ContentFilter();


    public SearchAdapter(List<User> contents) {
//        Log.e(TAG, "Search Adapter constructor");
        this.contents = contents;
        filterContents = new ArrayList<User>();

    }

    @Override
    public int getCount() {
        return filterContents.size();
    }

    @Override
    public User getItem(int position) {

        return filterContents.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.userlist_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bind(getItem(position),parent.getContext());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return contentFilter;
    }

    private class ContentFilter extends Filter {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
//            Log.e(TAG, constraint.toString());
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();
            final List<User> list = SearchAdapter.this.contents;
            final ArrayList<User> nlist = new ArrayList<User>(list.size());
            if (!filterString.equals("")){
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getFull_name().toLowerCase().contains(filterString) ||
                            list.get(i).getUsername().toLowerCase().contains(filterString)) {
                        nlist.add(list.get(i));
                    }
                }
            }else{
                nlist.clear();
            }

            results.values = nlist;
            results.count = nlist.size();


            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filterContents = (ArrayList<User>) results.values;
            notifyDataSetChanged();
        }
    }


    /**
     * This class contains all butterknife-injected Views & Layouts from layout file 'searchadapter_item_layout.xml'
     * for easy to all layout elements.
     *
     * @author ButterKnifeZelezny, plugin for Android Studio by Avast Developers (http://github.com/avast)
     */

    static class ViewHolder {
        @InjectView(R.id.profilePhoto)
        ImageView profilePhoto;
        @InjectView(R.id.full_name)
        FontTextView fullName;
        @InjectView(R.id.user_name)
        FontTextView userName;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }

        public void bind(User user,Context context) {
            fullName.setText(user.getFull_name());
            userName.setText(Html.fromHtml(user.getUsername()));
            Picasso.with(context).load(user.getProfile_picture()).into(profilePhoto);
        }
    }

}
