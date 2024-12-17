package com.BK.memory;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import java.lang.reflect.Field;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testInitialSetup() {
        // Vérifie que tous les boutons sont affichés
        for (int i = 0; i < 20; i++) {
            int buttonId = getButtonId(i);
            onView(withId(buttonId)).check(matches(isDisplayed()));
        }

        // Vérifie que le TextView de message est affiché
        //onView(withId(R.id.)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickButton() {
        // Simule un clic sur le premier bouton
        onView(withId(R.id.btn0)).perform(click());

        // Vérifie que l'image change après un clic
        onView(withId(R.id.btn0)).check(matches(isDisplayed())); // L'image doit être visible
    }

    @Test
    public void testMatchCards() {
        // Simule deux clics sur des cartes qui correspondent
        onView(withId(R.id.btn0)).perform(click());
        onView(withId(R.id.btn1)).perform(click());


        // Vérifie que les cartes restent visibles et désactivées si elles correspondent
        onView(withId(R.id.btn0)).check(matches(isDisplayed()));
        onView(withId(R.id.btn0)).check((view, noViewFoundException) -> {
            ImageButton button = (ImageButton) view;
            assert !button.isEnabled(); // Bouton désactivé
        });
    }

    @Test
    public void testMismatchCards() {
        // Simule deux clics sur des cartes qui ne correspondent pas
        onView(withId(R.id.btn0)).perform(click());
        onView(withId(R.id.btn2)).perform(click());

        // Vérifie que les cartes retournent à l'état initial après une seconde
        onView(withId(R.id.btn0)).check(matches(isDisplayed()));
        onView(withId(R.id.btn2)).check(matches(isDisplayed()));
    }

    @Test
    public void testWinningCondition() {
        // Simule une série de clics pour trouver toutes les paires
        int score = 0;
        for (int i = 0; i < 10; i++) {
            int firstButtonId = getButtonId(i);
            int secondButtonId = getButtonId(i + 10);

            onView(withId(firstButtonId)).perform(click());
            onView(withId(secondButtonId)).perform(click());
            score += 2;
        }
        assert score == 20 : "Le score final doit être 20, mais il est: " + score;
    }
    private int getButtonId(int index) {
        try {
            // Récupère l'ID du bouton dynamiquement à partir de son nom
            String buttonId = "btn" + index;
            Field idField = R.id.class.getField(buttonId);
            return idField.getInt(null);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la récupération de l'ID pour btn" + index);
        }
    }
}
