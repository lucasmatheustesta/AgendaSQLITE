package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.data.ContatoDAO;
import br.edu.ifspsaocarlos.agenda.model.Contato;

public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    private ContatoDAO cDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getIntent().hasExtra("contato")) {
            this.c = (Contato) getIntent().getSerializableExtra("contato");
            EditText nameText = (EditText) findViewById(R.id.editText1);
            nameText.setText(c.getNome());
            EditText foneText = (EditText) findViewById(R.id.editText2);
            foneText.setText(c.getFone());
            EditText fone2Text = (EditText) findViewById(R.id.etTelefone2);
            fone2Text.setText(c.getFone2());
            EditText emailText = (EditText) findViewById(R.id.editText3);
            emailText.setText(c.getEmail());
            EditText diaText = (EditText) findViewById(R.id.etDia);
            diaText.setText(c.getDia_aniversario());
            EditText mesText = (EditText) findViewById(R.id.etMes);
            mesText.setText(c.getMes_aniversario());
            int pos = c.getNome().indexOf(" ");
            if (pos == -1) {
                pos = c.getNome().length();
                setTitle(c.getNome().substring(0, pos));
            }
        }
        cDAO = new ContatoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if(!getIntent().hasExtra("contato")) {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                break;
            case R.id.delContato:
                apagar();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void salvar() {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.etTelefone2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String dia = ((EditText) findViewById(R.id.etDia)).getText().toString();
        String mes = ((EditText) findViewById(R.id.etMes)).getText().toString();

        if(c == null) {
            c = new Contato();
            c.setNome(name);
            c.setFone(fone);
            c.setFone2(fone2);
            c.setEmail(email);
            c.setDia_aniversario(dia);
            c.setMes_aniversario(mes);
            cDAO.insereContato(c);
        }
        else {
            c.setNome(name);
            c.setFone(fone);
            c.setFone2(fone2);
            c.setEmail(email);
            c.setDia_aniversario(dia);
            c.setMes_aniversario(mes);
            cDAO.atualizaContato(c);
        }
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void apagar() {
        cDAO.apagaContato(c);
        Intent resultIntent = new Intent();
        setResult(3, resultIntent);
        finish();
    }
}
