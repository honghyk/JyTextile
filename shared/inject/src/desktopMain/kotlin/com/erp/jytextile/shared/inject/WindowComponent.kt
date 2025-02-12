package com.erp.jytextile.shared.inject

import com.erp.jytextile.shared.base.inject.ActivityScope
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class WindowComponent(
    @Component val applicationComponent: DesktopApplicationComponent,
) : UiComponent {

    companion object
}
