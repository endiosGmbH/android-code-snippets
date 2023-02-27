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
        binding.apply {
            cardviewCssEpcActionPanel.setCardBackgroundColor(Color.parseColor(dashboardConfigsValues?.currentConsumptionLegendColor))

            if (sessionViewModel.selectedMeter?.obisDataList?.get(0)?.priceCapOverview?.isPredictionUnderSubsidizedLimit == false) {
                fragmentCssEpcTextViewActionPanelTitle.text =
                    dashboardConfigsValues?.increasedExpenditurePanelHeader
                fragmentCssEpcTextViewActionPanelSubTitle.text =
                    dashboardConfigsValues?.increasedExpenditurePanelBody
            } else {
                fragmentCssEpcTextViewActionPanelTitle.text =
                    dashboardConfigsValues?.reducedExpenditurePanelHeader
                fragmentCssEpcTextViewActionPanelSubTitle.text =
                    dashboardConfigsValues?.reducedExpenditurePanelBody
            }

            fragmentCssEpcTariffName.text = sessionViewModel.selectedContract?.type
            fragmentCssEpcContractNumber.text = sessionViewModel.selectedContract?.label
            fragmentCssEpcMeterLastReadingDate.text =
                "${getString(R.string.meter_last_reading_date)} ${sessionViewModel.selectedMeter?.latestReadingDateFormatted}"

            getString(
                R.string.meter_last_reading_date,
                sessionViewModel.selectedMeter?.latestReadingDateFormatted
            )

            barGraphicEpcPrognosePercent.apply {
                dashboardConfigsValues?.currentConsumptionLegendColor.takeIf {
                    it.toString().isNotEmpty()
                }?.let { color ->
                    firstBarColor = Color.parseColor(color)
                }
                dashboardConfigsValues?.projectedConsumptionLegendColor.takeIf {
                    it.toString().isNotEmpty()
                }?.let { color ->
                    secondBarColor = Color.parseColor(color)
                }
                dashboardConfigsValues?.subsidizedConsumptionLegendColor.takeIf {
                    it.toString().isNotEmpty()
                }?.let { color ->
                    indicatorTrianglePaintColor = Color.parseColor(color)
                }

                val subsidizedConsumptionLimit =
                    sessionViewModel.selectedMeter?.obisDataList?.get(0)?.priceCapOverview?.subsidizedConsumptionLimit
                val currentConsumption =
                    sessionViewModel.selectedMeter?.obisDataList?.get(0)?.priceCapOverview?.currentConsumption
                val predictedConsumption =
                    sessionViewModel.selectedMeter?.obisDataList?.get(0)?.priceCapOverview?.predictedConsumption
                val baselineConsumption =
                    sessionViewModel.selectedMeter?.obisDataList?.get(0)?.priceCapOverview?.baselineConsumption

                firstBarValue = calculateValue(currentConsumption, baselineConsumption)
                middleBarValue = calculateValue(predictedConsumption, baselineConsumption)
                indicatorValue = calculateValue(subsidizedConsumptionLimit, baselineConsumption)
            }
        }
    }

    private fun calculateValue(value: Float?, baselineConsumption: Float?): Float {
        var calculateValue = 0f
        if (value != null && baselineConsumption != null) {
            calculateValue = baselineConsumption.takeIf { it != null }?.let { value / it } ?: 0f
        }
        return calculateValue
    }
}
