package com.tiptimes.tp.Db;


/**
 * Created by haoli on 14-10-5.
 */
public class Model {
    private long _id;

    public long get_id() {
        return _id;
    }

    /**
     * 更新或者插入
     * @return
     */
    public long save(){
        Dao<Model> dao = new Dao<Model>();
        if(_id != 0){
            return dao.updateById(this);
        }else{
            _id = dao.insert(this);
            return _id;
        }
    }

    /**
     * 删除
     * @return
     */
    public long delete(){
        if(_id == 0){
            return -1;
        }else{
            Dao<Model> dao = new Dao<Model>();
            return dao.delete(this.getClass(),"_id=?",_id+"");
        }
    }
}
