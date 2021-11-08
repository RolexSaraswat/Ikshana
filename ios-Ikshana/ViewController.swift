import UIKit
import WebKit

class ViewController: UIViewController,WKUIDelegate {
    @IBOutlet var webview: WKWebView!

    let reachability = try! Reachability()
 
    override func viewDidLoad() {
           super.viewDidLoad()
        

           let webConfiguration = WKWebViewConfiguration()
           webview = WKWebView(frame: .zero, configuration: webConfiguration)

           // [1] Since we're using auto layout, this ignores the frame and
           // considers only the auto-layout constraints
           webview.translatesAutoresizingMaskIntoConstraints = false

           let myURL = URL(string:"https://ikshana.herokuapp.com/")
           let myRequest = URLRequest(url: myURL!)

           // [2] Adds the webview as a subview of the view
           view.addSubview(webview)
           webview.load(myRequest)

           // [3] Pins the webview to the safe area layout guides.
           // From the documentation: When the view is visible onscreen, this guide
           // reflects the portion of the view that is not covered by navigation bars,
           // tab bars, toolbars, and other ancestor views.
           // https://developer.apple.com/documentation/uikit/uiview/2891102-safearealayoutguide
           NSLayoutConstraint.activate([
               webview.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor),
               webview.bottomAnchor.constraint(equalTo: view.safeAreaLayoutGuide.bottomAnchor),
               webview.leadingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.leadingAnchor),
               webview.trailingAnchor.constraint(equalTo: view.safeAreaLayoutGuide.trailingAnchor),
           ])
       }
    override func viewWillAppear(_ animated: Bool) {
            super.viewWillAppear(animated)
            DispatchQueue.main.async {
                self.reachability.whenReachable = { reachability in
                    if reachability.connection == .wifi {
                        print("Reachable via WiFi")
                    } else {
                        print("Reachable via Cellular")
                    }
                    self.view.window?.rootViewController?.dismiss(animated: true)
                }
                self.reachability.whenUnreachable = { _ in
                    print("Not reachable")
                    if let networkVC = self.storyboard?.instantiateViewController(identifier: "NetworkErrorViewController") as? NetworkErrorViewController{
                        self.present(networkVC, animated: true)
                    }
                }
                
                do {
                    try self.reachability.startNotifier()
                } catch {
                    print("Unable to start notifier")
                }
            }
        }

        deinit {
            reachability.stopNotifier()
        }


}
