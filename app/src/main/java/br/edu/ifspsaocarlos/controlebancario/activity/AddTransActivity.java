package br.edu.ifspsaocarlos.controlebancario.activity;

import br.edu.ifspsaocarlos.controlebancario.R;
import br.edu.ifspsaocarlos.controlebancario.dao.DAO;
import br.edu.ifspsaocarlos.controlebancario.model.Conta;
import br.edu.ifspsaocarlos.controlebancario.model.Transacao;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddTransActivity extends AppCompatActivity {

    private Spinner spinnerTrans;
    private Spinner spinnerContas;
    private Spinner spinnerPeriodo;
    private Spinner spinnerRepeticao;
    private EditText valorEditText;
    private Button btAddTrans;
    private DAO dao;
    private List<String> contasNome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trans);

        dao = new DAO();
        this.contasNome = dao.selectOnlyNames(getApplicationContext());

        String[] transacoes;
        String[] periodos;
        String[] repeticoes;
        String[] contasArray = new String[this.contasNome.size()];

        this.contasNome.toArray(contasArray);


        transacoes=getResources().getStringArray(R.array.listaTransacoes);
        periodos=getResources().getStringArray(R.array.listaPeriodo);
        repeticoes=getResources().getStringArray(R.array.listaRepeticao);

        this.spinnerTrans = (Spinner) findViewById(R.id.spinnerTrans);
        this.spinnerContas = (Spinner) findViewById(R.id.spinnerContas);
        this.spinnerPeriodo = (Spinner) findViewById(R.id.spinnerPeriodo);
        this.spinnerRepeticao = (Spinner) findViewById(R.id.spinnerRepeticao);

        this.valorEditText = (EditText) findViewById(R.id.valorEditText);
        this.btAddTrans = (Button) findViewById(R.id.btAddTrans);

        this.btAddTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DAO d = new DAO();
                Transacao t = new Transacao();
                String msg = "";



                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String reportDate = df.format(Calendar.getInstance().getTime());

                t.setData(reportDate);

                t.setTipo(spinnerTrans.getSelectedItem().toString());
                t.setValor(valorEditText.getText().toString());
                t.setConta(spinnerContas.getSelectedItem().toString());
                t.setPeriodo(spinnerPeriodo.getSelectedItem().toString());
                t.setRepeticao(Integer.parseInt(spinnerRepeticao.getSelectedItem().toString()));
                t.setRepetido(0);

                boolean retorno = d.addTrans(getApplicationContext(), t);


                if (retorno) {
                    msg = "Transação adicionada!";
                    finish();
                } else {
                    msg = "Erro ao criar transação. Verifique os dados fornecidos";
                }

                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();


        }
        });


        ArrayAdapter<String> transacoesAdapter =  new ArrayAdapter<String>(this, R.layout.spinner_item, transacoes);
        ArrayAdapter<String> contasAdapter =  new ArrayAdapter<String>(this, R.layout.spinner_item, contasArray);
        ArrayAdapter<String> periodoAdapter =  new ArrayAdapter<String>(this, R.layout.spinner_item, periodos);
        ArrayAdapter<String> repeticaoAdapter =  new ArrayAdapter<String>(this, R.layout.spinner_item, repeticoes);

        this.spinnerTrans.setAdapter(transacoesAdapter);
        this.spinnerContas.setAdapter(contasAdapter);
        this.spinnerPeriodo.setAdapter(periodoAdapter);
        this.spinnerRepeticao.setAdapter(repeticaoAdapter);



    }
}
