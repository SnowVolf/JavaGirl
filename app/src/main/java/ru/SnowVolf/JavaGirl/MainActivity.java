package ru.SnowVolf.JavaGirl;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
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
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.jksiezni.permissive.PermissionsGrantedListener;
import com.github.jksiezni.permissive.PermissionsRefusedListener;
import com.github.jksiezni.permissive.Permissive;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

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
            Intent abt = new Intent(this, AboutActivity.class);
            startActivity(abt);
            //showAboutDialog();
            return true;
        }else if (id == R.id.changelog){
            showChangelog();
            return true;
        }else if (id==R.id.settings) {
            Intent setting = new Intent(this, SettingsActivity.class);
            startActivity(setting);
            return true;
        } else if (id == R.id.open_girl){
            openGirlImage();
            return true;
        }
            return super.onOptionsItemSelected(item);
    }
    public void GoToGit(){
        new MaterialDialog.Builder(this)
                .content(R.string.choose_fork)
                .positiveText(R.string.master)
                .neutralText(R.string.alpha)
                .onPositive(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction){
                        Uri uri = Uri.parse("https://github.com/SnowVolf/JavaGirl/tree/master");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction){
                        Uri uri = Uri.parse("https://github.com/SnowVolf/JavaGirl/tree/alpha");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                })
                .show();
    }
    /*public void showAboutDialog(){
        new MaterialDialog.Builder(this)
                .title(getBuildName(this))
                .content(R.string.about_content)
                .positiveText(R.string.cle_ar)
                .positiveColorRes(R.color.colorAccent)
                .show();
    }*/
    //собираем инфу о приложении
    public static String getBuildName(Context context){
        String programBuild = context.getString(R.string.app_name);
        try{
            String pkg = context.getPackageName();
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(pkg, PackageManager.GET_META_DATA);
            programBuild += " v."+pkgInfo.versionName+" build "+pkgInfo.versionCode;//параметры задаются в build.gradle
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
        }return programBuild;
    }
    //получаем дату компиляции
    //
    public String getCompilationDate(Context context){
        String comp = context.getString(R.string.ot);//префикс
        try {
            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(),0);
            ZipFile zf = new ZipFile(ai.sourceDir);//открываем апк
            //выбираем classes.dex. можно выбрать resources.arcs,
            //AndroidManifest.xml.. не суть важно.
            ZipEntry ze = zf.getEntry("classes.dex");
            long time = ze.getTime();//получаем дату
            String date = SimpleDateFormat.getInstance().format(new java.util.Date(time));//выбираем формат даты
            zf.close();
            comp+=" ["+date+"]";//ставим скобки до и после даты
        }catch (Exception ex){
            ex.printStackTrace();
        } return comp;
    }
    public void onGirlClick(View v){
        String[] rnd = new String[]{
                getString(R.string.hello),getString(R.string.jgv2),getString(R.string.powered_me),getString(R.string.image_provided),getString(R.string.test_1),getString(R.string.test_2),getString(R.string.test_3),
                getString(R.string.test_4),getString(R.string.test_5),getString(R.string.test_6),getString(R.string.test_7),getString(R.string.test_8),getString(R.string.test_9),getString(R.string.test_10)
        };
        Toast toast = Toast.makeText(getApplicationContext(), rnd[(int) (Math.random()*rnd.length)], Toast.LENGTH_SHORT);
        toast.show();
        //позиция тоста - чуть ниже тулбара
        //измеряется в px
        toast.setGravity(Gravity.TOP, 0,100);
        try {
            //5.0+ должен использовать RelativeLayout, при использовании
            //LinearLayout (Android <5.x) картинка не показывается
            RelativeLayout toastContainer = (RelativeLayout) toast.getView();
            ImageView girlImageView = new ImageView(getApplicationContext());
            girlImageView.setImageResource(R.mipmap.ic_launcher);
            toastContainer.addView(girlImageView,1);
        }
        catch (Exception e){
           Snackbar.make(findViewById(R.id.activity_main), "Исключение '"+ e.getMessage()+"' обработано", Snackbar.LENGTH_LONG)
                   .setDuration(2500)//2,5 секунды
                   .show();
        }
        toast.show();
    }
    public void onDeviantArtClick(View view){
        //заглушка
        Snackbar.make(findViewById(R.id.myGirl), R.string.coming_soon, Snackbar.LENGTH_SHORT)
        .show();
    }
    public void onWebClick(View view){
        Uri uri = Uri.parse("http://4pda.ru/forum/index.php?showuser=4324432");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }
    private void showChangelog() {
        final StringBuilder sbd = new StringBuilder();
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(getAssets().open("change.log"), "UTF-8"));
            String line;
            while ((line = bfr.readLine()) != null) {
                sbd.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final String clToast = "<b><font color=\"#c62828\">Powered by Snow Volf</font><br><font color=\"#1565c0\"><i>А это текст другого цвета с курсивом</i></font></b><br>A вот это обычный не текст";
        new MaterialDialog.Builder(this)
                //в title забираем данные из PM и Date
                .title(getBuildName(this)+getCompilationDate(this))
                .content(sbd)
                .positiveText(R.string.ok)
                .neutralText(R.string.debug)
                .onNeutral(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog materialDialog, @NonNull DialogAction dialogAction){
                        Toast tst = Toast.makeText(getApplicationContext(), Html.fromHtml(clToast), Toast.LENGTH_LONG);
                        tst.show();
                    }}).show();
    }
    public static final int CHOOSE_GIRL_IMG =1;
    public String girlLocation = Environment.getExternalStorageDirectory().getPath();
    public void openGirlImage() {
        Toast.makeText(getApplicationContext(), R.string.in_developing,Toast.LENGTH_LONG).show();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    new Permissive.Request(Manifest.permission.READ_EXTERNAL_STORAGE).whenPermissionsGranted(new PermissionsGrantedListener() {
                        @Override
                        public void onPermissionsGranted(String[] permissions) throws SecurityException {
                            openGirlFinder();
                        }
                    }).whenPermissionsRefused(new PermissionsRefusedListener() {
                        @Override
                        public void onPermissionsRefused(String[] permissions) {
                            Toast.makeText(getApplicationContext(), "Разрешение на доступ нужно для загрузки фотографий в JavaGirl", Toast.LENGTH_LONG).show();
                        }
                    }).getContext();
                } catch (Exception e) {
                    e.getMessage();
                }
            } else {
                openGirlFinder();
            }
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public void openGirlFinder(){
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(), "need perm", Toast.LENGTH_LONG).show();
            return;
        }
        try{
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2)
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
           // intent.setDataAndType(Uri.parse("file://"+ girlLocation), "image/png")
            startActivityForResult(intent, CHOOSE_GIRL_IMG);

        }catch (ActivityNotFoundException ex){
            Toast.makeText(getApplicationContext(),"Файл менеджер не найден!", Toast.LENGTH_LONG).show();
        }catch (Exception ex){
            Toast.makeText(getApplicationContext(),"тест", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CHOOSE_GIRL_IMG && resultCode == Activity.RESULT_OK) {
            //TODO: написать обработчик
        }
    }
    public void onBackPressed(){
        finish();
    }
    public void onExitClick(View view){
        Process.killProcess(Process.myPid());
        System.exit(1);
    }
}
