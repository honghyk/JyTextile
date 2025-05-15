package com.erp.trillion.shared

import com.erp.trillion.core.base.inject.ActivityScope
import com.erp.trillion.feature.root.TrillionUiViewController
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
    fun uiViewController(bind: TrillionUiViewController): UIViewController = bind()

    companion object
}
