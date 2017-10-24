package com.evetochkin.atlan.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.evetochkin.atlan.R;
import com.evetochkin.atlan.model.User;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

    List<User> data = new ArrayList<>();
    private Context context;

//    public UsersAdapter(Context context) {
//        this.context = context;
//    }

    @Override
    public UsersAdapter.UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cl_user, parent, false);
        UsersAdapter.UserViewHolder viewHolder = new UsersAdapter.UserViewHolder(v);
        return viewHolder;
    }

    public void clear() {
        data.clear();
    }

    @Override
    public void onBindViewHolder(UsersAdapter.UserViewHolder holder, final int position) {
        final User user = data.get(position);
        holder.user_name.setText(user.getName());
        holder.user_username.setText(user.getUsername());
        holder.user_email.setText(user.getEmail());
        String address = user.getAddress().toString();
        holder.user_address.setText(address);
        String[] split = user.getPhone().split("x");
        String phone = split[0];
        holder.user_phone.setText(phone);
        holder.user_company.setText(user.getCompany().getName());
        holder.user_site.setText(user.getWebsite());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addAll(List<User> list) {
        data.addAll(list);
        notifyDataSetChanged();
    }

    public void add(User user) {
        data.add(user);
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_name)
        TextView user_name;
        @BindView(R.id.user_email)
        TextView user_email;
        @BindView(R.id.user_username)
        TextView user_username;
        @BindView(R.id.user_address)
        TextView user_address;
        @BindView(R.id.user_phone)
        TextView user_phone;
        @BindView(R.id.user_site)
        TextView user_site;
        @BindView(R.id.user_company)
        TextView user_company;

        public UserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
