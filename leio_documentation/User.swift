//
//  User.swift
//  Leio
//
//  Created by Daniel M on 10/10/15.
//  Copyright © 2015 Daniel Ballester Marques. All rights reserved.
//

import Foundation
import RealmSwift

class User: Object {

    dynamic var selectedBook: Book?

    dynamic var countAllArchived: Bool = true
    dynamic var autoStart: Bool = true
    dynamic var bookDetail: String = "timeToGo"
    dynamic var weeklyChart: String = "timeRead"

}
