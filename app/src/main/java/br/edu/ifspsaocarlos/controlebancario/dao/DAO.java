package br.edu.ifspsaocarlos.controlebancario.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import br.edu.ifspsaocarlos.controlebancario.model.Conta;
import br.edu.ifspsaocarlos.controlebancario.model.Transacao;

/**
 * Created by Leo Lira on 08/12/2018.
 */

public class DAO {

    private SQLiteHelper dbHelper;
    private SQLiteDatabase database;


    public boolean addAcc(Context context, Conta conta){
        Double d;
        if(conta.getDescricao() == null ||
                conta.getDescricao().equals("") ||
                conta.getValor() == null ||
                conta.getValor().equals("")){

            return false;
        }

        else {
            try{
                d = Double.parseDouble(conta.getValor());
            }
            catch (Exception e){
                System.out.println("Erro ao converter:" + e.getMessage());
                return false;
            }

            dbHelper = new SQLiteHelper(context);
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(SQLiteHelper.KEY_DESC, conta.getDescricao());
            values.put(SQLiteHelper.KEY_VALOR, d);

            if (conta.getId() > 0)
                database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                        + conta.getId(), null);
            else
                database.insert(SQLiteHelper.DATABASE_TABLE, null, values);

            database.close();

            return true;
        }

    }

    public boolean addTrans(Context context, Transacao transacao){
        Double d;
        if(transacao.getConta() == null ||
                transacao.getConta().equals("") ||
                transacao.getData() == null ||
                transacao.getTipo() == null ||
                transacao.getTipo().equals("") ||
                transacao.getValor() == null ||
                transacao.getValor().equals("")){

            return false;
        }

        else {
            try{
                d = Double.parseDouble(transacao.getValor());
            }
            catch (Exception e){
                System.out.println("Erro ao converter:" + e.getMessage());
                return false;
            }

            dbHelper = new SQLiteHelper(context);
            database = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("tipo", transacao.getTipo());
            values.put("conta", transacao.getConta());
            values.put("valor", d);

//            DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
//            String reportDate = df.format(transacao.getData());
//
//            System.out.println("Report Date: " + reportDate);

            values.put("data",transacao.getData());
            values.put("periodo", transacao.getPeriodo());
            values.put("repeticao", transacao.getRepeticao());
            values.put("repetido", transacao.getRepetido());

            database.insert("transacao", null, values);
            database.close();

            this.updateSaldoConta(context, d, transacao.getConta(),transacao.getTipo());

            return true;
        }

    }

    public void updateSaldoConta(Context context, Double valor, String conta, String transacao){
        Conta c = this.selectWholeByName(context, conta).get(0);
        Double valorC = Double.parseDouble(c.getValor());

        if (transacao.equals("Salário")){
            valorC = valorC + valor;
        }
        else{
            valorC = valorC - valor;
        }


        c.setValor(valorC.toString());

        ContentValues values = new ContentValues();
        values.put(SQLiteHelper.KEY_DESC, c.getDescricao());
        values.put(SQLiteHelper.KEY_VALOR, valorC);

        dbHelper = new SQLiteHelper(context);
        database = dbHelper.getWritableDatabase();

        database.update(SQLiteHelper.DATABASE_TABLE, values, SQLiteHelper.KEY_ID + "="
                    + c.getId(), null);

        database.close();
        System.out.println("Update saldo conta feito");

    }

    public List<Conta> select(Context context){
        dbHelper = new SQLiteHelper(context);
        database=dbHelper.getWritableDatabase();
        List<Conta> listaConta = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {"id", "descricao", "valor"};

        cursor = database.query("conta", cols, null , null, null, null, "descricao");

        while (cursor.moveToNext())
        {
            Conta c = new Conta();
            c.setId(cursor.getInt(0));
            c.setDescricao(cursor.getString(1));
            c.setValor(String.valueOf(cursor.getDouble(2)));
            listaConta.add(c);
        }
        cursor.close();


        database.close();

        return listaConta;

    }

    public List<String> selectOnlyNames(Context context){
        dbHelper = new SQLiteHelper(context);
        database=dbHelper.getWritableDatabase();
        List<String> listaNomesConta = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {"id", "descricao", "valor"};

        cursor = database.query("conta", cols, null , null, null, null, "descricao");

        while (cursor.moveToNext())
        {

            listaNomesConta.add(cursor.getString(1));
        }
        cursor.close();


        database.close();

        return listaNomesConta;

    }

    public List<String> selectByName(Context context, String descricao){
        dbHelper = new SQLiteHelper(context);
        database=dbHelper.getWritableDatabase();
        List<String> listaNomesConta = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {"id", "descricao", "valor"};

        cursor = database.query("conta", cols, "descricao = ?" , new String[] { descricao},null, null, null, null);

        while (cursor.moveToNext())        {

            listaNomesConta.add(cursor.getString(1));
        }
        cursor.close();


        database.close();

        return listaNomesConta;

    }

    public List<Conta> selectWholeByName(Context context, String descricao){
        dbHelper = new SQLiteHelper(context);
        database=dbHelper.getWritableDatabase();
        List<Conta> listaConta = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {"id", "descricao", "valor"};

        cursor = database.query("conta", cols, "descricao = ?" , new String[] { descricao},null, null, null, null);

        while (cursor.moveToNext())        {

            Conta c = new Conta();
            c.setId(cursor.getInt(0));
            c.setDescricao(cursor.getString(1));
            c.setValor(String.valueOf(cursor.getDouble(2)));
            listaConta.add(c);
        }
        cursor.close();


        database.close();

        return listaConta;

    }

    public List<Transacao> selectTrans(Context context){
        dbHelper = new SQLiteHelper(context);
        database=dbHelper.getWritableDatabase();
        List<Transacao> listaTransacao = new ArrayList<>();

        Cursor cursor;

        String[] cols=new String[] {"id", "tipo", "valor", "conta", "data", "periodo", "repeticao", "repetido"};

        cursor = database.query("transacao", cols, null , null, null, null, "id");

        while (cursor.moveToNext())
        {
            Transacao t = new Transacao();
            t.setId(cursor.getInt(0));
            t.setTipo(cursor.getString(1));
            t.setValor(String.valueOf(cursor.getDouble(2)));
            t.setConta(cursor.getString(3));

            try {
                Date thedate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH).parse(cursor.getString(4));

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String reportDate = df.format(thedate);
                System.out.println("DATA");
                System.out.println(reportDate);
                t.setData(reportDate);
            } catch (ParseException e) {
                e.printStackTrace();

            }
            t.setPeriodo(cursor.getString(5));
            t.setRepeticao(cursor.getInt(6));
            t.setRepetido(cursor.getInt(7));
            listaTransacao.add(t);
        }
        cursor.close();
        database.close();

        return listaTransacao;

    }

    public void selectAndUpdateTrans(Context context){
        dbHelper = new SQLiteHelper(context);
        database=dbHelper.getWritableDatabase();

        Cursor cursor;

        String[] cols=new String[] {"id", "tipo", "valor", "conta", "data", "periodo", "repeticao", "repetido"};
        cursor = database.rawQuery("SELECT id, tipo, valor, conta, data, periodo, repeticao, repetido FROM transacao WHERE repeticao != repetido", null);

        while (cursor.moveToNext())
        {
            ContentValues values = new ContentValues();
            values.put("repetido", cursor.getInt(7) + 1);

            Date thedate = null;
            try {
                thedate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH).parse(cursor.getString(4));

                DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String reportDate = df.format(thedate);

                values.put("data",reportDate );
            } catch (ParseException e) {

                e.printStackTrace();
            }


            Date dataAtual = Calendar.getInstance().getTime();
            long diff = dataAtual.getTime() - thedate.getTime();

            float days = (diff / (1000*60*60*24));
            boolean doUpdate = false;

            switch (cursor.getString(5)){
                case "Anual":
                    if (days >= 365){
                        doUpdate = true;
                    }
                    break;
                case "Mensal":
                    if (days >= 30){
                        doUpdate = true;
                    }
                    break;
                case "Semanal":
                    if (days >= 7){
                        doUpdate = true;
                    }
                    break;
                case "Diario":
                    if (days >= 1){
                        doUpdate = true;
                    }
                    break;
                default:
                    break;

            }

            if (doUpdate) {
                database.update("transacao", values, "id "+ "="
                        + cursor.getInt(0), null);
                this.updateSaldoConta(context, Double.parseDouble(values.get("valor").toString()), values.get("conta").toString(), values.get("tipo").toString());
            }
        }
        cursor.close();
        database.close();

    }

}
