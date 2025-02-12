package com.erp.jytextile.shared

import com.erp.jytextile.core.base.inject.ActivityScope
import com.erp.jytextile.feature.root.JyUiViewController
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import platform.UIKit.UIViewController

@ActivityScope
@Component
abstract class HomeUiControllerComponent(
    @Component val applicationComponent: IosApplicationComponent,
) : UiComponent {

    abstract val uiViewControllerFactory: () -> UIViewController

    @Provides
    @ActivityScope
    fun uiViewController(bind: JyUiViewController): UIViewController = bind()

    companion object
}
