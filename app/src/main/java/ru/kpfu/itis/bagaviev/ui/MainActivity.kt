package ru.kpfu.itis.bagaviev.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import ru.kpfu.itis.bagaviev.R
import ru.kpfu.itis.bagaviev.data.repository.UsersRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}