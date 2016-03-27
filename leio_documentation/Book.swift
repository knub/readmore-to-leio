//
//  Book.swift
//  Leio
//
//  Created by Daniel M on 10/10/15.
//  Copyright © 2015 Daniel Ballester Marques. All rights reserved.
//

import Foundation
import RealmSwift

public class Book: Object {

    // Always change the saveBook() function at the NewBookTableViewController
    // to match changes made here.

    dynamic var title: String = ""
    dynamic var author: String = ""
    dynamic var firstPage: Int = 0
    dynamic var lastPage: Int = 0
    dynamic var key: String = ""

    dynamic var ebook: Bool = false
    dynamic var readingUnit: String = "page"
    dynamic var outOfOrder: Bool = false

    dynamic var archived: Bool = false
    dynamic var date: NSDate? // Archiving date

    dynamic var scheduledDate: NSDate? = nil
    dynamic var scheduledTime: Double = 0.0
    dynamic var scheduledPages: Int = 0
    dynamic var scheduledDays: Double = 0.0
    dynamic var schedulingDate: NSDate? = nil

    dynamic var order: Int = 0

    var readingSessions: [ReadingSession] {
        return linkingObjects(ReadingSession.self, forProperty: "book")
    }

    override public static func primaryKey() -> String? {
        return "key"
    }

}
