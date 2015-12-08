package io.github.mikovali.balance.android.infrastructure.android;

import javax.inject.Singleton;

import dagger.Component;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(TransactionListPresenter transactionListPresenter);
}
