package ru.SnowVolf.JavaGirl;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Process;
import android.support.annotation.NonNull;
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

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar Toolbar=(Toolbar) findViewById(R.id.ToolBar);
        setSupportActionBar(Toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_open_nav_drawer);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//если true - показывается стрелочка на месте hamburger
        //getSupportActionBar().setIcon(R.mipmap.ic_launcher);//задает иконку в тулбаре. выглядит некрасиво, но кому-то может быть сгодится
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#ffffff\">"+getString(R.string.app_name)+ " Dev" +"</font>")));
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
        }else if (id==R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
            return super.onOptionsItemSelected(item);

    }
    public void GoToGit(){
        Uri uri = Uri.parse("https://github.com/SnowVolf/JavaGirl/");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
    }
    //собираем инфу о приложении
    public void showAboutDialog(){
        new MaterialDialog.Builder(this)
                .title(getBuildName(this))
                .content(R.string.about_content)
                .positiveText(R.string.cle_ar)
                .positiveColorRes(R.color.colorAccent)
                .show();
    }
    public static String getBuildName(Context context){
        String programBuild = context.getString(R.string.app_name);
        try{
            String pkg = context.getPackageName();
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(pkg, PackageManager.GET_META_DATA);
            programBuild += " v."+pkgInfo.versionName+" build "+pkgInfo.versionCode;


        }catch (PackageManager.NameNotFoundException e1){
        }return programBuild;
    }
    public void onGirlClick(View v){
        String[] rnd = new String[]{
                getString(R.string.hello),getString(R.string.jgv2),getString(R.string.powered_me),getString(R.string.image_provided),getString(R.string.test_1),getString(R.string.test_2),getString(R.string.test_3),
                getString(R.string.test_4),getString(R.string.test_5),getString(R.string.test_6),getString(R.string.test_7),getString(R.string.test_8),getString(R.string.test_9),getString(R.string.test_10)
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
           Snackbar.make(findViewById(R.id.activity_main), "Исключение обработано", Snackbar.LENGTH_LONG)
                   .setDuration(2500)//2,5 секунды
                   .show();
        }
        toast.show();
    }
    public void onDeviantArtClick(View view){
        Snackbar.make(findViewById(R.id.myGirl), R.string.coming_soon, Snackbar.LENGTH_SHORT)
        .show();
    }
    public void onWebClick(View view){
        Uri uri = Uri.parse("http://4pda.ru/forum/index.php?showuser=4324432");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void showChangelog() {
        final StringBuilder sb = new StringBuilder();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open("change.log"), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String clToast = "<b><font color=\"#c62828\">Powered by Snow Volf</font><p><font color=\"#1565c0\"><i>А это текст другого цвета с курсивом</i></font></b><p>A вот это обычный не текст";
        new MaterialDialog.Builder(this)
                .title(getBuildName(this))
                .content(sb)
                .positiveText(R.string.ok)
                .neutralText("Debug")
                .onNeutral(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction){
                        Toast tst = Toast.makeText(getApplicationContext(),Html.fromHtml(clToast), Toast.LENGTH_LONG);
                        tst.show();
                    }
                })
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
