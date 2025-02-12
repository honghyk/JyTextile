package com.erp.jytextile.shared

import com.erp.jytextile.core.base.inject.ActivityScope
import com.erp.jytextile.shared.DesktopApplicationComponent
import com.erp.jytextile.shared.UiComponent
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class WindowComponent(
    @Component val applicationComponent: DesktopApplicationComponent,
) : UiComponent {

    companion object
}
