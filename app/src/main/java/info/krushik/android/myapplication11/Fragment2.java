package info.krushik.android.myapplication11;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class Fragment2 extends Fragment {
    private static final String EXTRA_STUDENT = "info.krushik.android.myapplication11.STUDENT";

    private Student mStudent;
    private EditText mEditTextFirstName;
    private EditText mEditTextLastName;
    private EditText mEditTextAge;
    private Button mButtonSave;

    public static Fragment2 newInstance(Student student){
        Fragment2 fragment = new Fragment2();

        Bundle args = new Bundle();
        args.putParcelable(EXTRA_STUDENT, student);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        Bundle args = getArguments();
        mStudent = args.getParcelable(EXTRA_STUDENT);

        mEditTextFirstName = (EditText) view.findViewById(R.id.editTextFirstName);
        mEditTextLastName = (EditText) view.findViewById(R.id.editTextLastName);
        mEditTextAge = (EditText) view.findViewById(R.id.editTextAge);

        mEditTextFirstName.setText(mStudent.FirstName);
        mEditTextLastName.setText(mStudent.LastName);
        mEditTextAge.setText(String.valueOf(mStudent.Age));

        mButtonSave = (Button) view.findViewById(R.id.buttonSave);
        mButtonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStudent.FirstName = mEditTextFirstName.getText().toString();
                mStudent.LastName = mEditTextLastName.getText().toString();
                mStudent.Age = Integer.parseInt(mEditTextAge.getText().toString());

                if (mListener != null) {
//                    mStudent.FirstName = mEditTextFirstName.getText().toString();
//                    mStudent.LastName = mEditTextLastName.getText().toString();
//                    mStudent.Age = Long.parseLong(mEditTextFirstName.getText().toString());

                    mListener.onStudentSaved(mStudent);
                }
            }
        });

        return view;
    }

    private StudentListener mListener;

    public  void setOnStudentListener(StudentListener listener){
        mListener = listener;
    }

    public interface StudentListener{
        void onStudentSaved(Student student);
    }
}
