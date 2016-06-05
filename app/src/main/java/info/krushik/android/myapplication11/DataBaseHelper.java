package info.krushik.android.myapplication11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {//конструктор создания БД
        super(context, "MyDB.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Student.TABLE_NAME + " ("
                + Student.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Student.COLUMN_FIRST_NAME + " TEXT NOT NULL,"
                + Student.COLUMN_LAST_NAME + " TEXT NOT NULL,"
                + Student.COLUMN_AGE + " INTEGER NOT NULL);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean saveStudent(Student student){ //проверку можно делать в сервисе(AsyncTask)
        if (student.id == 0){
            return insertStudent(student)>0;
        }else {
            return updateStudent(student)>0;
        }
    }


    private long insertStudent(Student student){//сохранение студента(новый)
        SQLiteDatabase db = getWritableDatabase();
        long id=0;

        try {
            ContentValues values = new ContentValues();

            values.put(Student.COLUMN_FIRST_NAME, student.FirstName);
            values.put(Student.COLUMN_LAST_NAME, student.LastName);
            values.put(Student.COLUMN_AGE, student.Age);

            id = db.insert(Student.TABLE_NAME, null, values);

        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    private int updateStudent(Student student){//обновление студента(возвр количество сохр записей)
        SQLiteDatabase db = getWritableDatabase();
        int count = 0;

        try {
            ContentValues values = new ContentValues();

            values.put(Student.COLUMN_FIRST_NAME, student.FirstName);
            values.put(Student.COLUMN_LAST_NAME, student.LastName);
            values.put(Student.COLUMN_AGE, student.Age);

            count = db.update(Student.TABLE_NAME, values, Student.COLUMN_ID + "+" + student.id, null);

        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    public ArrayList<Student> getStudents(){//чтение студентов
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<Student> students = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(Student.TABLE_NAME, null, null, null, null, null, null);//ищем студентов
            if (cursor.moveToFirst()) {//проверяем что что-то нашло, перемещает курсор на первую строку в результате запроса;
                while (!cursor.isAfterLast()) {//не конец запроса?
                    Student student = new Student();// заполняем студента

                    student.id = cursor.getLong(cursor.getColumnIndex(Student.COLUMN_ID));
                    student.FirstName = cursor.getString(cursor.getColumnIndex(Student.COLUMN_FIRST_NAME));
                    student.LastName = cursor.getString(cursor.getColumnIndex(Student.COLUMN_LAST_NAME));
                    student.Age = cursor.getLong(cursor.getColumnIndex(Student.COLUMN_AGE));

                    students.add(student);
                    cursor.moveToNext();//перемещает курсор на следующую строку;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();//обязательно следует закрывать для освобождения памяти
            }
        }
        return  students;
    }

    public Student getStudent(long id){//чтение 1го студента по id
        SQLiteDatabase db = getWritableDatabase();
        Student student = null;
        Cursor cursor = null;

        try {
            cursor = db.query(Student.TABLE_NAME, null, Student.COLUMN_ID + "=" + id, null, null, null, null);//ищем указанного студента
            if (cursor.moveToFirst()) {//проверяем что что-то нашло
                student = new Student();// заполняем студента

                student.id = cursor.getLong(cursor.getColumnIndex(Student.COLUMN_ID));
                student.FirstName = cursor.getString(cursor.getColumnIndex(Student.COLUMN_FIRST_NAME));
                student.LastName = cursor.getString(cursor.getColumnIndex(Student.COLUMN_LAST_NAME));
                student.Age = cursor.getLong(cursor.getColumnIndex(Student.COLUMN_AGE));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return  student;
    }
}
