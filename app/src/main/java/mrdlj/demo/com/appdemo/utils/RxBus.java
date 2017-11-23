package mrdlj.demo.com.appdemo.utils;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class RxBus {
    private static final String TAG = "RxBus";

    private static volatile RxBus defaultInstance;

    private final Subject<Object, Object> bus;

    /**
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     */
    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    /**
     * 单例RxBus
     *
     * @return
     */
    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    /**
     * 发送一个新的事件
     */
    public void post(Object o) {
        bus.onNext(o);
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
//      ofType = filter + cast
//        return bus.filter(new Func1<Object, Boolean>() {
//            @Override
//            public Boolean call(Object o) {
//                return eventType.isInstance(o);
//            }
//        }) .cast(eventType);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    /**
     * A simple logger for RxBus which can also prevent
     * potential crash(OnErrorNotImplementedException) caused by error in the workflow.
     */
    public static Subscriber<Object> defaultSubscriber() {
        return new Subscriber<Object>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "Duty off!!!");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "What is this? Please solve this as soon as possible!", e);
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "New event received: " + o);
            }
        };
    }
}