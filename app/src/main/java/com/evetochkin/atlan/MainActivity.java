package com.evetochkin.atlan;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.evetochkin.atlan.api.AtlanApi;
import com.evetochkin.atlan.model.User;
import com.evetochkin.atlan.ui.BaseActivity;
import com.evetochkin.atlan.ui.ContactsFragment;
import com.evetochkin.atlan.ui.MainFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private static final String TAG_FRAGMENT_MAIN = "tag_fragment_home";
    private static final String TAG_FRAGMENT_CONTACTS = "tag_fragment_contacts";
    private static List<User> datum;
    private TextView mTextMessage;
    private List<Fragment> fragments = new ArrayList<>(2);
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
//                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    return true;
                case R.id.navigation_home:
                    switchFragment(0, TAG_FRAGMENT_MAIN);
                    return true;
                case R.id.navigation_contacts:
                    switchFragment(1, TAG_FRAGMENT_CONTACTS);
                    return true;
            }
            return false;
        }

    };
    private AtlanApi api;

    private void switchFragment(int pos, String tag) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content, fragments.get(pos), tag)
                .commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        datum = new ArrayList<>();
//        api = ((AtlanApp) getApplication().getApplicationContext()).atlanApi();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        loadItems();
        initUI();
    }

    private void initUI() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        fragments.add(new MainFragment());
        fragments.add(new ContactsFragment());
        switchFragment(0, TAG_FRAGMENT_MAIN);
    }

}
