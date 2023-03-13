package de.endios.endiosone.oneWidgetEnergyPriceCap.ui.dashboard

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import de.endios.endiosone.foundation.fragment.viewBinding
import de.endios.endiosone.oneWidgetEnergyPriceCap.R
import de.endios.endiosone.oneWidgetEnergyPriceCap.configs.DashboardConfigsValues
import de.endios.endiosone.oneWidgetEnergyPriceCap.databinding.FragmentEpcDashboardBinding
import de.endios.endiosone.oneWidgetEnergyPriceCap.ui.SessionViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

internal class DashboardFragment : Fragment(R.layout.fragment_epc_dashboard) {
    private val binding by viewBinding(FragmentEpcDashboardBinding::bind)

    private val sessionViewModel: SessionViewModel by sharedViewModel()

    private val dashboardConfigsValues: DashboardConfigsValues? by lazy {
        sessionViewModel.cssConfigValues?.dashboardConfigsValues
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
