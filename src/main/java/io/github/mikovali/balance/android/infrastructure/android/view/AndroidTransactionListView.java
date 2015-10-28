package io.github.mikovali.balance.android.infrastructure.android.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionListView;
import io.github.mikovali.balance.android.domain.model.Transaction;
import io.github.mikovali.balance.android.infrastructure.android.App;

public class AndroidTransactionListView extends FrameLayout implements TransactionListView {

    @Inject
    TransactionListPresenter presenter;

    @Bind(android.R.id.content)
    ViewAnimator contentView;
    @Bind(android.R.id.list)
    RecyclerView listView;

    private final TransactionAdapter adapter;

    public AndroidTransactionListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            App.getAppComponent(context).inject(this);
        }
        inflate(context, R.layout.transaction_list, this);
        adapter = new TransactionAdapter();
    }

    @Override
    public void setInProgress(boolean inProgress) {
        contentView.setDisplayedChild(inProgress ? 1 : 0);
    }

    @Override
    public void setTransactions(List<Transaction> transactions) {
        adapter.setTransactions(transactions);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind(this);

        listView.setHasFixedSize(true);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setAdapter(adapter);
        listView.addItemDecoration(new CardViewBottomMarginDecorator());
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            presenter.attachView(this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        presenter.detachView();
        super.onDetachedFromWindow();
    }
}
