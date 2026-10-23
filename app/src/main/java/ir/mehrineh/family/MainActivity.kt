package ir.mehrineh.family

import android.app.Activity
import android.app.AlertDialog
import android.os.Bundle
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.widget.*
import android.content.Intent
import android.net.Uri

class MainActivity: Activity() {
 private lateinit var root:LinearLayout
 private val navy=0xFF142044.toInt();private val pink=0xFFEE3368.toInt()
 private val prefs by lazy { getSharedPreferences("family_v4",MODE_PRIVATE) }
 private fun dp(n:Int)=(n*resources.displayMetrics.density).toInt()
 private fun shape(c:Int)=GradientDrawable().apply { setColor(c);cornerRadius=dp(19).toFloat() }
 private fun label(s:String,size:Float=17f,bold:Boolean=false):TextView=TextView(this).apply { text=s;textSize=size;setTextColor(navy);gravity=Gravity.RIGHT or Gravity.CENTER_VERTICAL;if(bold)setTypeface(null,Typeface.BOLD);setPadding(dp(9),dp(8),dp(9),dp(8)) }
 private fun card(title:String,detail:String,action:(()->Unit)?=null) { val box=LinearLayout(this).apply { orientation=LinearLayout.VERTICAL;background=shape(Color.WHITE);setPadding(dp(12),dp(12),dp(12),dp(12));elevation=dp(2).toFloat() };box.addView(label(title,19f,true));box.addView(label(detail,15f));root.addView(box,LinearLayout.LayoutParams(-1,-2).apply {bottomMargin=dp(12)});if(action!=null)box.setOnClickListener{action()} }
 private fun btn(s:String,run:()->Unit){val b=Button(this).apply {text=s;setTextColor(Color.WHITE);background=shape(pink);textSize=16f;setOnClickListener{run()} };root.addView(b,LinearLayout.LayoutParams(-1,dp(58)).apply {bottomMargin=dp(12)})}
 override fun onCreate(savedInstanceState:Bundle?) {super.onCreate(savedInstanceState);window.statusBarColor=0xFFFBF9FD.toInt();window.decorView.systemUiVisibility=android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;setContentView(R.layout.activity_main);root=findViewById(R.id.root);root.alpha=0f;root.animate().alpha(1f).setDuration(650).start();render()}
 private fun render(){root.removeAllViews();root.addView(label("مهرینه خانواده",29f,true));root.addView(label("کنار عزیزانتان، حتی از راه دور",15f));card("اتصال به سالمند","برای ارتباط زنده، سرویس ابری باید پیکربندی شود. در این نسخه اتصال آزمایشی بین دو گوشی هنوز فعال نیست."){connect()};card("آخرین اعلام حال","پس از اتصال امن حساب‌ها، آخرین وضعیت سالمند در این قسمت نمایش داده می‌شود.");card("درخواست‌های کمک","اعلان‌های اضطراری پس از فعال‌سازی سرویس پیام‌رسانی در این بخش نمایش داده می‌شوند.");card("یادآوری دارو","پیگیری مصرف دارو با رضایت سالمند و پس از اتصال حساب‌ها.");card("پیشرفت آموزش","ویدئوهای تماشا شده و تمرین‌های انجام‌شده.");btn("تنظیم شماره تماس سالمند"){phone()};card("حریم خصوصی","بدون اتصال واقعی، هیچ اطلاعاتی از گوشی سالمند دریافت یا نمایش داده نمی‌شود.")}
 private fun connect(){AlertDialog.Builder(this).setTitle("اتصال امن").setMessage("این نسخه رابط مستقل خانواده را فراهم می‌کند. برای اتصال واقعی، Firebase یا بک‌اند امن و تأیید رضایت سالمند لازم است. هیچ وضعیت ساختگی به عنوان داده زنده نشان داده نمی‌شود.").setPositiveButton("متوجه شدم",null).show()}
 private fun phone(){val input=EditText(this).apply {inputType=android.text.InputType.TYPE_CLASS_PHONE;setText(prefs.getString("phone",""))};AlertDialog.Builder(this).setTitle("شماره سالمند").setView(input).setPositiveButton("ذخیره"){_,_->prefs.edit().putString("phone",input.text.toString()).apply()}.setNeutralButton("تماس"){_,_->val n=input.text.toString();if(n.isNotBlank())startActivity(Intent(Intent.ACTION_DIAL,Uri.parse("tel:"+Uri.encode(n))))}.setNegativeButton("انصراف",null).show()}
}
