package com.erp.jytextile.shared

import com.erp.jytextile.core.base.inject.Singleton
import com.erp.jytextile.shared.ApplicationComponent
import me.tatarka.inject.annotations.Component

@Component
@Singleton
abstract class DesktopApplicationComponent
    : ApplicationComponent {

    companion object
}
