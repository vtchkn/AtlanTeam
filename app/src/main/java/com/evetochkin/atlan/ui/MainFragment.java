package com.evetochkin.atlan.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.andreabaccega.widget.FormEditText;
import com.evetochkin.atlan.AtlanApp;
import com.evetochkin.atlan.R;
import com.evetochkin.atlan.adapters.UsersAdapter;
import com.evetochkin.atlan.api.AtlanApi;
import com.evetochkin.atlan.model.*;
import com.squareup.picasso.Picasso;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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


    public static final String POST_NUMBER = "POST_NUMBER";
    public static final String COMMENT_NUMBER = "COMMENT_NUMBER";
    private static List<User> datum = new ArrayList<>();
    @BindView(R.id.comment_name)
    TextView comment_name;
    @BindView(R.id.comment_body)
    TextView comment_body;
    @BindView(R.id.comment_email)
    TextView comment_email;
    @BindView(R.id.main_photo)
    ImageView main_photo;
    @BindView(R.id.Todo)
    TextView mainTodo;
    @BindView(R.id.todos)
    CardView todos;
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
        loadPosts(1);
        loadComments(1);
        loadUsers();
        loadPhotos(3);
        Random random = new Random();
        loadTodos(random.nextInt(200) + 1);

    }

    private void loadTodos(int id) {
        Call<Todo> call = api.todos(id);
        call.enqueue(new Callback<com.evetochkin.atlan.model.Todo>() {
            @Override
            public void onResponse(Call<Todo> call, Response<Todo> response) {
                Todo todo = response.body();
                if (todo == null) {
                    Toast.makeText(getContext(), "load_error", Toast.LENGTH_SHORT).show();
                } else {
                    mainTodo.setText(todo.getTitle());
                    if (todo.getCompleted()) {
                        mainTodo.setTextColor(getContext().getResources().getColor(R.color.secondaryColor));
                    }
                }
            }

            @Override
            public void onFailure(Call<Todo> call, Throwable t) {

            }
        });
    }

    private void loadPhotos(int id) {
        Call<Photo> call = api.photos(id);
        call.enqueue(new Callback<Photo>() {
            @Override
            public void onResponse(Call<Photo> call, Response<Photo> response) {
                Photo photo = response.body();
                if (photo == null) {
                    Toast.makeText(getContext(), "load_error", Toast.LENGTH_SHORT).show();
                } else {
                    Picasso.with(getContext())
                            .load(photo.getUrl())
                            .resize(600, 600)
                            .centerCrop()
                            .into(main_photo);
                }
            }

            @Override
            public void onFailure(Call<Photo> call, Throwable t) {

            }
        });
    }

    private void loadComments(int id) {
        Call<Comment> call = api.comments(id);
        call.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                Comment comment = response.body();
                if (comment == null) {
                    Toast.makeText(getContext(), "load_error", Toast.LENGTH_SHORT).show();
                } else {
                    comment_name.setText(comment.getName());
                    comment_body.setText(comment.getBody());
                    comment_email.setText(comment.getEmail());
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {

            }
        });
    }

    private void loadPosts(int id) {
        Call<Post> call = api.posts(id);
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();
                if (post == null) {
                    Toast.makeText(getContext(), "load_error", Toast.LENGTH_SHORT).show();
                } else {
                    post_title.setText(post.getTitle());
                    post_body.setText(post.getBody());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void loadUsers() {
        showProgressDialog();
        adapter.clear();

        Call<User>[] calls = new Call[]{api.users(1), api.users(2), api.users(3), api.users(4), api.users(5)};
        for (Call<User> call : calls) {
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if (user == null) {
                        Toast.makeText(getContext(), "load_error", Toast.LENGTH_SHORT).show();
                    } else {
                        datum.add(user);
                        adapter.add(user);
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });
        }
        hideProgressDialog();


    }

    private void initUI() {
        post_number.setTag(POST_NUMBER);
        comment_number.setTag(COMMENT_NUMBER);
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
        comment_number.setOnEditorActionListener(listener);
    }

    public void onClickValidate(View v) {
        FormEditText fdt = (FormEditText) v;
        if (fdt.testValidity()) {
            if (fdt.getTag().equals(POST_NUMBER)) {
                loadPosts(Integer.parseInt(fdt.getText().toString()));
            } else if (fdt.getTag().equals(COMMENT_NUMBER)) {
                loadComments(Integer.parseInt(fdt.getText().toString()));
            }

        }

    }
}