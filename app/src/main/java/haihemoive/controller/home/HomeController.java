package haihemoive.controller.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tiptimes.R;
import com.tiptimes.tp.Db.DbHelper;
import com.tiptimes.tp.annotation.A;
import com.tiptimes.tp.annotation.C;
import com.tiptimes.tp.annotation.S;
import com.tiptimes.tp.common.ActionBundle;
import com.tiptimes.tp.common.ActionDeal;
import com.tiptimes.tp.common.CacheManager;
import com.tiptimes.tp.common.ImageLoadListenerAdapter;
import com.tiptimes.tp.common.Message;
import com.tiptimes.tp.common.Signal;
import com.tiptimes.tp.controller.AController;
import com.tiptimes.tp.widget.AsyImageView;

import haihemoive.bto.User;

@C(Layout = R.layout.activity_my)
public class HomeController extends AController {
    private Button IB_bnt;
    private TextView IB_text;
    private AsyImageView IB_image;

    @A(url="http://172.16.139.3:8080/movies/index.php/Home/Index/getUser")
    private ActionDeal<User> actionDeal = new ActionDeal<User>(){
        @Override
        public void handleResult(ActionBundle<User> acitonBundle) {
            if(acitonBundle.isNomal){
                //success do something
                showCentenrToast(acitonBundle.data.getUserName()+"\n"+
                acitonBundle.data.getPsw()+"\n"+acitonBundle.data.getSex());
            }else{
                //faile
                showCentenrToast(acitonBundle.msg);
            }
        }
        
        @Override
        public void doAction(){
            actionPerformed(this, null);
        }
    };

    @Override
    public void initView(Bundle savedInstanceState) {
        IB_bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IB_image.loadImage(HomeController.this, "http://www.iyi8.com/uploadfile/2014/0720/20140720105621810.jpg",
                        R.drawable.noimage, new ImageLoadListenerAdapter() {
                            @Override
                            public void loding(int progress) {
                                IB_text.setText(progress+"%");
                            }

                            @Override
                            public void fail(Message message) {
                                IB_text.setText("下载失败!"+message.message);
                            }

                            @Override
                            public void loadded(Bitmap image) {
                                 IB_image.setImageBitmap(image);
                            }
                        });
                IB_text.setText("开始下载!");
            }
        });
        IB_text.setText(CacheManager.getImageACacheSize()+"mb");
    }

    @Override
    public void initData() {
        new DbHelper(this).test();
    }

    @Override
    @S(name="c1")
    public boolean handleSignal(Signal signal) {
        IB_text.setText(signal.action);
        return true;
    }
}
