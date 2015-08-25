package com.cihankaptan.android.whounfollowedme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.adapter.SearchAdapter;
import com.cihankaptan.android.whounfollowedme.instagram.User;
import com.cihankaptan.android.whounfollowedme.util.Constans;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SearchActivity extends BaseActivity implements TextWatcher,
        AdapterView.OnItemClickListener ,Constans{

    private static final String TAG = SearchActivity.class.getSimpleName();
    @InjectView(R.id.autocomplete_et)
    EditText autocompleteEt;
    @InjectView(R.id.clear_button)
    ImageView clearButton;
    @InjectView(R.id.contentListView)
    ListView contentListView;
    private List<User> users;
    private SearchAdapter searchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);


        users = getIntent().getParcelableArrayListExtra(ALL_USERS);

        assert users != null;
        searchAdapter = new SearchAdapter(users);
        contentListView.setAdapter(searchAdapter);
        contentListView.setOnItemClickListener(this);
        autocompleteEt.addTextChangedListener(this);

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autocompleteEt.setText("");
            }
        });
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchAdapter.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, UserDetailActivity.class);
        User content = ((SearchAdapter) parent.getAdapter()).getItem(position);
        intent.putExtra("user", content);
        startActivity(intent);
    }
}
