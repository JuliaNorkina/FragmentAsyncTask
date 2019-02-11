package com.example.factasynctask;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, FragmentAsyncTask.TaskCallbacks {

    private static final String TAG_TASK_FRAGMENT = "task_fragment";
    private EditText etNumber;
    private TextView tvResult;
    private FragmentManager fm;
    private FragmentAsyncTask mTaskFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = findViewById(R.id.etNumber);
        tvResult = findViewById(R.id.tvResult);
        findViewById(R.id.bCalculate).setOnClickListener(this);

        fm = getSupportFragmentManager();
        mTaskFragment = (FragmentAsyncTask) fm.findFragmentByTag(TAG_TASK_FRAGMENT);
        if (mTaskFragment == null) {
            Bundle bundle = new Bundle();
            mTaskFragment = new FragmentAsyncTask();
            mTaskFragment.setArguments(bundle);
            fm.beginTransaction().add(mTaskFragment, TAG_TASK_FRAGMENT).commit();
        }
    }

    @Override
    public void onClick(View v) {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();

        for (Fragment fragment : fragments) {
            if (fragment instanceof Task) {
                ((Task) fragment).run(Integer.parseInt(etNumber.getText().toString()));
            }
        }
    }

    public void onPostExecute(Integer integer) {
        tvResult.setText(String.valueOf(integer));
        removeFragment();
    }

    private void removeFragment() {
        if (mTaskFragment != null) {
            fm.beginTransaction()
                    .remove(mTaskFragment)
                    .commit();
        }
    }
}
