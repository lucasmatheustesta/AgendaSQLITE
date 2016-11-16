package br.edu.ifspsaocarlos.agenda.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import br.edu.ifspsaocarlos.agenda.R;
import br.edu.ifspsaocarlos.agenda.model.Contato;
import io.realm.Realm;
import io.realm.RealmResults;

public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.realm = Realm.getDefaultInstance();

        if(getIntent().hasExtra("contato")) {
            long id = getIntent().getLongExtra("contato", -1);
            if(id != -1) {
                this.c = realm.where(Contato.class).equalTo("id", id).findFirst();
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
        }
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

    private long getPrimaryKey() {
        RealmResults<Contato> contatos = realm.where(Contato.class).findAll();
        long idSugerido = contatos.size() + 1;
        while(true) {
            Contato contato = realm.where(Contato.class).equalTo("id", idSugerido).findFirst();
            if(contato == null) {
                return idSugerido;
            }
            idSugerido += 1;
        }
    }

    public void salvar() {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.etTelefone2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String dia = ((EditText) findViewById(R.id.etDia)).getText().toString();
        String mes = ((EditText) findViewById(R.id.etMes)).getText().toString();



        if(c == null) {
            realm.beginTransaction();
            Contato contato = realm.createObject(Contato.class, getPrimaryKey());
            contato.setNome(name);
            contato.setFone(fone);
            contato.setFone2(fone2);
            contato.setEmail(email);
            contato.setDia_aniversario(dia);
            contato.setMes_aniversario(mes);
            realm.commitTransaction();
        }
        else {
            realm.beginTransaction();
            c.setNome(name);
            c.setFone(fone);
            c.setFone2(fone2);
            c.setEmail(email);
            c.setDia_aniversario(dia);
            c.setMes_aniversario(mes);
            realm.commitTransaction();
        }


        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    public void apagar() {
        realm.beginTransaction();
        c.deleteFromRealm();
        realm.commitTransaction();
        Intent resultIntent = new Intent();
        setResult(3, resultIntent);
        finish();
    }
}
