import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        DIKt.configure()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
