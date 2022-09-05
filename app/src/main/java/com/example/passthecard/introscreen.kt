package com.example.passthecard

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor

class introscreen : AppCompatActivity() {

    lateinit var front_anim: AnimatorSet
    lateinit var back_anim: AnimatorSet
    var isFront = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introscreen)
        supportActionBar?.hide()
        window?.statusBarColor=ContextCompat.getColor(this,R.color.seashell)

        var scale = applicationContext.resources.displayMetrics.density

        // val fronttextlogo=findViewById<ImageView>(R.id.frontextlogoView) as ImageView
        val front = findViewById<CardView>(R.id.card_front) as CardView
        // val back =findViewById<TextView>(R.id.card_back) as TextView
        // val flip = findViewById<Button>(R.id.flip_btn) as Button

        front.cameraDistance = 50000 * scale
        // fronttextlogo.cameraDistance=500*scale
        //  back.cameraDistance = 5000 * scale


        // Now we will set the front animation
        front_anim = AnimatorInflater.loadAnimator(
            applicationContext,
            R.animator.front_animator
        ) as AnimatorSet

        //  back_anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.back_animator) as AnimatorSet

        // Now we will set the event listener
        if (isFront) {
            front_anim.setTarget(front);
            //  back_anim.setTarget(back);
            front_anim.start()
            front_anim.doOnEnd {
                val i = Intent(this, ManLayoutActivity::class.java)
                startActivity(i)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                finish()

            }


        }
    }
}