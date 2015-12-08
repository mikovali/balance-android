package io.github.mikovali.balance.android.infrastructure.dagger;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Application-wide scope.
 */
@Scope
@Documented
@Retention(RUNTIME)
public @interface AppScope {}
