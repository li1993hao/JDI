package haihemoive.controller.home;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.C;
import com.tiptimes.tp.annotation.S;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.controller.AController;

@C(Layout = R.layout.activity_my)
public class HomeController extends AController {
    private Button IB_bnt;
    private TextView IB_text;


    @Override
    public void initView() {
        IB_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSignal("c2","c1给你发的信息做些什么吧");
                pushController(SectionController.class);
            }
        });
        IB_text.setText("这是controller1");
    }

    @Override
    public void initData() {

    }

    @Override
    @S(name="c1")
    public boolean handleSignal(Signal signal) {
        IB_text.setText(signal.action);
        return true;
    }
}
