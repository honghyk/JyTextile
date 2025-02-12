package com.erp.jytextile.shared

import com.erp.jytextile.core.base.inject.ActivityScope
import com.erp.jytextile.core.base.inject.Singleton
import me.tatarka.inject.annotations.Component

@Singleton
@ActivityScope
@Component
abstract class IosApplicationComponent : ApplicationComponent {

    companion object
}
