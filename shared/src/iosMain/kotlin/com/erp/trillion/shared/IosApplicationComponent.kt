package com.erp.trillion.shared

import com.erp.trillion.core.base.inject.ActivityScope
import com.erp.trillion.core.base.inject.Singleton
import me.tatarka.inject.annotations.Component

@Singleton
@ActivityScope
@Component
abstract class IosApplicationComponent : ApplicationComponent {

    companion object
}
