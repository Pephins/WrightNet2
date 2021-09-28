package example.com.right2wright;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Terms_And_Conditions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms__and__conditions);
        configureNextButton();
    }
    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.TsCs);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Terms_And_Conditions.this, Login.class));
            }
        });

        TextView textViewAbout = findViewById(R.id.aboutTv);

        textViewAbout.setText("WrightNet is a mobile App that allows people to connect and work online. How does it work? It's simple, say you have an issue in your household, maybe \n" +
                "perhaps your bathroom sink or pipes are leaking. The whole room is  now a mess and you have no knowledge whatsoever on how to fix them. This Covid-19 pandemic makes it \n" +
                "impossible to go around looking for a plumber to come and fix your problem, that's when we introduce WrightNet to you, not only because of Covid-19 but some other reasons too. \n" +
                "How it works is that it helps you connect to skilled and experienced Artisans on your specific problem, you can get their contact details and communicate through video call,\n" +
                "which is where they will give you clear and straightforward instructions on how to solve your problem efficiently and effectively while adhering to the social distancing protocol\n" +
                "introduced by the government. That's not all, if you are experienced in any of the artisan fields, namely plumbing, carpentry, welding, etc, you can register through our website and \n" +
                "be able to help other people around you and get paid for it. So what you waiting for? Let's get started and Thank you for signing up.");

    }

}