package br.edu.ifsp.agendasqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.agendasqlite.R;
import br.edu.ifsp.agendasqlite.data.ContatoDAO;
import br.edu.ifsp.agendasqlite.model.Contato;
import br.edu.ifsp.agendasqlite.utils.Mask;

public class DetalheActivity extends AppCompatActivity {

    Contato c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent().hasExtra("contato"))
        {
            this.c = (Contato)getIntent().getSerializableExtra("contato");

            EditText nome = findViewById(R.id.editTextNome);
            nome.setText(c.getNome());

            EditText fone = findViewById(R.id.editTextFone);
            fone.setText(c.getFone());

            EditText fone2 = findViewById(R.id.editTextFone2);
            fone2.setText(c.getFone2());

            EditText email = findViewById(R.id.editTextEmail);
            email.setText(c.getEmail());

            EditText nascimento = findViewById(R.id.editTextNasc);
            nascimento.setText(c.getNascimento());
            nascimento.addTextChangedListener(Mask.insert("##/##", nascimento));

        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            //finish() // close this activity and return to preview activity (if there is any)
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivityForResult(i, 1);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alterarContato) {
            ContatoDAO dao = new ContatoDAO(this);

            String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
            String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
            String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
            String nascimento = ((EditText) findViewById(R.id.editTextNasc)).getText().toString();

            c.setNome(nome);
            c.setFone(fone);
            c.setFone2(fone2);
            c.setEmail(email);
            c.setNascimento(nascimento);

            dao.alterarContato(c);
            Log.d("ID: ", Integer.toString(c.getId()));
            Log.d("NOME: ",c.getNome());

            MainActivity.adapter.atualizaContatoAdapter(c);

            Toast.makeText(getApplicationContext(),"Contato alterado",Toast.LENGTH_LONG).show();

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivityForResult(i,1);
        }

        if (id ==R.id.action_excluirContato) {
            ContatoDAO dao = new ContatoDAO(this);
            dao.excluirContato(c);
            MainActivity.adapter.apagaContatoAdapter(c);

            Toast.makeText(getApplicationContext(),"Contato excluído",Toast.LENGTH_LONG).show();

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivityForResult(i,1);

        }


        return super.onOptionsItemSelected(item);
    }




}
