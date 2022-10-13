package com.example.passthecard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.transition.Visibility
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.contextaware.ContextAware
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.passthecard.ui.theme.PassTheCardTheme

class MainActivitycompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PassTheCardTheme {
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(colorResource(id = R.color.seashell)),

                   
                ) {
                    mybutton();

                }
            }
        }
    }

}





@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PassTheCardTheme {
        mybutton()
    }

}

@Composable
fun mybutton() {


    Column(
        Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(top = 140.dp)
            .background(colorResource(id = R.color.seashell)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {


        val context = LocalContext.current
        Button(
            onClick = {
                val intent = context.startActivity(Intent(context, activitya::class.java))

            },
            modifier = Modifier
                .height(100.dp)
                .width(200.dp)
                .padding(start = 20.dp, end = 10.dp),


            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            enabled = true,
            border = BorderStroke(width = 5.dp, brush = SolidColor(Color.Yellow)),
            shape = RoundedCornerShape(40),


            ) {


            Text(text = "DIV A", color = colorResource(id = R.color.white))


        }

        Spacer(
            Modifier
                .height(50.0.dp)
                .width(50.dp)
        )

        Button(
            onClick = {
                val intent = Intent(context, Activityb::class.java)
                context.startActivity(intent)
            },

            modifier = Modifier
                .height(100.dp)
                .width(200.dp)
                .padding(start = 30.dp, end = 10.dp),


            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            enabled = true,
            border = BorderStroke(width = 5.dp, brush = SolidColor(Color.Yellow)),
            shape = RoundedCornerShape(40),


            ) {

            Text(text = "DIV B", color = colorResource(id = R.color.white))

        }

        Spacer(
            Modifier
                .height(50.0.dp)
                .width(50.dp)
        )


        Button(
            onClick = {
                val intent = Intent(context, Activityc::class.java)
                context.startActivity(intent)
            },

            modifier = Modifier
                .height(100.dp)
                .width(200.dp)
                .padding(start = 30.dp, end = 10.dp),


            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            enabled = true,
            border = BorderStroke(width = 5.dp, brush = SolidColor(Color.Yellow)),
            shape = RoundedCornerShape(40),


            ) {

            Text(text = "DIV C", color = colorResource(id = R.color.white))


        }
        Spacer(
            Modifier
                .height(50.0.dp)
                .width(50.0.dp)

        )

       /* Button(onClick = { /*TODO*/ },


            Modifier
                .height(100.dp)
                .width(200.dp)
                .padding(start = 30.dp, end = 10.dp),

            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            enabled = true,
            border = BorderStroke(width = 5.dp, brush = SolidColor(Color.Yellow)),
            shape = RoundedCornerShape(40)
        ) {

            Text(text = "Faculty", color= colorResource(id = R.color.white))


        }*/


    }


}

