package io.unlokk.onboarding.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import org.bson.types.ObjectId
import org.joda.time.DateTime
import java.util.*

/*@RealmClass(embedded = true)*/
open class LoanPaymentInfo(
    @PrimaryKey
    var _id: ObjectId = ObjectId(),
    var endDate: Date = Date(),
    var paidDate: Date = Date(),
    var status: String = "Not paid",
    var _partition: String = ""
): RealmObject() { }