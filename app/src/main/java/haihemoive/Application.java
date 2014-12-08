package haihemoive;

import com.tiptimes.tp.Db.Dao;
import com.tiptimes.tp.Db.DbHelper;
import com.tiptimes.tp.Db.DbListener;
import com.tiptimes.tp.Db.User;
import com.tiptimes.tp.common.CacheManager;

/**
 * 应用程序的实例类
 * @author tiptimes LH
 * 
 */
public class Application extends android.app.Application {
	private static Application application; //单例


	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		application = this;
        DbHelper.setDbListener(new DbConfig());
	}

	public static Application getApplication() {
		return application;
	}

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        CacheManager.clearImgCache();
        CacheManager.clearDataCache();
    }
}


class DbConfig implements DbListener{
    private final static int VERSION = 0;
    private final static String DATABASENAME="JDI";

    @Override
    public void onCreate(DbHelper dh ) {

        dh.creatTable(User.class);
        Dao<User> userDao = new Dao<User>();

        User user = new User();
        user.setAge(1);
        user.save();
    }

    @Override
    public void onUpgrade(DbHelper dh,int oldVersion, int newVersion) {

    }

    @Override
    public int getVerson() {
        return VERSION;
    }

    @Override
    public String getDataBaseName() {
        return DATABASENAME;
    }
}