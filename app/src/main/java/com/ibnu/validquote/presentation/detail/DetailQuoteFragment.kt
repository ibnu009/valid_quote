package com.ibnu.validquote.presentation.detail

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ibnu.validquote.data.model.Quote
import com.ibnu.validquote.databinding.FragmentDetailQuoteBinding
import com.ibnu.validquote.utils.extension.popTap


class DetailQuoteFragment : Fragment() {

    private var _binding: FragmentDetailQuoteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailQuoteBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val safeArgs = arguments?.let { DetailQuoteFragmentArgs.fromBundle(it) }
        val quote = safeArgs?.quote

        initiateAppBar()

        binding.txvQuote.text = quote?.quote
        binding.txvAuthor.text = "- ${quote?.author}"
        binding.btnShare.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                shareQuote(quote)
            }, 200)
        }

        binding.btnCopy.setOnClickListener {
            it.popTap()
            Handler(Looper.getMainLooper()).postDelayed({
                copyQuote(quote)
            }, 200)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initiateAppBar() {
        binding.appBar.tvToolbarTitle.text = "Valid Quote"
        binding.appBar.imgBack.setOnClickListener {
            it.popTap()
            findNavController().popBackStack()
        }
    }

    private fun copyQuote(quote: Quote?) {
        (requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager).apply {
            setPrimaryClip(
                ClipData.newPlainText("Quote", "\n'${quote?.quote}' \n\nby ${quote?.author}"),
            )
        }
        Toast.makeText(
            context, "Quote Copied :)", Toast.LENGTH_SHORT
        ).show()
    }

    private fun shareQuote(quote: Quote?) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Quote")
        val shareMessage = "Quotes hari ini dari Valid Quote : \n\n'${quote?.quote}' \n" +
                "\nby ${quote?.author}"
        intent.putExtra(Intent.EXTRA_TEXT, shareMessage)
        activity?.startActivity(
            Intent.createChooser(intent, "Bagikan Quote")
        )
    }

}