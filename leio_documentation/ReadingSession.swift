//
//  ReadingSession.swift
//  Leio
//
//  Created by Daniel M on 10/10/15.
//  Copyright © 2015 Daniel Ballester Marques. All rights reserved.
//

import Foundation
import RealmSwift

public class ReadingSession: Object {

    dynamic var book: Book?
    dynamic var firstPage: Int = 1
    dynamic var lastPage: Int = 1
    dynamic var duration: Double = 0.0
    dynamic var date: NSDate? = nil
    dynamic var key: String = ""

    override public static func primaryKey() -> String? {
        return "key"
    }

}
