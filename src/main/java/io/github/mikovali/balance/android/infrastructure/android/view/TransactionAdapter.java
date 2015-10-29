package io.github.mikovali.balance.android.infrastructure.android.view;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.domain.model.Transaction;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {

    @IntDef({EMPTY_STATE_LOADING, EMPTY_STATE_EMPTY})
    public @interface EmptyState {}

    public static final int EMPTY_STATE_LOADING = 0;
    public static final int EMPTY_STATE_EMPTY = 1;

    private EmptyViewLayoutManager layoutManager;

    private int emptyState = EMPTY_STATE_LOADING;

    private List<Transaction> transactions;

    public void setEmptyState(@EmptyState int emptyState) {
        this.emptyState = emptyState;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
        setLayoutManagerIsEmpty();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case android.R.id.progress:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.progress_content, parent, false));
            case android.R.id.empty:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.empty_text, parent, false));
            default:
                return new ViewHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.transaction_list_item, parent, false));
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void onBindViewHolder(ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case android.R.id.progress:
                return;
            case android.R.id.empty:
                holder.emptyText.setText(R.string.transaction_list_empty_text);
                return;
            default:
                final Transaction transaction = transactions.get(position);
                // TODO Formatter
                holder.amount.setText(String.format("%d", transaction.getAmount()));
        }
    }

    @Override
    public int getItemCount() {
        final int size = CollectionUtils.size(transactions);
        return size == 0 ? 1 : size;
    }

    @Override
    public int getItemViewType(int position) {
        if (CollectionUtils.isNotEmpty(transactions)) {
            return super.getItemViewType(position);
        }
        switch (emptyState) {
            case EMPTY_STATE_LOADING:
                return android.R.id.progress;
            case EMPTY_STATE_EMPTY:
                return android.R.id.empty;
            default:
                throw new IllegalArgumentException(String.format("%d is not a valid emptyState",
                        emptyState));
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        if (recyclerView.getLayoutManager() instanceof EmptyViewLayoutManager) {
            layoutManager = (EmptyViewLayoutManager) recyclerView.getLayoutManager();
        }
        setLayoutManagerIsEmpty();
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        layoutManager = null;
        super.onDetachedFromRecyclerView(recyclerView);
    }

    private void setLayoutManagerIsEmpty() {
        if (layoutManager != null) {
            layoutManager.setIsEmptyView(CollectionUtils.isEmpty(transactions));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(android.R.id.empty)
        @Nullable
        TextView emptyText;

        @Bind(R.id.transactionAmount)
        @Nullable
        TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
