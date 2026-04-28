package com.antigravity.twentyfortyeight.di;

import com.antigravity.twentyfortyeight.data.GameRepository;
import com.antigravity.twentyfortyeight.data.PreferencesDataStore;
import com.antigravity.twentyfortyeight.data.ScoreDao;
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
public final class DiModule_ProvideGameRepositoryFactory implements Factory<GameRepository> {
  private final Provider<PreferencesDataStore> preferencesDataStoreProvider;

  private final Provider<ScoreDao> scoreDaoProvider;

  public DiModule_ProvideGameRepositoryFactory(
      Provider<PreferencesDataStore> preferencesDataStoreProvider,
      Provider<ScoreDao> scoreDaoProvider) {
    this.preferencesDataStoreProvider = preferencesDataStoreProvider;
    this.scoreDaoProvider = scoreDaoProvider;
  }

  @Override
  public GameRepository get() {
    return provideGameRepository(preferencesDataStoreProvider.get(), scoreDaoProvider.get());
  }

  public static DiModule_ProvideGameRepositoryFactory create(
      Provider<PreferencesDataStore> preferencesDataStoreProvider,
      Provider<ScoreDao> scoreDaoProvider) {
    return new DiModule_ProvideGameRepositoryFactory(preferencesDataStoreProvider, scoreDaoProvider);
  }

  public static GameRepository provideGameRepository(PreferencesDataStore preferencesDataStore,
      ScoreDao scoreDao) {
    return Preconditions.checkNotNullFromProvides(DiModule.INSTANCE.provideGameRepository(preferencesDataStore, scoreDao));
  }
}
