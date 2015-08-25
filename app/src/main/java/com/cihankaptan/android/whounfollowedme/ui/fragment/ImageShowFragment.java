package com.cihankaptan.android.whounfollowedme.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.cihankaptan.android.whounfollowedme.R;
import com.cihankaptan.android.whounfollowedme.instagram.Media;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageShowFragment extends Fragment {
    @InjectView(R.id.bigImage)
    ImageView bigImage;
    @InjectView(R.id.bigImageLabel)
    RelativeLayout bigImageLabel;

    Media medieEvent;

    public static ImageShowFragment newInstance(Media medieEvent) {
        ImageShowFragment fragment = new ImageShowFragment();
        Bundle args = new Bundle();
        args.putParcelable("MediaEvent",medieEvent);
        fragment.setArguments(args);
        return fragment;
    }

    public ImageShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            medieEvent = getArguments().getParcelable("MediaEvent");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_show, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Picasso.with(getActivity()).load(medieEvent.getImages().getStandart_resulation().getUrl()).into(bigImage);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }


    @OnClick({R.id.bigImageLabel,R.id.bigImage})
    public void bigImageLAbelAction(View view){
        getFragmentManager().popBackStack();
    }
}
