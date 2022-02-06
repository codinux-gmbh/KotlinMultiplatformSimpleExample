//
//  ContentView.swift
//  FaviconFinder
//
//  Created by ganymed on 05.02.22.
//

import SwiftUI
import common

struct ContentView: View {

    @State var url: String = ""

    @State var favicons: [Favicon] = []

    private let faviconFinder = FaviconFinderService()


    var body: some View {
        VStack {
            HStack {
                Text("URL:")
                    .padding()
                TextField("URL from which to extract Favicons from", text: $url)
                    .keyboardType(.webSearch)
                    .disableAutocorrection(true)
                    .autocapitalization(.none)
                    .onChange(of: url) {
                        self.extractFavicons($0)
                    }
            }.padding()

            List(self.favicons, id: \.self) { favicon in
                HStack {
                    Text(favicon.size?.getDisplayText() ?? "")
                        .frame(width: 94, alignment: .leading)
                    Text(favicon.iconType.name)
                        .frame(width: 100, alignment: .leading)

                    RemoteImageView(urlString: favicon.url)
                }.frame(alignment: .leading)
            }
        }
    }

    func extractFavicons(_ url: String) {
        self.faviconFinder.extractFavicons(url: url, completionHandler: { (favicons, error) in
            if let favicons = favicons {
                self.favicons = favicons
            }
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
