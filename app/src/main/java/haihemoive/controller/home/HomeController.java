package haihemoive.controller.home;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.tiptimes.R;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.controller.Controller_Activity;



public class HomeController extends Controller_Activity {
    private Button IB_bnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        dynBind();
    }

    @Override
    public void initView() {
        IB_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomToast("煞笔");
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public boolean handleSignal(Signal signal) {
        return false;
    }
}
