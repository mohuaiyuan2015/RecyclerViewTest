package com.example.mohuaiyuan.recyclerviewtest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;

public class AutoPollRecyclerView extends RecyclerView{
    private static final long TIME_AUTO_POLL = 1600;
    AutoPollTask autoPollTask;
    private boolean running; //标示是否正在自动轮询
    private boolean canRun;//标示是否可以自动轮询,可在不需要的是否置false
    private static int currentPosition;
    private static boolean turnToFirstPosition;

    public AutoPollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        autoPollTask = new AutoPollTask(this);
    }
    static class AutoPollTask implements Runnable {
        private final WeakReference<AutoPollRecyclerView> mReference;
        //使用弱引用持有外部类引用->防止内存泄漏
        public AutoPollTask(AutoPollRecyclerView reference) {
            this.mReference = new WeakReference<AutoPollRecyclerView>(reference);
        }
        @Override
        public void run() {
            Log.d("yfing.wei", "run()----------------------------------- ");
            AutoPollRecyclerView recyclerView = mReference.get();

            boolean toEndPosition=false;
            int allCount=0;
            if (!turnToFirstPosition){
                currentPosition=((GridLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                Log.d("yfing.wei", "currentPosition: "+currentPosition);
                currentPosition+=3;

                allCount=recyclerView.getAdapter().getItemCount();
                Log.d("yfing.wei", "allCount: "+allCount);



                if (currentPosition>allCount){
                    toEndPosition=true;
                }

            }

//            boolean toFirstPosition=false;
//            toFirstPosition = currentPosition == allCount ;
//            Log.d("yfing.wei", "toFirstPosition: "+toFirstPosition);

            if (recyclerView != null && recyclerView.running &&recyclerView.canRun) {
//                recyclerView.scrollBy(2, 6);
                if (toEndPosition){
                    recyclerView.smoothScrollToPosition(allCount-1);
                    currentPosition=allCount;
                    toEndPosition=false;
                    turnToFirstPosition=true;
                }else  if (turnToFirstPosition){
                    Log.d("yfing.wei", "smoothScrollToPosition(0): ");
                    recyclerView.scrollToPosition(0);
                    turnToFirstPosition=false;
                }else {
                    Log.d("yfing.wei", "smoothScrollToPosition(currentPosition): "+currentPosition);
                    recyclerView.smoothScrollToPosition(currentPosition);
                }
                recyclerView.postDelayed(recyclerView.autoPollTask,recyclerView.TIME_AUTO_POLL);
            }
        }
    }
    //开启:如果正在运行,先停止->再开启
    public void start() {
        if (running){
            stop();
        }
        canRun = true;
        running = true;
        postDelayed(autoPollTask,TIME_AUTO_POLL);
    }

    public void stop(){
        running = false;
        removeCallbacks(autoPollTask);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                if (running){
                    stop();
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_OUTSIDE:
                if (canRun){
                    start();
                }
                break;
        }
        return super.onTouchEvent(e);
    }
}
