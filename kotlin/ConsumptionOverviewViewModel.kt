package de.endios.endiosone.oneWidgetConsumption.css.ui.consumption

internal class ConsumptionOverviewViewModel(
    app: Application,
    private val consumptionRepository: ConsumptionRepository
) : StatefulViewModel<String>(app) {

    fun updateConsumption(items: List<ConsumptionBodyItem>) {
        viewModelScope.launch {
            val result = consumptionRepository.updateConsumption(items)
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