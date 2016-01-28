package com.asdar.lasaschedules.views;

import com.asdar.lasaschedules.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * Created by Ehsan on 4/18/2014.
 */
public class AboutDialog extends Dialog{
    private Context context;
    public AboutDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.about);
        TextView about = (TextView)findViewById(R.id.about);
        about.setText(Html.fromHtml(readRawTextFile(R.raw.about)));
        Linkify.addLinks(about, Linkify.ALL);
        Pattern icons8matcher = Pattern.compile("icons8");
        String icons8 = "http://icons8.com/?";
        Linkify.addLinks(about, icons8matcher, icons8);
        setTitle("");
    }


    public String readRawTextFile(int id) {
        InputStream inputStream = context.getResources().openRawResource(id);

        InputStreamReader in = new InputStreamReader(inputStream);
        BufferedReader buf = new BufferedReader(in);
        String line;

        StringBuilder text = new StringBuilder();
        try {
            while ((line = buf.readLine()) != null)
                text.append(line);
        } catch (IOException e) {
            return null;
        }

        return text.toString();
    }
}
