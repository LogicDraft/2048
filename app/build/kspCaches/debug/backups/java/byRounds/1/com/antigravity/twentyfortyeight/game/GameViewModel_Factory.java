package com.antigravity.twentyfortyeight.game;

import android.content.Context;
import com.antigravity.twentyfortyeight.data.GameRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava",
    "cast",
    "deprecation"
})
public final class GameViewModel_Factory implements Factory<GameViewModel> {
  private final Provider<GameRepository> repoProvider;

  private final Provider<Context> appContextProvider;

  public GameViewModel_Factory(Provider<GameRepository> repoProvider,
      Provider<Context> appContextProvider) {
    this.repoProvider = repoProvider;
    this.appContextProvider = appContextProvider;
  }

  @Override
  public GameViewModel get() {
    return newInstance(repoProvider.get(), appContextProvider.get());
  }

  public static GameViewModel_Factory create(Provider<GameRepository> repoProvider,
      Provider<Context> appContextProvider) {
    return new GameViewModel_Factory(repoProvider, appContextProvider);
  }

  public static GameViewModel newInstance(GameRepository repo, Context appContext) {
    return new GameViewModel(repo, appContext);
  }
}
