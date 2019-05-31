package edu.washington.info448.hatemail

import android.util.Log
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

const val TAG = "DATA_MANAGER"

const val NAME = "name"
const val URL = "url"
const val FIELDS = "fields"

class DataManager(inputStream: InputStream) {
    private var messages: List<MessageType> = parseJson(inputStream)
    fun setData(data: List<MessageType>) {
        messages = data
    }
    fun getData(): List<MessageType> = messages


    private fun parseJson(inputStream: InputStream): List<MessageType> {
        val jsonString: String? = try {
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            Log.e(TAG, "Failed to open file: %s".format(e.message))
            null
        }

        val parsedMessages = mutableListOf<MessageType>()
        jsonString?.let {
            val messagesJSONArray = JSONArray(jsonString)
            for (i in 0 until messagesJSONArray.length()) {
                val messageObject = messagesJSONArray.getJSONObject(i)
                val messageName = messageObject.getString(NAME)
                val messageUrl = messageObject.getString(URL)

                val messageFieldsArray = messageObject.getJSONArray(FIELDS)
                val fieldsList = mutableListOf<String>()

                for (j in 0 until messageFieldsArray.length()) {
                    val fieldObject = messageFieldsArray.getJSONObject(j)
                    val fieldName = fieldObject.getString(NAME)
                    fieldsList.add(fieldName)
                }

                parsedMessages.add(MessageType(messageName, messageUrl, fieldsList))
            }
        }

        return parsedMessages
    }
}