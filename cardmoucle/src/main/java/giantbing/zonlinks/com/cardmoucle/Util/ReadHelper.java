package giantbing.zonlinks.com.cardmoucle.Util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.security.InvalidParameterException;

import giantbing.zonlinks.com.cardmoucle.Excute.ReadExcute;


/**
 * Created by P on 2017/9/12.
 */

public class ReadHelper {
    private final static String port = "/dev/ttyS3";
    private final static int iBaudRate = 115200;
    private final static String hexData = "000003030000";
    private static SerialHelper cmad;

    //---------------------------------------打开串口
    public static void OpenPort(ReadExcute excute, Context context) {
        if (cmad == null) {
            cmad = new SerialHelper(port, iBaudRate, excute);
        }

        OpenComPort(cmad, context);
    }

    //---------------------------------------读取一次数据
    public static void ReadData() {
        if (cmad != null)
            sendPortData(cmad, hexData);
    }
    //---------------------------------------自动读取参数为
    /**
     * @param time 请求延时
     *
     * */
    public static void ReadAutoData(String time) {
        if (cmad != null)
            StartSend(cmad, time);
    }
    /**
     * 停止自动读取
     *
     * */
    public static void stopAutoRead() {
        if (cmad != null)
            StopSend(cmad);
    }
    /**
     * 关闭串口
     *
     * */
    public static void ClosePort() {
        if (cmad != null)
            CloseComPort(cmad);
    }

    //----------------------------------------------------串口发送
    private static void sendPortData(SerialHelper ComPort, String sOut) {
        if (ComPort != null && ComPort.isOpen()) {

            ComPort.sendHex(sOut);

        }
    }

    //----------------------------------------------------关闭串口
    private static void CloseComPort(SerialHelper ComPort) {
        if (ComPort != null) {
            ComPort.stopSend();
            ComPort.close();
        }
    }

    //----------------------------------------------------打开串口
    private static void OpenComPort(SerialHelper ComPort, Context context) {
        try {
            ComPort.open();
        } catch (SecurityException e) {
            Log.e("2333", "OpenComPort: " + "打开串口失败:没有串口读/写权限!");
            ShowMessage("打开串口失败:没有串口读/写权限!", context);

        } catch (IOException e) {
            Log.e("2333", "OpenComPort: " + "打开串口失败:未知错误!");
            ShowMessage("打开串口失败:未知错误!", context);
        } catch (InvalidParameterException e) {
            Log.e("2333", "OpenComPort: " + "打开串口失败:参数错误!");
            ShowMessage("打开串口失败:参数错误!", context);
        }

    }

    //------------------------------------------显示消息
    private static void ShowMessage(String sMsg, Context context) {
        Toast.makeText(context, sMsg, Toast.LENGTH_LONG).show();
    }

    //-------------------------------------------设置群发
    private static void StartSend(SerialHelper ComPort, String sTime) {
        ComPort.setHexLoopData(hexData);
        ComPort.setiDelay(Integer.parseInt(sTime));
        ComPort.startSend();
    }

    private static void StopSend(SerialHelper ComPort) {
        ComPort.stopSend();
    }
}
