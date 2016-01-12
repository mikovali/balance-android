package io.github.mikovali.balance.android.infrastructure.android.view;

import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.balance.android.R;

import static io.github.mikovali.balance.android.application.Constants.KEY_FRAGMENT_ANIMATION_PIVOT;

public class TransactionUpdateFragment extends Fragment {

    @Bind(R.id.toolbar)
    Toolbar toolbarView;

    private PointF animationPivot;
    private int animationDuration;

    // lifecycle

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (transit == 0) {
            return super.onCreateAnimation(transit, enter, nextAnim);
        }
        // FIXME on rotation the view moves to another location so the pivot is wrong
        final ScaleAnimation animation = new ScaleAnimation(
                enter ? 0.0f : 1.0f,
                enter ? 1.0f : 0.0f,
                enter ? 0.0f : 1.0f,
                enter ? 1.0f : 0.0f,
                animationPivot.x, animationPivot.y);
        animation.setDuration(animationDuration);
        animation.setInterpolator(new FastOutSlowInInterpolator());
        return animation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animationPivot = getArguments().getParcelable(KEY_FRAGMENT_ANIMATION_PIVOT);
        animationDuration = getResources().getInteger(android.R.integer.config_mediumAnimTime);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transaction_update_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        ButterKnife.unbind(this);
        super.onDestroyView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // tint navigation icon
        final Drawable navigationIcon = DrawableCompat.wrap(ResourcesCompat
                .getDrawable(getResources(), R.drawable.ic_clear, null));
        DrawableCompat.setTint(navigationIcon, Color.WHITE);
        toolbarView.setNavigationIcon(navigationIcon);
        toolbarView.setNavigationContentDescription(R.string.transaction_update_close);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarView);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(R.string.transaction_update_create_title);
    }

    // factories

    public static TransactionUpdateFragment newInstance(PointF animationPivot) {
        final Bundle arguments = new Bundle();
        arguments.putParcelable(KEY_FRAGMENT_ANIMATION_PIVOT, animationPivot);

        final TransactionUpdateFragment fragment = new TransactionUpdateFragment();
        fragment.setArguments(arguments);
        return fragment;
    }
}
