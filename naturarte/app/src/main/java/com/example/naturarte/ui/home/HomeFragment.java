package com.example.naturarte.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.naturarte.Registro;
import com.example.naturarte.databinding.FragmentHomeBinding;
import com.example.naturarte.sqlHelper;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private String valor;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.lista;

//         caso queira criar uma pesquisa por registro, dá. mas não tem
        valor = "";

        List<Registro> registros = sqlHelper.getInstance(getActivity()).getRegistro(valor);
//        Log.d("teste", registros.toString());
        ListaValores adapter = new ListaValores(registros);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

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
            return new ListaValoresViewHolder(getLayoutInflater().inflate(android.R.layout.simple_list_item_1,parent,false));
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
                ((TextView) itemView).setText("Função: "+dado.tipo+"\nNome: "+dado.nome+"\nEspecialidade: "+dado.especialidade+"\nSalario: "+String.valueOf(dado.salario));

            }
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}