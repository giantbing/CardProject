package giantbing.zonlinks.com.cardmoucle.Excute;


import giantbing.zonlinks.com.cardmoucle.Bean.ComBean;

/**
 * Created by P on 2017/9/12.
 */

public interface ReadExcute {
    void onDataRead(ComBean data);
    void onErro(String msg);
}
