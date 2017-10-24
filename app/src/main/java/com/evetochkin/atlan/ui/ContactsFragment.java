package com.evetochkin.atlan.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.evetochkin.atlan.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import jp.wasabeef.picasso.transformations.MaskTransformation;

public class ContactsFragment extends Fragment {


    @BindView(R.id.photo)
    ImageView photo;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.textView2)
    TextView textView2;
    @BindView(R.id.telegram)
    TextView telegram;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.comment_email)
    TextView email;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.skype)
    TextView skype;
    private Transformation transformation;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contacts, container, false);
        ButterKnife.bind(this, rootView);
        initUI();
        return rootView;
    }

    private void initUI() {
        transformation = new MaskTransformation(getContext(), R.drawable.circle_white);
        Picasso.with(getContext())
                .load(R.drawable.photo)
                .resize(200, 200)
                .centerInside()
                .transform(transformation)
                .into(photo);
    }
}
