package com.example.appp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;

import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;

public class Register extends AppCompatActivity {


    EditText et_Id, et_Password, et_Passwordcheck, et_Email, et_Signnumber, et_name, emailAuth_number;
    RadioGroup rb_Gender;
    RadioButton rb_Man, rb_Woman;
    Button btn_Registerfinish, btn_Cancel, btn_Idcheck, btn_Signnummercheck, btn_numberokay, emailAuth_btn;
    TextView tv_idchecked, tv_passcheck, textView4, emailAuth_time_counter,tv_emailokay;

    SharedPreferences sharedPreferences;
    ArrayList<userdata> registerlist = new ArrayList<>();


    LayoutInflater dialog; //LayoutInflater
    View dialogLayout; //layout을 담을 View
    Dialog authDialog; //dialog 객체

    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 300 * 1000; //총 시간 (300초 = 5분)
    final int COUNT_DOWN_INTERVAL = 1000; //onTick 메소드를 호출할 간격 (1초)

    //public static
    class userdata {

        String name;
        String id;
        String pass;
        String email;
        String gender;


    }


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btn_Registerfinish = findViewById(R.id.btn_Registerfinish);
        et_Id = findViewById(R.id.et_Id);
        et_Password = findViewById(R.id.et_Password);
        et_Passwordcheck = findViewById(R.id.et_Passwordcheck);
        et_name = findViewById(R.id.et_name);


        et_Email = findViewById(R.id.et_Email);


        rb_Gender = findViewById(R.id.rb_Gender);
        rb_Man = findViewById(R.id.rb_Man);
        rb_Woman = findViewById(R.id.rb_Woman);


        btn_Cancel = findViewById(R.id.btn_Cancel);
        btn_Signnummercheck = findViewById(R.id.btn_Signnummercheck);


        btn_Idcheck = findViewById(R.id.btn_Idcheck);

        tv_emailokay = findViewById(R.id.tv_emailokay);
        tv_idchecked = findViewById(R.id.tv_idchecked);
        tv_passcheck = findViewById(R.id.tv_passcheck);
        textView4 = findViewById(R.id.textView4);


        final String sEmail = "tjrgus3709@gmail.com";
        final String ssPassword = "zxzx3709";


