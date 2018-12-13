package br.edu.ifspsaocarlos.controlebancario.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import br.edu.ifspsaocarlos.controlebancario.R;
import br.edu.ifspsaocarlos.controlebancario.dao.DAO;
import br.edu.ifspsaocarlos.controlebancario.model.Conta;

public class AddAccActivity extends AppCompatActivity {


    private EditText descEditText;
    private EditText valorEditText;
    private Button btAddAcc;
    private Conta c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acc);

        this.initComponents();
    }

    private void initComponents(){
        this.descEditText = (EditText) findViewById(R.id.descEditText);
        this.valorEditText = (EditText) findViewById(R.id.valorEditText);
        this.btAddAcc = (Button) findViewById(R.id.btAddAcc);

        if (getIntent().hasExtra("conta")) {
            this.c = (Conta) getIntent().getSerializableExtra("conta");
            EditText descEditText = (EditText) findViewById(R.id.descEditText);
            descEditText.setText(c.getDescricao());

            EditText valorEditText = (EditText) findViewById(R.id.valorEditText);
            valorEditText .setText(c.getValor());

        }

        this.btAddAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d = new DAO();
                String msg = "";

                if (c == null){
                    c = new Conta();
                }

                List<String> verificacao = d.selectByName(getApplicationContext(), descEditText.getText().toString());

                if (verificacao.size()  >0){
                    msg = "Conta já existente!";
                }
                else {


                    c.setDescricao(descEditText.getText().toString());
                    c.setValor(valorEditText.getText().toString());

                    boolean retorno = d.addAcc(getApplicationContext(), c);


                    if (retorno) {
                        msg = "Conta adicionada!";
                        finish();
                    } else {
                        msg = "Erro ao criar conta. Verifique os dados fornecidos";
                    }
                }
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();

            }
        });
    }
}
