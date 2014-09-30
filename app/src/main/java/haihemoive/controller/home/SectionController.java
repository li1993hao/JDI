package haihemoive.controller.home;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.C;
import com.tiptimes.tp.annotation.S;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.controller.AController;

/**
 * Created by haoli on 14-9-30.
 */
@C(Layout = R.layout.activity_my)
public class SectionController extends  AController{
    private Button IB_bnt;
    private TextView IB_text;
    @Override
    public void initView() {
        IB_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popController();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    @S(name="c2")
    public boolean handleSignal(Signal signal) {
        IB_text.setText(signal.action);
        return true;
    }
}
