package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.domain.model.TransactionRepository;
import io.github.mikovali.balance.android.infrastructure.android.content.AppDatabase;
import io.github.mikovali.balance.android.infrastructure.android.content.SqliteTransactionRepository;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    // Application

    @Provides
    @Singleton
    BriteDatabase provideAppDatabase() {
        return SqlBrite.create()
                .wrapDatabaseHelper(new AppDatabase(application));
    }

    // Transaction

    @Provides
    @Singleton
    TransactionRepository provideTransactionRepository(BriteDatabase appDatabase) {
        return new SqliteTransactionRepository(appDatabase);
    }

    @Provides
    TransactionListPresenter provideTransactionListPresenter(
            TransactionRepository transactionRepository) {
        return new TransactionListPresenter(transactionRepository);
    }
}
