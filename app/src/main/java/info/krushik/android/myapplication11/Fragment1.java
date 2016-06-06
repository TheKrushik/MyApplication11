package info.krushik.android.myapplication11;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    private static final String EXTRA_STUDENTS = "info.krushik.android.myapplication11.STUDENTS";

    private ListView mListView;
    private ArrayList<Student> mStudents;

    public static Fragment1 newInstance(ArrayList<Student> students){
        Fragment1 fragment = new Fragment1();

        Bundle args = new Bundle();
        args.putParcelableArrayList(EXTRA_STUDENTS, students);

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        Bundle args = getArguments();
        mStudents = args.getParcelableArrayList(EXTRA_STUDENTS);

        ArrayAdapter<Student> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                mStudents );

        mListView = (ListView) view.findViewById(R.id.listView);
        mListView.setAdapter(adapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Student student = mStudents.get(position);//позиция в списке, то куда кликнули

                if (mListener != null){
                    mListener.onItemClick(student.id);
                }
            }
        });

        return view;
    }

    private StudentsItemListener mListener;

    public void setStudentsItemListener(StudentsItemListener listener){
        mListener = listener;
    }

    public interface StudentsItemListener {
        void onItemClick(long id);
    }
}
