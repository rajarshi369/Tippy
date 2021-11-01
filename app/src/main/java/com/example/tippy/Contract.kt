package com.example.tippy

interface Contract {

    interface View {
        fun setTipAmount(amount: String)
        fun setTotalAmount(amount: String)
    }

    interface Presenter {
        fun calculateTipAmount(amount: Double, tipPercent: Int)
    }
}