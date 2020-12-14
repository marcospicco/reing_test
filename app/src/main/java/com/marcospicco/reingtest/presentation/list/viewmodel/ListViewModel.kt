package com.marcospicco.reingtest.presentation.list.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.*
import com.marcospicco.reingtest.core.actions.GetList
import com.marcospicco.reingtest.core.domain.ListItemModel
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.utils.SchedulerProvider

class ListViewModel(
    private val schedulerProvider: SchedulerProvider,
    private val getList: GetList,
    private val platform: String
): ViewModel(), LifecycleObserver {

    val listDataLD = MutableLiveData<MutableList<ListItemModel>>()
    val linkLD = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @SuppressLint("CheckResult")
    fun getList() {
        // showLoading.postValue(PaymentMethodListViewState.ShowLoading())
        getList.doAction(platform)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::renderScreen, this::onError)
    }

    fun renderScreen(result: ListModel) {
        // hideLoading.postValue(PaymentMethodListViewState.HideLoading())
        listDataLD.postValue(result.list)
    }

    fun onError(error: Throwable) {
        // hideLoading.postValue(PaymentMethodListViewState.HideLoading())
        Log.e("Error en Issuer: ", error.toString())
    }

    fun onItemClick(link: String?) {
        link?.let {
            linkLD.postValue(link)
        }
    }
}