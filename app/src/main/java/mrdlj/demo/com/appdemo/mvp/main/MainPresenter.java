package mrdlj.demo.com.appdemo.mvp.main;


import mrdlj.demo.com.appdemo.mvp.other.BasePresenter;
import mrdlj.demo.com.appdemo.retrofit.ApiCallback;
import mrdlj.demo.com.appdemo.utils.LogUtil;

/**
 * MVP presenter 请求接口
 */
public class MainPresenter extends BasePresenter<MainView> {

    public MainPresenter(MainView view) {
        super(view);
    }

    public void loadDataByRetrofitRxjava(String cityId) {
        LogUtil.d("onActivityResult loadDataByRetrofitRxjava");
        mvpView.showLoading();
        addSubscription(apiStores.loadDataByRetrofitRxjava(cityId),
                new ApiCallback<MainModel>() {
                    @Override
                    public void onSuccess(MainModel model) {
                        mvpView.getDataSuccess(model);
                    }

                    @Override
                    public void onFailure(String msg) {
                        mvpView.getDataFail(msg);
                    }


                    @Override
                    public void onFinish() {
                        mvpView.hideLoading();
                    }
                });
    }

}
