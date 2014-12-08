package haihemoive.controller.home;

import android.os.Bundle;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.C;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.controller.AController;

/**
 * Created by haoli on 14-9-30.
 */
@C(Layout = R.layout.activity_my)
public class SectionController extends  AController{
    @Override
    public void initView(Bundle savedInstanceState) {

    }


    @Override
    public void initData() {

    }

    @Override
    public boolean handleSignal(Signal signal) {
        return false;
    }

}
