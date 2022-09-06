package com.example.soundplayer

import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.soundplayer.ui.theme.SoundPlayerTheme
import 	android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SoundPlayerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column {
                        Greeting("Android")
                        PlaySoundButton("Play On Phone", true)
                        PlaySoundButton("Play On OtherSpeaker", false)                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Composable
fun PlaySoundButton(buttonText: String, playOnPhone: Boolean){
    val context = LocalContext.current
    var isPlaying by remember {
        mutableStateOf(false)
    }
    var mp by remember { mutableStateOf(MediaPlayer.create(context, Uri.parse("https://www2.cs.uic.edu/~i101/SoundFiles/CantinaBand60.wav")))}
    if(playOnPhone){
        val audioDeviceType = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) AudioDeviceInfo.TYPE_BUILTIN_SPEAKER_SAFE else AudioDeviceInfo.TYPE_BUILTIN_SPEAKER
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val playbackDevice = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS).firstOrNull { it.type == audioDeviceType }
        mp.preferredDevice = playbackDevice
    }



    Button(onClick = {
        if(isPlaying){
            mp.pause()
        }
        else{
            mp.startPlaying()
        }
        isPlaying = !isPlaying
    }) {
        Text(text=buttonText)
    }

}

fun MediaPlayer.startPlaying(){
    isLooping = true
    start()
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoundPlayerTheme {
        Column {
            Greeting("Android")
            PlaySoundButton("Play On Phone", true)
        }

    }
}