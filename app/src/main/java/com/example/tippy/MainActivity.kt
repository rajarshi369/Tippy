package com.example.tippy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import com.example.tippy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), Contract.View {
    private lateinit var binding: ActivityMainBinding
    private lateinit var amount: EditText
    private lateinit var tipPercentLabel: TextView
    private lateinit var tipAmount: TextView
    private lateinit var totalAmount: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var presenter: Presenter
    private lateinit var tipPercentReview: TextView
    private var _progress: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tipPercentLabel = binding.tvTipPercentLabel
        tipAmount = binding.tvTipAmount
        totalAmount = binding.tvTotalAmount
        seekBar = binding.seekBar
        amount = binding.etAmount
        tipPercentReview = binding.tvTipPercentReview
        tipPercentLabel.text = _progress.toString().plus("%")
        presenter = Presenter(this)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                _progress = progress
                tipPercentLabel.text = progress.toString().plus("%")
                getBillAmount().also {
                    if (it.isNotEmpty()) {
                        presenter.calculateTipAmount(
                            it.toDouble(),
                            progress
                        )
                    }
                }
                binding.tvTipPercentReview.text = when (progress) {
                    in 0..5 -> "Poor"
                    in 6..15 -> "Good"
                    in 16..25 -> "Best"
                    else -> "Amazing"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        amount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                getBillAmount().also {
                    if (it.isEmpty()) {
                        setTipAmount("")
                        setTotalAmount("")
                    } else {
                        presenter.calculateTipAmount(
                            it.toDouble(),
                            _progress
                        )
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) = Unit
        })
    }

    private fun getBillAmount(): String {
        return amount.text.toString()
    }

    override fun setTipAmount(amount: String) {
        if (amount.isNotEmpty())
            tipAmount.text = "%.3f".format(amount.toDouble())
        else
            tipAmount.text = ""
    }

    override fun setTotalAmount(amount: String) {
        if (amount.isNotEmpty())
            totalAmount.text = "%.3f".format(amount.toDouble())
        else
            totalAmount.text = ""
    }
}
