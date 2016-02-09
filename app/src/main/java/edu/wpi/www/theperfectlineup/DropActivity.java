package edu.wpi.www.theperfectlineup;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ClipData;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Display;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.Toast;


public class DropActivity extends AppCompatActivity {

    private User mUser = new User("Rowing");//TODO this is a mock user with rowing.

    private Athlete[] mAthletes = new Athlete[]{
        new Athlete("Athlete 1", 21),
                new Athlete("Athlete 2", 24),
                new Athlete("Athlete 3", 21),
                new Athlete("Athlete 4", 27),
                new Athlete("Athlete 5", 18),
                new Athlete("Athlete 6", 17),
                new Athlete("Athlete 7", 15),
                new Athlete("Athlete 8", 19),
                new Athlete("Athlete 9", 17),
                new Athlete("Athlete 10", 17),
                new Athlete("Athlete 11", 20),
                new Athlete("Athlete 12", 22),
                new Athlete("Athlete 13", 21),}; //TODO remove an dreplace with actual database. This is a mock




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drop);
        Display display = getWindowManager().getDefaultDisplay();
        int width = display.getWidth();//This is depereceated
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.leftBracketLinearLayout);
        LinearLayout layout2 = (LinearLayout) findViewById(R.id.rightBracketLinearLayout);
        addAthletes(layout1, mAthletes,width);
        addAthletes(layout2, mAthletes, width);
        addBoat(width);
        TextView temp = new TextView(this);
    }

    public boolean alertUser (String title,String msg) {
        final boolean[] mResult = {false};//default as false
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mResult[0] = true;
                    }})
                .setNegativeButton(android.R.string.no, null).show();
        return mResult[0];
    }

//    This method adds all athletes in a roster to the view.
// It takes in a viewGroup we want to attach to and an array of athletes


    public void addAthletes(ViewGroup view, Athlete[] athleteArr, int width) {
        view.setScrollContainer(true);

        for (int i = 0; i < athleteArr.length; i++) {
            TextView[] viewAthletes = new TextView[athleteArr.length];  //TODO add textView to portrower obj
                        viewAthletes[i] = new TextView(this);     //TODO initialize in portrower object -> Why important
            viewAthletes[i].setText(athleteArr[i].getName());    //TODO change i to name attribute
            viewAthletes[i].setBackgroundResource(R.drawable.portrower);// TODO should eventually be replaced by Icon that is usable in multiple sports
            viewAthletes[i].setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            viewAthletes[i].setWidth((int) (width / 3 * .85));
            viewAthletes[i].setHeight((int) (width / 3 * .85) / 2);
            viewAthletes[i].setMaxLines(2);
            viewAthletes[i].setId(989023490 + i);//needed some unique tag for athletes.
            viewAthletes[i].setOnTouchListener(new ChoiceTouchListener());
            view.addView(viewAthletes[i]);
        }
    }



    public void addBoat(int width)
    {
        LinearLayout layout = (LinearLayout)findViewById(R.id.lineupLinearLayout);
        layout.setScrollContainer(true);
        TextView temp = new TextView(this);
        temp.setText("Cox");
        temp.setTextSize(1, 35);
        temp.setBackgroundResource(R.drawable.cox);
        temp.setWidth((int) (width / 3 * .85));
        temp.setHeight((int) (width / 3 * .85) / 2);
        temp.setOnDragListener(new ChoiceDragListener());
        layout.addView(temp);
        for (int i = 8; i > 0; i--)
        {
            temp = new TextView(this);
            if (i % 2 != 0) {
                temp.setText("" + i);
                temp.setBackgroundResource(R.drawable.starboardrower_shadow);
                temp.setWidth((int) (width / 3 * .85));
                temp.setHeight((int) (width / 3 * .85) / 2);
                temp.setTextSize(1, 35);
                temp.setOnDragListener(new ChoiceDragListener());
                layout.addView(temp);
            } else {
                temp.setText("" + i);
                temp.setBackgroundResource(R.drawable.portrower_shadow);
                temp.setWidth((int) (width / 3 * .85));
                temp.setHeight((int) (width / 3 * .85) / 2);
                temp.setTextSize(1, 35);
                temp.setOnDragListener(new ChoiceDragListener());
                layout.addView(temp);
            }
        }
    }


    private final class ChoiceTouchListener implements OnTouchListener {

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                //start dragging the item touched
                view.startDrag(data, shadowBuilder, view, 0);
                return true;
            }
            else {
                return false;
            }
        }
    }

    private class ChoiceDragListener implements OnDragListener {

        //A helper to reset the view
        private void resetView (View view) {
            view.setAlpha(1);
            view.setEnabled(true);
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            View view = (View) event.getLocalState();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    //no action necessary
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    //no action necessary
                    //grey out oar
                    view.setAlpha((float) 0.5);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    //no action necessary
                    resetView(view);
                    break;
                case DragEvent.ACTION_DROP:
                    //handle the dragged view being dropped over a target view
                    view.setEnabled(false);
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText());
                    dropTarget.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                    //  String temp = dropped.getText().toString();
                    // temp = temp.charAt(temp.length()-1);
                    //  int temp2 = Integer.parseInt(temp);
                    if(dropped.getMaxLines() == 2){
                        dropTarget.setBackgroundResource(R.drawable.portrower);
                    }
                    else {
                        dropTarget.setBackgroundResource(R.drawable.starboardrower);
                    }
                    Display display = getWindowManager().getDefaultDisplay();
                    int width = display.getWidth();
                    dropTarget.setWidth((int) (width / 3 * .85));
                    dropTarget.setHeight((int) (width / 3 * .85) / 2);
                    dropTarget.setPaddingRelative(0, 0, 0, 0);
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tag = dropTarget.getTag();
                    //if there is already an item here, set it back visible in its original place
                    if(tag!=null)
                    {
                        if (alertUser("Warning!","Do you want to replace this Athlete?")) {
                            //the tag is the view id already dropped here
                            int existingID = (Integer) tag;
                            //set the original view visible again
                            View existView = findViewById(existingID);
                            existView.setVisibility(View.VISIBLE);
                            resetView(existView);
                        }
                    }
                    //set the tag in the target view to the ID of the view being dropped
                    Integer test = dropped.getId();
                    dropTarget.setTag(test);

                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    //no action necessary
                    break;
                default:
                    break;
            }

            return true;
        }
    }

}
