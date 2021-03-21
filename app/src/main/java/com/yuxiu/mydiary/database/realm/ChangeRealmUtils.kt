package com.yuxiu.mydiary.database.realm

import android.util.Log
import io.realm.RealmMigration
import io.realm.DynamicRealm

/**
 * Created by YuX on 2018/9/6 15:15
 * ^_^ qq:565749553
 */
class ChangeRealmUtils : RealmMigration {
    override fun migrate(realm: DynamicRealm, oldVersion: Long, newVersion: Long) {
        Log.e("realmversion", "$oldVersion,$newVersion")
        //        RealmSchema schema = realm.getSchema();
//        if(newVersion>oldVersion) {
//            if(oldVersion<2) {
//            }
//        }
    }
}