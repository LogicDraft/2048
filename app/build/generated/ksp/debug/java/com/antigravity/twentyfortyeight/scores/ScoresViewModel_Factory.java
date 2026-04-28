package com.antigravity.twentyfortyeight.scores;

import com.antigravity.twentyfortyeight.data.GameRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class ScoresViewModel_Factory implements Factory<ScoresViewModel> {
  private final Provider<GameRepository> repoProvider;

  public ScoresViewModel_Factory(Provider<GameRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public ScoresViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static ScoresViewModel_Factory create(Provider<GameRepository> repoProvider) {
    return new ScoresViewModel_Factory(repoProvider);
  }

  public static ScoresViewModel newInstance(GameRepository repo) {
    return new ScoresViewModel(repo);
  }
}
