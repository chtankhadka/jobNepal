package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog

@Composable
fun AboutUsDialog(
    onDismiss: (Boolean) -> Unit
) {
    Dialog(onDismissRequest = {
        onDismiss(false)
    }, content = {
        Card() {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
             ) {
                Text(text = "Our Vision:", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(text = "Revolutionizing Exam Registration for Seamless Success")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Our Mission:", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Text(text = "Streamlining Form-Filling with Unparalleled Convenience")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Our Objectives:", fontWeight = FontWeight.Bold, fontSize = 25.sp)
                Spacer(modifier = Modifier.height(3.dp))

                Text(text = "Effortless Form Completion:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = " We aspire to eliminate the complexities of exam registration. By submitting your information once, our dedicated technical team takes charge, ensuring every form is accurately and efficiently completed.")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Admit Card Empowerment:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = " Bid farewell to the hassle of tracking down your admit card. With our app, your admit card is securely stored and readily accessible whenever you need it.")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Cost-Effective Solutions:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "We're committed to reducing your financial burden. Our app not only saves you time but also offers exclusive discounts on form-filling services, making excellence more affordable.")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Seamless Accessibility:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Our app's user-friendly interface ensures that anyone, regardless of technical expertise, can navigate the process effortlessly. We're dedicated to making sure technology is accessible to all.")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Customer-Centric Approach:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Your convenience is at the core of everything we do. We continuously refine our services based on your feedback, ensuring an unparalleled experience tailored to your needs.")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Empowering Exam Candidates:", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                Text(text = "Our goal is to empower you with the tools and resources needed for exam success. We want to alleviate the stress of administrative tasks, allowing you to focus on your studies.")
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Join us in reshaping the exam registration landscape. Experience the future of form-filling, where efficiency, affordability, and empowerment converge. Together, we're making the journey to success smoother than ever before.", fontWeight = FontWeight.Bold)

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    onClick = {
                    onDismiss(false)
                }) {
                    Text(text = "Okay")
                }
            }

        }

    })
}