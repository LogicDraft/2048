package com.antigravity.twentyfortyeight.settings;

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
public final class SettingsViewModel_Factory implements Factory<SettingsViewModel> {
  private final Provider<GameRepository> repoProvider;

  public SettingsViewModel_Factory(Provider<GameRepository> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public SettingsViewModel get() {
    return newInstance(repoProvider.get());
  }

  public static SettingsViewModel_Factory create(Provider<GameRepository> repoProvider) {
    return new SettingsViewModel_Factory(repoProvider);
  }

  public static SettingsViewModel newInstance(GameRepository repo) {
    return new SettingsViewModel(repo);
  }
}
