package com.test.skb.bus

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxBusSavedUpdate @Inject constructor() {

   private val bus: PublishSubject<Any> = PublishSubject.create()

   fun send(o: Any) {
      bus.onNext(o)
   }

   fun waitCall(): Observable<Any> {
      return bus
   }

}