package io.unlokk.onboarding.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.unlokk.onboarding.data.LoanPaymentInfo
import org.bson.types.ObjectId
import java.util.*

open class RealmLoanDetails3(
    var fullLoan: Double = 0.00,
    var loanTaken: Double = 0.00,
    var loanPaid: Double = 0.00,
    var date: Date = Date(),
    var term: Int = 0,
    var endDateList: RealmList<LoanPaymentInfo> = RealmList(),
    var status: String = "Not approved",
    var nextLoanPayment: Double = 0.00,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}

/*open class RealmLoanDetails3(
    var fullLoan: Double = 0.00,
    var loanTaken: Double = 0.00,
    var loanPaid: Double = 0.00,
    var date: Date = Date(),
    var term: Int = 0,
    var status: String = "Not approved",
    var nextLoanPayment: Double = 0.00,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}*/

/*
open class RealmLoanDetails2(
    var fullLoan: Double = 0.00,
    var loanTaken: Double = 0.00,
    var loanPaid: Double= 0.00,
    var date: Date = Date(),
    //var startDate: Date = Date(), tas pats kas date
    var endDate: Date = Date(),
    var term: Int = 0,
    //var endDateArray: RealmList<LoanPaymentInfo>,
    var status: String = "not approved",
    var nextLoanPayment: Double= 0.00,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}
*/

