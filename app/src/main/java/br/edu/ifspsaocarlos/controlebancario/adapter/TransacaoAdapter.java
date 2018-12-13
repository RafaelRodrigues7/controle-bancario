package br.edu.ifspsaocarlos.controlebancario.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ifspsaocarlos.controlebancario.R;
import br.edu.ifspsaocarlos.controlebancario.model.Transacao;

/**
 * Created by Leo Lira on 08/12/2018.
 */

public class TransacaoAdapter extends RecyclerView.Adapter<TransacaoAdapter.TransacaoViewHolder>{

    private static List<Transacao> listaTransacao;
    private Context context;


    private static ItemClickListener clickListener;


    public TransacaoAdapter(List<Transacao> transcoes, Context context) {
        this.listaTransacao = transcoes;
        this.context = context;
    }

    @Override
    public TransacaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.trans_celula, parent, false);

        return new TransacaoViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TransacaoViewHolder holder, int position) {

        Date thedate = null;
        try {
            thedate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH).parse(listaTransacao.get(position).getData());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String reportDate = df.format(thedate);
        holder.descricaoValor.setText("Tipo: "+ listaTransacao.get(position).getTipo() + ", Conta: " + listaTransacao.get(position).getTipo() + ", Valor: R$ " + listaTransacao.get(position).getValor() +
                " \n " +
                "Data: " + reportDate +
                " \n " +
                "Periodo: " + listaTransacao.get(position).getPeriodo() + ", Repetição: " + listaTransacao.get(position).getRepeticao() + ", Repetido: " + listaTransacao.get(position).getRepetido()) ;

    }

    @Override
    public int getItemCount() {
        return listaTransacao.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public  class TransacaoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView descricaoValor;


        TransacaoViewHolder(View view) {
            super(view);
            descricaoValor = (TextView)view.findViewById(R.id.descricaoValor);


            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }
}
