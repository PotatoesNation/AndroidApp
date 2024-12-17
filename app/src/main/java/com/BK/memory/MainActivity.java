package com.BK.memory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    int[] memory;  // Tableau des cartes
    Button[] Btn;  // Tableau des boutons
    boolean[] revealedCards;  // Tableau pour garder une trace des cartes retournées
    int firstCard = -1;  // Index de la première carte retournée
    int secondCard = -1;  // Index de la deuxième carte retournée
    boolean isChecking = false;  // Si on est en train de comparer deux cartes
    int revealedCount = 0;  // Compteur de cartes révélées
    int score = 0;  // Variable de score
    int numtry = 0;

    TextView scoreTextView;  // Référence au TextView du score
    TextView numtryTextView;  // Référence au TextView du numtryTextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        memory = new int[20];
        revealedCards = new boolean[20];  // Initialisation de l'état des cartes (toutes non révélées)

        // Initialiser les valeurs des cartes (10 paires de cartes)
        for (int i = 0; i < 10; i++) {
            memory[i] = i;
            memory[i + 10] = i;  // Créer des paires
        }

        // Mélanger les cartes
        Random random = new Random();
        int nb, oldMemory;
        for (int i = 0; i < 1000; i++) {
            nb = random.nextInt(20);
            oldMemory = memory[nb];
            memory[nb] = memory[3];
            memory[3] = oldMemory;
        }

        setContentView(R.layout.activity_main);

        // Initialiser le TextView pour afficher le score
        scoreTextView = findViewById(R.id.scoreTextView);
        // Initialiser le TextView pour afficher le nombre de try
        numtryTextView = findViewById(R.id.numtryTextView);

        // Initialiser les boutons
        Btn = new Button[20];
        Btn[0] = findViewById(R.id.btn0);
        Btn[1] = findViewById(R.id.btn1);
        Btn[2] = findViewById(R.id.btn2);
        Btn[3] = findViewById(R.id.btn3);
        Btn[4] = findViewById(R.id.btn4);
        Btn[5] = findViewById(R.id.btn5);
        Btn[6] = findViewById(R.id.btn6);
        Btn[7] = findViewById(R.id.btn7);
        Btn[8] = findViewById(R.id.btn8);
        Btn[9] = findViewById(R.id.btn9);
        Btn[10] = findViewById(R.id.btn10);
        Btn[11] = findViewById(R.id.btn11);
        Btn[12] = findViewById(R.id.btn12);
        Btn[13] = findViewById(R.id.btn13);
        Btn[14] = findViewById(R.id.btn14);
        Btn[15] = findViewById(R.id.btn15);
        Btn[16] = findViewById(R.id.btn16);
        Btn[17] = findViewById(R.id.btn17);
        Btn[18] = findViewById(R.id.btn18);
        Btn[19] = findViewById(R.id.btn19);

        // Initialiser les événements click
        for (int i = 0; i < 20; i++) {
            Btn[i].setTag(i);  // Définir un tag pour chaque bouton
            Btn[i].setOnClickListener(this);  // Définir l'écouteur de clic pour chaque bouton
        }
    }

    @Override
    public void onClick(View v) {
        int index = (int) v.getTag();  // Récupérer l'index du bouton cliqué

        // Si la carte est déjà retournée, on ne fait rien
        if (revealedCards[index] || isChecking) return;

        // Retourner la carte (afficher la valeur)
        Btn[index].setText(String.valueOf(memory[index]));  // Afficher la valeur de la carte sur le bouton
        revealedCards[index] = true;  // Marquer cette carte comme retournée

        // Si c'est la première carte retournée
        if (firstCard == -1) {
            firstCard = index;
            return;
        }

        // Si c'est la deuxième carte retournée
        secondCard = index;
        isChecking = true;  // On commence la vérification

        // Vérifier les cartes
        checkCards();
    }

    private void checkCards() {
        // Si les cartes sont égales
        if (memory[firstCard] == memory[secondCard]) {
            // Incrémenter le score
            score += 1;
            numtry +=1;
            // Mettre à jour le TextView du score
            scoreTextView.setText("Score: " + score);
            // Les cartes restent visibles, réinitialiser les variables
            resetCards();
            numtryTextView.setText("Try: " + numtry);


        } else {
            // Si elles ne sont pas égales, on cache les cartes après un délai
            new android.os.Handler().postDelayed(() -> {
                Btn[firstCard].setText("?");  // Cacher la première carte
                Btn[secondCard].setText("?");  // Cacher la deuxième carte
                revealedCards[firstCard] = false;  // Marquer comme non révélée
                revealedCards[secondCard] = false;  // Marquer comme non révélée
                resetCards();  // Réinitialiser l'état pour les prochaines cartes
                numtry +=1;
                numtryTextView.setText("Try: " + numtry);

            }, 1000);  // Délai de 1 seconde
        }
    }

    // Réinitialiser les variables pour vérifier les prochaines cartes
    private void resetCards() {
        firstCard = -1;
        secondCard = -1;
        isChecking = false;
    }
}
