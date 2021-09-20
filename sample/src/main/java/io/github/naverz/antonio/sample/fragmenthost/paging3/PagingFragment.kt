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

package io.github.naverz.antonio.sample.fragmenthost.paging3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import io.github.naverz.antonio.databinding.PagingFragmentBinding
import io.github.naverz.antonio.databinding.paging3.AntonioPagingDataAdapter
import io.github.naverz.antonio.sample.antonio.ContainerForSmallContents
import io.github.naverz.antonio.sample.etc.HashDiffItemCallback
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PagingFragment : Fragment() {
    private val viewModel by viewModels<PagingFragmentViewModel>()
    private val adapter =
        AntonioPagingDataAdapter<ContainerForSmallContents>(HashDiffItemCallback())
    private var binding: PagingFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return PagingFragmentBinding.inflate(inflater, container, false)
            .apply { binding = this }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.recyclerView?.adapter = adapter
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.flow.collectLatest(adapter::submitData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.recyclerView?.adapter = null
        binding = null
    }
}