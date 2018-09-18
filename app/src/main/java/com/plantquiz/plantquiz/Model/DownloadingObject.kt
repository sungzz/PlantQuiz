package com.plantquiz.plantquiz.Model

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL




class DownloadingObject {


    @Throws(IOException::class)
    fun downloadJSONDataFromLink(link: String): String {
        val stringBuilder: StringBuilder = StringBuilder()

        val url = URL(link)
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val bufferedInputString: BufferedInputStream =
                    BufferedInputStream(urlConnection.inputStream)
            val bufferedReader: BufferedReader =
                    BufferedReader(InputStreamReader(bufferedInputString))
            // temporary string to hold each line read from the reader.
            var inputLineString: String?
            inputLineString = bufferedReader.readLine()
            while (inputLineString != null) {
                stringBuilder.append(inputLineString)
                inputLineString = bufferedReader.readLine()
            }
        } finally {
            // regardless of success of Try Block or failure of Try Block, we will disconnect
            urlConnection.disconnect()
        }
        return stringBuilder.toString()


    }


}