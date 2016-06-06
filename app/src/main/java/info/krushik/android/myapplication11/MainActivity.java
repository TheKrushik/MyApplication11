package info.krushik.android.myapplication11;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//        implements LoaderManager.LoaderCallbacks<ArrayList<Student>> {

    private ProgressDialog mDialog;
//    private SaveTask mSaveTask;

    private DataBaseHelper mHelper;
    private ArrayList<Student> mStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mDialog = new ProgressDialog(this);
//        mDialog.setMessage("Loading...");
//        mDialog.setCancelable(false);
//        mDialog.show();

        mHelper = new DataBaseHelper(this);
        init();

//        getSupportLoaderManager().initLoader(0, null, this);
    }

    //    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//        if (mSaveTask!=null){
//            mSaveTask =
//        }
//    }
    private void init() {//вычитка студентов и установка фрагментов
        mStudents = mHelper.getStudents();

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment1 fragment = Fragment1.newInstance(mStudents);
        fragment.setStudentsItemListener(new Fragment1.StudentsItemListener() {
            @Override
            public void onItemClick(long id) {
                Student student = mHelper.getStudent(id);
                edit(student);
            }
        });
        transaction.replace(R.id.fragmentView, fragment);
        transaction.commit();
    }

    private void edit(Student student) {//принимает студента на редактирование
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        Fragment2 fragment2 = Fragment2.newInstance(student);
        fragment2.setOnStudentListener(new Fragment2.StudentListener() {
            @Override
            public void onStudentSaved(Student student) {
                if (student.id == 0) {
                    mHelper.insertStudent(student);
                } else {
                    mHelper.updateStudent(student);
                }
                init();
            }
        });
        transaction.replace(R.id.fragmentView, fragment2);

        transaction.commit();
    }

    public void OnClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        switch (v.getId()) {
            case R.id.button1:
//                Fragment fragment1 = Fragment1.newInstance("I am Fragment 1");
//                transaction.replace(R.id.fragmentView, fragment1);
//                onStudentSelected(new Student());
                edit(new Student());
                break;
//            case R.id.button2:
//                Fragment fragment2 = Fragment2.newInstance("I am Fragment 2");
//                transaction.replace(R.id.fragmentView, fragment2);
//                break;
        }
        transaction.commit();
    }

//    @Override
//    public Loader<ArrayList<Student>> onCreateLoader(int id, Bundle args) {
//        return new StudentsLoader(this);
//    }
//
//    @Override
//    public void onLoadFinished(Loader<ArrayList<Student>> loader, final ArrayList<Student> data) {
//        Handler handler = new Handler() {
//            @Override
//            public void handleMessage(Message msg) {
//                onLoaded(data);
//
//            }
//        };
//        handler.sendEmptyMessage(0);
//    }
//
//    private void onLoaded(ArrayList<Student> students) {
//        Fragment1 fragment1 = Fragment1.newInstance(students);
//        fragment1.setStudentsItemListener(new Fragment1.StudentsItemListener() {
//            @Override
//            public void onItemClick(Student student) {
//                onStudentSelected(student);
//            }
//        });
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragmentView, fragment1);
//        transaction.commit();
//
//        if (mDialog != null) {
//            mDialog.dismiss();
//        }
//    }
//
//    private void onStudentSelected(Student student){
//        Fragment2 fragment2 = Fragment2.newInstance(student);
//        fragment2.setOnStudentListener(new Fragment2.StudentListener() {
//            @Override
//            public void onStudentSaved(Student student) {
//                mSaveTask = new SaveTask();
//                mSaveTask.execute();
//
//            }
//        });
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.fragmentView, fragment2);
//        transaction.commit();
//
//    }
//
//    @Override
//    public void onLoaderReset(Loader<ArrayList<Student>> loader) {
//
//    }
//
//    class SaveTask extends AsyncTask<Student, Void, Boolean>{
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            mDialog = new ProgressDialog(MainActivity.this);
//            mDialog.setMessage("Loading...");
//            mDialog.setCancelable(false);
//            mDialog.show();
//        }
//
//        @Override
//        protected Boolean doInBackground(Student... params) {
//            Student student = params[0];
//            DataBaseHelper helper = new DataBaseHelper(MainActivity.this);
//            return helper.saveStudent(student);
//        }
//
//        @Override
//        protected void onPostExecute(Boolean aBoolean) {
//            super.onPostExecute(aBoolean);
//            if (mDialog!=null){
//                mDialog.dismiss();
//            }
//        }
//    }
}
