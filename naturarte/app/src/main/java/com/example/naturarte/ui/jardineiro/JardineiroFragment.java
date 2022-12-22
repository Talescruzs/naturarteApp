package com.example.naturarte.ui.jardineiro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.naturarte.R;
import com.example.naturarte.databinding.FragmentJardineirosBinding;
import com.example.naturarte.sqlHelper;

public class JardineiroFragment extends Fragment {
    private FragmentJardineirosBinding binding;
    private EditText nome;
    private EditText especialidade;
    private EditText salario;
    private Button bt;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentJardineirosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        nome = binding.Nome;
        especialidade = binding.Especialidade;
        salario = binding.Salario;
        bt = binding.Salva;

        bt.setOnClickListener(this::insere);

        return root;
    }

    public void insere(View view){
        String nomeInserido = nome.getText().toString();
        String especialidadeInserida = especialidade.getText().toString();
        String salarioInserido = salario.getText().toString();
        if(
                nomeInserido.isEmpty() || especialidadeInserida.isEmpty() || salarioInserido.isEmpty()
        ){
            Toast.makeText(getActivity(),R.string.embranco,Toast.LENGTH_LONG).show();
            return;
        }
        Float salarioFinal = Float.parseFloat(salarioInserido);

        long id_table = sqlHelper.getInstance(getActivity()).addRegistro("jardineiro",nomeInserido,especialidadeInserida, salarioFinal);
        if (id_table>0) {
            Toast.makeText(getActivity(), R.string.salvo, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}