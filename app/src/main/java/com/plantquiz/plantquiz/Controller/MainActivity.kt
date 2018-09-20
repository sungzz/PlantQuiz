package com.plantquiz.plantquiz.Controller

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.plantquiz.plantquiz.Model.ParsePlantUtility
import com.plantquiz.plantquiz.Model.Plant
import com.plantquiz.plantquiz.R

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private var cameraButton: Button? = null
    private var photoGalleryButton: Button? = null
    private var imgView: ImageView? = null

//    private var button1: Button? = null
//    private var button2: Button? = null
//    private var button3: Button? = null
//    private var button4: Button? = null


    val OPEN_CAMERA_BUTTON_REQUEST_ID = 1000
    val OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID = 2000

    var correctAnswerIndex: Int = 0
    var correctPlant: Plant? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        if (checkForInternetConnection()) {
            val innerClassObject = DownloadingPlantTask()
            innerClassObject.execute()

        }


        /*Toast.makeText(this, "The ON CREATE Method is Called",
                Toast.LENGTH_SHORT).show()

        val myPlant: Plant = Plant("", "", "", "", "" ,
                "", 0, 0)

        myPlant.plantName = "Wadas Memory Magnolia"

        var nameOfPlant = myPlant.plantName*/

        /*var flower = Plant()
        var tree = Plant()

        var collectionOfPlant: ArrayList<Plant> = ArrayList()
        collectionOfPlant.add(flower)
        collectionOfPlant.add(tree)*/

        cameraButton = findViewById<Button>(R.id.btnOpenCamera)
        photoGalleryButton = findViewById<Button>(R.id.btnOpenPhotoGallery)

        imgView = findViewById<ImageView>(R.id.img_View)

        cameraButton?.setOnClickListener(View.OnClickListener {

            Toast.makeText(this, "The Camera Button is Clicked",
                    Toast.LENGTH_SHORT).show()

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, OPEN_CAMERA_BUTTON_REQUEST_ID)

        })

        photoGalleryButton?.setOnClickListener(View.OnClickListener {

            val galleryIntent = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

            startActivityForResult(galleryIntent, OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID)

        })




    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OPEN_CAMERA_BUTTON_REQUEST_ID) {

            if (resultCode == Activity.RESULT_OK){

                val imageData = data?.getExtras()?.get("data") as Bitmap

                imgView?.setImageBitmap(imageData)


            }
        }

        if (requestCode == OPEN_PHOTO_GALLERY_BUTTON_REQUEST_ID) {

            if (resultCode == Activity.RESULT_OK) {

                val contentURI = data?.getData()
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,
                        contentURI)

                img_View.setImageBitmap(bitmap)
            }
        }

    }

    fun imageViewIsClicked(view: View){

        val randomNumber: Int = (Math.random() * 6).toInt() + 1
        Log.i("TAG", "THE RANDOM NUMBER IS $randomNumber")

        /*if (randomNumber == 1) {

            btnOpenCamera.setBackgroundColor(Color.BLUE)

        } else if (randomNumber == 2) {

            btnOpenPhotoGallery.setBackgroundColor(Color.CYAN)

        } else if (randomNumber == 3 ) {

            button1.setBackgroundColor(Color.GREEN)

        } else if (randomNumber == 4 ) {

            button2.setBackgroundColor(Color.DKGRAY)

        } else if (randomNumber == 5 ) {

            button3.setBackgroundColor(Color.RED)

        } else if (randomNumber == 6 ) {

            button4.setBackgroundColor(Color.BLACK)
        }
        */

        when(randomNumber){

            1 -> btnOpenCamera.setBackgroundColor(Color.BLUE)
            2 -> btnOpenPhotoGallery.setBackgroundColor(Color.CYAN)
            3 -> button1.setBackgroundColor(Color.GREEN)
            4 -> button2.setBackgroundColor(Color.DKGRAY)
            5 -> button3.setBackgroundColor(Color.RED)
            6 -> button4.setBackgroundColor(Color.BLACK)

        }


    }

    // Check for Internet connection

    private fun checkForInternetConnection(): Boolean {

        val connectivityManager: ConnectivityManager = this
                .getSystemService(android.content.Context.
                CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        val isDeviceConnectedToInternet = networkInfo != null
                && networkInfo.isConnected
        if (isDeviceConnectedToInternet) {

            return true

        } else {

            createAlert()
            return false

        }


    }

    private fun createAlert() {

        val alertDialog: AlertDialog =
                AlertDialog.Builder(this@MainActivity).create()
        alertDialog.setTitle("Network Error")
        alertDialog.setMessage("Please check for internet connection")

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE,"OK") {
            dialog: DialogInterface?, which: Int ->

            startActivity(Intent(Settings.ACTION_SETTINGS))

        }

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") {
            dialog: DialogInterface?, which: Int ->

            Toast.makeText(this@MainActivity,
                    "You must be connected to the internet",
                    Toast.LENGTH_SHORT).show()
            finish()
        }

        alertDialog.show()

    }

    private fun specifyTheRightAndWrongAnswer(userGuess: Int) {

        when (correctAnswerIndex) {
            0 -> button1.setBackgroundColor(Color.CYAN)
            1 -> button2.setBackgroundColor(Color.CYAN)
            2 -> button3.setBackgroundColor(Color.CYAN)
            3 -> button4.setBackgroundColor(Color.CYAN)
        }

        if (userGuess == correctAnswerIndex) {
            txtState.setText("Right!")
        } else {
            var correctPlantName = correctPlant.toString()
            txtState.setText("Wrong. Choose : $correctPlantName")
        }

    }




    fun button1onclick(buttonView: View) {

//        Toast.makeText(this, "Button 1 Onclick ",
//                Toast.LENGTH_SHORT).show()
//
//        var myNumber = 20
//        var myName : String = "Santoni"
//        var numberOfLetter = myName.length
//
//
//        var animalName : String? = null
//        var numberOfCharacterOfAnimal = animalName?.length ?: 100

        specifyTheRightAndWrongAnswer(0)

    }

    fun button2onclick(buttonView: View) {

//        Toast.makeText(this, "Button 2 Onclick ",
//                Toast.LENGTH_SHORT).show()
        specifyTheRightAndWrongAnswer(1)
    }

    fun button3onclick(buttonView: View) {

//        Toast.makeText(this, "Button 3 Onclick ",
//                Toast.LENGTH_SHORT).show()

        specifyTheRightAndWrongAnswer(2)
    }

    fun button4onclick(buttonView: View) {

//        Toast.makeText(this, "Button 4 Onclick ",
//                Toast.LENGTH_SHORT).show()

        specifyTheRightAndWrongAnswer(3)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    inner class DownloadingPlantTask: AsyncTask<String, Int, List<Plant>>() {

        override fun doInBackground(vararg p0: String?): List<Plant>? {

//            val downloadingObject: DownloadingObject = DownloadingObject()
//            var jsonData = downloadingObject.downloadJSONDataFromLink(
//                    "http://plantplaces.com/perl/mobile/flashcard.pl")
//            Log.i("JSON", jsonData)

            val parsePlant = ParsePlantUtility()


            return parsePlant.parsePlantObjectsFromJSONData()
        }

        override fun onPostExecute(result: List<Plant>?) {
            super.onPostExecute(result)

            var numberOfPlants = result?.size ?: 0

            if (numberOfPlants > 0 ) {

                var randomPlantIndexForButton1: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton2: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton3: Int = (Math.random() * result!!.size).toInt()
                var randomPlantIndexForButton4: Int = (Math.random() * result!!.size).toInt()

                var allRandomPlants = ArrayList<Plant>()
                allRandomPlants.add(result.get(randomPlantIndexForButton1))
                allRandomPlants.add(result.get(randomPlantIndexForButton2))
                allRandomPlants.add(result.get(randomPlantIndexForButton3))
                allRandomPlants.add(result.get(randomPlantIndexForButton4))

                button1.text = result.get(randomPlantIndexForButton1).toString()
                button2.text = result.get(randomPlantIndexForButton2).toString()
                button3.text = result.get(randomPlantIndexForButton3).toString()
                button4.text = result.get(randomPlantIndexForButton4).toString()


                correctAnswerIndex = (Math.random() * allRandomPlants.size).toInt()
                correctPlant = allRandomPlants.get(correctAnswerIndex)
            }

        }


    }


    override fun onStart() {
        super.onStart()

        Toast.makeText(this, "The ON START Method is Called",
                Toast.LENGTH_SHORT).show()

    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(this, "The ON RESUME Method is Called",
                Toast.LENGTH_SHORT).show()

        checkForInternetConnection()

    }

    override fun onPause() {
        super.onPause()

        Toast.makeText(this, "The ON PAUSE Method is Called",
                Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()

        Toast.makeText(this, "The ON STOP Method is Called",
                Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()

        Toast.makeText(this, "The ON RESTART Method is Called",
                Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        Toast.makeText(this, "The ON Destroy Method is Called",
                Toast.LENGTH_SHORT).show()
    }



}
