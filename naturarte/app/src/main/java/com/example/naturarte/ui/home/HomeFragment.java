package com.example.naturarte.ui.home;

import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturarte.R;
import com.example.naturarte.Registro;
import com.example.naturarte.databinding.FragmentHomeBinding;
import com.example.naturarte.sqlHelper;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private String valor;
    private sqlHelper banco;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        banco = sqlHelper.getInstance(getActivity());

        recyclerView = binding.lista;

//         caso queira criar uma pesquisa por registro, dá. mas não tem
        valor = "";
        List<Registro> registros = banco.getRegistro(valor);
//        Log.d("teste", registros.toString());
        ListaValores adapter = new ListaValores(registros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        return root;
    }
    private class ListaValores extends RecyclerView.Adapter<ListaValores.ListaValoresViewHolder>{

        private List<Registro> dados;
        //private AdapterView.OnItemClickListener listener;

        public ListaValores(List<Registro> dados){
            this.dados = dados;
        }
        @NonNull
        @Override
        public ListaValoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType ){
            return new ListaValoresViewHolder(getLayoutInflater().inflate(R.layout.registro,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ListaValoresViewHolder holder, int position){
            Registro dado = dados.get(position);
            holder.bind(dado);
        }
        @Override
        public int getItemCount(){
            return dados.size();
        }
        private class ListaValoresViewHolder extends RecyclerView.ViewHolder{
            public ListaValoresViewHolder(@NonNull View itemView){
                super(itemView);
            }
            public void bind(Registro dado){

                TextView tipoItem = itemView.findViewById(R.id.tipo);
                TextView nomeItem = itemView.findViewById(R.id.nome);
                TextView especialidadeItem = itemView.findViewById(R.id.especialidade);
                TextView salarioItem = itemView.findViewById(R.id.salario);
                Button bt = itemView.findViewById(R.id.remove);

                tipoItem.setText("Função: "+dado.tipo);
                nomeItem.setText("Nome: "+dado.nome);
                especialidadeItem.setText("Especialidade: "+dado.especialidade);
                salarioItem.setText("Salario: "+String.valueOf(dado.salario));

                bt.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        banco.removeRegistro(dado.tipo, dado.nome, dado.especialidade, dado.salario);
                        recyclerView.removeViewInLayout(itemView);
                        Toast.makeText(getContext(), "Registro Excluido", Toast.LENGTH_LONG).show();
                    }
                });

            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}