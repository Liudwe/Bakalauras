package io.unlokk.onboarding.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.util.*

open class RealmLoanDetails(
    var fullLoan: Int = 0,
    var loanPaid: Int= 0,
    var date: Date = Date(),
    var nextLoanPayment: Int= 0,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}