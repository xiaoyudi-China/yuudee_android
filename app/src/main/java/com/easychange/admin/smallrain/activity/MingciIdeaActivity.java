package com.easychange.admin.smallrain.activity;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.easychange.admin.smallrain.MyApplication;
import com.easychange.admin.smallrain.R;
import com.easychange.admin.smallrain.base.BaseActivity;
import com.easychange.admin.smallrain.utils.AnimationHelper;
import com.easychange.admin.smallrain.utils.MyUtils;
import com.easychange.admin.smallrain.views.CompletedView;
import com.easychange.admin.smallrain.views.IndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
/**
 * Created by chenlipeng on 2018/11/5 0005
   describe:  名词意义页面
 */
public class MingciIdeaActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.ll_indicator)
    IndicatorView llIndicator;
    @BindView(R.id.iv_home)
    ImageView ivHome;
    @BindView(R.id.rl_top)
    RelativeLayout rlTop;
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.iv_content1)
    ImageView ivContent1;
    @BindView(R.id.iv_content2)
    ImageView ivContent2;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.iv_choose1)
    ImageView ivChoose1;
    @BindView(R.id.tv_choose1)
    TextView tvChoose1;
    @BindView(R.id.iv_choose2)
    ImageView ivChoose2;
    @BindView(R.id.tv_choose2)
    TextView tvChoose2;
    @BindView(R.id.ll_choose1)
    LinearLayout ll_choose1;
    @BindView(R.id.ll_choose2)
    LinearLayout ll_choose2;
    @BindView(R.id.iv_choose3)
    ImageView ivChoose3;
    @BindView(R.id.tv_choose3)
    TextView tvChoose3;
    @BindView(R.id.iv_choose4)
    ImageView ivChoose4;
    @BindView(R.id.tv_choose4)
    TextView tvChoose4;
    @BindView(R.id.ll_choose3)
    LinearLayout ll_choose3;
    @BindView(R.id.ll_choose4)
    LinearLayout ll_choose4;
    @BindView(R.id.tv_paint)
    TextView tvPaint;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.rl_root)
    RelativeLayout rl_root;
    @BindView(R.id.fl_root)
    FrameLayout flRoot;
    @BindView(R.id.tasks_view)
    CompletedView mTasksView;
    @BindView(R.id.ll_money)
    LinearLayout ll_money;
    private int mTotalProgress = 360;
    private int mCurrentProgress = 0;
    private boolean isCorrect;
    private MediaPlayer player;

    private boolean isMediaPlayerCompletion = false;//标识。一开始的语言已经结束了
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {

            isMediaPlayerCompletion = true;
            //进度开启
            new Thread(new ProgressRunable()).start();
        }
    };
    private Handler handler = new Handler();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mingci_test2);
        ButterKnife.bind(this);
        ll_choose1.setOnClickListener(this);
        ll_choose2.setOnClickListener(this);
        ll_choose3.setOnClickListener(this);
        ll_choose4.setOnClickListener(this);
        ivHome.setOnClickListener(this);
        position = getIntent().getIntExtra("position", 0);
        initView();

    }

    private void initView() {
        if (position == 0) {
            ll_money.setVisibility(View.GONE);
        } else if (position == 1) {
            ll_money.setVisibility(View.VISIBLE);
            tv_money.setText("x 1");
            llIndicator.setSelectedPosition(1);
            ivImg.setImageResource(R.drawable.back_hou);
            ivChoose1.setImageResource(R.drawable.hou_bg);
            ivChoose3.setImageResource(R.drawable.black_img);
            tvPaint.setText("黑");
            tvContent.setText("猴");
            tvChoose1.setText("猴");
            tvChoose3.setText("黑");
        } else if (position == 2) {
            ll_money.setVisibility(View.VISIBLE);
            tv_money.setText("x 2");
            llIndicator.setSelectedPosition(2);
            ivImg.setImageResource(R.drawable.huitu);
            ivChoose1.setImageResource(R.drawable.huitu);
            ivChoose3.setImageResource(R.drawable.gray_img);
            tvPaint.setText("灰");
            tvContent.setText("兔");
            tvChoose1.setText("兔");
            tvChoose3.setText("灰");
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                playLocalVoice("男-这是什么样的东西.MP3");
                AnimationHelper.startScaleAnimation(MingciIdeaActivity.this, ivImg);
            }
        }, 2000);

    }

    private void playLocalVoice(String videoName) {
        try {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor afd = assetManager.openFd("boy/" + videoName);
            player = new MediaPlayer();
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            player.setLooping(false);//循环播放
            player.prepare();
            player.start();
            player.setOnCompletionListener(completionListener);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mTasksView.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_home:
                finish();
                break;
            case R.id.ll_choose1:

                if (!isMediaPlayerCompletion) {
                    return;
                }
                if (!isCorrect) {
                    return;
                }
                ll_choose1.setClickable(false);
                AnimationHelper.startScaleAnimation(mContext, ll_choose1);
                if (position == 0) {
                    playLocalVoice("男-鸟.MP3");
                } else if (position == 1) {
                    playLocalVoice("男-猴.MP3");
                } else if (position == 2) {
                    playLocalVoice("男-兔.MP3");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int screenWidth = MyUtils.getScreenWidth(mContext);
                        int screenHeight = MyUtils.getScreenHeight(mContext);
                        Animation scaleAnimation = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.6f);
                        //因为图片透明边距的问题微调
                        int y = screenHeight - MyUtils.dip2px(MyApplication.getGloableContext(), 350 + 30 + 110 + 55 + 20 - 77.5f)
                                - MyUtils.getStatusBarHeight(MyApplication.getGloableContext());
                        int x = MyUtils.dip2px(MyApplication.getGloableContext(), 90 + 20 + 45 + 45);
                        TranslateAnimation translateAnimation = new TranslateAnimation(0, x, 0, -y);
                        AnimationSet setAnimation = new AnimationSet(true);
                        setAnimation.setDuration(2000);
                        setAnimation.addAnimation(scaleAnimation);
                        setAnimation.addAnimation(translateAnimation);
                        setAnimation.setFillAfter(true);
                        ll_choose1.startAnimation(setAnimation);
                        setAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                tvContent.setVisibility(View.VISIBLE);
                                ll_choose1.clearAnimation();
                                ll_choose1.setVisibility(View.INVISIBLE);
                                ivContent2.setVisibility(View.INVISIBLE);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mergeText();
                                    }
                                }, 1000);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 1000);
                break;
            case R.id.ll_choose2:

                break;
            case R.id.ll_choose3:
                if (!isMediaPlayerCompletion) {
                    return;
                }
                isCorrect = true;
                ll_choose3.setClickable(false);
                AnimationHelper.startScaleAnimation(mContext, ll_choose3);
                if (position == 0) {
                    playLocalVoice("男-红.MP3");
                } else if (position == 1) {
                    playLocalVoice("男-黑.MP3");
                } else if (position == 2) {
                    playLocalVoice("男-灰.MP3");
                }
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        int screenWidth = MyUtils.getScreenWidth(mContext);
                        int screenHeight = MyUtils.getScreenHeight(mContext);
                        Animation scaleAnimation = new ScaleAnimation(1.0f, 0.8f, 1.0f, 0.6f);
                        int y = screenHeight - MyUtils.dip2px(MyApplication.getGloableContext(), 350 + 30 + 110 + 55 + 20 - 77.5f)
                                - MyUtils.getStatusBarHeight(MyApplication.getGloableContext());
                        int x = MyUtils.dip2px(MyApplication.getGloableContext(), 45 + 45);
                        //因为图片透明边距的问题微调
                        TranslateAnimation translateAnimation = new TranslateAnimation(0, -x, 0, -y);
                        AnimationSet setAnimation = new AnimationSet(true);
                        setAnimation.setDuration(2000);
                        setAnimation.addAnimation(scaleAnimation);
                        setAnimation.addAnimation(translateAnimation);
                        setAnimation.setFillAfter(true);
                        ll_choose3.startAnimation(setAnimation);
                        setAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                tvPaint.setVisibility(View.VISIBLE);
                                ll_choose3.clearAnimation();
                                ll_choose3.setVisibility(View.INVISIBLE);
                                ivContent1.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 1000);
                break;
            case R.id.ll_choose4:

                break;
        }
    }

    private void mergeText() {
//        另外，儿童选择完正确的2个答案后，其余的2个错误答案要被清除
//                笔消失动画
        AnimationHelper.startPaintGoneAnimation(MingciIdeaActivity.this, ll_choose2);
        AnimationHelper.startPaintGoneAnimation(MingciIdeaActivity.this, ll_choose4);

        if (position == 0) {
            playLocalVoice("男-红鸟.MP3");
        } else if (position == 1) {
            playLocalVoice("男-黑猴.MP3");
        } else if (position == 2) {
            playLocalVoice("男-灰兔.MP3");
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) tvPaint.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        tvPaint.setLayoutParams(layoutParams);
        tvPaint.setBackground(null);

        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) tvContent.getLayoutParams();
        layoutParams2.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams2.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        layoutParams2.leftMargin = 0;
        layoutParams2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        tvContent.setLayoutParams(layoutParams2);
        tvContent.setBackground(null);

        tvPaint.post(new Runnable() {
            @Override
            public void run() {
                rl_root.setBackgroundResource(R.drawable.painttext_bg);
                int i = (MyUtils.dip2px(MingciIdeaActivity.this, 190) - (tvPaint.getWidth() + tvContent.getWidth())) / 2;
                int paintX = MyUtils.dip2px(MingciIdeaActivity.this, 85) - tvPaint.getWidth();
                int contentX = MyUtils.dip2px(MingciIdeaActivity.this, 85 - 20) - tvContent.getWidth();
                TranslateAnimation tr = new TranslateAnimation(-contentX, -i, 0, 0);
                tr.setDuration(1000);
                tr.setFillAfter(true);
                tvContent.startAnimation(tr);

                TranslateAnimation tr2 = new TranslateAnimation(paintX, i, 0, 0);
                tr2.setDuration(1000);
                tr2.setFillAfter(true);
                tvPaint.startAnimation(tr2);
                tr2.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        rl_root.setBackground(null);
                        flRoot.setBackgroundResource(R.drawable.faguang_bg);
                        //发光背景缩小一些
                        RelativeLayout.LayoutParams layoutParams1 = (RelativeLayout.LayoutParams) flRoot.getLayoutParams();
                        layoutParams1.width = MyUtils.dip2px(MyApplication.getGloableContext(), 150);
                        flRoot.setLayoutParams(layoutParams1);
                        //最后放大一下
                        Animation aa = android.view.animation.AnimationUtils.loadAnimation(MingciIdeaActivity.this, R.anim.anim_scale_pic);
                        ivImg.startAnimation(aa);
                        aa.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                if (position == 0) {
                                    Intent intent = new Intent(MingciIdeaActivity.this, MingciIdeaActivity.class);
                                    intent.putExtra("position", 1);
                                    startActivity(intent);
                                } else if (position == 1) {
                                    Intent intent = new Intent(MingciIdeaActivity.this, MingciIdeaActivity.class);
                                    intent.putExtra("position", 2);
                                    startActivity(intent);
                                } else if (position == 2) {
                                    Intent intent = new Intent(MingciIdeaActivity.this, PinTuActivity.class);
                                    startActivity(intent);
                                }
                                finish();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        if (player != null)
            player.stop();
    }
}