package br.edu.ifsp.agendasqlite.model;

import android.view.View;
import android.widget.CheckBox;

public interface OnRecyclerViewItemClickListener<Model> {
    void onItemClick(View view, Model model);

    void onItemClick(int position);

    void onItemCheckBoxChecked(boolean isChecked, int position, CheckBox chkBob);
}
