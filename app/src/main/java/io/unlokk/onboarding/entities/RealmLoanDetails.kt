package io.unlokk.onboarding.entities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import org.bson.types.ObjectId
import java.time.LocalDate
import java.util.*


/*open class RealmLoanDetails(
    var fullLoan: Double = 0.00,
    var loanTaken: Double = 0.00,
    var loanPaid: Double= 0.00,
    var date: Date = Date(),
    //var startDate: Date = Date(), tas pats kas date
    var endDate: Date = Date(),
    var term: Int = 0,
    //var endDateArray: MutableList<LoanPaymentInfo>,
    var status: String = "not approved",
    var nextLoanPayment: Double= 0.00,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}*/

open class RealmLoanDetails(
    var fullLoan: Int = 0,
    var loanPaid: Int= 0,
    var date: Date = Date(),
    //var startDate:
    //var endDate:
    //var term:
    //var status:
    var nextLoanPayment: Int= 0,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}