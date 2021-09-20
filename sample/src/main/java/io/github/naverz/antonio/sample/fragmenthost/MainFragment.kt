/**
 * Antonio
 * Copyright (c) 2021-present NAVER Z Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.naverz.antonio.sample.fragmenthost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import io.github.naverz.antonio.R
import io.github.naverz.antonio.databinding.MainFragmentBinding
import io.github.naverz.antonio.databinding.setStateWithFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private val viewModel by viewModels<MainFragmentViewModel>()

    private var binding: MainFragmentBinding? = null
    private var mediator: TabLayoutMediator? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return MainFragmentBinding.inflate(inflater, container, false).also { createdBinding ->
            createdBinding.lifecycleOwner = viewLifecycleOwner
            createdBinding.viewModel = this.viewModel
            binding = createdBinding
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.goToSecondFragment.collect {
                findNavController().navigate(R.id.secondFragment)
            }
        }
        setPagerAndTabLayout()
    }

    private fun setPagerAndTabLayout() {
        val viewPager2 = binding?.viewPager2 ?: return
        val tabLayout = binding?.tabLayout ?: return
        viewPager2.setStateWithFragment(viewModel.viewPager2State)
        mediator =
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = viewModel.headTitles[position]
            }.apply {
                attach()
            }
        viewPager2.getChildAt(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediator?.detach()
        binding = null
    }
}