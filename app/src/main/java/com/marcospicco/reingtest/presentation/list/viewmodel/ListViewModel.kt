package com.marcospicco.reingtest.presentation.list.viewmodel

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.lifecycle.*
import com.marcospicco.reingtest.core.actions.DeleteItem
import com.marcospicco.reingtest.core.actions.GetList
import com.marcospicco.reingtest.core.actions.GetLocalList
import com.marcospicco.reingtest.core.domain.ListItemModel
import com.marcospicco.reingtest.core.domain.ListModel
import com.marcospicco.reingtest.core.utils.SchedulerProvider

class ListViewModel(
    private val sharedPreferences: SharedPreferences,
    private val schedulerProvider: SchedulerProvider,
    private val getList: GetList,
    private val getLocalList: GetLocalList,
    private val deleteItem: DeleteItem,
    private val platform: String
): ViewModel(), LifecycleObserver {

    val listDataLD = MutableLiveData<MutableList<ListItemModel>>()
    val linkLD = MutableLiveData<String>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    @SuppressLint("CheckResult")
    fun getList() {

        // podria ir un loading o spinner mientras se hace la pegada

        getList.doAction(sharedPreferences, platform)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
            .subscribe(this::renderScreen, this::onError)
    }

    fun renderScreen(result: ListModel) {

        // se quitaria loading

        listDataLD.postValue(result.list)
    }

    fun onError(error: Throwable) {

        // se quitaria loading

        listDataLD.postValue(getLocalList.doAction(sharedPreferences).list)
    }

    fun deleteItem(id: String) {
        deleteItem.doAction(sharedPreferences, id)
    }

    fun onItemClick(link: String?) {
        link?.let {
            linkLD.postValue(link)
        }
    }
}