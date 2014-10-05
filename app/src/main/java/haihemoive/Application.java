package haihemoive;

import android.database.sqlite.SQLiteDatabase;

import com.tiptimes.tp.Db.DbHelper;
import com.tiptimes.tp.Db.DbListener;
import com.tiptimes.tp.common.CacheManager;
import com.tiptimes.tp.util.L;

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
        configDb();
	}

    public void configDb(){
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
    private final static int VERSION = 3;
    private final static String DATABASENAME="JDI";

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table User(id,name)";
        sqLiteDatabase.execSQL(sql);
        L.e(L.TAG,"db_onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        String sql = "create table User(id,name)";
        sqLiteDatabase.execSQL(sql);
        L.e(L.TAG,"db_onUpgrade");
    }

    @Override
    public int getVerson() {
        return 4;
    }

    @Override
    public String getDataBaseName() {
        return DATABASENAME;
    }
}