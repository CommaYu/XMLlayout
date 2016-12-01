package com.example.xmllayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private Button button;
    private EditText editText;
    private ViewStub viewStub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_LinearLayoutCompat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.test_linearlayoutcompat);
            }
        });

        findViewById(R.id.btn_iv2tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.test_iv2tv);
            }
        });

        viewStub = (ViewStub) findViewById(R.id.vs_test);
        // 如果用inflate方法，就只能插入一次，再次隐藏再次view显示的时候，就会报错
        // 用了infalte反而绘制更加过度了
        // 反正如果想实现隐藏和显示就用下面这种方法
        findViewById(R.id.btn_ViewStub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View v = viewStub.inflate();
                textView = (TextView) v.findViewById(R.id.textView);
                button = (Button) findViewById(R.id.button); // 经过测试不加v，也可以的。跟那个<include />标签引进来的差不多。也不应定说要用一个v
                editText = (EditText) v.findViewById(R.id.editText);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Hello ViewStub", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // 用setVisibility的方法，可以成功控制显示和隐藏。还有就是并不一定要加v
        ((CheckBox)findViewById(R.id.checkBox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
//                    View v = viewStub.inflate();
                    viewStub.setVisibility(View.VISIBLE);
                    textView = (TextView) findViewById(R.id.textView);
                    button = (Button) findViewById(R.id.button); // 经过测试不加v，也可以的。跟那个<include />标签引进来的差不多。也不应定说要用一个v
                    editText = (EditText) findViewById(R.id.editText);

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(MainActivity.this, "Hello ViewStub", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    viewStub.setVisibility(View.GONE);
                }
            }
        });
    }

}
