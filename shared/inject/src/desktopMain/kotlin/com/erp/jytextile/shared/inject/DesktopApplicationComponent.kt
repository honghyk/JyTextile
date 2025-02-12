package com.erp.jytextile.shared.inject

import com.erp.jytextile.shared.base.inject.Singleton
import me.tatarka.inject.annotations.Component

@Component
@Singleton
abstract class DesktopApplicationComponent
    : ApplicationComponent {

    companion object
}
