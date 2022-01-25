/*
 *   Copyright 2022 Benoit LETONDOR
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.benoitletondor.pixelminimalwatchfacecompanion.view.debugphonebatterysync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.benoitletondor.pixelminimalwatchfacecompanion.device.Device
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DebugPhoneBatterySyncViewModel @Inject constructor(
    device: Device,
) : ViewModel() {
    private val isBatteryOptimizationOffMutableFlow = MutableStateFlow(device.isBatteryOptimizationOff())
    private val isForegroundServiceOnMutableFlow = MutableStateFlow(false) // TODO

    val stateFlow = combine(
        isBatteryOptimizationOffMutableFlow,
        isForegroundServiceOnMutableFlow,
        ::buildState
    ).stateIn(viewModelScope, SharingStarted.Eagerly, buildState(
        isBatteryOptimizationOff = isBatteryOptimizationOffMutableFlow.value,
        isForegroundServiceOn = isForegroundServiceOnMutableFlow.value,
    ))

    data class State(
        val isBatteryOptimizationOff: Boolean,
        val isForegroundServiceOn: Boolean,
    )

    companion object {
        private fun buildState(
            isBatteryOptimizationOff: Boolean,
            isForegroundServiceOn: Boolean,
        ): State {
            return State(
                isBatteryOptimizationOff = isBatteryOptimizationOff,
                isForegroundServiceOn = isForegroundServiceOn,
            )
        }
    }
}