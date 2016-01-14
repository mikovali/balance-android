package io.github.mikovali.balance.android.infrastructure.android.view;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.infrastructure.flow.Screen;

public class TransactionUpdateScreenView extends LinearLayout {

    public static final class TransactionUpdateScreen implements Screen {
        @Override
        public View getView(AppCompatActivity activity) {
            return new TransactionUpdateScreenView(activity);
        }
    }

    @Bind(R.id.toolbar)
    Toolbar toolbarView;

    public TransactionUpdateScreenView(AppCompatActivity activity) {
        super(activity);

        inflate(activity, R.layout.transaction_update_screen, this);
        ButterKnife.bind(this);

        setId(R.id.screen);
        setOrientation(VERTICAL);

        final Drawable navigationIcon = DrawableCompat.wrap(ResourcesCompat
                .getDrawable(activity.getResources(), R.drawable.ic_clear, null));
        DrawableCompat.setTint(navigationIcon, Color.WHITE);
        toolbarView.setNavigationIcon(navigationIcon);
        toolbarView.setNavigationContentDescription(R.string.transaction_update_close);
        activity.setSupportActionBar(toolbarView);
    }
}
