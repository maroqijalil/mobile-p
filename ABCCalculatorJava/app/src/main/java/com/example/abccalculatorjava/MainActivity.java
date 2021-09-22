package com.example.abccalculatorjava;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.abccalculatorjava.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mainBHasil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInputValid = true;

                try {
                    if (binding.mainTilNilaiA.getEditText().getText() == null) {
                        binding.mainTilNilaiA.getEditText().setError("Nilai A tidak boleh kosong!");
                        isInputValid = false;
                    } else if (Double.parseDouble(String.valueOf(binding.mainTilNilaiA.getEditText().getText())) == 0.0) {
                        binding.mainTilNilaiA.getEditText().setError("Nilai A tidak boleh 0!");
                        isInputValid = false;
                    }

                    if (binding.mainTilNilaiB.getEditText().getText() == null) {
                        binding.mainTilNilaiB.getEditText().setError("Nilai B tidak boleh kosong!");
                        isInputValid = false;
                    }

                    if (binding.mainTilNilaiC.getEditText().getText() == null) {
                        binding.mainTilNilaiC.getEditText().setError("Nilai C tidak boleh kosong!");
                        isInputValid = false;
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Terjadi kesalahan coba lagi!", Toast.LENGTH_SHORT).show();
                }

                if (isInputValid) {
                    double a = Double.parseDouble(String.valueOf(binding.mainTilNilaiA.getEditText().getText()));
                    double b = Double.parseDouble(String.valueOf(binding.mainTilNilaiB.getEditText().getText()));
                    double c = Double.parseDouble(String.valueOf(binding.mainTilNilaiC.getEditText().getText()));
                    double d = Math.pow(b, 2) - (4 * a * c);

                    if (d < 0) {
                        Toast.makeText(getBaseContext(), "Akar-akar tidak real, masukkan nilai lain!", Toast.LENGTH_SHORT).show();
                    } else {
                        double x1 = -((b + Math.sqrt(d)) / (2* a));
                        double x2 = -((b - Math.sqrt(d)) / (2* a));

                        binding.mainTvHasilX1.setText("x1: " + x1);
                        binding.mainTvHasilX2.setText("x2: " + x2);
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Coba masukkan lagi!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}