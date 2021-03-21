package com.yuxiu.mydiary.database.realm

import android.content.Context
import kotlin.jvm.JvmOverloads
import io.realm.*

/**
 * Created by YuX on 2018/9/5 16:34
 * ^_^ qq:565749553
 */
class JsonRealmUtils {
    var infoRealm: Realm? = null

    /**
     * @param context
     */
    @JvmOverloads
    fun init(
        context: Context,
        name: String,
        changeRealmUtils: ChangeRealmUtils = ChangeRealmUtils()
    ) {
        if (infoRealm == null) {
            Realm.init(context)
            var config: RealmConfiguration? = null
            if (RealmNameConfigure.configMap[name] == null) {
                config = RealmConfiguration.Builder().schemaVersion(context.resources.getInteger(
                    getIntegerID(name, context)).toLong()).migration(changeRealmUtils).name(name)
                    .build()
                infoRealm = Realm.getInstance(config)
                RealmNameConfigure.configMap[name] = config
            }
            infoRealm = Realm.getInstance(RealmNameConfigure.configMap[name])
        }
    }

    fun <T : RealmObject?> save(json: String?, t: Class<T>?) {
        infoRealm!!.beginTransaction()
        infoRealm!!.createOrUpdateObjectFromJson(t, json)
        infoRealm!!.commitTransaction()
    }

    fun <T : RealmObject?> save(t: T) {
        infoRealm!!.beginTransaction()
        infoRealm!!.copyToRealmOrUpdate(t)
        infoRealm!!.commitTransaction()
    }

    fun <T : RealmObject?> saveList(json: String?, t: Class<T>?) {
        infoRealm!!.beginTransaction()
        infoRealm!!.createOrUpdateAllFromJson(t, json)
        infoRealm!!.commitTransaction()
    }

    fun <T : RealmObject?> findAll(t: Class<T>?): RealmResults<T> {
        return infoRealm!!.where(t).findAll()
    }

    fun close(context: Context?) {
        if (infoRealm != null) {
            if (!infoRealm!!.isClosed) {
                infoRealm!!.close()
            }
            infoRealm = null
        }
    }

    fun <T : RealmObject?> deleteAll(t: Class<T>?) {
        infoRealm!!.beginTransaction()
        infoRealm!!.where(t).findAll().deleteAllFromRealm()
        infoRealm!!.commitTransaction()
    }

    fun <T : RealmObject?> delByFeild(t: Class<T>?, feild: String?, value: String?) {
        infoRealm!!.beginTransaction()
        infoRealm!!.where(t).contains(feild, value).findAll().deleteAllFromRealm()
        infoRealm!!.commitTransaction()
    }

    fun <T : RealmObject?> findByFeild(t: Class<T>?, feild: String?, value: String?): T? {
        return infoRealm!!.where(t).contains(feild, value).findFirst()
    }

    fun <T : RealmObject?> findListSizeByFeild(t: Class<T>?, feild: String?, value: String?): Long {
        return infoRealm!!.where(t).contains(feild, value).count()
    }

    fun <T : RealmObject?> findListByFeild(
        t: Class<T>?,
        feild: String?,
        value: String?
    ): RealmResults<T> {
        return infoRealm!!.where(t).contains(feild, value).findAll()
    }

    fun <T : RealmObject?> findObject(t: Class<T>?, feild: String?, value: String?): T? {
        return infoRealm!!.where(t).contains(feild, value).findFirst()
    }

    fun <T : RealmObject?> findAllSort(
        t: Class<T>?,
        sortfeild: String?,
        sort: Sort?
    ): RealmResults<T> {
        return infoRealm!!.where(t).sort(sortfeild, sort).findAll()
    }

    fun <T : RealmObject?> findListByFeildSort(
        t: Class<T>?,
        feild: String?,
        value: String?,
        sortfeild: String?,
        sort: Sort?
    ): RealmResults<T> {
        return infoRealm!!.where(t).contains(feild, value).sort(sortfeild, sort).findAll()
    }

    companion object {
        fun getIntegerID(strName: String, context: Context): Int {
            return context.resources.getIdentifier("realm_version_$strName", "integer",
                context.packageName)
        }
    }
}