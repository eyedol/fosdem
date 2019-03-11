/*
 * MIT License
 *
 * Copyright (c) 2017 - 2018 Henry Addo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.addhen.fosdem.sessions.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.annotation.NonNull
import androidx.fragment.app.Fragment
import com.addhen.fosdem.base.view.BaseFragment
import com.addhen.fosdem.sessions.R
import com.addhen.fosdem.sessions.databinding.SessionFilterFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior


class SessionFilterFragment : BaseFragment<SessionFilterViewModel, SessionFilterFragmentBinding>(
    clazz = SessionFilterViewModel::class.java
) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SessionFilterFragmentBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    private val bottomSheetBehavior: BottomSheetBehavior<*>
        get() = BottomSheetBehavior.from(binding.sessionsSheet)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        setupSessionBottomSheetDialogFragment()
        setupBottomSheetBehavior()
        lifecycle.addObserver(viewModel)
    }

    private fun setupSessionBottomSheetDialogFragment() {
        val fragment: Fragment = SessionBottomSheetDialogFragment.newInstance()
        childFragmentManager
            .beginTransaction()
            .replace(R.id.sessions_sheet, fragment, TAG)
            .disallowAddToBackStack()
            .commit()
    }

    private fun setupBottomSheetBehavior() {
        bottomSheetBehavior.isHideable = false
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        binding.sessionsSheet.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    binding.sessionsSheet.viewTreeObserver.removeOnPreDrawListener(this)
                    if (isDetached) return false
                    bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                    return false
                }
            }
        )
        bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                // Propagate new state change
            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                // Do nothing
            }
        })
    }


    companion object {

        const val TAG: String = "SessionFilterFragment"

        fun newInstance(args: SessionFilterFragmentArgs): SessionFilterFragment {
            return SessionFilterFragment().apply {
                arguments = args.toBundle()
            }
        }
    }
}
