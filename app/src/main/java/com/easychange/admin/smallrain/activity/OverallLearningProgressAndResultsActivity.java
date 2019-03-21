package com.easychange.admin.smallrain.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.GoToLoginActivityUtils;
import com.easychange.admin.smallrain.utils.LineChartManager;
import com.easychange.admin.smallrain.views.CircleBar;
import com.github.mikephil.charting.charts.LineChart;
import com.qlzx.mylibrary.base.BaseSubscriber;
import com.qlzx.mylibrary.bean.BaseBean;
import com.qlzx.mylibrary.http.HttpHelp;
import com.qlzx.mylibrary.util.PreferencesHelper;
import com.qlzx.mylibrary.util.TimeUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bean.AllTrainRecordBean;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import http.RemoteApi;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenlipeng on 2018/10/18 0018
 * describe:  总体学习进度及效果
 */
public class OverallLearningProgressAndResultsActivity extends BaseActivity {
    @BindView(R.id.cb)
    CircleBar cb;
    @BindView(R.id.linechart1)
    LineChart lineChart1;
    @BindView(R.id.linechart2)
    LineChart linechart2;
    @BindView(R.id.ll_linear1)
    LinearLayout llLinear1;
    @BindView(R.id.ll_linear)
    LinearLayout llLinear;
    @BindView(R.id.tv_time)
    TextView tvTime;
    ArrayList<Float> char1xValueList = new ArrayList<>();
    ArrayList<Float> char2ValueList = new ArrayList<>();
    ArrayList<String> charyValueList = new ArrayList<>();

    ArrayList<Float> xValues1 = new ArrayList<>();
    ArrayList<Float> xValues2 = new ArrayList<>();


//    List<Integer> yList = new ArrayList<>();
//    List<Float> chart1xList = new ArrayList<>();
//    List<Float> chart2xList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overall_learning_progress_and_results);
        ButterKnife.bind(this);

