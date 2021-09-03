@file:Suppress("unused", "NOTHING_TO_INLINE")

package com.dylanc.longan

import androidx.annotation.MainThread
import androidx.collection.arraySetOf
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * @author Dylan Cai
 */

inline fun <T> MutableLiveData<T>.toLiveData(): LiveData<T> = UnmodifiableLiveData(this)

inline fun EventLiveData() = EventLiveData<Unit>()

inline fun EventLiveData<Unit>.post() = postValue(Unit)

class UnmodifiableLiveData<T>(private val liveData: MutableLiveData<T>) : LiveData<T>() {

  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) =
    liveData.observe(owner, observer)

  override fun observeForever(observer: Observer<in T>) = liveData.observeForever(observer)

  override fun hasActiveObservers() = liveData.hasActiveObservers()

  override fun hasObservers() = liveData.hasObservers()

  override fun removeObserver(observer: Observer<in T>) = liveData.removeObserver(observer)

  override fun removeObservers(owner: LifecycleOwner) = liveData.removeObservers(owner)

  override fun getValue() = liveData.value
}

class EventLiveData<T> : WrapObserverLiveData<T, EventLiveData.ObserverWrapper<T>>() {

  override fun onCreateObserverWrapper(observer: Observer<in T>) = ObserverWrapper(observer)

  @MainThread
  override fun setValue(t: T?) {
    observers.forEach { it.pending = true }
    super.setValue(t)
  }

  override fun postValue(value: T?) {
    mainThread { setValue(value) }
  }

  class ObserverWrapper<T>(
    observer: Observer<in T>
  ) : WrapObserverLiveData.ObserverWrapper<T>(observer) {
    var pending = false

    override fun onChanged(t: T) {
      if (pending) {
        observer.onChanged(t)
        pending = false
      }
    }
  }
}

class ClearableLiveData<T> : WrapObserverLiveData<T, ClearableLiveData.ObserverWrapper<T>>() {

  override fun onCreateObserverWrapper(observer: Observer<in T>) = ObserverWrapper(observer)

  @MainThread
  fun clear() {
    observers.forEach { it.cleared = true }
    super.setValue(null)
  }

  class ObserverWrapper<T>(
    observer: Observer<in T>
  ) : WrapObserverLiveData.ObserverWrapper<T>(observer) {
    var cleared = false

    override fun onChanged(t: T?) {
      if (cleared && t == null) {
        cleared = false
      } else {
        observer.onChanged(t)
      }
    }
  }
}

abstract class WrapObserverLiveData<T, O : WrapObserverLiveData.ObserverWrapper<T>> : MutableLiveData<T>() {

  protected val observers = arraySetOf<O>()

  @MainThread
  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    super.observe(owner, onCreateObserverWrapper(observer).also { observers.add(it) })
  }

  @MainThread
  override fun observeForever(observer: Observer<in T>) {
    super.observeForever(onCreateObserverWrapper(observer).also { observers.add(it) })
  }

  @MainThread
  override fun removeObserver(observer: Observer<in T>) {
    observers.removeAll {
      if (it.observer == observer) super.removeObserver(observer)
      it.observer == observer
    }
  }

  abstract fun onCreateObserverWrapper(observer: Observer<in T>): O

  abstract class ObserverWrapper<T>(val observer: Observer<in T>) : Observer<T>
}