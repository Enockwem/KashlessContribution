package com.example.kashless.momopay

import com.example.kashless.DataClasses.Payer

class Transfer
/**
 * Transfer operation is used to transfer an
 * amount from the owner’s account to a payee account.
 *
 * mobile       String Mobile number to transfer to
 * amount       Amount that will be debited from the payer account.
 * externalId   External id is used as a reference to the transaction.
 * External id is used for reconciliation. The external id will be included in transaction history report.
 * External id is not required to be unique.
 * payeeNote    Message that will be written in the payee transaction history note field.
 * payerMessage Message that will be written in the payer transaction history message field.
 *currency     ISO4217 Currency
 */(mobile: String?, private var amount: String?, private var externalId: String?, private var payeeNote: String?, private var payerMessage: String?, private var currency: String?) {
    private var payee: Payer? = null
    init {
        payee = Payer(mobile!!, "MSISDN")
    }
}