package com.ihiabe.josh.fastnews.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ihiabe.josh.fastnews.R;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    private TextView mDetailTitle;
    private TextView mDetailAuthor;
    private TextView mDetailDescription;
    private TextView mViewFullArticle;
    private ImageView mDetailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        initViews();

        Intent intent = getIntent();
        String extraTitle = intent.getStringExtra("extra_title");
        String extraAuthor = intent.getStringExtra("extra_author");
        String extraDescription = intent.getStringExtra("extra_description");
        String extraImage = intent.getStringExtra("extra_image");
        final String extraUrl = intent.getStringExtra("extra_url");
        Picasso.get().load(extraImage).fit().centerCrop().into(mDetailImage);
        mDetailAuthor.setText(extraAuthor);
        mDetailTitle.setText(extraTitle);
        mDetailDescription.setText(extraDescription);

        mViewFullArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(extraUrl));
                startActivity(i);
            }
        });
    }

    private void initViews(){
        mDetailTitle = findViewById(R.id.detail_title);
        mDetailAuthor = findViewById(R.id.detail_author);
        mDetailDescription = findViewById(R.id.detail_description);
        mViewFullArticle = findViewById(R.id.detail_full_article);
        mDetailImage = findViewById(R.id.detail_image);
    }
}
