import SwiftUI
import JyTextileERP

class AppDelegate: UIResponder, UIApplicationDelegate, UNUserNotificationCenterDelegate {
    
    lazy var applicationComponent: IosApplicationComponent = createApplicationComponent(
        appDelegate: self
    )
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    var body: some Scene {
            WindowGroup {
                let uiComponent = createHomeUiControllerComponent(
                    applicationComponent: delegate.applicationComponent
                )
                ContentView(component: uiComponent)
            }
        }
}

private func createApplicationComponent(
    appDelegate: AppDelegate
) -> IosApplicationComponent {
    return IosApplicationComponent.companion.create()
}

private func createHomeUiControllerComponent(
    applicationComponent: IosApplicationComponent
) -> HomeUiControllerComponent {
    return HomeUiControllerComponent.companion.create(
        applicationComponent: applicationComponent
    )
}
