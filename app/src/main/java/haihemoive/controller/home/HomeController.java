package haihemoive.controller.home;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.tiptimes.R;
import com.tiptimes.tp.annotation.C;
import com.tiptimes.tp.annotation.S;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.controller.AController;
import com.tiptimes.tp.widget.AsyImageView;

@C(Layout = R.layout.activity_my)
public class HomeController extends AController {
    private Button IB_bnt;
    private TextView IB_text;
    private AsyImageView IB_image;



    @Override
    public void initView(Bundle savedInstanceState) {

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
