package com.evetochkin.atlan.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.andreabaccega.widget.FormEditText;
import com.evetochkin.atlan.AtlanApp;
import com.evetochkin.atlan.R;
import com.evetochkin.atlan.adapters.UsersAdapter;
import com.evetochkin.atlan.api.AtlanApi;
import com.evetochkin.atlan.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * - Для posts – отобразить результат для n-го поста (должна быть возможность вписать n в карточку и нажать «Подтвердить» для отображения результата).
 * Максимальный n = 100. Вызов конкретного ID поста осуществляется через добавление ‘’/n’’ (n - id поста) к концу ссылки
 * <p>
 * - Для comments – отобразить результат для n-го комментария (должна быть возможность вписать n в карточку и нажать «Подтвердить» для отображения результата).
 * Максимальный n = 500. Вызов конкретного ID комментария осуществляется через добавление ‘’/n’’ (n - id комментария) к концу ссылки
 * <p>
 * - Для users - отобразить результат первых 5-ти пользователей в единой карточке (в 5 линий).
 * Вызов конкретного ID пользователя осуществляется через добавление ‘’/n’’ (n - id пользователя) к концу ссылки.
 * <p>
 * - Для photo – отобразить результат 3-го фото в виде картинки, которая отобразится в карточке.
 * Вызов конкретного ID фото осуществляется через добавление ‘’/n’’ (n - id фото) к концу ссылки.
 * <p>
 * - Для todos – отобразить результат случайной задачи. Вызов конкретного ID задачи осуществляется через добавление ‘’/n’’ (n - id todo) к концу ссылки.
 */

public class MainFragment extends BaseFragment {


    private static List<User> datum = new ArrayList<>();
    @BindView(R.id.post_title)
    TextView post_title;
    @BindView(R.id.post_body)
    TextView post_body;
    @BindView(R.id.post_number)
    FormEditText post_number;
    @BindView(R.id.comment_number)
    FormEditText comment_number;
    @BindView(R.id.posts)
    CardView posts;
    @BindView(R.id.comments)
    CardView comments;
    @BindView(R.id.users)
    CardView users;
    @BindView(R.id.photos)
    CardView photos;
    @BindView(R.id.rv_users)
    RecyclerView recyclerView;
    @BindView(R.id.post_msg)
    TextView postMsg;
    @BindView(R.id.comment_msg)
    TextView commentMsg;
    private UsersAdapter adapter = new UsersAdapter();
    
    private AtlanApi api;


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        api = ((AtlanApp) getContext().getApplicationContext()).atlanApi();
        loadItems();
        adapter.clear();
        adapter.addAll(datum);
        initUI();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
//        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_users);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(adapter);
//        post_number = rootView.findViewById(R.id.post_number);
        return rootView;
    }

    private void initData() {
//        datum = new ArrayList<>();
    }

    private void initAdapter() {

    }

    private void loadItems() {
        loadUsers();


    }

    private void loadPosts(int id) {

    }

    private void loadUsers() {
        showProgressDialog();
        for (int i = 0; i < 5; i++) {
            final int finalI = i++;
            getLoaderManager().restartLoader(AtlanApp.LOADER_USERS, null, new LoaderManager.LoaderCallbacks<User>() {
                @Override
                public Loader<User> onCreateLoader(int id, Bundle bundle) {
                    return new AsyncTaskLoader<User>(getContext()) {
                        @Override
                        public User loadInBackground() {
                            try {
                                return api.users(finalI).execute().body();
                            } catch (IOException e) {
                                e.printStackTrace();
                                return null;
                            }
                        }
                    };
                }

                @Override
                public void onLoadFinished(Loader<User> loader, User user) {
                    if (user == null) {
                        Toast.makeText(getContext(), "load_error", Toast.LENGTH_SHORT).show();
                    } else {
                        datum.add(user);
                        //                    adapter.clear();
                        //                    adapter.addAll(datum);
                        Log.d("usersList", String.valueOf(datum.size()));
                        Log.d("usersAdapter", String.valueOf(adapter.getItemCount()));
                    }

                }

                @Override
                public void onLoaderReset(Loader<User> loader) {

                }
            }).forceLoad();
            hideProgressDialog();
        }
    }

    private void initUI() {

        TextView.OnEditorActionListener listener = new FormEditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    if (!event.isShiftPressed()) {
                        // the user is done typing.
                        onClickValidate(v);

                        return true; // consume.
                    }
                }
                return false; // pass on to other listeners.
            }
        };
        post_number.setOnEditorActionListener(listener);
    }

    public void onClickValidate(View v) {
        FormEditText[] allFields = {post_number, comment_number};


        boolean allValid = true;
        for (FormEditText field : allFields) {
            allValid = field.testValidity() && allValid;
        }

        if (allValid) {
            // YAY

        } else {
            // EditText are going to appear with an exclamation mark and an explicative message.

            if (TextUtils.isEmpty(post_number.getText())) {
                postMsg.setText(getString(R.string.number_error_empty));
            } else {
                postMsg.setText(getString(R.string.number_error_message));
            }
            if (TextUtils.isEmpty(comment_number.getText())) {
                commentMsg.setText(getString(R.string.number_error_empty));
            }else {
                commentMsg.setText(getString(R.string.number_error_message));
            }


        }
    }
}