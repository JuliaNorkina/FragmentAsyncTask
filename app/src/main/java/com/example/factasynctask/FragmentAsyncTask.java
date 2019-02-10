package com.example.factasynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.example.factasynctask.Utils.MathUtils;

import java.lang.ref.WeakReference;

public class FragmentAsyncTask extends Fragment implements Task {
    private TaskCallbacks callback;

    @Override
    public void run(int value) {
        MyAsyncTask mTask = new MyAsyncTask(this);
        mTask.execute(value);
    }

    public interface TaskCallbacks {
        void onPostExecute(Integer integer);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (TaskCallbacks) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    private static class MyAsyncTask extends AsyncTask<Integer, Void, Integer> {

        private WeakReference<FragmentAsyncTask> taskFragment;

        MyAsyncTask(FragmentAsyncTask context) {
            taskFragment = new WeakReference<>(context);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return MathUtils.calculateFactorial(integers[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            FragmentAsyncTask fragment = taskFragment.get();
            if (fragment == null) return;
            fragment.callback.onPostExecute(integer);
        }
    }
}
