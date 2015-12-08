package io.github.mikovali.balance.android.infrastructure.dagger;

import dagger.Component;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(TransactionListPresenter transactionListPresenter);
}
