package com.erp.trillion.shared

import com.erp.trillion.core.base.inject.ActivityScope
import me.tatarka.inject.annotations.Component

@ActivityScope
@Component
abstract class WindowComponent(
    @Component val applicationComponent: DesktopApplicationComponent,
) : UiComponent {

    companion object
}
