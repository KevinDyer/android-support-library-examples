
package com.l2a.main.cardflip;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.l2a.main.R;
import com.l2a.main.action.FragmentAction;

@SuppressLint("ValidFragment")
public class CardFlipAction extends FragmentAction {
    private boolean mShowingBack = false;

    public CardFlipAction(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getTitle() {
        return "Card Flip";
    }

    @Override
    public CharSequence getSummary() {
        if (mShowingBack) {
            return "Back";
        } else {
            return "Front";

        }
    }

    @Override
    protected Fragment getFragment() {
        return new CardFrontFragment();
    }

    private void flipCard() {
        if (mShowingBack) {
            getFragmentManager().popBackStack();
            return;
        }

        // Flip to the back.
        mShowingBack = true;

        /*
         * Create and commit a new fragment transaction that adds the fragment
         * for the back of the card, uses custom animations, and is part of the
         * fragment manager's back stack.
         */
        getFragmentManager()
                .beginTransaction()

                /*
                 * Replace the default fragment animations with animator
                 * resources representing rotations when switching to the back
                 * of the card, as well as animator resources representing
                 * rotations when flipping back to the front (e.g. when the
                 * system Back button is pressed).
                 */
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)

                /*
                 * Replace any fragments currently in the container view with a
                 * fragment representing the next page (indicated by the
                 * just-incremented currentPage variable).
                 */
                .replace(R.id.content_frame, new CardBackFragment())

                /*
                 * Add this transaction to the back stack, allowing users to
                 * press Back to get to the front of the card.
                 */
                .addToBackStack(null)

                // Commit the transaction.
                .commit();
    }

    public class CardBackFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_back, container, false);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.fragment_card_flip, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            final int id = item.getItemId();

            if (R.id.action_flip == id) {
                flipCard();
            } else {
                return super.onOptionsItemSelected(item);
            }
            return true;
        }
    }

    public class CardFrontFragment extends Fragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_card_front, container, false);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            inflater.inflate(R.menu.fragment_card_flip, menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            final int id = item.getItemId();

            if (R.id.action_flip == id) {
                flipCard();
            } else {
                return super.onOptionsItemSelected(item);
            }
            return true;
        }
    }
}
