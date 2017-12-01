package mrdlj.demo.com.appdemo.mvp.other;

import mrdlj.demo.com.appdemo.retrofit.ApiClient;
import mrdlj.demo.com.appdemo.retrofit.ApiStores;
import mrdlj.demo.com.appdemo.utils.RxBus;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * MVP presenter
 */
public abstract class BasePresenter<V> {
    public V mvpView;
    protected ApiStores apiStores;
    private CompositeSubscription mCompositeSubscription;

    public BasePresenter(V view) {
        attachView(view);
    }

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
        subjectEvents();
        apiStores = ApiClient.retrofit().create(ApiStores.class);
    }


    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    /**
     * 相当于EventBus事件处理
     */
    protected void subjectEvents() {
//        addSubscription(RxBus.getDefault().toObservable()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .doOnNext(new Action1<Object>() {
//                            @Override
//                            public void call(Object o) {
//                                if (o.equals(RxContants.RX_RED_POINT)) {
//                                    loadFileDatas(false);
//                                }
//                            }
//                        })
//                , RxBus.defaultSubscriber());
    }
}
