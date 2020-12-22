package com.felixtechlabs.todolist.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.felixtechlabs.todolist.R;
import com.felixtechlabs.todolist.helpers.DBHelper;
import com.felixtechlabs.todolist.models.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {

    DBHelper dbHelper;
    Context context;

    public NotesAdapter(DBHelper dbHelper) {
        this.dbHelper = dbHelper;

    }

    @NonNull
    @Override
    public NotesHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_notes, viewGroup, false);
        context = viewGroup.getContext();
        return new NotesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesHolder notesHolder, int i) {

        final Note note = dbHelper.getAllNotes().get(i);

        notesHolder.tvTitle.setText(note.getNoteTitle());

        notesHolder.tvBody.setText(note.getNoteBody());

        Date date = new Date(note.getCreatedAt());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM hh:mm a");

        notesHolder.tvTime.setText(simpleDateFormat.format(date));

        notesHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete note?");
                builder.setMessage("Do you really want to delete selected note?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteNote(note);
                        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel", null);
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dbHelper.getAllNotes().size();
    }


        class NotesHolder extends RecyclerView.ViewHolder {

        TextView tvTitle;

        TextView tvBody;

        TextView tvTime;

        ImageButton deleteBtn;

        public NotesHolder(@NonNull View itemView) {
            super(itemView);
                tvTitle = itemView.findViewById(R.id.tv_title);
                tvBody = itemView.findViewById(R.id.tv_body);
                tvTime = itemView.findViewById(R.id.tv_created_at);
                deleteBtn = itemView.findViewById(R.id.delete_btn);
        }
    }
}
