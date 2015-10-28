package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.SqliteTransactionRepository;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    // Transaction

    @Provides
    @Singleton
    TransactionRepository provideTransactionRepository() {
        return new SqliteTransactionRepository();
    }

    @Provides
    @Singleton
    TransactionListPresenter provideTransactionListPresenter(
            TransactionRepository transactionRepository) {
        return new TransactionListPresenter(transactionRepository);
    }
}
