package com.plantquiz.plantquiz.Model

import org.json.JSONArray
import org.json.JSONObject

class ParsePlantUtility {


    fun parsePlantObjectsFromJSONData(search: String?) : List<Plant>? {

        var allPlantObject: ArrayList<Plant> = ArrayList()
        var downloadingObject = DownloadingObject()
        var topLevelPlantJSONData = downloadingObject.
                downloadJSONDataFromLink("http://plantplaces.com/perl/mobile/flashcard.pl")
        var topLevelJSONObject: JSONObject = JSONObject(topLevelPlantJSONData)
        var plantObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("values")

        var index: Int = 0

        while (index < plantObjectsArray.length()) {

            var plantObject: Plant = Plant()
            var jsonObject = plantObjectsArray.getJSONObject(index)

            /*
            genus: String, species: String, cultivar: String, common: String, pictureName: String,
            description: String, difficulty: Int, id: Int = 0
             */

            with(jsonObject) {
                plantObject.genus = getString("genus")
                plantObject.species = getString("species")
                plantObject.cultivar = getString("cultivar")
                plantObject.common = getString("common")
                plantObject.pictureName = getString("picture_name")
                plantObject.description = getString("description")
                plantObject.difficulty = getInt("difficulty")
                plantObject.id = getInt("id")

            }

            index++
        }

        return allPlantObject


    }



}