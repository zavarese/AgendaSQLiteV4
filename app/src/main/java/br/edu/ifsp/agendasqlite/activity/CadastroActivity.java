package br.edu.ifsp.agendasqlite.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifsp.agendasqlite.R;
import br.edu.ifsp.agendasqlite.data.ContatoDAO;
import br.edu.ifsp.agendasqlite.model.Contato;
import br.edu.ifsp.agendasqlite.utils.Mask;

public class CadastroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText nascimento = (EditText) findViewById(R.id.editTextNasc);
        nascimento.addTextChangedListener(Mask.insert("##/##", nascimento));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_cadastro, menu);
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
        if (id == R.id.action_salvarContato) {
            ContatoDAO dao = new ContatoDAO(this);

            String nome = ((EditText) findViewById(R.id.editTextNome)).getText().toString();
            String fone = ((EditText) findViewById(R.id.editTextFone)).getText().toString();
            String fone2 = ((EditText) findViewById(R.id.editTextFone2)).getText().toString();
            String email = ((EditText) findViewById(R.id.editTextEmail)).getText().toString();
            String nascimento = ((EditText) findViewById(R.id.editTextNasc)).getText().toString();

            Contato c = new Contato(nome,fone,email,0,nascimento,fone2);

            int idContato = (int) dao.incluirContato(c);
            c.setId(idContato);

            MainActivity.adapter.adicionaContatoAdapter(c);

            Toast.makeText(getApplicationContext(),"Contato inserido",Toast.LENGTH_LONG).show();

            Intent i=new Intent(getApplicationContext(),MainActivity.class);
            startActivityForResult(i,1);


        }

        return super.onOptionsItemSelected(item);
    }



}
