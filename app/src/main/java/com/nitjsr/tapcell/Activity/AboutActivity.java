package com.nitjsr.tapcell.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nitjsr.tapcell.R;

public class AboutActivity extends Activity {

    TextView mAbout,mOnus,mTeam;
    Button fbLink,websiteLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mAbout=(TextView)findViewById(R.id.about);
        mOnus=(TextView)findViewById(R.id.onus);
        mTeam=(TextView)findViewById(R.id.team);

//        String history1="National Institute of Technology, Jamshedpur, earlier known as Regional Institute of Technology was established on 15th August 1960 as a joint venture of Government of India and the Government of Bihar in the chain of REC's (Regional Engineering College) in India with the aim to generate technical graduates of highest standards who could provide technological leadership to the region. It was among the first eight Regional Engineering Colleges (RECs) established as part of the Second Five-Year Plan (1956 - 1961). This was the only REC in the country which was named as RIT (Regional Institute of Technology). Therefore RIT Jamshedpur was actually the REC of undivided Bihar & Jharkhand. The foundation stone of RIT (REC) Jamshedpur was laid by Dr. Srikrishna Sinha, the then chief minister of Bihar , with the aim of nurturing talent and setting high standards of education and excellence.";
//        String history2="On 27th December 2002, in the line of all other RECs of India, RIT Jamshedpur (may be read as REC Jamshedpur too) was converted to National Institute of Technology, Jamshedpur with the status of a Deemed University as per the decision of Govt. of India. The Institute is fully funded and governed by the Ministry of Human Resource Development (MHRD), Government of India since 1st April, 2003. On 15 August 2007, NIT Jamshedpur was given the status of the Institute of National Importance through an Act of the Parliament known as the NIT Act.";
        String about="The Training & Placement Cell, NIT Jamshedpur facilitates the process of placement of students passing out from the Institute besides collaborating with leading organizations and institutes in setting up of internship and training program of students. The office liaises with various industrial establishments, corporate houses etc which conduct campus interviews and select graduate and post-graduate students from all disciplines. The Training & Placement Cell provides the infra-structural facilities to conduct group discussions, tests and interviews besides catering to other logistics. The Office interacts with many industries in the country, of which nearly 100+ companies visit the campus for holding campus interviews. The industries which approach the institute come under the purview of:";
        String onus="•\tCore Engineering industries\n•\tIT & IT enabled services\n•\tManufacturing Industries\n•\tConsultancy Firms\n•\tFinance Companies\n•\tManagement Organisations\n•\tR & D laboratories";
        String team="The placement season runs through the course of the year commencing the last week of July through to June. Pre-Placement Talks are also conducted in this regard as per mutual convenience. Job offers, dates of interviews, selection of candidates etc. are announced through the Training & Placement Cell. The Placement Office is assisted by a committee comprising representatives of students from the under-graduate and post-graduate engineering streams. The committee evolves a broad policy framework every year besides a set of rules which are inviolable. Students members are closely co-opted in implementing these policy decisions.\n\n";

        mAbout.setText(Html.fromHtml(about));
        mOnus.setText(onus);
        mTeam.setText(team);

        fbLink=(Button)findViewById(R.id.fbLink);
        websiteLink=(Button)findViewById(R.id.websiteLink);

        fbLink.setText("https://www.facebook.com/Training-and-Placement-Cell-NIT-Jamshedpur-527739790692082");
        websiteLink.setText("http://www.nitjsr.ac.in/");
    }
}