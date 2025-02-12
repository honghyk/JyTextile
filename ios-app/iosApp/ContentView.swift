import UIKit
import SwiftUI
import JyTextileERP

struct ContentView: View {
    private let component: HomeUiControllerComponent

    init(component: HomeUiControllerComponent) {
        self.component = component
    }

    var body: some View {
        ComposeView(component: self.component)
            .ignoresSafeArea(.keyboard)
    }
}

struct ComposeView: UIViewControllerRepresentable {
    private let component: HomeUiControllerComponent

    init(component: HomeUiControllerComponent) {
        self.component = component
    }

    func makeUIViewController(context _: Context) -> UIViewController {
        return component.uiViewControllerFactory()
    }

    func updateUIViewController(_: UIViewController, context _: Context) {}
}
