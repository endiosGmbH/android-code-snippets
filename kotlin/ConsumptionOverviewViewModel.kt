package de.endios.endiosone.oneWidgetConsumption.css.ui.consumption

internal class ConsumptionOverviewViewModel(
    app: Application,
    private val consumptionRepository: ConsumptionRepository
) : StatefulViewModel<String>(app) {

    fun updateConsumption(items: List<ConsumptionBodyItem>) {
        viewModelScope.launch {
            val result = consumptionRepository.updateConsumption(items)
            // No need to make these variables `var`. We can assign them directly and make them non-mutable.
            // Code is copy pasted as well and can be simplified with extension function or direct function if `obisDataNight`/`obisDataDay`- model (Likely same class used for both)
            // fun ObisData?.getConsumptionInt() = this?.previousYear?.consumptionInt.takeIf { it.toString().isNotEmpty() } ?: 0
            // val obisDataNightConsumptionInt = result?.obisDataNight.getConsumptionInt()
            // val obisDataDayConsumptionInt = result?.obisDataDay.getConsumptionInt()
            var obisDataNightConsumptionInt = 0
            var obisDataDayConsumptionInt = 0

            result?.obisDataNight?.previousYear?.consumptionInt.takeIf {
                it.toString().isNotEmpty()
            }?.let { value ->
                obisDataNightConsumptionInt = value
            }

            result?.obisDataDay?.previousYear?.consumptionInt.takeIf { it.toString().isNotEmpty() }
                ?.let { value ->
                    obisDataDayConsumptionInt = value
                }

            val consumptionText = app.getString(
                R.string.consumption_css_day_night_consumption_format,
                obisDataNightConsumptionInt,
                obisDataDayConsumptionInt
            )
            localState.postValue(State.Success(consumptionText))
        }
    }
}