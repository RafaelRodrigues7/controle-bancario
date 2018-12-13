package br.edu.ifspsaocarlos.controlebancario.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import java.util.List;

import br.edu.ifspsaocarlos.controlebancario.R;
import br.edu.ifspsaocarlos.controlebancario.adapter.TransacaoAdapter;
import br.edu.ifspsaocarlos.controlebancario.dao.DAO;
import br.edu.ifspsaocarlos.controlebancario.model.Transacao;

public class ShowTransActivity extends AppCompatActivity {

    private RecyclerView transRecyclerView;
    private DAO dao;
    private List<Transacao> transacoes;
    private TransacaoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_trans);

        initComponents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initComponents();

    }

    private void initComponents(){

        dao = new DAO();

        this.transacoes = dao.selectTrans(getApplicationContext());

        transRecyclerView = (RecyclerView) findViewById(R.id.TransRecyclerView);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this);
        transRecyclerView.setLayoutManager(layout);

        adapter = new TransacaoAdapter(this.transacoes, this);
        transRecyclerView.setAdapter(adapter);

        setup();
    }

    private void setup() {
//        this.adapter.setClickListener(new ContaAdapter.ItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                final Conta conta = transacoes.get(position);
//
//                Intent i = new Intent(getApplicationContext(), AddAccActivity.class);
//                i.putExtra("conta", conta);
//                startActivityForResult(i, 2);
//            }
//        });


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                if (swipeDir == ItemTouchHelper.RIGHT) {
//                    Conta conta = contas.get(viewHolder.getAdapterPosition());
//                    dao.apagaContato(contato);
//                    contatos.remove(viewHolder.getAdapterPosition());
//                    recyclerView.getAdapter().notifyItemRemoved(viewHolder.getAdapterPosition());
//                    showSnackBar(getResources().getString(R.string.contato_apagado));

                }
            }


            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                Bitmap icon;
                Paint p = new Paint();
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if (dX > 0) {
                        p.setColor(ContextCompat.getColor(getBaseContext(), R.color.colorAccent));
                        RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.addaccount);
                        RectF icon_dest = new RectF((float) itemView.getLeft() + width, (float) itemView.getTop() + width, (float) itemView.getLeft() + 2 * width, (float) itemView.getBottom() - width);
                        c.drawBitmap(icon, null, icon_dest, p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }


        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(transRecyclerView);

    }
}
