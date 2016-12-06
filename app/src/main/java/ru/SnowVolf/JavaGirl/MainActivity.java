package ru.SnowVolf.JavaGirl;

import android.content.Intent;
import android.net.Uri;
import android.os.Process;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar Toolbar=(Toolbar) findViewById(R.id.ToolBar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);//если true - показывается стрелочка на месте hamburger
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);//задает иконку в тулбаре. выглядит некрасиво, но кому-то может быть сгодится
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">"+getString(R.string.app_name)+ "</font>")));
        getSupportActionBar().setSubtitle((Html.fromHtml("<font color=\"#ffffff\">"+getString(R.string.created_by)+ "</font>")));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.visit_git){
            GoToGit();
            return true;
        }else if (id == R.id.about){
            showAboutDialog();
            return true;
        }else if (id == R.id.changelog){
            showChangelog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void GoToGit(){
        Uri uri = Uri.parse("https://github.com/SnowVolf/JavaGirl/");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    public void showAboutDialog(){
        new MaterialDialog.Builder(this)
                .title(R.string.about_program)
                .content("Автор:\nSnow Volf (Артём Жиганов)\nLicenced under the MIT LICENSE\n\n\nДата компиляции: 06.12.2016 21:37")
                .positiveText("Понятно")
                .positiveColorRes(R.color.colorAccent)
                .show();
    }
    public void onGirlClick(View v){
        String[] rnd = new String[]{
                "Привет","JavaGirl v2","Powered by Snow Volf","Image Provided by NeoArtCore","ТЕСТ","ТЕСТ2","ТЕСТ3",
                "ТЕСТ4","ТЕСТ5","ТЕСТ6","ТЕСТ7","ТЕСТ8","ТЕСТ9","ТЕСТ10"
        };
        Toast toast = Toast.makeText(getApplicationContext(), rnd[(int) (Math.random()*rnd.length)], Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.TOP, 0,100);
        try {
            LinearLayout toastContainer = (LinearLayout) toast.getView();
            ImageView girlImageView = new ImageView(getApplicationContext());
            girlImageView.setImageResource(R.mipmap.ic_launcher);
            toastContainer.addView(girlImageView,1);
        }catch (Exception VolfLog){/*Toast.makeText(getApplicationContext(),"Исключение обработано",Toast.LENGTH_LONG).show();*/
            Snackbar.make(findViewById(R.id.activity_main), "Исключение обработано",Snackbar.LENGTH_LONG).show();}
        toast.show();
    }
    public void onDeviantArtClick(View view){
        Toast toast = Toast.makeText(getApplicationContext(), "Скоро...", Toast.LENGTH_LONG);
        toast.show();
    }
    public void onWebClick(View view){
        Uri uri = Uri.parse("http://4pda.ru/forum/index.php?showuser=4324432");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void showChangelog() {
        StringBuilder sb = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("changelog.txt"), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } catch (IOException e) {
        }
       new MaterialDialog.Builder(this)
                .title("JavaGirl v.1.0.8 build 9")
                .content(sb)
                .positiveText("Ok")
                .show();
    }
    public void onBackPressed(){
        finish();
    }
    public void onExitClick(View view){
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
