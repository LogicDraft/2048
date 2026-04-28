package com.antigravity.twentyfortyeight.di;

import com.antigravity.twentyfortyeight.data.ScoreDao;
import com.antigravity.twentyfortyeight.data.ScoreDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class DiModule_ProvideScoreDaoFactory implements Factory<ScoreDao> {
  private final Provider<ScoreDatabase> databaseProvider;

  public DiModule_ProvideScoreDaoFactory(Provider<ScoreDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public ScoreDao get() {
    return provideScoreDao(databaseProvider.get());
  }

  public static DiModule_ProvideScoreDaoFactory create(Provider<ScoreDatabase> databaseProvider) {
    return new DiModule_ProvideScoreDaoFactory(databaseProvider);
  }

  public static ScoreDao provideScoreDao(ScoreDatabase database) {
    return Preconditions.checkNotNullFromProvides(DiModule.INSTANCE.provideScoreDao(database));
  }
}
