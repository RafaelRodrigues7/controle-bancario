package br.edu.ifspsaocarlos.controlebancario.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.controlebancario.R;
import br.edu.ifspsaocarlos.controlebancario.model.Conta;

/**
 * Created by Leo Lira on 08/12/2018.
 */

public class ContaAdapter extends RecyclerView.Adapter<ContaAdapter.ContaViewHolder>{

    private static List<Conta> listaConta;
    private Context context;


    private static ItemClickListener clickListener;


    public ContaAdapter(List<Conta> contatos, Context context) {
        this.listaConta = contatos;
        this.context = context;
    }

    @Override
    public ContaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.conta_celula, parent, false);

        return new ContaViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ContaViewHolder holder, int position) {
        holder.descricaoValor.setText("Nome: " + listaConta.get(position).getDescricao() + " - Valor: R$ " + listaConta.get(position).getValor());

    }

    @Override
    public int getItemCount() {
        return listaConta.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }

    public  class ContaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView descricaoValor;


        ContaViewHolder(View view) {
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
