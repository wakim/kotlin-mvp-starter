package com.app.id.dagger;

import com.app.id.dagger.modules.ActivityModule;

import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {ActivityModule.class})
public interface ActivityComponent {
}
