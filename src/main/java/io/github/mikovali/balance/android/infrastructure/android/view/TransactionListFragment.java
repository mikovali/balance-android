package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.App;
import rx.Observable;
import timber.log.Timber;

public class TransactionListFragment extends Fragment {

    @Inject
    TransactionRepository transactionRepository;

    @Bind(R.id.toolbar)
    Toolbar toolbarView;
    @Bind(R.id.transactionCreateButton)
    FloatingActionButton createButton;

    @OnClick(R.id.transactionCreateButton)
    @SuppressWarnings("unused")
    public void onCreateButtonClick() {
        Timber.e("onCreateButtonClick");
        // TODO create Transaction
        final Transaction transaction = new Transaction(UUID.randomUUID(), ((int) (Math.random() * 1000)));
        transactionRepository.insert(transaction).subscribe();
    }

    // lifecycle

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        App.getAppComponent(context).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.transaction_list_fragment, container, false);
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
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarView);
        final Drawable createButtonDrawable = DrawableCompat.wrap(createButton.getDrawable());
        DrawableCompat.setTint(createButtonDrawable, Color.WHITE);
    }

    // factories

    public static TransactionListFragment newInstance() {
        return new TransactionListFragment();
    }
}
