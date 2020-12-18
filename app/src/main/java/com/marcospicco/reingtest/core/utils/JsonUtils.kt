package com.marcospicco.reingtest.core.utils

import android.content.Context
import android.util.Log
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import java.io.*
import java.lang.reflect.Type

class JsonUtils {

    private val mGson by lazy {
        GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .setDateFormat(DATE_FORMAT)
            .create()
    }

    fun <T> fromJson(json: String, classOfT: Class<T>): T = mGson.fromJson(json, classOfT)

    fun <T> fromJson(json: String, typeOfT: Type): T = mGson.fromJson(json, typeOfT)

    fun <Result> fromJsonArray(string: String, clazz: Class<Result>): ArrayList<Result>? {
        val tmpList = mGson.fromJson<ArrayList<Result>>(string, object : TypeToken<ArrayList<Result>>() {}.type)
        if (tmpList != null) {
            val resultList = ArrayList<Result>(tmpList.size)
            for (map in tmpList) {
                val tmpJson = mGson.toJson(map)
                resultList.add(mGson.fromJson<Result>(tmpJson, clazz))
            }
            return resultList
        }
        return null
    }

    fun <T> toJson(src: T): String = mGson.toJson(src)

    @Suppress("MagicNumber")
    fun getFileContentsFromResource(ctx: Context, resource: String): String {
        val contents = StringBuilder()

        // we might pull resources using R.raw.<res_id>, but for some reason compiler fails to
        // generate references sometimes.
        val resId: Int = ctx.resources.getIdentifier(resource, "raw", ctx.packageName)
        val inputStream: InputStream = ctx.resources.openRawResource(resId)

        val input = BufferedReader(InputStreamReader(inputStream), 1024 * 8)
        try {
            while (true) {
                val line = input.readLine() ?: break
                contents.append(line)
            }
        } finally {
            input.close()
        }
        return contents.toString()
    }

    fun toJsonArray(list: Any, type: TypeToken<Any>): JsonArray = mGson.toJsonTree(list, type.type).asJsonArray

    fun <T> buildFromRaw(resource: String, clazz: Class<T>): T {

        val insputStream = javaClass.getResourceAsStream("/$resource")

        val writer = StringWriter()
        val buffer = CharArray(BUFFER_SIZE)
        try {
            val reader = BufferedReader(InputStreamReader(insputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
            insputStream?.close()

            val jsonString = writer.toString()
            return Gson().fromJson(jsonString, clazz)
        } catch (e: IOException) {
            Log.e(TAG, MESSAGE_FOR_LOG, e)
            throw IllegalStateException(e)
        } catch (e: JsonSyntaxException) {
            Log.e(TAG, MESSAGE_FOR_LOG, e)
            throw IllegalStateException(e)
        }

    }

    companion object {
        private const val BUFFER_SIZE = 1024
        private const val TAG = "JsonUtils"
        private const val MESSAGE_FOR_LOG = "buildFromRaw: "
        private const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"

        val instance = JsonUtils()

        fun isNull(jsonElement: JsonElement): Boolean = jsonElement.isJsonNull

        fun isNull(jsonObject: JsonObject): Boolean = jsonObject.isJsonNull
    }
}