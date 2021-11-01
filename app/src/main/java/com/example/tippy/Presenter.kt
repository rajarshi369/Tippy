package com.example.tippy

class Presenter(private var view: Contract.View?) : Contract.Presenter {
    override fun calculateTipAmount(amount: Double, tipPercent: Int) {
        val tip = (amount * tipPercent) / 100.0
        val totalAmount = amount + tip
        view?.setTipAmount(tip.toString())
        view?.setTotalAmount(totalAmount.toString())
    }
}