        btn_Signnummercheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");


                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sEmail, ssPassword);
                    }
                });
                try {

                    javax.mail.Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(sEmail));
                    message.setRecipients(javax.mail.Message.RecipientType.TO,
                            InternetAddress.parse(et_Email.getText().toString().trim()));
                    message.setSubject("  인증번호 입니다 .");
                    message.setText("안녕하십니까 어플 오브리 입니다. 회원님의 인증번호는  <354678> 입니다.");

                    new SendMail().execute(message);


                } catch (MessagingException e) {
                    e.printStackTrace();
                }


            }
        });


        et_Passwordcheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                String Password = et_Password.getText().toString();
                String Passwordcheck = et_Passwordcheck.getText().toString();

                if (Password.equals(Passwordcheck)) {
                    int color = Color.parseColor("#59E820");

                    et_Passwordcheck.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);


                    tv_passcheck.setTextColor(Color.parseColor("#59E820"));
                    tv_passcheck.setText("비밀번호가 일치합니다.");

                } else {
                    int color = Color.parseColor("#EE0707");

                    et_Passwordcheck.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    tv_passcheck.setTextColor(Color.parseColor("#EE0707"));
                    tv_passcheck.setText("비밀번호가 다릅니다.");

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        rb_Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {


                if (i == R.id.rb_Man) {
                    Toast.makeText(Register.this, "남성을 선택하였습니다.", Toast.LENGTH_SHORT).show();

                } else if (i == R.id.rb_Woman) {
                    Toast.makeText(Register.this, "여성을 선택하였습니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_Idcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("Userregisterdata", MODE_PRIVATE);
                String json = sharedPreferences.getString("register", "");

                try {
                    JSONArray jsonArray = new JSONArray(json);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        String saveid = jsonObject.getString("id");
                        Log.d("Saveid", "아이디 " + saveid);
                        String inputid = et_Id.getText().toString();
                        Log.d("Saveid", "인풋아이디 " + inputid);

                        if (saveid.equals(inputid) || inputid.equals("")) {

                            tv_idchecked.setTextColor(Color.parseColor("#EE0707"));
                            tv_idchecked.setText("이 아이디는 사용할 수 없습니다.");
                            et_Id.setText(" ");
                            Toast.makeText(Register.this, "이 아이디는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            break;

                        } else if (inputid.equals("")) {
                            tv_idchecked.setTextColor(Color.parseColor("#EE0707"));
                            tv_idchecked.setText("이 아이디는 사용할 수 없습니다.");

                            Toast.makeText(Register.this, "빈칸을 사용할 수 없습니다.", Toast.LENGTH_SHORT).show();
                            break;


                        } else {
                            tv_idchecked.setTextColor(Color.parseColor("#59E820"));
                            tv_idchecked.setText("아이디를 사용할 수 있습니다.");
                            Toast.makeText(Register.this, "아이디를 사용할 수 있습니다.", Toast.LENGTH_SHORT).show();

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_Registerfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final RadioButton Checkedgender = findViewById(rb_Gender.getCheckedRadioButtonId());


                if (et_name.getText().toString().length() == 0 || et_Id.equals("")) {
                    Toast.makeText(Register.this, "이름을 입력하세요.", Toast.LENGTH_SHORT).show();
                    et_name.requestFocus();
                    return;
                } else if (et_Id.getText().toString().length() == 0 || et_Id.equals("")) {
                    Toast.makeText(Register.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
                    et_Id.requestFocus();
                    return;
                } else if (et_Password.getText().toString().length() == 0) {
                    Toast.makeText(Register.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
                    et_Password.requestFocus();
                    return;
                } else if (et_Passwordcheck.getText().toString().length() == 0) {
                    Toast.makeText(Register.this, "비밀번호를 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    et_Passwordcheck.requestFocus();
                    return;
                } else if (et_Email.getText().toString().length() == 0) {
                    Toast.makeText(Register.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
                    et_Email.requestFocus();
                    return;
                } else if (!et_Password.getText().toString().equals(et_Passwordcheck.getText().toString())) {

                    Toast.makeText(Register.this, "비밀번호를 체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    et_Passwordcheck.requestFocus();
                    return;

                } else if (!rb_Man.isChecked() && !rb_Woman.isChecked()) {
                    Toast.makeText(Register.this, " 성별을 선택해주세요.", Toast.LENGTH_SHORT).show();
                    rb_Gender.requestFocus();
                    return;
                }else if (tv_emailokay.getText().toString().equals("")) {
                    Toast.makeText(Register.this, " 이메일 인증을 해주세요 .", Toast.LENGTH_SHORT).show();
                    return;
                }


                userdata userdata = new userdata();
                userdata.name = et_name.getText().toString();
                userdata.id = et_Id.getText().toString();
                Log.d("userID", "inputID " + userdata.id);
                userdata.pass = et_Password.getText().toString();
                userdata.email = et_Email.getText().toString();
                userdata.gender = Checkedgender.getText().toString();
                registerlist.add(userdata);


                try {

                    sharedPreferences = getSharedPreferences("Userregisterdata", MODE_PRIVATE);
                    String json = sharedPreferences.getString("register", "");

                    JSONArray jsonarray = new JSONArray(json);

                    for (int i = 0; i < registerlist.size(); i++) {

                        JSONObject userobject = new JSONObject();
                        userobject.put("name", registerlist.get(i).name);
                        userobject.put("id", registerlist.get(i).id);
                        userobject.put("pass", registerlist.get(i).pass);
                        userobject.put("gender", registerlist.get(i).gender);
                        userobject.put("email", registerlist.get(i).email);

                        jsonarray.put(userobject);
                        Log.d("array", "e" + jsonarray.toString());

                    }


                    String user = jsonarray.toString();
                    sharedPreferences = getSharedPreferences("Userregisterdata", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("register", user);
                    Log.d("shared", "saveeditor" + user);


                    editor.apply();


                    String name = et_name.getText().toString();
                    String id = et_Id.getText().toString();
                    String pass = et_Password.getText().toString();
                    String email = et_Email.getText().toString();
                    String gender = Checkedgender.getText().toString();


                    Toast.makeText(Register.this, "회원가입을 완료하였습니다.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Register.this, Login.class);
//                    intent.putExtra("name", name);
//                    intent.putExtra("id", id);
//                    intent.putExtra("pass", pass);
//                    intent.putExtra("email", email);
//                    intent.putExtra("gender", gender);


                    startActivity(intent);

                    //     finish();


                } catch (JSONException e) {

                    e.printStackTrace();

                }


            }
        });


        btn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Register.this, Login.class);
                Toast.makeText(Register.this, "회원가입이 되지 않았습니다.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
            }
        });
    }


    private class SendMail extends AsyncTask<javax.mail.Message, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(Register.this, "잠시만 기다려주세요", "메세지를 보내고 있습니다.", true, false);

        }

        @Override
        protected String doInBackground(Message... messages) {
            try {

                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {

                e.printStackTrace();
                return "Error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();
            if (s.equals("Success")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<front color = '#509324>Success</font>"));
                builder.setMessage("메세지가 보내졌습니다.");


                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();

                        dialog = LayoutInflater.from(Register.this);
                        dialogLayout = dialog.inflate(R.layout.activity_dialog, null); // LayoutInflater를 통해 XML에 정의된 Resource들을 View의 형태로 반환 시켜 줌
                        authDialog = new Dialog(Register.this); //Dialog 객체 생성
                        authDialog.setContentView(dialogLayout); //Dialog에 inflate한 View를 탑재 하여줌
                        authDialog.setCanceledOnTouchOutside(false); //Dialog 바깥 부분을 선택해도 닫히지 않게 설정함.


                        emailAuth_time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
                        //줄어드는 시간을 나타내는 TextView
                        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
                        //사용자 인증 번호 입력창
                        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
                        //인증하기 버튼
                        authDialog.show(); //Dialog를 나타내어 준다.

                        countdowntimer();


                    }

                });


                builder.show();

            } else {

                Toast.makeText(getApplicationContext(), "메세지가 보내지지 않았습니다. 이메일 아이디를 확인해주세요.", Toast.LENGTH_SHORT).show();
            }

        }


    }

    public void countdowntimer() {
        //줄어드는 시간을 나타내는 TextView
        emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    emailAuth_time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    emailAuth_time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }



            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                authDialog.cancel();

            }
        }.start();



        emailAuth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String  user_answer = emailAuth_number.getText().toString();
                if (user_answer.equals("354678") ) {
                    Toast.makeText(Register.this, "이메일 인증 성공", Toast.LENGTH_SHORT).show();

                    tv_emailokay.setText("이메일 인증을 성공하였습니다");
                    countDownTimer.cancel();
                    authDialog.cancel();


                } else if(! user_answer.equals("354678")  ){
                    Toast.makeText(Register.this, "다시 확인해주세요.", Toast.LENGTH_SHORT).show();


                } else if(emailAuth_number.getText().toString().length() == 0 ){
                    Toast.makeText(Register.this, "인증번호를 입력해주세요.", Toast.LENGTH_SHORT).show();


                }
//
//                    Toast.makeText(Register.this, "이메일 인증 실패", Toast.LENGTH_SHORT).show();
//                    countDownTimer.cancel();
//                    authDialog.cancel();




            }


        });
    }


}