        if (!TextUtils.isEmpty(new PreferencesHelper(this).getToken())) {
            getTrainingFileData(new PreferencesHelper(this).getToken());
        }
    }

    private void setLintChart(ViewGroup viewGroup, List<Float> yValues) {
        for (int i = 0; i < (viewGroup).getChildCount(); i++) {
            if (i == 0) {
                ((TextView) (viewGroup).getChildAt(i)).setText(100 + "");
            } else if (((TextView) (viewGroup).getChildAt(i - 1)).getText().toString() != "") {
                if (viewGroup.getId() == R.id.ll_linear) {
                    ((TextView) (viewGroup).getChildAt(i)).setText(100 / 2 + "");
                } else {
                    ((TextView) (viewGroup).getChildAt(i)).setText(
                            20 * (5 - i) + "");
                }
            } else {
                ((TextView) (viewGroup).getChildAt(i)).setText("");
                ((TextView) (viewGroup).getChildAt(i)).setVisibility(View.GONE);
            }
        }
    }
    private Boolean b =null;
    public void getTrainingFileData(String token) {
        HttpHelp.getInstance().create(RemoteApi.class).getTranningFileMaster(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<BaseBean<AllTrainRecordBean>>(this, null) {
                    @Override
                    public void onNext(BaseBean<AllTrainRecordBean> allTrainRecordBeanBaseBean) {
                        super.onNext(allTrainRecordBeanBaseBean);
                        AllTrainRecordBean allTrainRecordBean = null;
//                        {"msg":"","code":200,"data":{"sumRate":0.0825,"list":[]}}
                        if (allTrainRecordBeanBaseBean.code == 200) {
                            allTrainRecordBean = allTrainRecordBeanBaseBean.data;
                          if (allTrainRecordBean != null) {
                                cb.setRoundProgressColor(Color.parseColor("#24CFFD"));
//                                "schedule":1.00,
                                cb.setProgress(Float.parseFloat(allTrainRecordBean.getSumRate()) * 100.0f);

                                List<AllTrainRecordBean.ListBean> xListBeanList = new ArrayList<>();

//                                sumRate	double	总测试进度
//                                rate_all	double	测试进度
//                                learning_time	int	学习时长
//                                score	double	分数
                                xListBeanList = allTrainRecordBean.getList();
                                for(int i = 0; i < xListBeanList.size(); i++){
                                    if (Float.parseFloat(xListBeanList.get(i).getLearning_time()) > 3600) {
                                        b = true;
                                    }else if (Float.parseFloat(xListBeanList.get(i).getLearning_time()) > 60){
                                        b = false;
                                    }
                                }
                                for (int i = 0; i < xListBeanList.size(); i++) {
//                                    {
//                                        "id": 74,
//                                            "rate_all": 0,
//                                            "learning_time": 33,
//                                            "score": 3
//                                    }
//                                    {"msg":"","code":200,"data":{"sumRate":1.0,"list":[{"id":352,"rate_all":0.25,"learning_time":14,"score":30.00}]}}
                                    char1xValueList.add((float) xListBeanList.get(i).getScore());//学习时长
                                    char2ValueList.add((float) xListBeanList.get(i).getScore());//测试进度
                                    //学习时长是秒，需要做处理
                                    tvTime.setText("学习时长/s");
                                    if (Float.parseFloat(xListBeanList.get(i).getLearning_time()) > 3600) {
                                        tvTime.setText("学习时长/h");
                                    } else if (Float.parseFloat(xListBeanList.get(i).getLearning_time()) > 60) {
                                        tvTime.setText("学习时长/m");
                                    }
                                  if(b == null){
                                      xValues1.add(Float.parseFloat(xListBeanList.get(i).getLearning_time()));
                                  }else if(b){
                                        xValues1.add(TimeUtil.formatTime(Float.parseFloat(xListBeanList.get(i).getLearning_time())));
                                    }else if(!b){
                                        xValues1.add(TimeUtil.formatTime60(Float.parseFloat(xListBeanList.get(i).getLearning_time())));
                                    }

                                    xValues2.add((float) Float.parseFloat(xListBeanList.get(i).getRate_all()) * 100);

//                                    charyValueList.add(i);//分数
//                                    charyValueList.add(new Entry(i, xListBeanList.get(i).getScore()));//分数


//                                    yList.add(xListBeanList.get(i).getScore());
//                                    chart1xList.add(Float.parseFloat(xListBeanList.get(i).getLearning_time()));
//                                    chart2xList.add(Float.parseFloat(xListBeanList.get(i).getRate_all()));
                                }
//                                List<Float> char2Value = new ArrayList();
//                                List<Float> xValues2Value = new ArrayList();
//                                for (int i = 0; i <= 10; i++) {
//                                    char2Value.add(0f);
//                                    xValues2Value.add(i * 10f);
//
//                                }
//                              for (int a = 0; a < char2ValueList.size(); a++) {
//
//                                  char2Value.add(char2ValueList.get(a));
//                                  xValues2Value.add(xValues2.get(a));
//
//                              }
//                                if (xValues2Value.size() > 10) {
//                                    xValues2.clear();
//                                    xValues2.addAll(xValues2Value);
//                                    char2ValueList.clear();
//                                    char2ValueList.addAll(char2Value);
//                                }
                                if (xListBeanList.size() == 0) {

                                    for (int i = 0; i <= 10; i++) {
                                        char2ValueList.add(0f);
                                        xValues2.add(i * 10f);
                                        xValues1.add(i + 0f);
                                        // xValues2.add(0f);
                                        char1xValueList.add(0f);
                                    }
                                }

                                initChart1(xValues1, char1xValueList);
                                initChart2(xValues2, char2ValueList);

                            }
                        } else if (allTrainRecordBeanBaseBean.code == 205 || allTrainRecordBeanBaseBean.code == 209) {
                            GoToLoginActivityUtils.tokenFailureLoginOut(OverallLearningProgressAndResultsActivity.this);
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        super.onError(throwable);
                    }
                });

    }


    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:  //
                finish();
                break;

        }
    }


    private void initChart1(ArrayList<Float> xList, ArrayList<Float> entries) {
        LineChartManager lineChartManager1 = new LineChartManager(lineChart1);
        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#24CFFD"));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");

        if (xList.size() == 0) {
            return;
        }

        //创建单条折线的图表
        lineChartManager1.showLineChart(xList, entries, names.get(0), colors.get(0));
//        lineChartManager1.setYAxis(100, 0, 3);
//        lineChartManager1.setDescription("");
//        lineChartManager1.setXAxis(100, 0, 4);

        lineChartManager1.setYAxis(100, 0, 2);
        lineChartManager1.setDescription("");
        lineChartManager1.setXOverllAxis(Collections.max(xList) == 0 ? 10 : (Collections.max(xList)+1), 0, 11, xList);
        setLintChart(llLinear, entries);

//        List<Float> yValues;
//        //设置y轴的数据()
//        yValues = new ArrayList<>();
//        for (int j = 0; j < model.getDayResultList().size(); j++) {
////            yValues.add((float) (Math.random() * 80));
//            double accuracy = model.getDayResultList().get(j).getAccuracy();
//            yValues.add((float) accuracy);
//        }
//
//        //设置x轴的数据
//        ArrayList<Float> xValues = new ArrayList<>();
//        for (int i = 0; i < yValues.size(); i++) {//10组数据
//            xValues.add((float) i);
//        }
    }

    private void initChart2(ArrayList<Float> xList, ArrayList<Float> entries) {
        LineChartManager lineChartManager1 = new LineChartManager(linechart2);
        //颜色集合
        List<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#cd4744"));

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");
        if (xList.size() == 0) {
            return;
        }
        lineChartManager1.showLineChart(xList, entries, names.get(0), colors.get(0));
        lineChartManager1.setYAxisOnForce(100, 0, 6, true);
        lineChartManager1.setDescription("");
        lineChartManager1.setXOverllAxis(100, 0, 11, xList);
        setLintChart(llLinear1, entries);
    }
}