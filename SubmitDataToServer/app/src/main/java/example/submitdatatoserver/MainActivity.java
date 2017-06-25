package example.submitdatatoserver;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.submitdatatoserver.util.LoginService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.et_username)
    EditText mEtUsername;
    @BindView(R.id.et_password)
    EditText mEtPassword;
    @BindView(R.id.btn_get)
    Button mBtnGet;
    @BindView(R.id.btn_post)
    Button mBtnPost;
    @BindView(R.id.btn_client_get)
    Button mBtnClientGet;
    @BindView(R.id.btn_client_post)
    Button mBtnClientPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_get, R.id.btn_post, R.id.btn_client_get, R.id.btn_client_post})
    public void onViewClicked(View view) {
        final String username=mEtUsername.getText().toString().trim();
        final String password=mEtPassword.getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_get:
        new Thread(new Runnable() {
            @Override
            public void run() {
                //调用LoginService的方法提交数据
                final String result=LoginService.loginByGet(username,password);
                if (result!=null){
                    //ui线程更改界面
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    //请求失败
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        }).start();
                break;
            case R.id.btn_post:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String result=LoginService.loginByPost(username,password);
                        if (result!=null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }
                }).start();
                break;
            case R.id.btn_client_get:
                LoginService.loginByOkhttpGet(username, password, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获取响应消息体
                        final String result=response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
            case R.id.btn_client_post:
                LoginService.loginByOkhttpPost(username, password, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        //获取响应消息体
                        final String result=response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }
}
