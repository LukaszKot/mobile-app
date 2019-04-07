package com.example.k7.koncowy.projekt.projektkoncowy.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.k7.koncowy.projekt.projektkoncowy.R;
import com.example.k7.koncowy.projekt.projektkoncowy.repositories.FileRepository;

public class AlbumsActivity extends AppCompatActivity {

    private ListView list;
    private ImageView add;
    private FileRepository fileRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);
        list = findViewById(R.id.list);
        fileRepository = FileRepository.getInstance();
        displayList();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int folderId, long l) {
                Intent intent = new Intent(AlbumsActivity.this, PhotosActivity.class);
                String[] foldersList = fileRepository.getFolders();
                intent.putExtra("folderName", foldersList[folderId]);
                startActivity(intent);
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int folderId, long l) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Usuwanie folderu");
                alert.setMessage("Czy napewno chcesz usunąć?");
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alert.setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fileRepository.removeFolder(folderId);
                        displayList();
                    }
                });
                alert.show();
                return false;
            }
        });

        add = findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Nowy folder");
                alert.setMessage("Podaj nazwę nowego folderu:");

                final EditText input = new EditText(AlbumsActivity.this);
                input.setText("nowy folder");
                alert.setView(input);

                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                alert.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        fileRepository.createFolder(input.getText().toString());
                        displayList();
                    }
                });
                alert.show();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        displayList();
    }

    private void displayList()
    {
        String[] array = fileRepository.getFolders();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AlbumsActivity.this,
                R.layout.row,
                R.id.text,
                array );
        list.setAdapter(adapter);
    }
}
