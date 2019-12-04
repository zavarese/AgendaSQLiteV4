package br.edu.ifsp.agendasqlite.data;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.edu.ifsp.agendasqlite.R;
import br.edu.ifsp.agendasqlite.model.Contato;
import br.edu.ifsp.agendasqlite.model.OnRecyclerViewItemClickListener;

public class ContatoAdapter
        extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder>
        implements Filterable, View.OnClickListener  {

    static List<Contato> contatos;
    List<Contato> contactListFiltered;

    private static ItemClickListener clickListener;

    private static OnRecyclerViewItemClickListener mListener;

    @Override
    public void onClick(View v) {
        if (mListener != null) {
            ViewModel model = (ViewModel) v.getTag();
            mListener.onItemClick(v, model);
        }
    }


    public void adicionaContatoAdapter(Contato c)
    {
        contatos.add(0,c);

        Collections.sort(contatos, new Comparator<Contato>() {
            @Override
            public int compare(Contato o1, Contato o2) {
                return o1.getNome().compareTo(o2.getNome());
            }
        });

        notifyDataSetChanged();

    }

    public void atualizaContatoAdapter(Contato c)
    {


        contatos.set(contatos.indexOf(c),c);
        notifyItemChanged(contatos.indexOf(c));


    }

    public void apagaContatoAdapter(Contato c)
    {
        int pos = contatos.indexOf(c);
        contatos.remove(pos);
        notifyItemRemoved(pos);


    }

    public List<Contato> getContactListFiltered()
    {
        return contactListFiltered;
    }
/*
    public void setClickListener(ItemClickListener itemClickListener)
    {
        clickListener = itemClickListener;

    }
*/
    public ContatoAdapter(List<Contato> contatos)
    {
        this.contatos = contatos;
        contactListFiltered=contatos;
    }

    @NonNull
    @Override
    public ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                   .inflate(R.layout.contato_celula,parent,false);

        return new ContatoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ContatoViewHolder holder, final int position) {
        holder.nome.setText(contactListFiltered.get(position).getNome());
        CheckBox favorite = holder.favorite.findViewById(R.id.favorite);

        //No carragamento da pagina
        if(contactListFiltered.get(position).getFavorito()==1){
            favorite.setChecked(true);
        }else{
            favorite.setChecked(false);
        }

        final LinearLayout rlyItem = holder.rlyItem;
        final CheckBox favorito = holder.favorite;
        final TextView nome = holder.nome;

        nome.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                mListener.onItemClick(position);
            }
        });

        favorito.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    favorito.setChecked(false);
                } else {
                    favorito.setChecked(true);
                }
                // Inform to Activity or the Fragment where the RecyclerView reside.
                mListener.onItemCheckBoxChecked(((CheckBox) view).isChecked(), position, (CheckBox) view);
            }
        });

        /*
        if(contactListFiltered.get(position).getFavorito() == 0){
                holder.favorite.setChecked(false);
        }else{
                holder.favorite.setChecked(true);
        }
         */

    }

    // Define the method that allows the parent activity or fragment to define the listener.
    public void setOnRecyclerViewItemClickListener(OnRecyclerViewItemClickListener<ViewModel> listener) {
        this.mListener = listener;
    }

    @Override
    public int getItemCount() {
        return contactListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    contactListFiltered = contatos;
                } else {
                    List<Contato> filteredList = new ArrayList<>();
                    for (Contato row : contatos) {
                        if (row.getNome().toLowerCase().contains(charString.toLowerCase()) || row.getEmail().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    contactListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                contactListFiltered = (ArrayList<Contato>) results.values;
                notifyDataSetChanged();

            }
        };
    }


    public class ContatoViewHolder
            extends RecyclerView.ViewHolder
            implements View.OnClickListener
    {
        final TextView nome;
        final CheckBox favorite;
        final LinearLayout rlyItem;

        public ContatoViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = (TextView) itemView.findViewById(R.id.nome);
            favorite = (CheckBox) itemView.findViewById(R.id.favorite);
            rlyItem = (LinearLayout) itemView.findViewById(R.id.rlyitem);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
              if (clickListener!=null) {
                  clickListener.onItemClick(v, getAdapterPosition());
              }
        }
    }


    public  interface ItemClickListener
    {
        void onItemClick(View v, int position);
    }



}
