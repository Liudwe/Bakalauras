package io.unlokk.onboarding.entities

import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.unlokk.onboarding.data.LoanPaymentInfo2
import org.bson.types.ObjectId
import java.util.*


open class RealmLoanDetails4(
    var fullLoan: Double = 0.00,
    var loanTaken: Double = 0.00,
    var loanPaid: Double = 0.00,
    var date: Date = Date(),
    var term: Int = 0,
    var endDateList: RealmList<LoanPaymentInfo2> = RealmList(),
    var status: String = "Not approved",
    var nextLoanPayment: Double = 0.00,
    var _partition: String = ""
): RealmObject() {
    @PrimaryKey var _id: ObjectId = ObjectId()
}


