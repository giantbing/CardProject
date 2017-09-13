package giantbing.zonlinks.com.cardmoucle.Excute;

import android.app.Activity;

import java.util.LinkedList;
import java.util.Queue;

import giantbing.zonlinks.com.cardmoucle.Bean.ComBean;

/**
 * Created by P on 2017/9/13.
 */

public class DispQueueThread extends Thread {
    private Queue<ComBean> QueueList = new LinkedList<>();
    private Activity activity;
    private ReadExcute excute;
    public DispQueueThread(Activity activity,ReadExcute excute) {
        this.activity = activity;
        this.excute = excute;
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            final ComBean ComData;
            while ((ComData = QueueList.poll()) != null) {
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        excute.onDataRead(ComData);
                    }
                });
                try {
                    Thread.sleep(100);//显示性能高的话，可以把此数值调小。
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    public synchronized void AddQueue(ComBean ComData) {
        QueueList.add(ComData);
    }
}
