package br.edu.ifspsaocarlos.agenda.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.edu.ifspsaocarlos.agenda.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupRecyclerView(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupRecyclerView(null);
    }
}
