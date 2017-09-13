package giantbing.zonlinks.com.cardproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import giantbing.zonlinks.com.cardmoucle.Bean.ComBean;
import giantbing.zonlinks.com.cardmoucle.Excute.DispQueueThread;
import giantbing.zonlinks.com.cardmoucle.Excute.ReadExcute;
import giantbing.zonlinks.com.cardmoucle.Util.MathHelper;
import giantbing.zonlinks.com.cardmoucle.Util.ReadHelper;

public class MainActivity extends AppCompatActivity {
    private TextView showText;
    private Button sentBtn;
    DispQueueThread DispQueue;//刷新显示线程
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showText = (TextView) findViewById(R.id.show);
        sentBtn =(Button) findViewById(R.id.btnsend);
        init();
    }

    private void init(){
        openPortRead();
        DispQueue = new DispQueueThread(MainActivity.this, new ReadExcute() {
            @Override
            public void onDataRead(ComBean data) {
                DispRecData(data);
            }

            @Override
            public void onErro(String msg) {

            }
        });
        DispQueue.start();
        ReadHelper.ReadAutoData("1000");
    }

    private void DispRecData(ComBean ComRecData) {
        StringBuilder sMsg = new StringBuilder();
        String hex = MathHelper.ByteArrToHex(ComRecData.bRec);
        if (hex.length() >= 16) {
            if (!(MathHelper.getHexData(hex).equals(""))) {
                String dec = "" + MathHelper.HexToInt(MathHelper.getHexData(hex));
                sMsg.append(dec);
                sMsg.append("\r\n");
                showText.append(sMsg);
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ReadHelper.ClosePort();
        ReadHelper.stopAutoRead();
    }

    private void openPortRead() {
        ReadHelper.OpenPort(new ReadExcute() {
            @Override
            public void onDataRead(ComBean data) {
                DispQueue.AddQueue(data);//线程定时刷新显示(推荐)
            }

            @Override
            public void onErro(String msg) {

            }
        }, MainActivity.this);


    }
}
